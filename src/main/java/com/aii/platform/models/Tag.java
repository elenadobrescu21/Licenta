package com.aii.platform.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity(name="tag")
@Table(name="tag")
public class Tag {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tagId;
	
	@Column(name="denumire", nullable=false, unique=true)
	private String denumire;
	
	@ManyToMany
	@JoinTable(name = "article_tag", 
	joinColumns = {
	@JoinColumn(name="tag_id",
				referencedColumnName = "tagId"
			)
	},
		inverseJoinColumns = {
				@JoinColumn(name="article_id",
						referencedColumnName = "uploadedArticleId")
		})
	private List<UploadedArticle> articles = new ArrayList<UploadedArticle>();
	
	public Tag(){
		
	}
	
	public Tag(String denumire) {
		this.denumire = denumire;
	}

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public String getDenumire() {
		return denumire;
	}

	public void setDenumire(String denumire) {
		this.denumire = denumire;
	}

	public List<UploadedArticle> getArticles() {
		return articles;
	}

	public void setArticles(List<UploadedArticle> articles) {
		this.articles = articles;
	}
	
	public void addArticle(UploadedArticle article) {
		this.articles.add(article);
	}
	

}
