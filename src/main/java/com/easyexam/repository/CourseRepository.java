package com.easyexam.repository;

import com.easyexam.model.Course;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

  @Query("select c from Course c where c.user.email = ?1")
  Optional<List<Course>> findUserCourses(String email);
}
