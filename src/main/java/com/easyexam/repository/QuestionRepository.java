package com.easyexam.repository;

import java.util.List;
import java.util.Optional;

import com.easyexam.model.Question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

	@Query(value = "SELECT * FROM questions ORDER BY RANDOM() LIMIT :n", nativeQuery = true)
	Optional<List<Question>> getQuestionsUpTo(@Param("n") Integer limit);

	@Query(value = "SELECT question_id as id, content, created, updated FROM questions NATURAL JOIN (SELECT * FROM keywords K WHERE K.keywords IN :selected_keywords ) as K ORDER BY RANDOM() LIMIT :n",
			nativeQuery = true)
	Optional<List<Question>> getQuestionsByKeywords(@Param("selected_keywords") List<String> keywords,
			@Param("n") Integer limit);

}
