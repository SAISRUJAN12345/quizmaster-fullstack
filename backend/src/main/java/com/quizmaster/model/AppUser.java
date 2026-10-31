package com.quizmaster.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name="app_users")
public class AppUser {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, unique=true) private String email;
    @Column(nullable=false) private String fullName;
    @Column(nullable=false) private String passwordHash;
    @Column(nullable=false) private String role = "STUDENT";
    @Column(nullable=false) private Instant createdAt = Instant.now();
    public Long getId(){return id;} public String getEmail(){return email;} public void setEmail(String v){email=v;}
    public String getFullName(){return fullName;} public void setFullName(String v){fullName=v;}
    public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String v){passwordHash=v;}
    public String getRole(){return role;} public void setRole(String v){role=v;}
    public Instant getCreatedAt(){return createdAt;}
}
