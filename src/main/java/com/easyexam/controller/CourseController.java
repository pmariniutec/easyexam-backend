package com.easyexam.controller;

import com.easyexam.message.request.CourseExamForm;
import com.easyexam.message.request.CreateCourseForm;
import com.easyexam.model.Course;
import com.easyexam.message.response.SuccessfulCreation;
import com.easyexam.model.Exam;
import com.easyexam.model.User;
import com.easyexam.repository.CourseRepository;
import com.easyexam.repository.ExamRepository;
import com.easyexam.security.jwt.JwtUtils;
import com.easyexam.security.utils.AuthenticationUtils;
import java.util.List;
import java.util.Set;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/course")
public class CourseController {

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
	public ResponseEntity<?> getUserCourses() {
		String email = authenticationUtils.getAuthenticatedUserEmail();

		Optional<List<Course>> data = courseRepository.findUserCourses(email);

		return ResponseEntity.ok(data.orElse(List.of()));
	}

	@GetMapping("/{courseId}")
	@PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
	public ResponseEntity<?> getCourse(@PathVariable String courseId) {
		Long id = Long.valueOf(courseId);

		Optional<Course> course = courseRepository.findById(id);
		return ResponseEntity.ok(course.orElse(null));
	}

	@GetMapping("/{courseId}/exams")
	@PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
	public ResponseEntity<?> getCourseExams(@PathVariable String courseId) {
		Long id = Long.valueOf(courseId);
		Optional<Course> course = courseRepository.findById(id);

		if (course.isEmpty()) {
			return ResponseEntity.badRequest().body("Course not found");
		}

		Optional<Set<Exam>> exams = Optional.of(course.get()._getExams());
		return ResponseEntity.ok(exams.orElse(Set.of()));
	}

	@PostMapping("/create")
	@PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
	public ResponseEntity<?> createUserCourse(@Valid @RequestBody CreateCourseForm createCourseRequest) {
		Course course = new Course(createCourseRequest.getName(), createCourseRequest.getCode());

		// get authenticated user
		User user = authenticationUtils.getUserObject();
		course.setUser(user);

		courseRepository.save(course);

		Field field = ReflectionUtils.findField(Course.class, "id");
		ReflectionUtils.makeAccessible(field);
		Long courseId = (Long) ReflectionUtils.getField(field, course);

		return ResponseEntity.ok().body(new SuccessfulCreation(courseId, "Course"));
	}

	@PostMapping("/exam/attach")
	@PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
	public ResponseEntity<?> attachExamToCourse(@Valid @RequestBody CourseExamForm attachExamRequest) {
		Long examId = attachExamRequest.getExamId();
		Long courseId = attachExamRequest.getCourseId();

		Optional<Course> course = courseRepository.findById(courseId);
		Optional<Exam> exam = examRepository.findById(examId);

		if (exam.isEmpty()) {
			return ResponseEntity.badRequest().body("Cannot find an exam with id: " + examId.toString());
		}
		if (course.isEmpty()) {
			return ResponseEntity.badRequest().body("Cannot find a course with id: " + courseId.toString());
		}

		course.get().addExam(exam.get());

		courseRepository.save(course.get());
		return ResponseEntity.ok().body("Successfully attach exam with id " + examId.toString()
				+ " from course with id " + courseId.toString());
	}

	@PostMapping("/exam/detach")
	@PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
	public ResponseEntity<?> detachExamFromCourse(@Valid @RequestBody CourseExamForm detachExamRequest) {
		Long examId = detachExamRequest.getExamId();
		Long courseId = detachExamRequest.getCourseId();

		Optional<Course> course = courseRepository.findById(courseId);
		Optional<Exam> exam = examRepository.findById(examId);

		if (exam.isEmpty()) {
			return ResponseEntity.badRequest().body("Cannot find an exam with id: " + examId.toString());
		}
		if (course.isEmpty()) {
			return ResponseEntity.badRequest().body("Cannot find a course with id: " + courseId.toString());
		}

		course.get().removeExam(exam.get());
		exam.get().removeCourse(course.get());

		return ResponseEntity.ok().body("Successfully detached exam with id " + examId.toString()
				+ " from course with id " + courseId.toString());
	}

	@DeleteMapping("/{courseId}")
	@PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
	public ResponseEntity<?> deleteCourse(@PathVariable String courseId) {
		Long id = Long.valueOf(courseId);
		Optional<Course> course = courseRepository.findById(id);
		if (!course.isPresent()) {
			return ResponseEntity.badRequest().body("Cannot find course by id: " + courseId);
		}

		Set<Exam> exams = course.get()._getExams();

		for (Exam exam : exams) {
			exam.setCourse(null);
		}

		courseRepository.delete(course.get());

		return ResponseEntity.ok().body("Deleted course with id " + courseId);
	}

}
