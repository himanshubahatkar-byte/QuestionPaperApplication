package com.project.questionpapers.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank
    private String role;   // STUDENT or ADMIN

    @NotBlank
    private String studentCollegeId;

    @NotBlank
    private String password;
}
