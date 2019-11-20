package com.easyexam.message.request;

import com.easyexam.model.Question;
import javax.validation.constraints.*;

public class CreateSolutionForm {

  @NotBlank
  @Size(min = 1, max = 200)
  private String title;

  @NotBlank private String content;

  private Question question;

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Question getQuestion() {
    return this.question;
  }

  public void setQuestion(Question question) {
    this.question = question;
  }
}
