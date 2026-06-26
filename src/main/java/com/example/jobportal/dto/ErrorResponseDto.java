package com.example.jobportal.dto;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResponseDto(String apiPath, HttpStatus errorcode, String errorMessage, LocalDateTime errorTime) {



}
