package com.easyexam.model;

import com.easyexam.model.utils.TimestampedEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "exams")
public class Exam extends TimestampedEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(min = 1, max = 200)
  private String title;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "exam_questions",
      joinColumns = @JoinColumn(name = "exam_id"),
      inverseJoinColumns = @JoinColumn(name = "question_id"))
  private List<Question> questions = new ArrayList<>();

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "exam_solutions",
      joinColumns = @JoinColumn(name = "exam_id"),
      inverseJoinColumns = @JoinColumn(name = "solution_id"))
  private List<Solution> solutions = new ArrayList<>();

  @NotBlank
  @ElementCollection
  @CollectionTable(name = "keywords")
  private List<String> keywords = new ArrayList<>();

  public Exam(
      String title, List<Question> questions, List<Solution> solutions, List<String> keywords) {
    this.title = title;
    this.questions = questions;
    this.solutions = solutions;
    this.keywords = keywords;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<Question> getQuestions() {
    return this.questions;
  }

  public void setQuestions(List<Question> questions) {
    this.questions = questions;
  }

  public List<Solution> getSolutions() {
    return this.solutions;
  }

  public void setSolutions(List<Solution> solutions) {
    this.solutions = solutions;
  }

  public List<String> getKeywords() {
    return this.keywords;
  }

  public void setKeywords(List<String> keywords) {
    this.keywords = keywords;
  }
}
