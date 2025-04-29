package com.wgabbriel.carpooling.service;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wgabbriel.carpooling.config.exception.custom.ActionNotAllowedException;
import com.wgabbriel.carpooling.config.exception.custom.NotFoundException;
import com.wgabbriel.carpooling.dto.request.RequestUpdateUserDto;
import com.wgabbriel.carpooling.dto.response.ResponseUserProfileDto;
import com.wgabbriel.carpooling.repository.UserRepository;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthService authService;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthService authService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.authService = authService;
  }

  public ResponseUserProfileDto getUserProfile(UUID id) {

    var user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    return new ResponseUserProfileDto(user.getName(), user.getEmail(), user.getPhone(), user.getRole());
  }

  public ResponseUserProfileDto updateUserProfile(UUID id, RequestUpdateUserDto user) {

    var loggedUser = authService.getUserByToken();
    var userToUpdate = userRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("User not found"));

    if (!loggedUser.getId().equals(userToUpdate.getId())) {
      throw new ActionNotAllowedException("You are not allowed to update this user");
    }

    if (user.name() != null) {
      userToUpdate.setName(user.name());
    }

    if (user.email() != null) {
      userToUpdate.setEmail(user.email());
    }

    if (user.phone() != null) {
      userToUpdate.setPhone(user.phone());
    }

    if (user.password() != null) {
      userToUpdate.setPassword(passwordEncoder.encode(user.password()));
    }

    userRepository.save(userToUpdate);

    return new ResponseUserProfileDto(userToUpdate.getName(), userToUpdate.getEmail(), userToUpdate.getPhone(),
        userToUpdate.getRole());
  }

  public void deleteUserProfile(UUID id) {

    var loggedUser = authService.getUserByToken();
    var userToDelete = userRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("User not found"));

    if (!loggedUser.getId().equals( userToDelete.getId())) {
      throw new ActionNotAllowedException("You are not allowed to delete this user");
    }

    userToDelete.setEnabled(false);
    userRepository.save(userToDelete);
  }
}
