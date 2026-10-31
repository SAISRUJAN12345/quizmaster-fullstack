import React from 'react';

const STATIC_LEADERS = [
  { id: 1, name: 'Alex Rivera', avatar: '👨‍💻', track: 'Full-Stack Developer Challenge', score: '12/12', percentage: 100, time: '04:12', badge: '🥇 1st Place' },
  { id: 2, name: 'Sophia Chen', avatar: '👩‍🔬', track: 'Java & Spring Boot Mastery', score: '4/4', percentage: 100, time: '01:45', badge: '🥈 2nd Place' },
  { id: 3, name: 'Devon Vance', avatar: '👨‍🎨', track: 'Modern React & Web UI', score: '4/4', percentage: 100, time: '02:08', badge: '🥉 3rd Place' },
  { id: 4, name: 'Priya Sharma', avatar: '👩‍💻', track: 'Databases & REST Architecture', score: '4/4', percentage: 100, time: '02:22', badge: 'Top 5' },
  { id: 5, name: 'Lucas Meyer', avatar: '🧑‍💼', track: 'Full-Stack Developer Challenge', score: '11/12', percentage: 91.7, time: '05:40', badge: 'Top 5' },
  { id: 6, name: 'Emma Wilson', avatar: '👩‍🏫', track: 'Java & Spring Boot Mastery', score: '3/4', percentage: 75.0, time: '02:50', badge: 'Honor Roll' },
];

export default function Leaderboard({ recentAttempt, onTakeQuiz }) {
  // If the user recently passed a quiz, insert their entry into the leaderboard!
  const userEntry = recentAttempt ? {
    id: 999,
    name: 'You (Current Student)',
    avatar: '🎓',
    track: recentAttempt.category,
    score: `${recentAttempt.score}/${recentAttempt.totalQuestions}`,
    percentage: recentAttempt.percentage,
    time: recentAttempt.timeSpent || '03:15',
    badge: recentAttempt.passed ? '⭐ Verified Score' : '📝 Attempted',
    isUser: true,
  } : null;

  const allEntries = userEntry ? [userEntry, ...STATIC_LEADERS] : STATIC_LEADERS;

  return (
    <div className="portal-view-container">
      <div className="view-header">
        <h2 className="view-title">🏆 Academy Student Leaderboard</h2>
        <p className="view-subtitle">
          Recognizing top performers across Spring Boot, React, and Architecture assessments.
        </p>
      </div>

      <div className="leaderboard-table-card">
        <table className="leaderboard-table">
          <thead>
            <tr>
              <th>Rank</th>
              <th>Student</th>
              <th>Assessment Track</th>
              <th>Score</th>
              <th>Accuracy</th>
              <th>Time</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {allEntries.map((student, idx) => (
              <tr key={student.id} className={student.isUser ? 'user-highlight-row' : ''}>
                <td className="rank-cell">
                  {idx === 0 ? '🥇' : idx === 1 ? '🥈' : idx === 2 ? '🥉' : `#${idx + 1}`}
                </td>
                <td className="student-cell">
                  <span className="student-avatar">{student.avatar}</span>
                  <div>
                    <strong className="student-name">{student.name}</strong>
                    {student.isUser && <span className="you-pill">You</span>}
                  </div>
                </td>
                <td><span className="track-tag-table">{student.track}</span></td>
                <td><strong>{student.score}</strong></td>
                <td>
                  <div className="accuracy-cell">
                    <span className="acc-text">{student.percentage}%</span>
                    <div className="mini-bar">
                      <div className="mini-bar-fill" style={{ width: `${student.percentage}%` }}></div>
                    </div>
                  </div>
                </td>
                <td className="time-cell">{student.time}</td>
                <td><span className="badge-pill">{student.badge}</span></td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <div className="leaderboard-footer">
        <p>Want to see your name higher on the leaderboard?</p>
        <button type="button" className="btn-primary" onClick={onTakeQuiz}>
          Take an Assessment Now 🚀
        </button>
      </div>
    </div>
  );
}
