import React, { useState, useEffect } from 'react';
import Navbar from './components/Navbar';
import HomeHero from './components/HomeHero';
import CategoryGrid from './components/CategoryGrid';
import QuestionCard from './components/QuestionCard';
import ProgressBar from './components/ProgressBar';
import ScoreSummary from './components/ScoreSummary';
import QuizTimer from './components/QuizTimer';
import Leaderboard from './components/Leaderboard';
import HistoryView from './components/HistoryView';

export default function App() {
  const [activeTab, setActiveTab] = useState('home');
  const [selectedCategory, setSelectedCategory] = useState('ALL');
  const [darkMode, setDarkMode] = useState(() => {
    return localStorage.getItem('qm_dark_mode') === 'true';
  });

  const [questions, setQuestions] = useState([]);
  const [answers, setAnswers] = useState({});
  const [flaggedQuestions, setFlaggedQuestions] = useState({});
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState(null);
  const [showConfirmModal, setShowConfirmModal] = useState(false);

  // View mode in quiz: 'ALL' (scroll all questions) vs 'STEP' (one question at a time)
  const [viewMode, setViewMode] = useState('ALL');
  const [currentStep, setCurrentStep] = useState(0);

  // Review filter mode: 'ALL', 'CORRECT', 'INCORRECT'
  const [filterMode, setFilterMode] = useState('ALL');

  // Attempt history persisted in localStorage
  const [history, setHistory] = useState(() => {
    try {
      const saved = localStorage.getItem('qm_quiz_history');
      return saved ? JSON.parse(saved) : [];
    } catch {
      return [];
    }
  });

  // Toggle Dark Mode class on <html> / <body>
  useEffect(() => {
    if (darkMode) {
      document.documentElement.classList.add('dark');
      localStorage.setItem('qm_dark_mode', 'true');
    } else {
      document.documentElement.classList.remove('dark');
      localStorage.setItem('qm_dark_mode', 'false');
    }
  }, [darkMode]);

  // Fetch questions for selected category
  const fetchQuestions = async (category = selectedCategory) => {
    setLoading(true);
    setError(null);
    setAnswers({});
    setFlaggedQuestions({});
    setResult(null);
    setCurrentStep(0);

    try {
      const url =
        category && category !== 'ALL'
          ? `/api/quiz/questions?category=${encodeURIComponent(category)}`
          : '/api/quiz/questions';

      const response = await fetch(url);
      if (!response.ok) {
        throw new Error(`Failed to load questions (Status: ${response.status})`);
      }
      const data = await response.json();
      setQuestions(data);
    } catch (err) {
      console.error('Error fetching questions:', err);
      setError(
        'Could not connect to the backend server. Please verify that the Spring Boot API is running on port 8081.'
      );
    } finally {
      setLoading(false);
    }
  };

  // Start a specific category quiz
  const handleSelectCategory = (cat) => {
    setSelectedCategory(cat);
    setActiveTab('quiz');
    fetchQuestions(cat);
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  // Select an option for a question
  const handleSelectOption = (questionId, optionIndex) => {
    setAnswers((prev) => ({
      ...prev,
      [questionId]: optionIndex,
    }));
  };

  // Toggle flagged question
  const handleToggleFlag = (questionId) => {
    setFlaggedQuestions((prev) => ({
      ...prev,
      [questionId]: !prev[questionId],
    }));
  };

  // Check unanswered before submitting
  const handleSubmitAttempt = (e) => {
    if (e) e.preventDefault();

    const unansweredCount = questions.length - Object.keys(answers).length;
    if (unansweredCount > 0) {
      setShowConfirmModal(true);
    } else {
      executeSubmit();
    }
  };

  // Send answers to Spring Boot backend
  const executeSubmit = async () => {
    setShowConfirmModal(false);
    setSubmitting(true);
    setError(null);

    const payload = {
      answers: questions.map((q) => ({
        questionId: q.id,
        selectedOption: answers[q.id] !== undefined ? answers[q.id] : null,
      })),
    };

    try {
      const response = await fetch('/api/quiz/submit', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(payload),
      });

      if (!response.ok) {
        throw new Error(`Failed to submit quiz (Status: ${response.status})`);
      }

      const resultData = await response.json();
      setResult(resultData);

      // Save to attempt history
      const newRecord = {
        timestamp: new Date().toLocaleString(),
        category: selectedCategory === 'ALL' ? 'Full-Stack Challenge' : selectedCategory,
        score: resultData.score,
        totalQuestions: resultData.totalQuestions,
        percentage: resultData.percentage,
        passed: resultData.passed,
      };

      const updatedHistory = [newRecord, ...history];
      setHistory(updatedHistory);
      localStorage.setItem('qm_quiz_history', JSON.stringify(updatedHistory));

      window.scrollTo({ top: 0, behavior: 'smooth' });
    } catch (err) {
      console.error('Error submitting quiz:', err);
      setError('Failed to submit answers. Please try again.');
    } finally {
      setSubmitting(false);
    }
  };

  const handleRetakeQuiz = () => {
    setAnswers({});
    setFlaggedQuestions({});
    setResult(null);
    setCurrentStep(0);
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  const handleClearHistory = () => {
    setHistory([]);
    localStorage.removeItem('qm_quiz_history');
  };

  const answeredCount = Object.keys(answers).length;
  const unansweredCount = questions.length - answeredCount;

  // Filtered questions for post-submission review
  const getVisibleQuestions = () => {
    if (!result) return questions;
    if (filterMode === 'CORRECT') {
      return questions.filter((q) => {
        const r = result.results?.find((item) => item.questionId === q.id);
        return r?.isCorrect;
      });
    }
    if (filterMode === 'INCORRECT') {
      return questions.filter((q) => {
        const r = result.results?.find((item) => item.questionId === q.id);
        return !r?.isCorrect;
      });
    }
    return questions;
  };

  const visibleQuestions = getVisibleQuestions();

  return (
    <div className="portal-layout">
      {/* Navigation Bar */}
      <Navbar
        activeTab={activeTab}
        setActiveTab={setActiveTab}
        darkMode={darkMode}
        setDarkMode={setDarkMode}
      />

      {/* Main Container */}
      <main className="portal-main">
        {/* --- TAB 1: HOME & TRACKS --- */}
        {activeTab === 'home' && (
          <div className="tab-home-fade">
            <HomeHero onSelectCategory={handleSelectCategory} />
            <CategoryGrid
              onSelectCategory={handleSelectCategory}
              selectedCategory={selectedCategory}
            />
          </div>
        )}

        {/* --- TAB 2: ACTIVE QUIZ --- */}
        {activeTab === 'quiz' && (
          <div className="tab-quiz-fade">
            {/* Quiz Sub-header Banner */}
            <div className="quiz-header-banner">
              <div className="banner-left">
                <span className="track-indicator">
                  Active Track: <strong>{selectedCategory === 'ALL' ? 'Full-Stack Challenge' : selectedCategory}</strong>
                </span>
                <button
                  type="button"
                  className="btn-switch-track-text"
                  onClick={() => setActiveTab('home')}
                >
                  Change Track
                </button>
              </div>

              <div className="banner-right">
                {!result && questions.length > 0 && (
                  <QuizTimer
                    initialMinutes={selectedCategory === 'ALL' ? 10 : 5}
                    onExpire={executeSubmit}
                    isPaused={submitting || !!result}
                  />
                )}
                {!result && questions.length > 0 && (
                  <div className="view-toggle">
                    <button
                      type="button"
                      className={`toggle-opt ${viewMode === 'ALL' ? 'active' : ''}`}
                      onClick={() => setViewMode('ALL')}
                      title="Show all questions in one page"
                    >
                      All
                    </button>
                    <button
                      type="button"
                      className={`toggle-opt ${viewMode === 'STEP' ? 'active' : ''}`}
                      onClick={() => setViewMode('STEP')}
                      title="Show one question at a time"
                    >
                      Step
                    </button>
                  </div>
                )}
              </div>
            </div>

            {loading && (
              <div className="state-card loading-card">
                <div className="spinner"></div>
                <p className="state-text">Loading {selectedCategory} questions from Spring Boot server...</p>
              </div>
            )}

            {error && (
              <div className="state-card error-card">
                <div className="error-icon">⚠️</div>
                <h3 className="error-title">Connection Error</h3>
                <p className="error-desc">{error}</p>
                <button
                  type="button"
                  className="btn-retry"
                  onClick={() => fetchQuestions(selectedCategory)}
                >
                  Try Again
                </button>
              </div>
            )}

            {!loading && !error && questions.length === 0 && (
              <div className="state-card empty-card">
                <p className="state-text">No questions found for this track.</p>
                <button
                  type="button"
                  className="btn-primary"
                  onClick={() => handleSelectCategory('ALL')}
                >
                  Load All Questions
                </button>
              </div>
            )}

            {!loading && !error && questions.length > 0 && (
              <div className="quiz-flow">
                {/* Score Summary if submitted */}
                {result && (
                  <ScoreSummary
                    result={result}
                    category={selectedCategory === 'ALL' ? 'Full-Stack Challenge' : selectedCategory}
                    onRetake={handleRetakeQuiz}
                    onSelectAnotherTrack={() => setActiveTab('home')}
                    filterMode={filterMode}
                    setFilterMode={setFilterMode}
                  />
                )}

                {/* Progress bar with question jumping */}
                <ProgressBar
                  questions={questions}
                  answers={answers}
                  flaggedQuestions={flaggedQuestions}
                  results={result}
                  currentQuestionIndex={currentStep}
                  onJumpToQuestion={(idx) => setCurrentStep(idx)}
                />

                {/* Question Cards */}
                <form onSubmit={handleSubmitAttempt} className="questions-form">
                  <div className="questions-list">
                    {viewMode === 'ALL' || result ? (
                      // Display all visible questions
                      visibleQuestions.map((q, idx) => {
                        const qResult = result?.results?.find(
                          (r) => r.questionId === q.id
                        );
                        return (
                          <QuestionCard
                            key={q.id}
                            question={q}
                            index={idx}
                            total={questions.length}
                            selectedOption={answers[q.id]}
                            onSelectOption={handleSelectOption}
                            isFlagged={!!flaggedQuestions[q.id]}
                            onToggleFlag={handleToggleFlag}
                            result={qResult}
                          />
                        );
                      })
                    ) : (
                      // Single Question Step Mode
                      <div className="single-step-view">
                        <QuestionCard
                          question={questions[currentStep]}
                          index={currentStep}
                          total={questions.length}
                          selectedOption={answers[questions[currentStep].id]}
                          onSelectOption={handleSelectOption}
                          isFlagged={!!flaggedQuestions[questions[currentStep].id]}
                          onToggleFlag={handleToggleFlag}
                          result={null}
                        />

                        <div className="step-navigation-bar">
                          <button
                            type="button"
                            className="btn-step-nav"
                            disabled={currentStep === 0}
                            onClick={() => setCurrentStep((prev) => Math.max(0, prev - 1))}
                          >
                            ← Previous
                          </button>
                          <span className="step-counter">
                            Question {currentStep + 1} of {questions.length}
                          </span>
                          <button
                            type="button"
                            className="btn-step-nav"
                            disabled={currentStep === questions.length - 1}
                            onClick={() => setCurrentStep((prev) => Math.min(questions.length - 1, prev + 1))}
                          >
                            Next →
                          </button>
                        </div>
                      </div>
                    )}
                  </div>

                  {/* Submit Quiz Action Banner */}
                  {!result && (
                    <div className="submit-bar">
                      <div className="submit-info">
                        <strong>{answeredCount}</strong> of <strong>{questions.length}</strong> questions answered
                        {unansweredCount > 0 && (
                          <span className="unanswered-warning">
                            ({unansweredCount} remaining)
                          </span>
                        )}
                      </div>
                      <button
                        type="submit"
                        className="btn-submit-quiz"
                        disabled={submitting}
                      >
                        {submitting ? 'Submitting...' : 'Submit Quiz 🚀'}
                      </button>
                    </div>
                  )}
                </form>
              </div>
            )}
          </div>
        )}

        {/* --- TAB 3: LEADERBOARD --- */}
        {activeTab === 'leaderboard' && (
          <Leaderboard
            recentAttempt={history[0]}
            onTakeQuiz={() => {
              setActiveTab('quiz');
              if (questions.length === 0) fetchQuestions('ALL');
            }}
          />
        )}

        {/* --- TAB 4: MY HISTORY --- */}
        {activeTab === 'history' && (
          <HistoryView
            history={history}
            onClearHistory={handleClearHistory}
            onTakeQuiz={() => {
              setActiveTab('quiz');
              if (questions.length === 0) fetchQuestions('ALL');
            }}
          />
        )}
      </main>

      {/* Confirmation Modal for Unanswered Questions */}
      {showConfirmModal && (
        <div className="modal-overlay" onClick={() => setShowConfirmModal(false)}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <div className="modal-header">
              <span className="modal-icon">⚠️</span>
              <h3>Unanswered Questions</h3>
            </div>
            <p className="modal-text">
              You still have <strong>{unansweredCount}</strong> unanswered question
              {unansweredCount > 1 ? 's' : ''}. Unanswered questions will receive 0 points.
            </p>
            <p className="modal-question">Are you sure you want to submit your quiz now?</p>
            <div className="modal-actions">
              <button
                type="button"
                className="btn-secondary"
                onClick={() => setShowConfirmModal(false)}
              >
                Review Quiz
              </button>
              <button
                type="button"
                className="btn-primary"
                onClick={executeSubmit}
              >
                Yes, Submit Anyway
              </button>
            </div>
          </div>
        </div>
      )}

      {/* Footer */}
      <footer className="portal-footer">
        <div className="footer-content">
          <div className="footer-left">
            <strong>QuizMaster Academy</strong> • Full-Stack Java Spring Boot & React Application
          </div>
          <div className="footer-right">
            <span>Server: Spring Boot 3.3.5 (Port 8081)</span>
            <span>Database: In-Memory H2</span>
          </div>
        </div>
      </footer>
    </div>
  );
}
