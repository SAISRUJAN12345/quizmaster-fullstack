package com.quiz.dto;

import java.util.ArrayList;
import java.util.List;

public class QuizResultDto {
    private int score;
    private int totalQuestions;
    private double percentage;
    private boolean passed;
    private String feedbackMessage;
    private List<QuestionResultDto> results = new ArrayList<>();

    public QuizResultDto() {
    }

    public QuizResultDto(int score, int totalQuestions, double percentage, boolean passed,
                         String feedbackMessage, List<QuestionResultDto> results) {
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.percentage = percentage;
        this.passed = passed;
        this.feedbackMessage = feedbackMessage;
        this.results = results;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public String getFeedbackMessage() {
        return feedbackMessage;
    }

    public void setFeedbackMessage(String feedbackMessage) {
        this.feedbackMessage = feedbackMessage;
    }

    public List<QuestionResultDto> getResults() {
        return results;
    }

    public void setResults(List<QuestionResultDto> results) {
        this.results = results;
    }
}
