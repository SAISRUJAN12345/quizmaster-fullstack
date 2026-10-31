import React from 'react';

export default function Navbar({ activeTab, setActiveTab, darkMode, setDarkMode }) {
  return (
    <header className="portal-navbar">
      <div className="navbar-inner">
        <div className="brand-group" onClick={() => setActiveTab('home')} role="button" tabIndex={0}>
          <div className="brand-icon-wrapper">🎓</div>
          <div>
            <h1 className="brand-name">QuizMaster<span className="brand-accent">.io</span></h1>
            <p className="brand-sub">Software Engineering Knowledge Portal</p>
          </div>
        </div>

        <nav className="nav-links">
          <button
            type="button"
            className={`nav-btn ${activeTab === 'home' ? 'active' : ''}`}
            onClick={() => setActiveTab('home')}
          >
            <span className="nav-icon">🏠</span> Tracks & Home
          </button>
          <button
            type="button"
            className={`nav-btn ${activeTab === 'quiz' ? 'active' : ''}`}
            onClick={() => setActiveTab('quiz')}
          >
            <span className="nav-icon">✍️</span> Take Quiz
          </button>
          <button
            type="button"
            className={`nav-btn ${activeTab === 'leaderboard' ? 'active' : ''}`}
            onClick={() => setActiveTab('leaderboard')}
          >
            <span className="nav-icon">🏆</span> Leaderboard
          </button>
          <button
            type="button"
            className={`nav-btn ${activeTab === 'history' ? 'active' : ''}`}
            onClick={() => setActiveTab('history')}
          >
            <span className="nav-icon">📊</span> My History
          </button>
        </nav>

        <div className="navbar-actions">
          <div className="system-pill" title="Connected to Java Spring Boot REST API on port 8081">
            <span className="live-dot"></span> Spring Boot 3.3
          </div>
          <button
            type="button"
            className="theme-toggle-btn"
            onClick={() => setDarkMode(!darkMode)}
            title={`Switch to ${darkMode ? 'Light' : 'Dark'} mode`}
          >
            {darkMode ? '☀️' : '🌙'}
          </button>
        </div>
      </div>
    </header>
  );
}
