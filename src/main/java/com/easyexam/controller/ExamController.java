package com.easyexam.controller;

import com.easyexam.message.request.CreateExamForm;
import com.easyexam.model.Exam;
import com.easyexam.model.User;
import com.easyexam.repository.ExamRepository;
import com.easyexam.security.jwt.JwtUtils;
import com.easyexam.security.utils.AuthenticationUtils;
import java.util.List;
import java.util.HashMap;
import java.util.Optional;
import javax.validation.Valid;
import com.easyexam.message.response.SuccessfulCreation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;

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

    return ResponseEntity.ok(exams.orElse(List.of()));
  }
    
  @GetMapping("/{examId}")
  @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
    public ResponseEntity<?> getExamById(@PathVariable String examId) {
    Long id = Long.valueOf(examId);

    Optional<Exam> exam = examRepository.findById(id);
    return ResponseEntity.ok(exam.orElse(null));
  }

    @PostMapping("/create")
    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
    public ResponseEntity<?> createUserExam(@Valid @RequestBody CreateExamForm createExamRequest) {

        Exam exam =
            new Exam(
                createExamRequest.getTitle(),
                createExamRequest.getQuestions(),
                createExamRequest.getKeywords());

        User user = authenticationUtils.getUserObject();
        exam.setUser(user);

        // TODO: Search each question solution (if present) and add it

        examRepository.save(exam);

        Field field = ReflectionUtils.findField(Exam.class, "id");
        ReflectionUtils.makeAccessible(field);
        Long examId = (Long) ReflectionUtils.getField(field, exam);

        return ResponseEntity.ok().body(new SuccessfulCreation(examId, "Exam"));
  }
}
