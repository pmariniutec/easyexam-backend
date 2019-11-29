package com.easyexam.message.request;

import com.easyexam.model.Exam;
import com.easyexam.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;

public class CreateCourseForm {

	@NotBlank
    @Size(min = 1, max = 60, message = "Course name length must be between 1 and 60")
	private String name;

	@NotBlank
    @Size(min = 4, max = 20, message = "Course code length must be between 4 and 20")
	private String code;

	private User user;

	private List<Exam> exams = new ArrayList<Exam>();

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

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
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

}
