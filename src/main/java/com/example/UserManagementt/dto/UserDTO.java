package com.example.UserManagementt.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
    @Data
    public class UserDTO{
        @NotBlank(message = "Name is required")
        @Size(min = 3, message = "Name must be between 3-50 characters", max = 50)
        private String name;

        @Email(message = "invalid email format")
        @NotBlank(message = "email is required")
        private String email;

        @NotBlank(message = "birth date is required(yyyy-mm-dd format)")
        private String birthDate;

        @NotBlank(message = "password is required")
        @Size(min = 6, message = ("Password must be at least 6 characters"))
        private String password;

    }