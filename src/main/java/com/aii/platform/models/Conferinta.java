package com.aii.platform.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name="conferinta")
@Table(name="conferinta")
public class Conferinta {
	
	@Id
	private Long id;
	
	@Column(name="nume")
	private String nume;
	
	@Column(name="locatie")
	private String locatie;
	
	@Column(name="data")
	@Temporal(TemporalType.DATE)
	private Date data = new Date();
	
	@OneToOne
	@MapsId
	private UploadedArticle uploadedArticle;
	
	public Conferinta() {
		
	}

	public Conferinta(String nume, String locatie, Date data) {
		super();
		this.nume = nume;
		this.locatie = locatie;
		this.data = data;
	}
	

	public Conferinta(String nume, String locatie) {
		super();
		this.nume = nume;
		this.locatie = locatie;
	}

	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	public String getLocatie() {
		return locatie;
	}

	public void setLocatie(String locatie) {
		this.locatie = locatie;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public UploadedArticle getUploadedArticle() {
		return uploadedArticle;
	}

	public void setUploadedArticle(UploadedArticle uploadedArticle) {
		this.uploadedArticle = uploadedArticle;
	}
	
	
}
