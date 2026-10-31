package com.quiz.dto;

public class AnswerSubmissionDto {
    private Long questionId;
    private Integer selectedOption;

    public AnswerSubmissionDto() {
    }

    public AnswerSubmissionDto(Long questionId, Integer selectedOption) {
        this.questionId = questionId;
        this.selectedOption = selectedOption;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Integer getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(Integer selectedOption) {
        this.selectedOption = selectedOption;
    }
}
