package com.quiz.dto;

import java.util.List;

/**
 * Question data sent to the client during quiz taking.
 * IMPORTANT: Correct answers and explanations are omitted to prevent cheating before submission.
 */
public class QuestionResponseDto {
    private Long id;
    private String questionText;
    private List<String> options;
    private String category;
    private String difficulty;

    public QuestionResponseDto() {
    }

    public QuestionResponseDto(Long id, String questionText, List<String> options, String category, String difficulty) {
        this.id = id;
        this.questionText = questionText;
        this.options = options;
        this.category = category;
        this.difficulty = difficulty;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
