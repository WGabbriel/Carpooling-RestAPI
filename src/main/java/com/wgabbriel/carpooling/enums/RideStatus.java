package com.wgabbriel.carpooling.enums;

public enum RideStatus {
  OPEN("OPEN"),
  COMPLETED("COMPLETED");

  private String status;

  RideStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
