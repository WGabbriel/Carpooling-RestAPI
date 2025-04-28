package com.wgabbriel.carpooling.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wgabbriel.carpooling.dto.request.RequestUpdateUserDto;
import com.wgabbriel.carpooling.dto.response.ResponseUserProfileDto;
import com.wgabbriel.carpooling.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseUserProfileDto> getUserProfile(@PathVariable UUID id) {

    var user = userService.getUserProfile(id);
    return ResponseEntity.status(200).body(user);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ResponseUserProfileDto> updateUserProfile(@PathVariable UUID id,
      @RequestBody RequestUpdateUserDto user) {

    var updatedUser = userService.updateUserProfile(id, user);
    return ResponseEntity.status(200).body(updatedUser);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUserProfile(@PathVariable UUID id) {

    userService.deleteUserProfile(id);
    return ResponseEntity.status(204).build();
  }
}
