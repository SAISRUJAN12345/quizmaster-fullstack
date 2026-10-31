package com.quiz.dto;

import java.util.ArrayList;
import java.util.List;

public class QuizSubmissionDto {
    private List<AnswerSubmissionDto> answers = new ArrayList<>();

    public QuizSubmissionDto() {
    }

    public QuizSubmissionDto(List<AnswerSubmissionDto> answers) {
        this.answers = answers;
    }

    public List<AnswerSubmissionDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerSubmissionDto> answers) {
        this.answers = answers;
    }
}
