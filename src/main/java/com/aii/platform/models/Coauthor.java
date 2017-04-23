package com.aii.platform.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="autor_fara_cont")
@Table(name="autor_fara_cont")
public class Coauthor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="nume", nullable=false, unique=true)
	private String fullname;
	
	@ManyToMany(mappedBy = "coauthorsWithoutAccount")
	private List<UploadedArticle> articole = new ArrayList<UploadedArticle>();
		
	public Coauthor() {
		
	}

	public Coauthor(String fullname) {
		this.id = id;
		this.fullname = fullname;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	public void adaugaArticol(UploadedArticle uploadedArticle){
		this.articole.add(uploadedArticle);
	}
	

}
