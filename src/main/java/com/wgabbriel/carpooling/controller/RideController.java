package com.wgabbriel.carpooling.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wgabbriel.carpooling.entity.Ride;
import com.wgabbriel.carpooling.service.RideService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/ride")
public class RideController {

  private final RideService rideService;

  public RideController(RideService rideService) {
    this.rideService = rideService;
  }

  @GetMapping("")
  public ResponseEntity<List<Ride>> getAllRides() {
    return ResponseEntity.status(200).body(rideService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Ride> getRideById(@PathVariable UUID id) {

    return ResponseEntity.status(200).body(rideService.findById(id));
  }

  @PostMapping("")
  public ResponseEntity<Ride> createRide(@RequestBody @Valid Ride ride) {
    return ResponseEntity.status(201).body(rideService.create(ride));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Ride> updateRide(@PathVariable UUID id, @RequestBody Ride ride) {

    return ResponseEntity.status(200).body(rideService.update(id, ride));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteRide(@PathVariable UUID id) {

    rideService.delete(id);
    return ResponseEntity.status(204).build();
  }

}
