package com.easyexam.message.request;

import com.easyexam.model.Question;
import com.easyexam.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.Optional;
import com.easyexam.repository.QuestionRepository;
import java.util.List;
import javax.validation.constraints.*;

public class CompileExamForm {

	@NotBlank
	@Size(min = 1, max = 200)
	private String title;

	@Autowired
	QuestionRepository questionRepository;

	private List<Question> questions = new ArrayList<Question>();

	private Long courseId;

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

	public Long getCourseId() {
		return this.courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

}
