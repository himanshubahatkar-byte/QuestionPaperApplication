package com.project.questionpapers.controller;

import com.project.questionpapers.dto.QuestionPaperRequestDTO;
import com.project.questionpapers.dto.QuestionPaperResponseDTO;
import com.project.questionpapers.service.QuestionPaperService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import java.util.List;

@RestController
@RequestMapping("/api/admin/question-papers")
public class AdminQuestionPaperController {

    private final QuestionPaperService questionPaperService;

    public AdminQuestionPaperController(QuestionPaperService questionPaperService) {
        this.questionPaperService = questionPaperService;
    }

    // SAVE
    @PostMapping("/upload")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<QuestionPaperResponseDTO> uploadQuestionPaper(
            @RequestParam String studentCollegeId,
            @RequestParam String subject,
            @RequestParam String branch,
            @RequestParam int semester,
            @RequestParam int year,
            @RequestParam String examType,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        QuestionPaperResponseDTO response =
                questionPaperService.uploadQuestionPaper(
                        studentCollegeId,
                        subject,
                        branch,
                        semester,
                        year,
                        examType,
                        file
                );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET ALL
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<QuestionPaperResponseDTO>> getAllQuestionPapers() {

        return ResponseEntity.ok(
                questionPaperService.getAllQuestionPapers()
        );
    }

    // UPDATE
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<QuestionPaperResponseDTO> updateQuestionPaper(
            @PathVariable Long id,
            @Valid @RequestBody QuestionPaperRequestDTO dto) {

        return ResponseEntity.ok(
                questionPaperService.updateQuestionPaper(id, dto)
        );
    }

    // DELETE
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestionPaper(@PathVariable Long id) {

        questionPaperService.deleteQuestionPaper(id);
        return ResponseEntity.noContent().build();
    }
}

