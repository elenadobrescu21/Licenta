package com.aii.platform.models;

public class UserStatistics {
	
	private int numberOfUploadedArticles;
	
	private int numberOfDownloadedArticles;
	
	private int articoleCarteCompleta;
	
	private int articoleCarteCapitol;
	
	private int articoleConferinta;
	
	private int articoleJurnalRevista;
	
	public UserStatistics(int numberOfUploadedArticles, int numberOfDownloadedArticles) {
		this.numberOfUploadedArticles = numberOfUploadedArticles;
		this.numberOfDownloadedArticles = numberOfDownloadedArticles;
	}
	
	
	public UserStatistics(int numberOfUploadedArticles, int numberOfDownloadedArticles, int articoleCarteCompleta,
			int articoleCarteCapitol, int articoleConferinta, int articoleJurnalRevista) {
		super();
		this.numberOfUploadedArticles = numberOfUploadedArticles;
		this.numberOfDownloadedArticles = numberOfDownloadedArticles;
		this.articoleCarteCompleta = articoleCarteCompleta;
		this.articoleCarteCapitol = articoleCarteCapitol;
		this.articoleConferinta = articoleConferinta;
		this.articoleJurnalRevista = articoleJurnalRevista;
	}

	public int getNumberOfUploadedArticles() {
		return numberOfUploadedArticles;
	}

	public void setNumberOfUploadedArticles(int numberOfUploadedArticles) {
		this.numberOfUploadedArticles = numberOfUploadedArticles;
	}

	public int getNumberOfDownloadedArticles() {
		return numberOfDownloadedArticles;
	}

	public void setNumberOfDownloadedArticles(int numberOfDownloadedArticles) {
		this.numberOfDownloadedArticles = numberOfDownloadedArticles;
	}

	public int getArticoleCarteCompleta() {
		return articoleCarteCompleta;
	}

	public void setArticoleCarteCompleta(int articoleCarteCompleta) {
		this.articoleCarteCompleta = articoleCarteCompleta;
	}

	public int getArticoleCarteCapitol() {
		return articoleCarteCapitol;
	}

	public void setArticoleCarteCapitol(int articoleCarteCapitol) {
		this.articoleCarteCapitol = articoleCarteCapitol;
	}

	public int getArticoleConferinta() {
		return articoleConferinta;
	}

	public void setArticoleConferinta(int articoleConferinta) {
		this.articoleConferinta = articoleConferinta;
	}

	public int getArticoleJurnalRevista() {
		return articoleJurnalRevista;
	}

	public void setArticoleJurnalRevista(int articoleJurnalRevista) {
		this.articoleJurnalRevista = articoleJurnalRevista;
	}
	
	

}
