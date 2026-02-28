package com.project.questionpapers.dto;

import lombok.Getter;
import lombok.Setter;

public class RegisterRequest {

    private String studentCollegeId; // student college id
    @Getter
    @Setter
    private String password;

    // getters and setters


    public String getStudentCollegeId() {
        return studentCollegeId;
    }

    public void setStudentCollegeId(String studentCollegeId) {
        this.studentCollegeId = studentCollegeId;
    }
}
