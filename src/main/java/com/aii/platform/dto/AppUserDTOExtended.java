package com.aii.platform.dto;

import java.util.List;

public class AppUserDTOExtended {
	
	private Long id;
	
	private String nume;
	
	private String prenume;
	
	private String username;
	
	private List<UploadedArticleDTO> articoleIncarcate;
	
	private List<UploadedArticleDTO> articoleFavorite;
	
	private List<UploadedArticleDTO> articoleInColaborare;
	

	public AppUserDTOExtended(Long id, String nume, String prenume, String username,
			List<UploadedArticleDTO> articoleIncarcate, List<UploadedArticleDTO> articoleFavorite,
			List<UploadedArticleDTO> articoleInColaborare) {
		super();
		this.id = id;
		this.nume = nume;
		this.prenume = prenume;
		this.username = username;
		this.articoleIncarcate = articoleIncarcate;
		this.articoleFavorite = articoleFavorite;
		this.articoleInColaborare = articoleInColaborare;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	public String getPrenume() {
		return prenume;
	}

	public void setPrenume(String prenume) {
		this.prenume = prenume;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<UploadedArticleDTO> getArticoleIncarcate() {
		return articoleIncarcate;
	}

	public void setArticoleIncarcate(List<UploadedArticleDTO> articoleIncarcate) {
		this.articoleIncarcate = articoleIncarcate;
	}

	public List<UploadedArticleDTO> getArticoleFavorite() {
		return articoleFavorite;
	}

	public void setArticoleFavorite(List<UploadedArticleDTO> articoleFavorite) {
		this.articoleFavorite = articoleFavorite;
	}

	public List<UploadedArticleDTO> getArticoleInColaborare() {
		return articoleInColaborare;
	}

	public void setArticoleInColaborare(List<UploadedArticleDTO> articoleInColaborare) {
		this.articoleInColaborare = articoleInColaborare;
	}
	

}
