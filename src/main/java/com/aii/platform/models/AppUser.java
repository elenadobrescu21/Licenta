package com.aii.platform.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity(name="appUser")
@Table(name="appUser")
public class AppUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="nume", nullable=false)
	@NotNull
	private String nume;
	
	@Column(name="prenume", nullable=false)
	@NotNull
	private String prenume;
	
	@Column(name="username", unique=true, nullable=false)
	@NotNull
	private String username;
	
	@Column(name="password", nullable=false)
	@NotNull
	private String password;
	
	@Column(name="email", unique=true, nullable=false)
	@NotNull
	private String email;
	
	@Column(name="authorities")
	@NotNull
	private String authorities;
	
	public AppUser() {
		
	}
	
   
	public AppUser(Long id, String nume, String prenume, String username, String password, String email,
			String authorities) {
		super();
		this.id = id;
		this.nume = nume;
		this.prenume = prenume;
		this.username = username;
		this.password = password;
		this.email = email;
		this.authorities = authorities;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAuthorities() {
		return authorities;
	}

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}
		

}
