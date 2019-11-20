package com.easyexam.controller;

import com.easyexam.message.request.CreateQuestionForm;
import com.easyexam.model.Question;
import com.easyexam.repository.QuestionRepository;
import com.easyexam.security.jwt.JwtUtils;
import com.easyexam.security.utils.AuthenticationUtils;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/question")
public class QuestionController {

  @Autowired QuestionRepository questionRepository;
  @Autowired AuthenticationUtils authenticationUtils;

  @Autowired JwtUtils jwtUtils;

  @GetMapping("/{questionId}")
  @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
  public ResponseEntity<?> getCourseExams(@PathVariable String questionId) {
    Long id = Long.valueOf(questionId);

    Question question = questionRepository.findById(id).get();
    return ResponseEntity.ok(question);
  }

  @PostMapping("/create")
  @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
  public ResponseEntity<?> createQuestion(
      @Valid @RequestBody CreateQuestionForm createQuestionRequest) {
    Question question =
        new Question(createQuestionRequest.getTitle(), createQuestionRequest.getContent());

    questionRepository.save(question);
    return ResponseEntity.ok().body("Successfully created question");
  }
}
