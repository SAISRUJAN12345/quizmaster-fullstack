package com.quizmaster.controller;

import com.quizmaster.dto.AuthDtos.*;
import com.quizmaster.model.AppUser;
import com.quizmaster.repo.UserRepository;
import com.quizmaster.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController @RequestMapping("/api/auth")
public class AuthController {
 private final UserRepository users;private final PasswordEncoder encoder;private final JwtService jwt;
 public AuthController(UserRepository users,PasswordEncoder encoder,JwtService jwt){this.users=users;this.encoder=encoder;this.jwt=jwt;}
 @PostMapping("/register") @ResponseStatus(HttpStatus.CREATED)
 public AuthResponse register(@Valid @RequestBody RegisterRequest r){
  if(users.existsByEmailIgnoreCase(r.email()))throw new ResponseStatusException(HttpStatus.CONFLICT,"Email is already registered");
  AppUser u=new AppUser();u.setFullName(r.fullName().trim());u.setEmail(r.email().trim().toLowerCase());u.setPasswordHash(encoder.encode(r.password()));u.setRole("STUDENT");users.save(u);return response(u);
 }
 @PostMapping("/login") public AuthResponse login(@Valid @RequestBody LoginRequest r){
  AppUser u=users.findByEmailIgnoreCase(r.email()).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid email or password"));
  if(!encoder.matches(r.password(),u.getPasswordHash()))throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid email or password");return response(u);
 }
 private AuthResponse response(AppUser u){return new AuthResponse(jwt.generate(u.getEmail(),u.getRole()),u.getId(),u.getFullName(),u.getEmail(),u.getRole());}
}
