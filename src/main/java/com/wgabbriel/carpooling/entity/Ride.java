package com.wgabbriel.carpooling.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wgabbriel.carpooling.enums.DriverRideStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Entity
@Table(name = "tb_ride")
@Data
public class Ride {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "driver_id")
  private User driver;

  @ManyToMany
  @JoinTable(name = "ride_passengers", joinColumns = @JoinColumn(name = "ride_id"), inverseJoinColumns = @JoinColumn(name = "passenger_id"))
  private List<User> passengers = new ArrayList<>();

  @NotEmpty(message = "Origin should not be empty")
  private String origin;

  @NotEmpty(message = "Destination should not be empty")
  private String destination;

  @Future(message = "Date should be in the future")
  @NotNull(message = "Date should not be null")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime date;

  @PositiveOrZero(message = "Price should be valid")
  private BigDecimal price;

  private DriverRideStatus status;

}
