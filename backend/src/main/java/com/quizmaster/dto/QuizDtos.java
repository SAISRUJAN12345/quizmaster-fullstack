package com.quizmaster.dto;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.Map;
public class QuizDtos {
 public record QuestionInput(@NotBlank String text,@NotBlank String optionA,@NotBlank String optionB,@NotBlank String optionC,@NotBlank String optionD,@Pattern(regexp="[ABCD]") String correctOption){}
 public record QuizInput(@NotBlank String title,String description,@Min(1) @Max(240) int durationMinutes,boolean published,@NotEmpty List<QuestionInput> questions){}
 public record QuizSummary(Long id,String title,String description,int durationMinutes,int questionCount,boolean published){}
 public record QuestionView(Long id,String text,String optionA,String optionB,String optionC,String optionD){}
 public record SubmitRequest(@NotNull Map<Long,String> answers){}
 public record AttemptResult(Long id,String quizTitle,int totalQuestions,int correctAnswers,int wrongAnswers,double percentage,String status,String submittedAt){}
}
