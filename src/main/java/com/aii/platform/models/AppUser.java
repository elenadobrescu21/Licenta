
package com.aii.platform.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

	@OneToMany(fetch = FetchType.EAGER, mappedBy="appUser", orphanRemoval = true)
	private List<UploadedArticle> uploadedArticles;
	
	@ManyToMany(mappedBy = "coauthors")
	private List<UploadedArticle> coauthorArticles = new ArrayList<UploadedArticle>();
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "user_favouriteArticle", 
	joinColumns = {
	@JoinColumn( name="user_id",
				referencedColumnName = "id"
			)
	},
		inverseJoinColumns = {
				@JoinColumn(name="article_id",
						referencedColumnName = "uploadedArticleId")
		})
	private List<UploadedArticle> favouriteArticles = new ArrayList<UploadedArticle>();
	
	public AppUser() {
		
	}
	
	public AppUser(String nume, String prenume, String username, String password, String email,
			String authorities) {
		super();
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


	public List<UploadedArticle> getUploadedArticles() {
		return uploadedArticles;
	}
	
	public void addArticle(UploadedArticle uploadedArticle) {
		this.coauthorArticles.add(uploadedArticle);
	}


	public void setCoauthorArticles(List<UploadedArticle> coauthorArticles) {
		this.coauthorArticles = coauthorArticles;
	}


	public void setUploadedArticles(List<UploadedArticle> uploadedArticles) {
		this.uploadedArticles = uploadedArticles;
	}


	public List<UploadedArticle> getFavouriteArticles() {
		return favouriteArticles;
	}


	public void setFavouriteArticles(List<UploadedArticle> favouriteArticles) {
		this.favouriteArticles = favouriteArticles;
	}
	
	public UploadedArticle getUploadedArticleById(Long id){
		UploadedArticle article = null;
		for(UploadedArticle u: this.uploadedArticles) {
			if(u.getUploadedArticleId()==id) {
				article = u;
				return u;
			}
		}
		return article;
	}
	
	public void addArticleToFavourites(UploadedArticle uploadedArticle){
		this.uploadedArticles.add(uploadedArticle);
	}
	
	public void addArticleInCollaboration(UploadedArticle uploadedArticle) {
		this.coauthorArticles.add(uploadedArticle);
	}
	
				
}
