package com.aii.platform.dto;

import java.util.Date;
import java.util.List;



public class ConferintaDTO extends UploadedArticleDTO {
	
	
	private String nume;
	
	private String locatie;
		
	private Date data;

	public ConferintaDTO(String nume, String locatie, Date data) {
		super();
		this.nume = nume;
		this.locatie = locatie;
		this.data = data;
	}

	public ConferintaDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ConferintaDTO(Long id, String titlu, String filepath, int numberOfDownloads, Date date, AppUserDTO owner,
			List<AppUserDTO> coauthors, String[] tags, Long idTipArticol, String tipArticol,String nume, String locatie, Date data) {
		super(id, titlu, filepath, numberOfDownloads, date, owner, coauthors, tags, idTipArticol, tipArticol);
		this.nume = nume;
		this.locatie = locatie;
		this.data = data;
	}

	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	public String getLocatie() {
		return locatie;
	}

	public void setLocatie(String locatie) {
		this.locatie = locatie;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
}
