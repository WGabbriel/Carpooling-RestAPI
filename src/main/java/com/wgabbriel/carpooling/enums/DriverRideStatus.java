package com.wgabbriel.carpooling.enums;

public enum DriverRideStatus {
  OPEN("OPEN"),
  COMPLETED("COMPLETED");

  private String status;

  DriverRideStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
