package com.easyexam.message.request;

import javax.validation.constraints.*;

public class CourseExamForm {

	private Long examId;

	private Long courseId;

	public Long getExamId() {
		return this.examId;
	}

	public void setExamId(Long examId) {
		this.examId = examId;
	}

	public Long getCourseId() {
		return this.courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

}
