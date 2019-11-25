package com.easyexam.message.request;

import com.easyexam.model.Question;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.*;

public class PatchExamForm {

	@Size(min = 1, max = 200)
	private String title;

	private List<Question> questions = new ArrayList<Question>();

	private List<String> keywords = new ArrayList<String>();

	public Optional<String> getTitle() {
		return Optional.ofNullable(this.title);
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Optional<List<Question>> getQuestions() {
		return Optional.ofNullable(this.questions);
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public Optional<List<String>> getKeywords() {
		return Optional.ofNullable(this.keywords);
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

}
