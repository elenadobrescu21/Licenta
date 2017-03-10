package com.aii.platform.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name="uploadedArticle")
@Table(name="uploadedArticle")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UploadedArticle {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long uploadedArticle_ID;
	
	@Column(name = "title", unique=true)
	private String title;
	
	@Column(name = "filename")
	private String filename;
	
	@Column(name="uploaded_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date uploadedOn = new Date();
	
	@Transient
	@JsonIgnore
	private byte[] file;
	
	@ManyToOne
	@JsonIgnore
	private AppUser appUser;
	
	public UploadedArticle() {
		
	}

//	public UploadedArticle(Long id,String title,String filename, byte[] file) {
//		this.uploadedArticle_ID = id;
//		this.title = title;
//		this.filename = filename;
//		this.file = file;
//	}
	
	public UploadedArticle(String title,String filename, byte[] file) {
		super();
		this.title = title;
		this.filename = filename;
		this.file = file;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	
	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser owner) {
		this.appUser = owner;
	}
	

}
