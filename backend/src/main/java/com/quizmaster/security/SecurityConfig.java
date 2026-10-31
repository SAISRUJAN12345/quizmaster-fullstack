package com.quizmaster.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.*;

@Configuration
public class SecurityConfig {
 private final JwtFilter jwtFilter;
 @Value("${app.cors.allowed-origin}") private String frontend;
 public SecurityConfig(JwtFilter jwtFilter){this.jwtFilter=jwtFilter;}
 @Bean SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
  return http.csrf(c->c.disable()).cors(c->c.configurationSource(corsSource()))
   .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
   .authorizeHttpRequests(a->a.requestMatchers("/api/auth/**","/api/quizzes","/api/quizzes/*/questions").permitAll()
    .requestMatchers("/api/admin/**").hasRole("ADMIN").anyRequest().authenticated())
   .addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class).build();
 }
 @Bean CorsConfigurationSource corsSource(){CorsConfiguration c=new CorsConfiguration();c.setAllowedOrigins(java.util.List.of(frontend,"http://localhost:5174"));c.setAllowedMethods(java.util.List.of("GET","POST","PUT","DELETE","OPTIONS"));c.setAllowedHeaders(java.util.List.of("*"));c.setAllowCredentials(false);UrlBasedCorsConfigurationSource s=new UrlBasedCorsConfigurationSource();s.registerCorsConfiguration("/**",c);return s;}
 @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
 @Bean AuthenticationManager authenticationManager(AuthenticationConfiguration c)throws Exception{return c.getAuthenticationManager();}
}
