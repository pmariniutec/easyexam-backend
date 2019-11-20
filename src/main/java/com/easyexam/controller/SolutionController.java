package com.easyexam.controller;

import com.easyexam.message.request.CreateSolutionForm;
import com.easyexam.model.Solution;
import com.easyexam.repository.SolutionRepository;
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
@RequestMapping("/api/solution")
public class SolutionController {

  @Autowired SolutionRepository solutionRepository;
  @Autowired AuthenticationUtils authenticationUtils;

  @Autowired JwtUtils jwtUtils;

  @GetMapping("/{solutionId}")
  @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
  public ResponseEntity<?> getCourseExams(@PathVariable String solutionId) {
    Long id = Long.valueOf(solutionId);

    Solution solution = solutionRepository.findById(id).get();
    return ResponseEntity.ok(solution);
  }

  @PostMapping("/create")
  @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
  public ResponseEntity<?> createSolution(
      @Valid @RequestBody CreateSolutionForm createSolutionRequest) {
    Solution solution =
        new Solution(createSolutionRequest.getTitle(), createSolutionRequest.getContent());

    solutionRepository.save(solution);
    return ResponseEntity.ok().body("Successfully created solution");
  }
}
