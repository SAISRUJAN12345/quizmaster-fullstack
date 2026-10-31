import React from 'react';

export default function ScoreSummary({
  result,
  category,
  onRetake,
  onSelectAnotherTrack,
  filterMode,
  setFilterMode,
}) {
  if (!result) return null;

  const { score, totalQuestions, percentage, passed, feedbackMessage } = result;
  const incorrectCount = totalQuestions - score;

  return (
    <div className={`score-summary-card ${passed ? 'summary-passed' : 'summary-failed'}`}>
      <div className="summary-badge-row">
        <span className={`pass-fail-badge ${passed ? 'badge-pass' : 'badge-fail'}`}>
          {passed ? '🎉 Passed & Certified' : '📚 Needs Practice'}
        </span>
        <span className="summary-category-pill">Track: {category}</span>
      </div>

      <h2 className="summary-title">Performance Summary</h2>
      <p className="summary-feedback">{feedbackMessage}</p>

      <div className="score-display">
        <div className="score-circle">
          <span className="score-number">{score}</span>
          <span className="score-divider">/</span>
          <span className="score-total">{totalQuestions}</span>
        </div>
        <div className="percentage-display">{percentage}%</div>
      </div>

      <div className="stats-grid">
        <div className="stat-card stat-correct">
          <div className="stat-icon">✓</div>
          <div className="stat-value">{score}</div>
          <div className="stat-label">Correct Answers</div>
        </div>
        <div className="stat-card stat-incorrect">
          <div className="stat-icon">✗</div>
          <div className="stat-value">{incorrectCount}</div>
          <div className="stat-label">Incorrect Answers</div>
        </div>
        <div className="stat-card stat-total">
          <div className="stat-icon">📋</div>
          <div className="stat-value">{totalQuestions}</div>
          <div className="stat-label">Total Questions</div>
        </div>
      </div>

      {/* Review Filters */}
      <div className="review-filters-bar">
        <span className="filter-title">Filter Solutions:</span>
        <div className="filter-btn-group">
          <button
            type="button"
            className={`filter-btn ${filterMode === 'ALL' ? 'active' : ''}`}
            onClick={() => setFilterMode('ALL')}
          >
            All Questions ({totalQuestions})
          </button>
          <button
            type="button"
            className={`filter-btn ${filterMode === 'CORRECT' ? 'active' : ''}`}
            onClick={() => setFilterMode('CORRECT')}
          >
            Correct ({score})
          </button>
          <button
            type="button"
            className={`filter-btn ${filterMode === 'INCORRECT' ? 'active' : ''}`}
            onClick={() => setFilterMode('INCORRECT')}
          >
            Incorrect ({incorrectCount})
          </button>
        </div>
      </div>

      <div className="summary-actions">
        <button
          type="button"
          onClick={onRetake}
          className="btn-retake"
        >
          🔄 Retake Quiz
        </button>
        <button
          type="button"
          onClick={onSelectAnotherTrack}
          className="btn-change-track"
        >
          📑 Browse Other Tracks
        </button>
      </div>
    </div>
  );
}
