package com.quizmaster.controller;
import com.quizmaster.repo.UserRepository;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.Map;
@RestController @RequestMapping("/api")
public class ProfileController {
 private final UserRepository users;public ProfileController(UserRepository u){users=u;}
 @GetMapping("/me") public Map<String,Object> me(Principal p){var u=users.findByEmailIgnoreCase(p.getName()).orElseThrow();return Map.of("id",u.getId(),"fullName",u.getFullName(),"email",u.getEmail(),"role",u.getRole());}
}
