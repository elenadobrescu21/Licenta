
package com.aii.platform.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name="uploadedArticle")
@Table(name="uploadedArticle")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UploadedArticle {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long uploadedArticleId;
	
	@Column(name = "title", unique=true)
	private String title;
	
	@Column(name = "filename")
	private String filename;
	
	@Column(name="uploaded_on")
	@Temporal(TemporalType.DATE)
	private Date uploadedOn = new Date();
	
	@Column(name="downloads")
	private int numberOfDownloads;
	
	@Column(name="abstract")
	@Size(min=50, max=1500)
	private String abstractSection;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "appUserId", nullable = false)
	private AppUser appUser;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "article_coauthor", 
	joinColumns = {
	@JoinColumn( name="article_id",
				referencedColumnName = "uploadedArticleId"
			)
	},
		inverseJoinColumns = {
				@JoinColumn(name="coauthor_id",
						referencedColumnName = "id")
		})
	private List<AppUser> coauthors = new ArrayList<AppUser>();
	
	@ManyToMany(mappedBy="favouriteArticles")
	@JsonIgnore
	private Set<AppUser> favouritedBy = new HashSet<AppUser>();
	
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "article_tag", 
	joinColumns = {
	@JoinColumn( name="article_id",
				referencedColumnName = "uploadedArticleId"
			)
	},
		inverseJoinColumns = {
				@JoinColumn(name="tag_id",
						referencedColumnName = "tagId")
		})
	private List<Tag> tags = new ArrayList<Tag>();
	
	public UploadedArticle() {
		
	}
	
	public UploadedArticle(String title,String filename) {
		super();
		this.title = title;
		this.filename = filename;
		this.numberOfDownloads = 0;
	}
	
	public UploadedArticle(Long id, String title, String filename) {
		this.uploadedArticleId = id;
		this.title = title;
		this.filename = filename;
		this.numberOfDownloads = 0;
	}

	
	public Long getUploadedArticleId() {
		return uploadedArticleId;
	}

	public void setUploadedArticleId(Long uploadedArticleId) {
		this.uploadedArticleId = uploadedArticleId;
	}

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Date getUploadedOn() {
		return uploadedOn;
	}

	public void setUploadedOn(Date uploadedOn) {
		this.uploadedOn = uploadedOn;
	}

	public List<AppUser> getCoauthors() {
		return coauthors;
	}

	public void setCoauthors(List<AppUser> coauthors) {
		this.coauthors = coauthors;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser owner) {
		this.appUser = owner;
	}
	
	public void addCoauthor(AppUser coauthor) {
		this.coauthors.add(coauthor);
	}

	public int getNumberOfDownloads() {
		return numberOfDownloads;
	}

	public void setNumberOfDownloads(int numberOfDownloads) {
		this.numberOfDownloads = numberOfDownloads;
	}
	
	public void incrementNumberOfDownloads() {
		this.numberOfDownloads++;
	}

	public Set<AppUser> getFavouritedBy() {
		return favouritedBy;
	}
	
	
	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public void setFavouritedBy(Set<AppUser> favouritedBy) {
		this.favouritedBy = favouritedBy;
	}

	public void addTag(Tag tag) {
		this.tags.add(tag);
	}
	
	public void addOwner(AppUser appUser){
		this.appUser = appUser;
	}
	
	public void addToUsersWhoFavourited(AppUser appUser) {
		this.favouritedBy.add(appUser);
	}
	

}
