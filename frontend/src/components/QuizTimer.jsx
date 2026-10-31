import React, { useState, useEffect } from 'react';

export default function QuizTimer({ initialMinutes = 5, onExpire, isPaused }) {
  const [secondsLeft, setSecondsLeft] = useState(initialMinutes * 60);

  useEffect(() => {
    setSecondsLeft(initialMinutes * 60);
  }, [initialMinutes]);

  useEffect(() => {
    if (isPaused) return;

    if (secondsLeft <= 0) {
      if (onExpire) onExpire();
      return;
    }

    const interval = setInterval(() => {
      setSecondsLeft((prev) => {
        if (prev <= 1) {
          clearInterval(interval);
          if (onExpire) onExpire();
          return 0;
        }
        return prev - 1;
      });
    }, 1000);

    return () => clearInterval(interval);
  }, [secondsLeft, isPaused, onExpire]);

  const minutes = Math.floor(secondsLeft / 60);
  const seconds = secondsLeft % 60;
  const formattedTime = `${String(minutes).padStart(2, '0')}:${String(seconds).padStart(2, '0')}`;

  const isLowTime = secondsLeft <= 60;
  const isCriticalTime = secondsLeft <= 30;

  return (
    <div
      className={`timer-pill ${isCriticalTime ? 'timer-critical' : isLowTime ? 'timer-warning' : ''}`}
      title="Time remaining"
    >
      <span className="timer-icon">⏱️</span>
      <span className="timer-text">{formattedTime}</span>
    </div>
  );
}
