package com.wgabbriel.carpooling.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wgabbriel.carpooling.dto.request.RequestLoginDto;
import com.wgabbriel.carpooling.dto.response.ResponseLoginDto;
import com.wgabbriel.carpooling.entity.User;
import com.wgabbriel.carpooling.service.AuthService;
import com.wgabbriel.carpooling.service.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService authService;
  private final AuthenticationManager authenticationManager;
  private final TokenService tokenService;

  public AuthController(AuthService authService,
      AuthenticationManager authenticationManager, TokenService tokenService) {
    this.authService = authService;
    this.authenticationManager = authenticationManager;
    this.tokenService = tokenService;
  }

  @PostMapping("/register")
  public ResponseEntity<User> register(@RequestBody @Valid User user) {
    return ResponseEntity.status(HttpStatus.CREATED).body(authService.create(user));
  }

  @PostMapping("/login")
  public ResponseEntity<ResponseLoginDto> login(@RequestBody @Valid RequestLoginDto user) {

    var usernamePassword = new UsernamePasswordAuthenticationToken(user.email(), user.password());
    var auth = this.authenticationManager.authenticate(usernamePassword);
    var token = tokenService.generateToken((User) auth.getPrincipal());

    return ResponseEntity.status(200).body(new ResponseLoginDto(token));

  }
}