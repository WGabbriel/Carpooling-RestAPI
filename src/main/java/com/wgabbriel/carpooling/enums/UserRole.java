package com.wgabbriel.carpooling.enums;

public enum UserRole {
  Driver("driver"),
  Passenger("passenger");

  private String role;

  UserRole(String role) {
    this.role = role;
  }

  public String getRole() {
    return role;
  }
}
