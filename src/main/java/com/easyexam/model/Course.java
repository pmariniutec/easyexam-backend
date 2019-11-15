package com.easyexam.model;

import com.easyexam.model.utils.TimestampedEntity;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "courses")
public class Course extends TimestampedEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(min = 1, max = 60)
  private String name;

  @NotBlank
  @Size(min = 5, max = 20)
  private String code;

  @ManyToOne @NotBlank private User user;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "course_exams",
      joinColumns = @JoinColumn(name = "course_id"),
      inverseJoinColumns = @JoinColumn(name = "exam_id"))
  private List<Exam> exams;

  public Course() {}

  public Course(String name, String code) {
    this.name = name;
    this.code = code;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public List<Exam> getExams() {
    return this.exams;
  }

  public void setExams(List<Exam> exams) {
    this.exams = exams;
  }

  public void setExam(Exam exam) {
    this.exams.add(exam);
  }

  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
