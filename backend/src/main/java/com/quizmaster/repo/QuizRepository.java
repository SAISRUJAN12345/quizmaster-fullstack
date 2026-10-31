package com.quizmaster.repo;
import com.quizmaster.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface QuizRepository extends JpaRepository<Quiz,Long>{List<Quiz> findByPublishedTrueOrderByCreatedAtDesc();}
