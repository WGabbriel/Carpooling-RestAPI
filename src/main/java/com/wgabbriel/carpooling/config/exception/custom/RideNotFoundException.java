package com.wgabbriel.carpooling.config.exception.custom;

public class RideNotFoundException extends RuntimeException {

  public RideNotFoundException(String message) {
    super(message);
  }

}
