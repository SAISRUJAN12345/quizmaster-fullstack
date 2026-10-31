package com.quizmaster.controller;

import com.quizmaster.dto.QuizDtos.*;
import com.quizmaster.model.*;
import com.quizmaster.repo.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@RestController @RequestMapping("/api/admin")
public class AdminController {
 private final QuizRepository quizzes;private final QuestionRepository questions;private final AttemptRepository attempts;
 public AdminController(QuizRepository q,QuestionRepository qs,AttemptRepository a){quizzes=q;questions=qs;attempts=a;}
 @GetMapping("/quizzes") public List<QuizSummary> all(){return quizzes.findAll().stream().map(q->new QuizSummary(q.getId(),q.getTitle(),q.getDescription(),q.getDurationMinutes(),questions.findByQuizId(q.getId()).size(),q.isPublished())).toList();}
 @PostMapping("/quizzes") @ResponseStatus(HttpStatus.CREATED) public QuizSummary create(@Valid @RequestBody QuizInput in){Quiz q=new Quiz();apply(q,in);quizzes.save(q);return new QuizSummary(q.getId(),q.getTitle(),q.getDescription(),q.getDurationMinutes(),q.getQuestions().size(),q.isPublished());}
 @PutMapping("/quizzes/{id}") public QuizSummary update(@PathVariable Long id,@Valid @RequestBody QuizInput in){Quiz q=quizzes.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));apply(q,in);quizzes.save(q);return new QuizSummary(q.getId(),q.getTitle(),q.getDescription(),q.getDurationMinutes(),q.getQuestions().size(),q.isPublished());}
 @DeleteMapping("/quizzes/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id){Quiz q=quizzes.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));if(!attempts.findAllByOrderBySubmittedAtDesc().stream().filter(a->a.getQuiz().getId().equals(id)).toList().isEmpty())throw new ResponseStatusException(HttpStatus.CONFLICT,"Quiz has attempts and cannot be deleted");quizzes.delete(q);}
 private void apply(Quiz q,QuizInput in){q.setTitle(in.title());q.setDescription(in.description());q.setDurationMinutes(in.durationMinutes());q.setPublished(in.published());List<Question> list=new ArrayList<>();for(QuestionInput x:in.questions()){Question a=new Question();a.setText(x.text());a.setOptionA(x.optionA());a.setOptionB(x.optionB());a.setOptionC(x.optionC());a.setOptionD(x.optionD());a.setCorrectOption(x.correctOption());a.setQuiz(q);list.add(a);}q.setQuestions(list);}
}
