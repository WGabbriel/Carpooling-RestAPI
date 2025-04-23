package com.wgabbriel.carpooling.enums;

public enum UserRole {
  DRIVER("DRIVER"),
  PASSENGER("PASSENGER");

  private String role;

  UserRole(String role) {
    this.role = role;
  }

  public String getRole() {
    return role;
  }
}
