package com.quizmaster.model;

import jakarta.persistence.*;

@Entity
public class Question {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false,length=1500) private String text;
    @Column(nullable=false) private String optionA;
    @Column(nullable=false) private String optionB;
    @Column(nullable=false) private String optionC;
    @Column(nullable=false) private String optionD;
    @Column(nullable=false,length=1) private String correctOption;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="quiz_id",nullable=false) private Quiz quiz;
    public Long getId(){return id;} public String getText(){return text;} public void setText(String v){text=v;}
    public String getOptionA(){return optionA;} public void setOptionA(String v){optionA=v;}
    public String getOptionB(){return optionB;} public void setOptionB(String v){optionB=v;}
    public String getOptionC(){return optionC;} public void setOptionC(String v){optionC=v;}
    public String getOptionD(){return optionD;} public void setOptionD(String v){optionD=v;}
    public String getCorrectOption(){return correctOption;} public void setCorrectOption(String v){correctOption=v;}
    public Quiz getQuiz(){return quiz;} public void setQuiz(Quiz v){quiz=v;}
}
