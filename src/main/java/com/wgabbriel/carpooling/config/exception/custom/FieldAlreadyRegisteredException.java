package com.wgabbriel.carpooling.config.exception.custom;

public class FieldAlreadyRegisteredException extends RuntimeException {

  public FieldAlreadyRegisteredException(String message) {
    super(message);
  }

}