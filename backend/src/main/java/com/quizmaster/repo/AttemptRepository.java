package com.quizmaster.repo;
import com.quizmaster.model.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface AttemptRepository extends JpaRepository<QuizAttempt,Long>{List<QuizAttempt> findByStudentIdOrderBySubmittedAtDesc(Long studentId); List<QuizAttempt> findAllByOrderBySubmittedAtDesc();}
