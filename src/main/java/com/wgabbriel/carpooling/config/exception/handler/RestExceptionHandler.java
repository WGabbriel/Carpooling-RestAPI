package com.wgabbriel.carpooling.config.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.wgabbriel.carpooling.config.exception.custom.FieldAlreadyRegisteredException;
import com.wgabbriel.carpooling.config.exception.custom.ActionNotAllowedException;
import com.wgabbriel.carpooling.config.exception.custom.NotFoundException;

@RestControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
    return ResponseEntity.status(400).body(e.getMessage());
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException e) {
    return ResponseEntity.status(404).body(e.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    return ResponseEntity.status(400)
        .body(e.getAllErrors().get(0).getDefaultMessage());
  }

  @ExceptionHandler(FieldAlreadyRegisteredException.class)
  public ResponseEntity<String> handleFieldAlreadyRegisteredException(FieldAlreadyRegisteredException e) {
    return ResponseEntity.status(409).body(e.getMessage());
  }

  @ExceptionHandler(ActionNotAllowedException.class)
  public ResponseEntity<String> handleActionNotAllowedExceptionException(ActionNotAllowedException e) {
    return ResponseEntity.status(403).body(e.getMessage());
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
    return ResponseEntity.status(404).body(e.getMessage());
  }
}
