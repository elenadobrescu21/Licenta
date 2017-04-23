package com.aii.platform.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="comentariu")
@Table(name="comentariu")
public class Comment {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="continut", nullable=false)
	@Size(min=5, max=1500)
	private String continut;
	
	
	@ManyToOne
	@JoinColumn(name = "appUserId", nullable = false)
	private AppUser appUser;
	
	@ManyToOne
	@JoinColumn(name="comentarii")
	@JsonIgnore
	private UploadedArticle article;
	
	public Comment() {
		
	}
	
	public Comment(String continut, AppUser owner, UploadedArticle article) {
		this.continut = continut;
		this.appUser = owner;
		this.article = article;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContinut() {
		return continut;
	}

	public void setContinut(String continut) {
		this.continut = continut;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public UploadedArticle getArticle() {
		return article;
	}

	public void setArticle(UploadedArticle article) {
		this.article = article;
	}
	
	
	

}
