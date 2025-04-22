package com.wgabbriel.carpooling.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wgabbriel.carpooling.entity.Ride;
import com.wgabbriel.carpooling.enums.DriverRideStatus;

@Repository
public interface RideRepository extends JpaRepository<Ride, UUID> {
  List<Ride> findByStatus(DriverRideStatus status);

  List<Ride> findAllByDriverId(UUID driverId);
}
