package com.wgabbriel.carpooling.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wgabbriel.carpooling.entity.User;
import com.wgabbriel.carpooling.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthService implements UserDetailsService {

  private final UserRepository userRepository;
  private final TokenService tokenService;
  private final HttpServletRequest request;

  public AuthService(UserRepository userRepository, TokenService tokenService, HttpServletRequest request) {
    this.userRepository = userRepository;
    this.tokenService = tokenService;
    this.request = request;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    return userRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  public User getUserByToken() {

    var token = request.getHeader("Authorization").replace("Bearer ", "");
    var user = userRepository
        .findByEmail(tokenService.validateToken(token)).get();
    return user;
  }

}
