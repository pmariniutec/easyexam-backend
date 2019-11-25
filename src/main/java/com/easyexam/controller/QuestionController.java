package com.easyexam.controller;

import com.easyexam.message.request.CreateQuestionForm;
import com.easyexam.message.request.RatingAddForm;
import com.easyexam.model.Question;
import com.easyexam.message.response.SuccessfulCreation;
import com.easyexam.repository.QuestionRepository;
import com.easyexam.security.jwt.JwtUtils;
import com.easyexam.security.utils.AuthenticationUtils;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/question")
public class QuestionController {

	@Autowired
	QuestionRepository questionRepository;

	@Autowired
	AuthenticationUtils authenticationUtils;

	@Autowired
	JwtUtils jwtUtils;

	@GetMapping("")
	@PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
	public ResponseEntity<?> getQuestionsByKeywords(@RequestParam(required = false) Optional<List<String>> keywords) {
		// URL Example: {URL}/api/question?keywords=ada,analisis+algoritmos,os
		// TODO: define the limit in the env variables or something like that

		Integer limit = 3;
		Optional<List<Question>> questions;

		if (keywords.isPresent()) {
			if (keywords.get().isEmpty()) {
				return ResponseEntity.badRequest().body(
						"Keywords query string array can't be empty. If you want to fetch random queries without considering tags try to use /api/question instead of /api/question&keywords");
			}
			questions = questionRepository.getQuestionsByKeywords(keywords.get(), limit);
		}
		else {
			questions = questionRepository.getQuestionsUpTo(limit);
		}

		return ResponseEntity.ok().body(questions.orElse(List.of()));
	}

	@GetMapping("/{questionId}")
	@PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
	public ResponseEntity<?> getQuestionById(@PathVariable String questionId) {
		Long id = Long.valueOf(questionId);

		Optional<Question> question = questionRepository.findById(id);
		return ResponseEntity.ok(question.orElse(null));
	}

	@GetMapping("/{questionId}/rating")
	@PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
	public ResponseEntity<?> getQuestionRatings(@PathVariable String questionId) {
		Long id = Long.valueOf(questionId);
		Optional<Question> question = questionRepository.findById(id);

        if(!question.isPresent()){
           return ResponseEntity.badRequest().body("Cannot find question by that id");
        }

		return ResponseEntity.ok(question.get().getRatings());
	}

    @PostMapping("/{questionId}/rating")
    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
    public ResponseEntity<?> addRating(@PathVariable String questionId, @Valid @RequestBody RatingAddForm ratingRequest){
           Long id = Long.valueOf(questionId);
           Optional<Question> question = questionRepository.findById(id);
            
           if(!question.isPresent()){
               return ResponseEntity.badRequest().body("Cannot find question by that id");
           }

           Rating rating = ratingRequest.getRating();
           quetsion.get().addRating(rating);

           return ResponseEntity.ok().body("Succesfully added rating");

    }
	@PostMapping("/create")
	@PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
	public ResponseEntity<?> createQuestion(@Valid @RequestBody CreateQuestionForm createQuestionRequest) {
		Question question = new Question(createQuestionRequest.getContent(), createQuestionRequest.getKeywords());

		questionRepository.save(question);

		Field field = ReflectionUtils.findField(Question.class, "id");
		ReflectionUtils.makeAccessible(field);
		Long questionId = (Long) ReflectionUtils.getField(field, question);

	}
}
