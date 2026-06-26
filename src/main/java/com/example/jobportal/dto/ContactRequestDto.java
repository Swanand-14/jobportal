package com.example.jobportal.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * DTO for {@link com.example.jobportal.entity.Contact}
 */
public record ContactRequestDto(@NotBlank(message = "Email cannot be empty") String email, String message, String name, String subject,
                                String userType) implements Serializable {

}