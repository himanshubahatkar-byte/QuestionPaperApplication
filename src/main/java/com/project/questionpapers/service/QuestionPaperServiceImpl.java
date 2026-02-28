package com.project.questionpapers.service;

import com.project.questionpapers.dto.QuestionPaperRequestDTO;
import com.project.questionpapers.dto.QuestionPaperResponseDTO;
import com.project.questionpapers.entity.QuestionPaper;
import com.project.questionpapers.exception.ResourceNotFoundException;
import com.project.questionpapers.repository.QuestionPaperRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class QuestionPaperServiceImpl implements QuestionPaperService {

    private final QuestionPaperRepository repository;

    // ================= SAVE =================
    @Override
    public QuestionPaperResponseDTO saveQuestionPaper(QuestionPaperRequestDTO dto) {

        // DTO → Entity
        QuestionPaper paper = new QuestionPaper();
        paper.setStudentCollegeId(dto.getStudentCollegeId());
        paper.setSubject(dto.getSubject());
        paper.setBranch(dto.getBranch());
        paper.setSemester(dto.getSemester());
        paper.setYear(dto.getYear());
        paper.setExamType(dto.getExamType());
        paper.setFilePath(dto.getFilePath());

        // Save to DB
        QuestionPaper savedPaper = repository.save(paper);

        // Entity → Response DTO
        return mapToResponseDTO(savedPaper);
    }

    // UPDATE

    @Override
    public QuestionPaperResponseDTO updateQuestionPaper(Long id, QuestionPaperRequestDTO dto) {

        QuestionPaper paper = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Question paper not found with id: " + id));

        paper.setStudentCollegeId(dto.getStudentCollegeId());
        paper.setSubject(dto.getSubject());
        paper.setBranch(dto.getBranch());
        paper.setSemester(dto.getSemester());
        paper.setYear(dto.getYear());
        paper.setExamType(dto.getExamType());
        paper.setFilePath(dto.getFilePath());

        QuestionPaper updatedPaper = repository.save(paper);

        return mapToResponseDTO(updatedPaper);
    }

    //DELETE

    @Override
    public void deleteQuestionPaper(Long id) {

        QuestionPaper paper = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Question paper not found with id: " + id));

        repository.delete(paper);
    }


    // ================= GET BY COLLEGE ID =================
    @Override
    public QuestionPaperResponseDTO getByStudentCollegeId(String studentCollegeId) {

        QuestionPaper paper = repository
                .findByStudentCollegeId(studentCollegeId)
                .orElseThrow(() -> new ResourceNotFoundException("Question paper not found with studentCollegeId: " + studentCollegeId));

        return mapToResponseDTO(paper);
    }

    // ================= GET ALL =================
    @Override
    public List<QuestionPaperResponseDTO> getAllQuestionPapers() {

        return repository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // ================= GET BY SUBJECT =================
    @Override
    public List<QuestionPaperResponseDTO> getBySubject(String subject) {

        return repository.findBySubject(subject)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // ================= GET BY BRANCH & SEMESTER =================
    @Override
    public List<QuestionPaperResponseDTO> getByBranchAndSemester(String branch, int semester) {

        return repository.findByBranchAndSemester(branch, semester)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // ================= GET BY YEAR =================
    @Override
    public List<QuestionPaperResponseDTO> getByYear(int year) {

        return repository.findByYear(year)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // ================= GET BY EXAM TYPE =================
    @Override
    public List<QuestionPaperResponseDTO> getByExamType(String examType) {

        return repository.findByExamType(examType)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<QuestionPaperResponseDTO> getAllQuestionPapers(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<QuestionPaper> questionPapers = repository.findAll(pageable);

        return questionPapers.map(this::mapToResponseDTO);
    }

    // ================= MAPPER METHOD =================
    private QuestionPaperResponseDTO mapToResponseDTO(QuestionPaper paper) {

        QuestionPaperResponseDTO response = new QuestionPaperResponseDTO();
        response.setId(paper.getId());
        response.setStudentCollegeId(paper.getStudentCollegeId());
        response.setSubject(paper.getSubject());
        response.setBranch(paper.getBranch());
        response.setSemester(paper.getSemester());
        response.setYear(paper.getYear());
        response.setExamType(paper.getExamType());
        response.setFilePath(paper.getFilePath());
        response.setUploadedAt(paper.getUploadedAt());

        return response;
    }

    @Override
    public QuestionPaperResponseDTO uploadQuestionPaper(
            String studentCollegeId,
            String subject,
            String branch,
            int semester,
            int year,
            String examType,
            MultipartFile file
    ) throws IOException {

        // Create uploads folder if not exists
        String uploadDir = "uploads/";
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Create unique file name
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);

        // Save file
        Files.write(filePath, file.getBytes());

        // Save in database
        QuestionPaper questionPaper = new QuestionPaper();
        questionPaper.setStudentCollegeId(studentCollegeId);
        questionPaper.setSubject(subject);
        questionPaper.setBranch(branch);
        questionPaper.setSemester(semester);
        questionPaper.setYear(year);
        questionPaper.setExamType(examType);
        questionPaper.setFilePath(filePath.toString());
        questionPaper.setUploadedAt(LocalDateTime.now());

        QuestionPaper saved = repository.save(questionPaper);

        return mapToResponseDTO(saved);
    }
}

