package com.easyexam.controller;

import com.easyexam.message.request.CreateExamForm;
import com.easyexam.model.Course;
import com.easyexam.model.Exam;
import com.easyexam.model.User;
import com.easyexam.model.Question;
import com.easyexam.model.Solution;
import com.easyexam.repository.CourseRepository;
import com.easyexam.repository.ExamRepository;
import com.easyexam.security.jwt.JwtUtils;
import com.easyexam.security.utils.AuthenticationUtils;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import com.easyexam.message.response.SuccessfulCreation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/exam")
public class ExamController {

	private static final Logger log = LoggerFactory.getLogger(ExamController.class);

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	ExamRepository examRepository;

	@Autowired
	AuthenticationUtils authenticationUtils;

	@Autowired
	JwtUtils jwtUtils;

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

	@PatchMapping("/{examId}")
	@PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
	public ResponseEntity<?> partialUpdate(@RequestBody Map<String, Object> fields) {
		Long id = (Long) fields.get("id");
		Optional<Exam> exam = examRepository.findById(id);

		if (!exam.isPresent()) {
			return ResponseEntity.badRequest().body("Cannot find exam by that id");
		}

		fields.forEach((k, v) -> {
			Field field = ReflectionUtils.findField(Exam.class, k);
			ReflectionUtils.makeAccessible(field);
			ReflectionUtils.setField(field, exam.get(), v);
		});
		examRepository.saveAndFlush(exam.get());

		return ResponseEntity.ok().body("Successfully updated the exam.");

	}

	@PostMapping("/create")
	@PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
	public ResponseEntity<?> createUserExam(@Valid @RequestBody CreateExamForm createExamRequest) {

		Exam exam = new Exam(
      createExamRequest.getTitle(), 
      createExamRequest.getQuestions()
    );

		User user = authenticationUtils.getUserObject();
		exam.setUser(user);

		// TODO: Search each question solution (if present) and add it

		Exam savedExam = examRepository.saveAndFlush(exam);

		if (createExamRequest.getCourseId() != null) {
			Course course = courseRepository.getOne(createExamRequest.getCourseId());
			course.addExam(savedExam);
			courseRepository.save(course);
			examRepository.save(exam);
		}

		Field field = ReflectionUtils.findField(Exam.class, "id");
		ReflectionUtils.makeAccessible(field);
		Long examId = (Long) ReflectionUtils.getField(field, exam);

		return ResponseEntity.ok().body(new SuccessfulCreation(examId, "Exam"));
	}

	@DeleteMapping("/{examId}")
	@PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
	public ResponseEntity<?> deleteExam(@PathVariable String examId) {
		Long id = Long.valueOf(examId);
		Optional<Exam> exam = examRepository.findById(id);

		if (!exam.isPresent()) {
			return ResponseEntity.badRequest().body("Cannot find exam by that id");
		}

		Optional<Course> course = Optional.of(exam.get().getCourse());
		if (course.isPresent()) {
			course.get().removeExam(exam.get());
		}

		for (Question q : exam.get().getQuestions()) {
			q.removeExam(exam.get());
		}

		for (Solution s : exam.get().getSolutions()) {
			s.removeExam(exam.get());
		}

		examRepository.delete(exam.get());

		return ResponseEntity.ok().body("Successfully deleted Exam with id: " + examId);
	}

}
