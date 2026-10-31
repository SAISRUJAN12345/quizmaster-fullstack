package com.quiz.service;

import com.quiz.dto.*;
import com.quiz.model.Question;
import com.quiz.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuizService {

    private final QuestionRepository questionRepository;

    public QuizService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    /**
     * Retrieve quiz questions, optionally filtered by category.
     * Guaranteed: Correct answers and explanations are omitted.
     */
    public List<QuestionResponseDto> getQuizQuestions(String category) {
        List<Question> questions;
        if (category != null && !category.trim().isEmpty() && !category.equalsIgnoreCase("ALL")) {
            questions = questionRepository.findByCategoryIgnoreCase(category.trim());
        } else {
            questions = questionRepository.findAll();
        }

        return questions.stream()
                .map(q -> new QuestionResponseDto(
                        q.getId(),
                        q.getQuestionText(),
                        q.getOptions(),
                        q.getCategory(),
                        q.getDifficulty()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Evaluate the submitted answers, compute the score, and return detailed results.
     */
    public QuizResultDto evaluateQuiz(QuizSubmissionDto submission) {
        List<Question> allQuestions = questionRepository.findAll();
        Map<Long, Question> questionMap = allQuestions.stream()
                .collect(Collectors.toMap(Question::getId, q -> q));

        // Map submitted answers by questionId
        Map<Long, Integer> submittedAnswersMap = new HashMap<>();
        List<Long> submittedQuestionIds = new ArrayList<>();

        if (submission != null && submission.getAnswers() != null) {
            for (AnswerSubmissionDto answer : submission.getAnswers()) {
                if (answer.getQuestionId() != null) {
                    submittedAnswersMap.put(answer.getQuestionId(), answer.getSelectedOption());
                    submittedQuestionIds.add(answer.getQuestionId());
                }
            }
        }

        // If the user submitted a category-subset quiz, evaluate those submitted questions;
        // otherwise evaluate all questions.
        List<Question> questionsToEvaluate = new ArrayList<>();
        if (!submittedQuestionIds.isEmpty()) {
            for (Long qId : submittedQuestionIds) {
                Question q = questionMap.get(qId);
                if (q != null) {
                    questionsToEvaluate.add(q);
                }
            }
        } else {
            questionsToEvaluate = allQuestions;
        }

        int totalQuestions = questionsToEvaluate.size();
        int score = 0;
        List<QuestionResultDto> results = new ArrayList<>();

        for (Question question : questionsToEvaluate) {
            Integer selectedOption = submittedAnswersMap.get(question.getId());
            boolean isCorrect = selectedOption != null && selectedOption == question.getCorrectOptionIndex();

            if (isCorrect) {
                score++;
            }

            List<String> options = question.getOptions();
            String selectedOptionText = "Not answered";
            if (selectedOption != null && selectedOption >= 0 && selectedOption < options.size()) {
                selectedOptionText = options.get(selectedOption);
            }

            String correctOptionText = "";
            if (question.getCorrectOptionIndex() >= 0 && question.getCorrectOptionIndex() < options.size()) {
                correctOptionText = options.get(question.getCorrectOptionIndex());
            }

            results.add(new QuestionResultDto(
                    question.getId(),
                    question.getQuestionText(),
                    options,
                    selectedOption,
                    question.getCorrectOptionIndex(),
                    isCorrect,
                    selectedOptionText,
                    correctOptionText,
                    question.getExplanation(),
                    question.getCategory(),
                    question.getDifficulty()
            ));
        }

        double percentage = totalQuestions > 0 ? ((double) score / totalQuestions) * 100.0 : 0.0;
        percentage = Math.round(percentage * 10.0) / 10.0;
        boolean passed = percentage >= 60.0;

        String feedbackMessage;
        if (percentage == 100.0) {
            feedbackMessage = "🏆 Outstanding! Perfect score! True mastery demonstrated.";
        } else if (percentage >= 80.0) {
            feedbackMessage = "🌟 Great job! Excellent understanding and problem solving.";
        } else if (percentage >= 60.0) {
            feedbackMessage = "👍 Good effort! You passed the assessment.";
        } else {
            feedbackMessage = "💡 Needs practice! Review the explanations below and try again.";
        }

        return new QuizResultDto(score, totalQuestions, percentage, passed, feedbackMessage, results);
    }
}
