package com.wgabbriel.carpooling.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.wgabbriel.carpooling.entity.User;

@Service
public class TokenService {

  @Value("${api.jwt.secret.key}")
  private String secret;

  public String generateToken(User user) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      String token = JWT.create()
          .withIssuer("carpooling-api")
          .withSubject(user.getEmail())
          .withExpiresAt(genExpirationDate())
          .sign(algorithm);
      return token;
    } catch (JWTCreationException e) {
      throw new RuntimeException("Error while generating token", e);
    }
  }

  public String tokenValidator(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      return JWT.require(algorithm)
          .withIssuer("carpooling-api")
          .build()
          .verify(token)
          .getSubject();
    } catch (JWTVerificationException e) {
      return "";
    }
  }

  private Instant genExpirationDate() {
    return LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.of("-03:00"));
  }
}
