package com.easyexam.repository;
import com.easyexam.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
  @Query("select q from Question q")
    Optional<List<Question>> getQuestions();
}
