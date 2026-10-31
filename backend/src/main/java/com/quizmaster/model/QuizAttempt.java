package com.quizmaster.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class QuizAttempt {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(nullable=false) private AppUser student;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(nullable=false) private Quiz quiz;
    private int totalQuestions;
    private int correctAnswers;
    private double percentage;
    private String status;
    private Instant submittedAt=Instant.now();
    public Long getId(){return id;} public AppUser getStudent(){return student;} public void setStudent(AppUser v){student=v;}
    public Quiz getQuiz(){return quiz;} public void setQuiz(Quiz v){quiz=v;}
    public int getTotalQuestions(){return totalQuestions;} public void setTotalQuestions(int v){totalQuestions=v;}
    public int getCorrectAnswers(){return correctAnswers;} public void setCorrectAnswers(int v){correctAnswers=v;}
    public double getPercentage(){return percentage;} public void setPercentage(double v){percentage=v;}
    public String getStatus(){return status;} public void setStatus(String v){status=v;}
    public Instant getSubmittedAt(){return submittedAt;}
}
