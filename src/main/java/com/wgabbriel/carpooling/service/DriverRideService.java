package com.wgabbriel.carpooling.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.wgabbriel.carpooling.config.exception.custom.NotAllowedDriver;
import com.wgabbriel.carpooling.config.exception.custom.RideNotFoundException;
import com.wgabbriel.carpooling.entity.Ride;
import com.wgabbriel.carpooling.enums.DriverRideStatus;
import com.wgabbriel.carpooling.repository.RideRepository;

@Service
public class DriverRideService {

  private final RideRepository rideRepository;
  private final AuthService authService;

  public DriverRideService(RideRepository rideRepository, AuthService authService) {
    this.rideRepository = rideRepository;
    this.authService = authService;
  }

  public Ride create(Ride ride) {

    ride.setDriver(authService.getUserByToken());
    ride.setStatus(DriverRideStatus.OPEN);
    return rideRepository.save(ride);
  }

  public Ride findById(UUID id) {

    var driver = authService.getUserByToken();
    var ride = rideRepository.findById(id).orElseThrow(() -> new RideNotFoundException("Ride not found"));

    if (!ride.getDriver().getId().equals(driver.getId())) {
      throw new NotAllowedDriver("You are not allowed to get this ride");
    }

    return ride;
  }

  public List<Ride> findAll() {

    var driver = authService.getUserByToken().getId();

    return rideRepository.findAllByDriverId(driver);
  }

  public Ride update(UUID id, Ride ride) {

    var driver = authService.getUserByToken();
    var rideToUpdate = rideRepository.findById(id)
        .orElseThrow(() -> new RideNotFoundException("Ride not found"));

    if (!rideToUpdate.getDriver().getId().equals(driver.getId())) {
      throw new NotAllowedDriver("You are not allowed to update this ride");
    }

    if (rideToUpdate.getStatus() == DriverRideStatus.COMPLETED) {
      throw new NotAllowedDriver("You are not allowed to update this ride");
    }

    if (ride.getPassengers() != null) {
      rideToUpdate.setPassengers(ride.getPassengers());
    }
    if (ride.getOrigin() != null) {
      rideToUpdate.setOrigin(ride.getOrigin());
    }
    if (ride.getDestination() != null) {
      rideToUpdate.setDestination(ride.getDestination());
    }
    if (ride.getDate() != null) {
      rideToUpdate.setDate(ride.getDate());
    }
    if (ride.getPrice() != null) {
      rideToUpdate.setPrice(ride.getPrice());
    }
    if (ride.getStatus() != null) {
      rideToUpdate.setStatus(ride.getStatus());
    }

    return rideRepository.save(rideToUpdate);
  }

  public void delete(UUID id) {

    var driver = authService.getUserByToken().getId();
    var ride = rideRepository.findById(id).orElseThrow(() -> new RideNotFoundException("Ride not found"));

    if (!ride.getDriver().getId().equals(driver)
        || ride.getStatus().equals(DriverRideStatus.COMPLETED)) {
      throw new NotAllowedDriver("You are not allowed to delete this ride");
    }

    rideRepository.delete(ride);
  }

  // public void autoCompleteRides

}