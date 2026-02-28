package com.project.questionpapers.controller;

import com.project.questionpapers.dto.QuestionPaperResponseDTO;
import com.project.questionpapers.service.QuestionPaperService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;

@RestController
@RequestMapping("/api/question-papers")
public class StudentQuestionPaperController {
    private final QuestionPaperService questionPaperService;

    public StudentQuestionPaperController(QuestionPaperService questionPaperService) {
        this.questionPaperService = questionPaperService;
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {

        try {
            Path filePath = Paths.get("uploads").resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // ================= GET BY COLLEGE ID =================
    //@PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/{studentCollegeId}")
    public ResponseEntity<QuestionPaperResponseDTO> getByCollegeId(
            @PathVariable String studentCollegeId) {
        return ResponseEntity.ok(
                questionPaperService.getByStudentCollegeId(studentCollegeId)
        );
    }

    // ================= GET ALL =================
    //@PreAuthorize("hasAnyRole('STUDENT','ADMIN')")
    @GetMapping
    public ResponseEntity<List<QuestionPaperResponseDTO>> getAllQuestionPapers() {
        return ResponseEntity.ok(
                questionPaperService.getAllQuestionPapers()
        );
    }

    // ================= GET BY SUBJECT =================
    @GetMapping("/subject/{subject}")
    public ResponseEntity<List<QuestionPaperResponseDTO>> getBySubject(
            @PathVariable String subject) {
        return ResponseEntity.ok(
                questionPaperService.getBySubject(subject)
        );
    }

    // ================= GET BY BRANCH & SEMESTER =================
    @GetMapping("/branch/{branch}/semester/{semester}")
    public ResponseEntity<List<QuestionPaperResponseDTO>> getByBranchAndSemester(
            @PathVariable String branch,
            @PathVariable int semester) {
        return ResponseEntity.ok(
                questionPaperService.getByBranchAndSemester(branch, semester)
        );
    }

    // ================= GET BY YEAR =================
    @GetMapping("/year/{year}")
    public ResponseEntity<List<QuestionPaperResponseDTO>> getByYear(
            @PathVariable int year) {
        return ResponseEntity.ok(
                questionPaperService.getByYear(year)
        );
    }

    // ================= GET BY EXAM TYPE =================
    @GetMapping("/exam/{examType}")
    public ResponseEntity<List<QuestionPaperResponseDTO>> getByExamType(
            @PathVariable String examType) {
        return ResponseEntity.ok(
                questionPaperService.getByExamType(examType)
        );
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<QuestionPaperResponseDTO>> getAllQuestionPapersPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return ResponseEntity.ok(
                questionPaperService.getAllQuestionPapers(page, size)
        );
    }
}