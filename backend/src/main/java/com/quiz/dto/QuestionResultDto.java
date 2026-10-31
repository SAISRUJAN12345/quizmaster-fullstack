package com.quiz.dto;

import java.util.List;

public class QuestionResultDto {
    private Long questionId;
    private String questionText;
    private List<String> options;
    private Integer selectedOption;
    private int correctOption;
    private boolean isCorrect;
    private String selectedOptionText;
    private String correctOptionText;
    private String explanation;
    private String category;
    private String difficulty;

    public QuestionResultDto() {
    }

    public QuestionResultDto(Long questionId, String questionText, List<String> options,
                             Integer selectedOption, int correctOption, boolean isCorrect,
                             String selectedOptionText, String correctOptionText, String explanation,
                             String category, String difficulty) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.options = options;
        this.selectedOption = selectedOption;
        this.correctOption = correctOption;
        this.isCorrect = isCorrect;
        this.selectedOptionText = selectedOptionText;
        this.correctOptionText = correctOptionText;
        this.explanation = explanation;
        this.category = category;
        this.difficulty = difficulty;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
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

    public Integer getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(Integer selectedOption) {
        this.selectedOption = selectedOption;
    }

    public int getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(int correctOption) {
        this.correctOption = correctOption;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public String getSelectedOptionText() {
        return selectedOptionText;
    }

    public void setSelectedOptionText(String selectedOptionText) {
        this.selectedOptionText = selectedOptionText;
    }

    public String getCorrectOptionText() {
        return correctOptionText;
    }

    public void setCorrectOptionText(String correctOptionText) {
        this.correctOptionText = correctOptionText;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
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
