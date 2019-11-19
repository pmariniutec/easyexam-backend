package com.easyexam.message.request;

import com.easyexam.model.Question;
// import com.easyexam.model.Solution;
import com.easyexam.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;

public class CreateExamForm {

  @NotBlank
  @Size(min = 1, max = 200)
  private String title;

  private User user;

  private List<Question> questions = new ArrayList<Question>();
  // private List<Solution> solutions = new ArrayList<Solution>();
  private List<String> keywords = new ArrayList<String>();

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public List<Question> getQuestions() {
    return this.questions;
  }

  public void setQuestions(List<Question> questions) {
    this.questions = questions;
  }

  public void setQuestion(Question question) {
    this.questions.add(question);
  }

  public List<String> getKeywords() {
    return this.keywords;
  }

  public void setKeywords(List<String> keywords) {
    this.keywords = keywords;
  }
}
