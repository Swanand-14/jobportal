package com.example.jobportal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link com.example.jobportal.entity.Contact}
 */
public record ContactRequestDto(@NotBlank(message = "Email cannot be empty")
                                @Email(message = "Invalid Email") String email,
                                @NotBlank(message = "Email cannot be empty")
                                @Size(min = 5,max = 500,message = "Invalid size range") String message,
                                @NotBlank(message = "Email cannot be empty") String name,
                                @NotBlank(message = "Email cannot be empty") String subject,
                                @Pattern(regexp = "Job Seeker|Employer|Other",message = "user must be Job Seeker|Employer|Other ") String userType) implements Serializable {


}