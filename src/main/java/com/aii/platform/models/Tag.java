
package com.aii.platform.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="tag")
@Table(name="tag")
public class Tag {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tagId;
	
	@Column(name="denumire", nullable=false, unique=true)
	private String denumire;
	
	@ManyToMany(mappedBy="tags")
	@JsonIgnore
	private List<UploadedArticle> articles = new ArrayList<UploadedArticle>();
	
	public Tag(){
		
	}
	
	public Tag(String denumire) {
		this.denumire = denumire;
	}

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public String getDenumire() {
		return denumire;
	}

	public void setDenumire(String denumire) {
		this.denumire = denumire;
	}

	public List<UploadedArticle> getArticles() {
		return articles;
	}

	public void setArticles(List<UploadedArticle> articles) {
		this.articles = articles;
	}
	
	public void addArticle(UploadedArticle article) {
		this.articles.add(article);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((denumire == null) ? 0 : denumire.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tag other = (Tag) obj;
		if (denumire == null) {
			if (other.denumire != null)
				return false;
		} else if (!denumire.equals(other.denumire))
			return false;
		return true;
	}
	
	
}
