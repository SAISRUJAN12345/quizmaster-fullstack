package com.quiz;

import com.quiz.dto.AnswerSubmissionDto;
import com.quiz.dto.QuizResultDto;
import com.quiz.dto.QuizSubmissionDto;
import com.quiz.model.Question;
import com.quiz.repository.QuestionRepository;
import com.quiz.service.QuizService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class QuizServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuizService quizService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetQuizQuestionsHidesCorrectAnswers() {
        Question q = new Question("What is Java?", Arrays.asList("OOP Language", "Fruit"), 0, "Java is OOP", "Java & Spring", "Easy");
        q.setId(1L);
        when(questionRepository.findAll()).thenReturn(List.of(q));

        var questions = quizService.getQuizQuestions(null);
        assertEquals(1, questions.size());
        assertEquals("What is Java?", questions.get(0).getQuestionText());
        assertEquals(2, questions.get(0).getOptions().size());
        assertEquals("Java & Spring", questions.get(0).getCategory());
    }

    @Test
    void testEvaluateQuizCalculatesScoreAccurately() {
        Question q1 = new Question("Q1", Arrays.asList("A", "B", "C"), 1, "Explanation 1", "Category 1", "Easy");
        q1.setId(1L);
        Question q2 = new Question("Q2", Arrays.asList("X", "Y"), 0, "Explanation 2", "Category 2", "Medium");
        q2.setId(2L);

        when(questionRepository.findAll()).thenReturn(List.of(q1, q2));

        // Submit correct for Q1, incorrect for Q2
        QuizSubmissionDto submission = new QuizSubmissionDto(Arrays.asList(
                new AnswerSubmissionDto(1L, 1),
                new AnswerSubmissionDto(2L, 1) // wrong answer
        ));

        QuizResultDto result = quizService.evaluateQuiz(submission);

        assertEquals(2, result.getTotalQuestions());
        assertEquals(1, result.getScore());
        assertEquals(50.0, result.getPercentage());
        assertFalse(result.isPassed());

        assertEquals(2, result.getResults().size());
        assertTrue(result.getResults().get(0).isCorrect());
        assertFalse(result.getResults().get(1).isCorrect());
        assertEquals(0, result.getResults().get(1).getCorrectOption());
        assertEquals(1, result.getResults().get(1).getSelectedOption());
    }
}
