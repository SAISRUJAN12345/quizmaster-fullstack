package com.quizmaster.config;
import com.quizmaster.model.*;
import com.quizmaster.repo.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;
@Configuration
public class SeedData {
 @Bean CommandLineRunner seed(UserRepository users,QuizRepository quizzes,PasswordEncoder encoder,
  @Value("${app.admin.email}") String adminEmail,@Value("${app.admin.password}") String adminPassword){
  return args->{
   if(!users.existsByEmailIgnoreCase(adminEmail)){AppUser a=new AppUser();a.setFullName("Quiz Administrator");a.setEmail(adminEmail.toLowerCase());a.setPasswordHash(encoder.encode(adminPassword));a.setRole("ADMIN");users.save(a);}
   if(quizzes.count()==0){Quiz q=new Quiz();q.setTitle("Java Fundamentals");q.setDescription("Practice core Java concepts with five questions.");q.setDurationMinutes(10);q.setPublished(true);
    q.setQuestions(List.of(question(q,"Which keyword is used to inherit a class?","implements","extends","inherits","instanceof","B"),question(q,"Which method is the Java application entry point?","start()","run()","main()","init()","C"),question(q,"Which collection prevents duplicate elements?","ArrayList","LinkedList","HashSet","Vector","C"),question(q,"Which type stores true or false?","int","boolean","char","float","B"),question(q,"Which keyword creates an object?","class","this","new","static","C")));quizzes.save(q);}
  };}
 private Question question(Quiz q,String t,String a,String b,String c,String d,String correct){Question x=new Question();x.setQuiz(q);x.setText(t);x.setOptionA(a);x.setOptionB(b);x.setOptionC(c);x.setOptionD(d);x.setCorrectOption(correct);return x;}
}
