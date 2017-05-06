package com.aii.platform.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity(name="carte_completa")
@Table(name="carte_completa")
public class CarteCompleta {
	
	@Id
	private Long id;
	
	@Column(name="editura")
	private String editura;
	
	@Column(name="editie")
	private String editie;
	
	@Column(name="ISBN")
	@Size(min=13)
	private String isbn;
	
	@Column(name="ISSN")
	@Size(min=9)
	private String issn;
	
	@Column(name="an_publicare")
	private int anPublicare;
	
	@OneToOne
	@MapsId
	private UploadedArticle uploadedArticle;
	
	public CarteCompleta() {
		
	}

	public CarteCompleta(String editura, String editie, String isbn, String issn, int anPublicare) {
		super();
		this.editura = editura;
		this.editie = editie;
		this.isbn = isbn;
		this.issn = issn;
		this.anPublicare = anPublicare;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEditura() {
		return editura;
	}

	public void setEditura(String editura) {
		this.editura = editura;
	}

	public String getEditie() {
		return editie;
	}

	public void setEditie(String editie) {
		this.editie = editie;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getIssn() {
		return issn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	public int getAnPublicare() {
		return anPublicare;
	}

	public void setAnPublicare(int anPublicare) {
		this.anPublicare = anPublicare;
	}

	public UploadedArticle getUploadedArticle() {
		return uploadedArticle;
	}

	public void setUploadedArticle(UploadedArticle uploadedArticle) {
		this.uploadedArticle = uploadedArticle;
	}
	
	

}
