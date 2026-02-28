package com.project.questionpapers.service;

import com.project.questionpapers.dto.QuestionPaperRequestDTO;
import com.project.questionpapers.dto.QuestionPaperResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface QuestionPaperService {

    QuestionPaperResponseDTO saveQuestionPaper(QuestionPaperRequestDTO dto);

    QuestionPaperResponseDTO getByStudentCollegeId(String studentCollegeId);

    List<QuestionPaperResponseDTO> getAllQuestionPapers();

    List<QuestionPaperResponseDTO> getBySubject(String subject);

    List<QuestionPaperResponseDTO> getByBranchAndSemester(String branch, int semester);

    List<QuestionPaperResponseDTO> getByYear(int year);

    List<QuestionPaperResponseDTO> getByExamType(String examType);

    QuestionPaperResponseDTO updateQuestionPaper(Long id, QuestionPaperRequestDTO dto);

    void deleteQuestionPaper(Long id);

    Page<QuestionPaperResponseDTO> getAllQuestionPapers(int page, int size);

    QuestionPaperResponseDTO uploadQuestionPaper(
            String studentCollegeId,
            String subject,
            String branch,
            int semester,
            int year,
            String examType,
            MultipartFile file
    ) throws IOException;
}

