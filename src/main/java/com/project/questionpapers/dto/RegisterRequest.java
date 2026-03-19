package com.project.questionpapers.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank
    @Pattern(regexp="[A-Z0-9]{10}", message = "College ID must be exactly 10 alphanumeric characters")
    private String studentCollegeId;

    @NotBlank
    private String fullName;

    @Email
    private String email;

    @Pattern(regexp="\\d{10}")
    private String mobileNumber;

    @NotBlank
    private String department;

    @NotBlank
    private String password;
}
