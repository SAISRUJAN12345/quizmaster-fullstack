import React from 'react';

export default function HistoryView({ history = [], onClearHistory, onTakeQuiz }) {
  const totalTaken = history.length;
  const avgScore = totalTaken > 0
    ? Math.round((history.reduce((acc, h) => acc + h.percentage, 0) / totalTaken) * 10) / 10
    : 0;
  const passCount = history.filter((h) => h.passed).length;
  const passRate = totalTaken > 0 ? Math.round((passCount / totalTaken) * 100) : 0;

  return (
    <div className="portal-view-container">
      <div className="view-header">
        <h2 className="view-title">📊 Assessment History & Analytics</h2>
        <p className="view-subtitle">
          Track your learning progression and exam records across past quiz sessions.
        </p>
      </div>

      <div className="analytics-summary-grid">
        <div className="analytic-card">
          <div className="analytic-icon">📝</div>
          <div className="analytic-value">{totalTaken}</div>
          <div className="analytic-label">Quizzes Completed</div>
        </div>
        <div className="analytic-card">
          <div className="analytic-icon">📈</div>
          <div className="analytic-value">{avgScore}%</div>
          <div className="analytic-label">Average Accuracy</div>
        </div>
        <div className="analytic-card">
          <div className="analytic-icon">🎖️</div>
          <div className="analytic-value">{passRate}%</div>
          <div className="analytic-label">Pass Rate</div>
        </div>
        <div className="analytic-card">
          <div className="analytic-icon">⚡</div>
          <div className="analytic-value">{passCount}</div>
          <div className="analytic-label">Passed Exams</div>
        </div>
      </div>

      {totalTaken === 0 ? (
        <div className="empty-history-card">
          <div className="empty-icon">📂</div>
          <h3>No past quiz attempts yet</h3>
          <p>Complete any quiz track and your results will be automatically saved here.</p>
          <button type="button" className="btn-primary" onClick={onTakeQuiz}>
            Start Your First Quiz →
          </button>
        </div>
      ) : (
        <div className="history-table-card">
          <div className="history-table-header">
            <h3>Recent Attempts ({totalTaken})</h3>
            <button
              type="button"
              className="btn-clear-history"
              onClick={onClearHistory}
            >
              Clear Records
            </button>
          </div>

          <table className="history-table">
            <thead>
              <tr>
                <th>Date & Time</th>
                <th>Track / Category</th>
                <th>Score</th>
                <th>Accuracy</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              {history.map((item, idx) => (
                <tr key={idx}>
                  <td className="date-cell">{item.timestamp}</td>
                  <td><strong>{item.category}</strong></td>
                  <td>{item.score} / {item.totalQuestions}</td>
                  <td>
                    <span className="acc-tag">{item.percentage}%</span>
                  </td>
                  <td>
                    <span className={`status-badge ${item.passed ? 'status-passed' : 'status-failed'}`}>
                      {item.passed ? '✓ Passed' : '✗ Needs Practice'}
                    </span>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}
