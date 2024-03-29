package com.aii.platform.dto;

import java.util.Date;
import java.util.List;

public class UploadedArticleDTO {
	
	private Long id;
	
	private String titlu;
	
	private int numberOfDownloads;
	
	private Date uploadedOn;
	
	private String filePath;
	
	private AppUserDTO owner;
	
	private List<AppUserDTO> coauthors;
	
	private String[] tags;
	
	private Long idTipArticol;
	
	private String tipArticol;
	
	private String abstractSection;
	
	private String wos;
	
	private String doi;
	
	public UploadedArticleDTO () {
		
	}
	
	public UploadedArticleDTO(Long id, String titlu, String filepath,int numberOfDownloads, Date date, AppUserDTO owner,
			List<AppUserDTO> coauthors, String[] tags, Long idTipArticol, String tipArticol, String abstractSection,
			String wos, String doi) {
		super();
		this.id = id;
		this.titlu = titlu;
		this.filePath = filepath;
		this.numberOfDownloads = numberOfDownloads;
		this.uploadedOn = (Date) date;
		this.owner = owner;
		this.coauthors = coauthors;
		this.tags = tags;
		this.idTipArticol = idTipArticol;
		this.tipArticol = tipArticol;
		this.abstractSection = abstractSection;
		this.wos = wos;
		this.doi = doi;
		
	}
	
	public UploadedArticleDTO(Long id, String titlu, String filepath,int numberOfDownloads, Date date, AppUserDTO owner,
			List<AppUserDTO> coauthors, String[] tags) {
		super();
		this.id = id;
		this.titlu = titlu;
		this.filePath = filepath;
		this.numberOfDownloads = numberOfDownloads;
		this.uploadedOn = (Date) date;
		this.owner = owner;
		this.coauthors = coauthors;
		this.tags = tags;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitlu() {
		return titlu;
	}

	public void setTitlu(String titlu) {
		this.titlu = titlu;
	}

	public int getNumberOfDownloads() {
		return numberOfDownloads;
	}

	public void setNumberOfDownloads(int numberOfDownloads) {
		this.numberOfDownloads = numberOfDownloads;
	}

	public Date getUploadedOn() {
		return uploadedOn;
	}

	public void setUploadedOn(Date uploadedOn) {
		this.uploadedOn = uploadedOn;
	}

	public AppUserDTO getOwner() {
		return owner;
	}

	public void setOwner(AppUserDTO owner) {
		this.owner = owner;
	}

	public List<AppUserDTO> getCoauthors() {
		return coauthors;
	}

	public void setCoauthors(List<AppUserDTO> coauthors) {
		this.coauthors = coauthors;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public Long getIdTipArticol() {
		return idTipArticol;
	}

	public void setIdTipArticol(Long idTipArticol) {
		this.idTipArticol = idTipArticol;
	}

	public String getTipArticol() {
		return tipArticol;
	}

	public void setTipArticol(String tipArticol) {
		this.tipArticol = tipArticol;
	}

	public String getAbstractSection() {
		return abstractSection;
	}

	public void setAbstractSection(String abstractSection) {
		this.abstractSection = abstractSection;
	}

	public String getWos() {
		return wos;
	}

	public void setWos(String wos) {
		this.wos = wos;
	}

	public String getDoi() {
		return doi;
	}

	public void setDoi(String doi) {
		this.doi = doi;
	}
	
		
}
