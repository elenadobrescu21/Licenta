package com.aii.platform.models;

public class Coauthor {
	
	private int id;
	
	private String fullname;
	
	public Coauthor() {
		
	}

	public Coauthor(int id, String fullname) {
		this.id = id;
		this.fullname = fullname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	

}
