package com.easyexam.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.easyexam.message.request.CreateExamForm;
import com.easyexam.message.request.PatchExamForm;
import com.easyexam.message.response.SuccessfulCreation;
import com.easyexam.model.Course;
import com.easyexam.model.Exam;
import com.easyexam.model.Question;
import com.easyexam.model.Solution;
import com.easyexam.model.User;
import com.easyexam.repository.CourseRepository;
import com.easyexam.repository.ExamRepository;
import com.easyexam.repository.QuestionRepository;
import com.easyexam.security.jwt.JwtUtils;
import com.easyexam.security.utils.AuthenticationUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ReflectionUtils;
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
@RequestMapping("/api/exam")
public class ExamController {

	private static final Logger log = LoggerFactory.getLogger(ExamController.class);

	@Autowired
    CourseRepository courseRepository;

	@Autowired
	QuestionRepository questionRepository;

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
	public ResponseEntity<?> partialUpdate(@PathVariable String examId, @RequestBody PatchExamForm requestForm) {
		// TODO: atm questions in patch request will override current
		// questions and other questions will be left on air.

		Long id = Long.valueOf(examId);
		Optional<Exam> exam = examRepository.findById(id);

		if (exam.isEmpty()) {
			return ResponseEntity.badRequest().body("Cannot find exam by that id");
		}

		Optional<String> title = requestForm.getTitle();
		Optional<List<Question>> questions = requestForm.getQuestions();

		if (title.isPresent()) {
			exam.get().setTitle(title.get());
		}
		if (questions.isPresent()) {
			exam.get().setQuestions(questions.get());
		}
		examRepository.save(exam.get());

		return ResponseEntity.ok().body("Successfully updated the exam.");

	}

	@PostMapping("/create")
	@PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
	public ResponseEntity<?> createUserExam(@Valid @RequestBody CreateExamForm createExamRequest) {

        ArrayList<Question> questions = new ArrayList<Question>(createExamRequest.getQuestions().size());
        
        for (Question q : createExamRequest.getQuestions()) {
            if (q.getId() == null) {
                log.info("Question with no id: ", q.getId());
                Question question = new Question(q.getContent(), q.getKeywords());
                question = questionRepository.saveAndFlush(question);
                questions.add(question);
            }
            else {
                questions.add(q);
            }
        }

        Exam exam = new Exam(createExamRequest.getTitle(), questions);

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
