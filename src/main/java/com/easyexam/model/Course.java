package com.easyexam.model;

import com.easyexam.model.utils.TimestampedEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Set;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "courses")
@JsonIgnoreProperties(allowGetters = true)
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

	@JsonIgnore
	@ManyToOne
	private User user;

	@OneToMany(mappedBy = "course")
	private Set<Exam> exams;

	public Course() {
	}

	public Course(String name, String code) {
		this.name = name;
		this.code = code;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Set<Exam> _getExams() {
		return this.exams;
	}

	public void setExams(Set<Exam> exams) {
		this.exams = exams;
	}

	public void addExam(Exam exam) {
		this.exams.add(exam);
		exam.setCourse(this);
	}

	public void removeExam(Exam exam) {
		this.exams.remove(exam);
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
