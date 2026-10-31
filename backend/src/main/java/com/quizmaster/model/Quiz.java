package com.quizmaster.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Quiz {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String title;
    @Column(length=1200) private String description;
    private int durationMinutes=10;
    private boolean published=true;
    private Instant createdAt=Instant.now();
    @OneToMany(mappedBy="quiz",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.LAZY)
    private List<Question> questions=new ArrayList<>();
    public Long getId(){return id;} public String getTitle(){return title;} public void setTitle(String v){title=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;}
    public int getDurationMinutes(){return durationMinutes;} public void setDurationMinutes(int v){durationMinutes=v;}
    public boolean isPublished(){return published;} public void setPublished(boolean v){published=v;}
    public Instant getCreatedAt(){return createdAt;} public List<Question> getQuestions(){return questions;}
    public void setQuestions(List<Question> v){questions.clear(); if(v!=null) for(Question q:v){q.setQuiz(this);questions.add(q);}}
}
