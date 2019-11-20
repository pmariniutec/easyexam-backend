package com.easyexam.message.request;

import com.easyexam.model.Question;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;

public class CreateQuestionForm {

  @NotBlank
  @Size(min = 1, max = 200)
  private String title;

  @NotBlank
  private String content;

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

}
