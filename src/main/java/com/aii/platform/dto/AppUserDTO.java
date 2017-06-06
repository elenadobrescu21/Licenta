package com.aii.platform.dto;
import java.util.List;

import com.aii.platform.models.*;

public class AppUserDTO implements Comparable<AppUserDTO>{
	
	private Long id;
	
	private String fullname;
	
	private String username;
	
	private int numberOfUploadedArticles;
	
	private int totalNumberOfDownloads = 0;
	
	public AppUserDTO() {
		
	}
	
	public AppUserDTO(Long id, String fullname, String username) {
		 this.id = id;
		 this.fullname = fullname;
		 this.username = username;
	}

	
	public AppUserDTO(Long id, String fullname, String username, int numberOfUploadedArticles) {
		super();
		this.id = id;
		this.fullname = fullname;
		this.username = username;
		this.numberOfUploadedArticles = numberOfUploadedArticles;
	}
	
	public AppUserDTO(Long id, String fullname, String username, List<UploadedArticle> uploadedArticles) {
		super();
		this.id = id;
		this.fullname = fullname;
		this.username = username;
		this.numberOfUploadedArticles = uploadedArticles.size();
		
		for(UploadedArticle u: uploadedArticles) {
			this.totalNumberOfDownloads = totalNumberOfDownloads + u.getNumberOfDownloads();
		}
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getNumberOfUploadedArticles() {
		return numberOfUploadedArticles;
	}

	public void setNumberOfUploadedArticles(int numberOfUploadedArticles) {
		this.numberOfUploadedArticles = numberOfUploadedArticles;
	}

	@Override
	public int compareTo(AppUserDTO o) {
		int c;
		int rez = 0;
		int comparatie = 0;
		c = this.numberOfUploadedArticles - o.numberOfUploadedArticles;
		if(c == comparatie) {
			rez = 0;
		}
		if(c > comparatie) {
			rez = -1;
		}
		
		if(c < comparatie) {
			rez = 1;
		}
		return rez;
	}

	public int getTotalNumberOfDownloads() {
		return totalNumberOfDownloads;
	}

	public void setTotalNumberOfDownloads(int totalNumberOfDownloads) {
		this.totalNumberOfDownloads = totalNumberOfDownloads;
	}
	
	
}
