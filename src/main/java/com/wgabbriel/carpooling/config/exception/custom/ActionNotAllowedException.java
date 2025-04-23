package com.wgabbriel.carpooling.config.exception.custom;

public class ActionNotAllowedException extends RuntimeException {

  public ActionNotAllowedException(String message) {
    super(message);
  }

}
