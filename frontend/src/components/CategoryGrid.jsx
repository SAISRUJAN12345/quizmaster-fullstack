import React from 'react';

const TRACKS = [
  {
    id: 'Java & Spring',
    title: 'Java & Spring Boot Mastery',
    icon: '☕',
    badge: 'Backend Core',
    color: '#f59e0b',
    questionsCount: 4,
    estimatedMinutes: 5,
    difficulty: 'Intermediate',
    description: 'Core collections, JVM runtime internals, Spring annotations, and Spring Data JPA entity mappings.',
    topics: ['LinkedHashSet', 'JVM Architecture', '@RestController', '@Id Key Generation'],
  },
  {
    id: 'Frontend & Web',
    title: 'Modern React & Web UI',
    icon: '⚛️',
    badge: 'Frontend Core',
    color: '#06b6d4',
    questionsCount: 4,
    estimatedMinutes: 5,
    difficulty: 'Beginner - Intermediate',
    description: 'React state hooks, component side-effects, Virtual DOM reconciliation, and modern CSS Flexbox layout.',
    topics: ['useState', 'useEffect Lifecycle', 'Flexbox Model', 'Virtual DOM Diffing'],
  },
  {
    id: 'Databases & Architecture',
    title: 'Databases & REST Architecture',
    icon: '💾',
    badge: 'System Design',
    color: '#8b5cf6',
    questionsCount: 4,
    estimatedMinutes: 5,
    difficulty: 'Intermediate - Hard',
    description: 'Relational ACID principles, in-memory testing with H2, HTTP status standards, and idempotent API methods.',
    topics: ['ACID Atomicity', 'H2 Integration', 'HTTP 201 Created', 'Idempotent Methods'],
  },
  {
    id: 'ALL',
    title: 'Full-Stack Developer Challenge',
    icon: '🏆',
    badge: 'All-in-One Exam',
    color: '#4f46e5',
    questionsCount: 12,
    estimatedMinutes: 10,
    difficulty: 'Comprehensive',
    description: 'The ultimate assessment spanning backend engineering, frontend React dynamics, and database architecture.',
    topics: ['Java & Spring', 'React & CSS', 'Databases & REST', 'Full Evaluation'],
  },
];

export default function CategoryGrid({ onSelectCategory, selectedCategory }) {
  return (
    <section className="tracks-section">
      <div className="section-header">
        <h2 className="section-title">Select Your Learning Track</h2>
        <p className="section-subtitle">
          Pick a focused module or take the full test. Your score will be calculated dynamically by the server.
        </p>
      </div>

      <div className="tracks-grid">
        {TRACKS.map((track) => {
          const isCurrent = selectedCategory === track.id;
          return (
            <div
              key={track.id}
              className={`track-card ${isCurrent ? 'track-card-active' : ''}`}
            >
              <div className="track-card-top">
                <span className="track-icon">{track.icon}</span>
                <span className="track-badge" style={{ backgroundColor: `${track.color}15`, color: track.color }}>
                  {track.badge}
                </span>
              </div>

              <h3 className="track-title">{track.title}</h3>
              <p className="track-description">{track.description}</p>

              <div className="track-meta">
                <span className="meta-pill">📋 {track.questionsCount} Questions</span>
                <span className="meta-pill">⏱️ ~{track.estimatedMinutes} mins</span>
                <span className="meta-pill">🎯 {track.difficulty}</span>
              </div>

              <div className="track-tags">
                {track.topics.map((t, idx) => (
                  <span key={idx} className="topic-tag">#{t}</span>
                ))}
              </div>

              <button
                type="button"
                className="btn-start-track"
                onClick={() => onSelectCategory(track.id)}
              >
                Start Quiz →
              </button>
            </div>
          );
        })}
      </div>
    </section>
  );
}
