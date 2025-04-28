package com.wgabbriel.carpooling.entity;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.wgabbriel.carpooling.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "tb_users")
@Data
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotBlank(message = "Name should not be blank")
  private String name;

  @Column(unique = true)
  @Email(message = "Email should be valid")
  @NotBlank(message = "Email should not be blank")
  private String email;

  @NotBlank(message = "Password should not be blank")
  @Size(min = 6, message = "Password should be at least 6 characters long")
  private String password;

  @Column(unique = true)
  @NotBlank(message = "Phone should not be blank")
  private String phone;

  @Column(name = "user_role")
  private UserRole role;

  private boolean isEnabled;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    switch (this.role) {
      case DRIVER:
        return List.of(new SimpleGrantedAuthority("ROLE_DRIVER"), new SimpleGrantedAuthority("ROLE_PASSENGER"));
      case PASSENGER:
        return List.of(new SimpleGrantedAuthority("ROLE_PASSENGER"));
      default:
        throw new IllegalStateException("Unexpected role: " + this.role);
    }
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isEnabled() {
    return this.isEnabled;
  }
}
