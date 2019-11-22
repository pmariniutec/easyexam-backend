package com.easyexam.repository;
import com.easyexam.model.Exam;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
  @Query("select e from Exam e where e.user.email = ?1")
  Optional<List<Exam>> findUserExams(String email);
}
