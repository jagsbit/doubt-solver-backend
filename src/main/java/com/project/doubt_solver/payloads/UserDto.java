package com.project.doubt_solver.payloads;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class UserDto {
    private int userId;

    @NotNull(message = "Username cannot be null")
    @Size(min = 4, message = "Username must be at least 4 characters")
    private String name;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email address is not valid")
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 4, message = "Password must be at least 4 characters")
    private String password;

    @NotNull(message = "Institution name cannot be null")
    @Size(min = 1, message = "Institution cannot be empty")
    private String institution;

    private LocalDateTime createdAt;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
