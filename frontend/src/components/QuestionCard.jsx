import React from 'react';

export default function QuestionCard({
  question,
  index,
  total,
  selectedOption,
  onSelectOption,
  isFlagged,
  onToggleFlag,
  result, // null when quiz is in progress; present after submission
}) {
  const isSubmitted = !!result;
  const isCorrect = result?.isCorrect;

  return (
    <div
      className={`question-card ${
        isSubmitted ? (isCorrect ? 'card-correct' : 'card-incorrect') : ''
      }`}
      id={`question-${question.id}`}
    >
      <div className="question-card-header">
        <div className="header-meta-left">
          <span className="question-badge">Question {index + 1} of {total}</span>
          {question.category && (
            <span className="category-tag">{question.category}</span>
          )}
          {question.difficulty && (
            <span className={`difficulty-tag diff-${question.difficulty.toLowerCase()}`}>
              {question.difficulty}
            </span>
          )}
        </div>

        <div className="header-meta-right">
          {!isSubmitted && (
            <button
              type="button"
              className={`flag-btn ${isFlagged ? 'flagged' : ''}`}
              onClick={() => onToggleFlag(question.id)}
              title={isFlagged ? 'Unflag question' : 'Flag for review'}
            >
              {isFlagged ? '🚩 Flagged' : '🏳️ Flag'}
            </button>
          )}

          {isSubmitted && (
            <span className={`result-tag ${isCorrect ? 'tag-correct' : 'tag-incorrect'}`}>
              {isCorrect ? '✓ Correct (+1)' : '✗ Incorrect (0)'}
            </span>
          )}
        </div>
      </div>

      <h3 className="question-title">{question.questionText}</h3>

      <div className="options-list">
        {question.options.map((optionText, optIdx) => {
          const isSelected = selectedOption === optIdx;
          let optionClass = 'option-item';

          if (!isSubmitted) {
            if (isSelected) optionClass += ' selected';
          } else {
            // Post-submission styling
            const isThisCorrectAnswer = result.correctOption === optIdx;
            if (isThisCorrectAnswer) {
              optionClass += ' correct-answer';
            } else if (isSelected && !isThisCorrectAnswer) {
              optionClass += ' user-wrong-answer';
            } else {
              optionClass += ' disabled-neutral';
            }
          }

          const optionLetter = String.fromCharCode(65 + optIdx); // A, B, C, D

          return (
            <label
              key={optIdx}
              className={optionClass}
              onClick={() => {
                if (!isSubmitted) {
                  onSelectOption(question.id, optIdx);
                }
              }}
            >
              <input
                type="radio"
                name={`question-${question.id}`}
                value={optIdx}
                checked={isSelected}
                onChange={() => {
                  if (!isSubmitted) {
                    onSelectOption(question.id, optIdx);
                  }
                }}
                disabled={isSubmitted}
                className="option-radio-input"
              />
              <span className="option-letter">{optionLetter}</span>
              <span className="option-text">{optionText}</span>

              {isSubmitted && result.correctOption === optIdx && (
                <span className="status-pill correct-pill">✓ Correct Answer</span>
              )}
              {isSubmitted && isSelected && !isCorrect && (
                <span className="status-pill wrong-pill">✗ Your Choice</span>
              )}
            </label>
          );
        })}
      </div>

      {isSubmitted && result && (
        <div className="explanation-box">
          <div className="explanation-header">
            <span className="explanation-icon">💡</span>
            <strong>Pedagogical Explanation:</strong>
          </div>
          <p className="explanation-text">{result.explanation}</p>
        </div>
      )}
    </div>
  );
}
