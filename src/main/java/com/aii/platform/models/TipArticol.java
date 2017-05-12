package com.aii.platform.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="tip_articol")
@Table(name="tip_articol")
public class TipArticol {
	
	@Id
	private Long id;
	
	@Column
	private String denumire;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy="tipArticol", orphanRemoval = true)
	@JsonIgnore
	private List<UploadedArticle> articole = new ArrayList<UploadedArticle>();
	
	public TipArticol() {
		
	}
	
	public TipArticol(Long id, String denumire) {
		this.id = id;
		this.denumire = denumire;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDenumire() {
		return denumire;
	}

	public void setDenumire(String denumire) {
		this.denumire = denumire;
	}

	public List<UploadedArticle> getArticole() {
		return articole;
	}

	public void setArticole(List<UploadedArticle> articole) {
		this.articole = articole;
	}
	
	public void addArticol(UploadedArticle uploadedArticle) {
		this.articole.add(uploadedArticle);
	}
	
	
	
	

}
