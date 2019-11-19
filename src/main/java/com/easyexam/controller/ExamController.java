package com.easyexam.controller;

import com.easyexam.message.request.CreateExamForm;
import com.easyexam.model.Exam;
import com.easyexam.model.User;
import com.easyexam.repository.ExamRepository;
import com.easyexam.security.jwt.JwtUtils;
import com.easyexam.security.utils.AuthenticationUtils;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/exam")
public class ExamController {

  @Autowired ExamRepository examRepository;
  @Autowired AuthenticationUtils authenticationUtils;

  @Autowired JwtUtils jwtUtils;

  @GetMapping("")
  @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
  public ResponseEntity<?> getUserExams() {
    String email = authenticationUtils.getAuthenticatedUserEmail();

    Optional<List<Exam>> exams = examRepository.findUserExams(email);
    return ResponseEntity.ok(exams.get());
  }

  @PostMapping("/create")
  @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
  public ResponseEntity<?> createUserExam(@Valid @RequestBody CreateExamForm createExamRequest) {
    Exam exam =
        new Exam(
            createExamRequest.getTitle(),
            createExamRequest.getQuestions(),
            createExamRequest.getKeywords());

    // get authenticated user
    User user = authenticationUtils.getUserObject();
    exam.setUser(user);

    // TODO: Search each question solution (if present) and add it

    examRepository.save(exam);
    return ResponseEntity.ok().body("Successfully created exam");
  }
}
