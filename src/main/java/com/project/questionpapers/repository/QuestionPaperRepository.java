package com.project.questionpapers.repository;

import com.project.questionpapers.entity.QuestionPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface QuestionPaperRepository extends JpaRepository<QuestionPaper, Long> {

    Optional<QuestionPaper> findByStudentCollegeId(String studentCollegeId);

    List<QuestionPaper> findBySubject(String subject);

    List<QuestionPaper> findByBranchAndSemester(String branch, int semester);

    List<QuestionPaper> findByYear(int year);

    List<QuestionPaper> findByExamType(String examType);


}
