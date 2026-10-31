import React from 'react';

export default function HomeHero({ onSelectCategory }) {
  return (
    <section className="hero-banner">
      <div className="hero-content">
        <div className="hero-badge">⚡ Real-time Evaluation & Backend Secured</div>
        <h1 className="hero-title">
          Master Modern Tech Stacks with <span className="highlight-gradient">Interactive Quizzes</span>
        </h1>
        <p className="hero-description">
          Test your knowledge in Java, Spring Boot, React, and Database Architecture.
          Every submission is evaluated on our Spring Boot REST server with instant pedagogical feedback.
        </p>

        <div className="hero-actions">
          <button
            type="button"
            className="hero-primary-btn"
            onClick={() => onSelectCategory('ALL')}
          >
            🚀 Launch Comprehensive Exam (All Topics)
          </button>
        </div>

        <div className="hero-stats">
          <div className="stat-item">
            <span className="stat-number">12</span>
            <span className="stat-caption">Curated Questions</span>
          </div>
          <div className="stat-divider"></div>
          <div className="stat-item">
            <span className="stat-number">3</span>
            <span className="stat-caption">Specialized Tracks</span>
          </div>
          <div className="stat-divider"></div>
          <div className="stat-item">
            <span className="stat-number">100%</span>
            <span className="stat-caption">Server Verified</span>
          </div>
          <div className="stat-divider"></div>
          <div className="stat-item">
            <span className="stat-number">H2 DB</span>
            <span className="stat-caption">JPA Data Storage</span>
          </div>
        </div>
      </div>
    </section>
  );
}
