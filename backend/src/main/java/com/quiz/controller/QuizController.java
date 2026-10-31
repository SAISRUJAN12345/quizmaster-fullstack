package com.quiz.controller;

import com.quiz.dto.QuestionResponseDto;
import com.quiz.dto.QuizResultDto;
import com.quiz.dto.QuizSubmissionDto;
import com.quiz.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quiz")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000", "http://127.0.0.1:5173"})
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "online-quiz-backend");
        return ResponseEntity.ok(response);
    }

    /**
     * Get all quiz questions, optionally filtered by category.
     * Guaranteed: Correct answers and explanations are NEVER included here.
     */
    @GetMapping("/questions")
    public ResponseEntity<List<QuestionResponseDto>> getQuestions(
            @RequestParam(required = false) String category
    ) {
        List<QuestionResponseDto> questions = quizService.getQuizQuestions(category);
        return ResponseEntity.ok(questions);
    }

    /**
     * Submit answers and calculate score.
     * Evaluates submissions and reveals correct answers with score breakdown.
     */
    @PostMapping("/submit")
    public ResponseEntity<QuizResultDto> submitQuiz(@RequestBody QuizSubmissionDto submission) {
        QuizResultDto result = quizService.evaluateQuiz(submission);
        return ResponseEntity.ok(result);
    }
}
