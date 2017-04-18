package com.aii.platform.dto;

public class AppUserDTO {
	
	private Long id;
	
	private String fullname;
	
	private String username;
	
	public AppUserDTO() {
		
	}
	
	public AppUserDTO(Long id, String fullname, String username) {
		 this.id = id;
		 this.fullname = fullname;
		 this.username = username;
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
	
	
	

}
