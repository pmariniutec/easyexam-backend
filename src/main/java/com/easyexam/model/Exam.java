package com.easyexam.model;

import com.easyexam.model.utils.TimestampedEntity;
import java.util.ArrayList;
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
import javax.validation.constraints.Size;
import javax.persistence.CascadeType;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "exams")
public class Exam extends TimestampedEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(min = 1, max = 200)
	private String title;

	@ManyToOne
	private User user;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinTable(name = "exam_questions", joinColumns = @JoinColumn(name = "exam_id"),
			inverseJoinColumns = @JoinColumn(name = "question_id"))
	private List<Question> questions = new ArrayList<Question>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "exam_solutions", joinColumns = @JoinColumn(name = "exam_id"),
			inverseJoinColumns = @JoinColumn(name = "solution_id"))
	private List<Solution> solutions = new ArrayList<Solution>();

	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;

	public Exam() {
	}

	public Exam(String title, List<Question> questions) {
		this.title = title;
		this.questions = questions;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return this.user;
	}

	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

}
