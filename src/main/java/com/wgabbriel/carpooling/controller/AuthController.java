package com.wgabbriel.carpooling.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wgabbriel.carpooling.entity.User;
import com.wgabbriel.carpooling.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/register")
  public ResponseEntity<User> register(@RequestBody @Valid User user) {
    if (userRepository.findByEmail(user.getEmail()).isPresent()) {
      throw new IllegalArgumentException("Email already registered");
    }

    if (userRepository.findByPhone(user.getPhone()).isPresent()) {
      throw new IllegalArgumentException("Phone already registered");
    }

    if (user.getRole() == null) {
      throw new IllegalArgumentException("Role is required");
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return ResponseEntity.status(201).body(userRepository.save(user));
  }

}