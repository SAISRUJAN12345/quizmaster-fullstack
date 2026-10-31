import React from 'react';

export default function ProgressBar({
  questions,
  answers,
  flaggedQuestions = {},
  results,
  currentQuestionIndex,
  onJumpToQuestion,
}) {
  const total = questions.length;
  const answeredCount = Object.keys(answers).length;
  const percentage = total > 0 ? Math.round((answeredCount / total) * 100) : 0;

  const scrollToQuestion = (qId, idx) => {
    if (onJumpToQuestion) {
      onJumpToQuestion(idx);
    }
    const el = document.getElementById(`question-${qId}`);
    if (el) {
      el.scrollIntoView({ behavior: 'smooth', block: 'center' });
    }
  };

  return (
    <div className="progress-container">
      <div className="progress-top-row">
        <div>
          <span className="progress-label">Quiz Completion</span>
          <span className="progress-counter">
            {answeredCount} of {total} answered ({percentage}%)
          </span>
        </div>
        <div className="progress-legend">
          <span className="legend-item"><span className="legend-dot dot-answered"></span> Answered</span>
          <span className="legend-item"><span className="legend-dot dot-flagged"></span> Flagged</span>
          <span className="legend-item"><span className="legend-dot dot-empty"></span> Pending</span>
        </div>
      </div>

      <div className="progress-bar-bg">
        <div
          className="progress-bar-fill"
          style={{ width: `${percentage}%` }}
        ></div>
      </div>

      <div className="question-pills">
        {questions.map((q, idx) => {
          const isAnswered = answers[q.id] !== undefined;
          const isFlagged = !!flaggedQuestions[q.id];
          const isActive = currentQuestionIndex === idx;

          let pillClass = 'q-pill';

          if (results) {
            // After submission
            const qResult = results.results?.find((r) => r.questionId === q.id);
            if (qResult?.isCorrect) {
              pillClass += ' pill-correct';
            } else {
              pillClass += ' pill-wrong';
            }
          } else {
            // During quiz
            if (isAnswered) pillClass += ' pill-answered';
            if (isFlagged) pillClass += ' pill-flagged';
            if (isActive) pillClass += ' pill-active';
          }

          return (
            <button
              key={q.id}
              type="button"
              className={pillClass}
              onClick={() => scrollToQuestion(q.id, idx)}
              title={`Jump to Question ${idx + 1}${isFlagged ? ' (Flagged)' : ''}`}
            >
              Q{idx + 1}
              {isFlagged && !results && <span className="flag-dot"></span>}
            </button>
          );
        })}
      </div>
    </div>
  );
}
