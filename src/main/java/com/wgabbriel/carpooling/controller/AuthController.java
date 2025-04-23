package com.wgabbriel.carpooling.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wgabbriel.carpooling.dto.request.RequestLoginDto;
import com.wgabbriel.carpooling.dto.response.ResponseLoginDto;
import com.wgabbriel.carpooling.entity.User;
import com.wgabbriel.carpooling.repository.UserRepository;
import com.wgabbriel.carpooling.service.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final TokenService tokenService;

  public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder,
      AuthenticationManager authenticationManager, TokenService tokenService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
    this.tokenService = tokenService;
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
    user.setEnabled(true);
    return ResponseEntity.status(201).body(userRepository.save(user));
  }

  @PostMapping("/login")
  public ResponseEntity<ResponseLoginDto> login(@RequestBody @Valid RequestLoginDto user) {

    var authenticationToken = new UsernamePasswordAuthenticationToken(user.email(), user.password());
    var auth = this.authenticationManager.authenticate(authenticationToken);
    var token = tokenService.generateToken((User) auth.getPrincipal());

    return ResponseEntity.status(200).body(new ResponseLoginDto(token));

  }
}