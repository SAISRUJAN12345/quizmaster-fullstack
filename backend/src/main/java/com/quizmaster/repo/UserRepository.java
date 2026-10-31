package com.quizmaster.repo;
import com.quizmaster.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UserRepository extends JpaRepository<AppUser,Long>{Optional<AppUser> findByEmailIgnoreCase(String email); boolean existsByEmailIgnoreCase(String email);}
