package com.easyexam.controller;

import com.easyexam.message.request.AddExamToCourseForm;
import com.easyexam.message.request.CreateCourseForm;
import com.easyexam.model.Course;
import com.easyexam.model.Exam;
import com.easyexam.model.User;
import com.easyexam.repository.CourseRepository;
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
@RequestMapping("/api/course")
public class CourseController {

  @Autowired CourseRepository courseRepository;
  @Autowired ExamRepository examRepository;
  @Autowired AuthenticationUtils authenticationUtils;

  @Autowired JwtUtils jwtUtils;

  @GetMapping("")
  @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
  public ResponseEntity<?> getUserCourses() {
    String email = authenticationUtils.getAuthenticatedUserEmail();

    Optional<List<Course>> courses = courseRepository.findUserCourses(email);
    return ResponseEntity.ok(courses.get());
  }

  @PostMapping("/create")
  @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
  public ResponseEntity<?> createUserCourse(
      @Valid @RequestBody CreateCourseForm createCourseRequest) {
    Course course = new Course(createCourseRequest.getName(), createCourseRequest.getCode());

    // get authenticated user
    User user = authenticationUtils.getUserObject();
    course.setUser(user);

    courseRepository.save(course);
    return ResponseEntity.ok().body("Successfully created course");
  }

  @PostMapping("/exam/add")
  @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
  public ResponseEntity<?> addExamToCourse(@Valid @RequestBody AddExamToCourseForm addExamRequest) {
    // TODO: Handle exceptions
    Course course = courseRepository.getOne(addExamRequest.getCourseId());
    Optional<Exam> exam = examRepository.findById(addExamRequest.getExamId());
    course.setExam(exam.get());

    courseRepository.save(course);
    return ResponseEntity.ok().body("Successfully added exam to course");
  }
}
