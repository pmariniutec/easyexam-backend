package com.easyexam.model;

import com.easyexam.model.utils.TimestampedEntity;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "questions")
public class Question extends TimestampedEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @OneToMany(mappedBy = "question")
    private List<Rating> ratings;

	@NotBlank
	@Column(columnDefinition = "text")
	private String content;

	@ManyToMany(mappedBy = "questions")
	private List<Exam> exams = new ArrayList<Exam>();

	@ElementCollection
	@CollectionTable(name = "keywords")
	private List<String> keywords = new ArrayList<String>();

	public Question() {
	}

	public Question(String content, List<String> keywords) {
		this.content = content;
		this.keywords = keywords;
	}

	public Long getId() {
		return this.id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getKeywords() {
		return this.keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public void removeExam(Exam exam) {
		this.exams.remove(exam);
	}
    public List<Rating> getRatings(){
        return this.ratings;
    }

    public void addRating(Rating rate){
        this.ratings.add(rate);
    }

}
