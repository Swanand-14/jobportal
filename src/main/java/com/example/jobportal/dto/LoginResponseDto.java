package com.example.jobportal.dto;

public record LoginResponseDto(String message,UserDto user,String jwtToken) {

}
