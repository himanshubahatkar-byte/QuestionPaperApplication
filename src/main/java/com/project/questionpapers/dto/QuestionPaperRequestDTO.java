package com.project.questionpapers.dto;
import jakarta.validation.constraints.*;

public class QuestionPaperRequestDTO {

    @NotBlank(message = "Student College ID is required")
    @Pattern(regexp = "\\d{11}", message = "College ID must be exactly 11 digits")
    private String studentCollegeId;

    @NotBlank(message = "Subject is required")
    private String subject;

    @NotBlank(message = "Branch is required")
    private String branch;

    @Min(value = 1, message = "Semester must be at least 1")
    @Max(value = 8, message = "Semester cannot exceed 8")
    private int semester;

    @Min(value = 2000, message = "Year must be valid")
    private int year;

    @NotBlank(message = "Exam type is required")
    private String examType;

    @NotBlank(message = "File path is required")
    private String filePath;

    // Getters & Setters
    public String getStudentCollegeId() {
        return studentCollegeId;
    }

    public void setStudentCollegeId(String studentCollegeId) {
        this.studentCollegeId = studentCollegeId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}

