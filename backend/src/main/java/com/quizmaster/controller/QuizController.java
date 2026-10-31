package com.quizmaster.controller;

import com.quizmaster.dto.QuizDtos.*;
import com.quizmaster.model.*;
import com.quizmaster.repo.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.Instant;
import java.util.*;

@RestController @RequestMapping("/api")
public class QuizController {
 private final QuizRepository quizzes;private final QuestionRepository questions;private final UserRepository users;private final AttemptRepository attempts;
 public QuizController(QuizRepository q,QuestionRepository qs,UserRepository u,AttemptRepository a){quizzes=q;questions=qs;users=u;attempts=a;}
 @GetMapping("/quizzes") public List<QuizSummary> list(){return quizzes.findByPublishedTrueOrderByCreatedAtDesc().stream().map(this::summary).toList();}
 @GetMapping("/quizzes/{id}/questions") public List<QuestionView> questions(@PathVariable Long id){
  Quiz quiz=quizzes.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Quiz not found"));
  if(!quiz.isPublished())throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Quiz not found");
  return questions.findByQuizId(id).stream().map(q->new QuestionView(q.getId(),q.getText(),q.getOptionA(),q.getOptionB(),q.getOptionC(),q.getOptionD())).toList();
 }
 @PostMapping("/quizzes/{id}/submit") public AttemptResult submit(@PathVariable Long id,@RequestBody SubmitRequest req,java.security.Principal principal){
  AppUser user=users.findByEmailIgnoreCase(principal.getName()).orElseThrow();
  Quiz quiz=quizzes.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Quiz not found"));
  List<Question> qs=questions.findByQuizId(id);int score=0;
  for(Question q:qs){String selected=req.answers().get(q.getId());if(selected!=null&&selected.equalsIgnoreCase(q.getCorrectOption()))score++;}
  double pct=qs.isEmpty()?0:Math.round(score*10000.0/qs.size())/100.0;
  QuizAttempt a=new QuizAttempt();a.setStudent(user);a.setQuiz(quiz);a.setTotalQuestions(qs.size());a.setCorrectAnswers(score);a.setPercentage(pct);a.setStatus(pct>=40?"PASS":"FAIL");attempts.save(a);
  return result(a);
 }
 @GetMapping("/attempts/my") public List<AttemptResult> myAttempts(java.security.Principal p){AppUser u=users.findByEmailIgnoreCase(p.getName()).orElseThrow();return attempts.findByStudentIdOrderBySubmittedAtDesc(u.getId()).stream().map(this::result).toList();}
 private QuizSummary summary(Quiz q){return new QuizSummary(q.getId(),q.getTitle(),q.getDescription(),q.getDurationMinutes(),questions.findByQuizId(q.getId()).size(),q.isPublished());}
 private AttemptResult result(QuizAttempt a){return new AttemptResult(a.getId(),a.getQuiz().getTitle(),a.getTotalQuestions(),a.getCorrectAnswers(),a.getTotalQuestions()-a.getCorrectAnswers(),a.getPercentage(),a.getStatus(),a.getSubmittedAt().toString());}
}
