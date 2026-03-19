package com.project.questionpapers.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AuthenticationRequest {

    @NotBlank
    @Pattern(regexp = "[A-Z0-9]{10}", message = "College ID must be exactly 10 alphanumeric characters")
    private String studentCollegeId;

    @NotBlank
    private String password;

    public AuthenticationRequest() {}

    public String getStudentCollegeId() {
        return studentCollegeId;
    }

    public void setStudentCollegeId(String studentCollegeId) {
        this.studentCollegeId = studentCollegeId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

