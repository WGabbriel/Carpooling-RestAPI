package com.wgabbriel.carpooling.config.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.wgabbriel.carpooling.entity.User;
import com.wgabbriel.carpooling.repository.UserRepository;
import com.wgabbriel.carpooling.service.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

  private final TokenService tokenService;
  private final UserRepository userRepository;

  public SecurityFilter(TokenService tokenService, UserRepository userRepository) {
    this.tokenService = tokenService;
    this.userRepository = userRepository;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    var token = this.recoverToken(request);

    if (token != null) {

      var subject = tokenService.validateToken(token);
      Optional<User> user = userRepository.findByEmail(subject);

      if (user.isPresent()) {
        var authentication = new UsernamePasswordAuthenticationToken(user.get(), null, user.get().getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }

    }
    filterChain.doFilter(request, response);
  }

  private String recoverToken(HttpServletRequest request) {

    var authHeader = request.getHeader("Authorization");

    if (authHeader == null) {
      return null;
    }

    return authHeader.replace("Bearer ", "");
  }
}
