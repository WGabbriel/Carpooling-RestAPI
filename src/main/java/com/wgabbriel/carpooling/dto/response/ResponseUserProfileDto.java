package com.wgabbriel.carpooling.dto.response;

import com.wgabbriel.carpooling.enums.UserRole;

public record ResponseUserProfileDto(String name, String email, String phone, UserRole role) {
}
