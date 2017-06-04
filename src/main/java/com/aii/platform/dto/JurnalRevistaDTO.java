package com.aii.platform.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

public class JurnalRevistaDTO extends UploadedArticleDTO{
	
	
	private String titluJurnal;
	
	private int numar;
	
	private int volum;
	
	private int paginaStart;
	
	private int paginaSfarsit;
	
	private String issn;
	
	private String isbn;
	
	private Date dataAparitie;

	public JurnalRevistaDTO(String titluJurnal, int numar, int volum, int paginaStart, int paginaSfarsit, String issn,
			String isbn, Date dataAparitie) {
		super();
		this.titluJurnal = titluJurnal;
		this.numar = numar;
		this.volum = volum;
		this.paginaStart = paginaStart;
		this.paginaSfarsit = paginaSfarsit;
		this.issn = issn;
		this.isbn = isbn;
		this.dataAparitie = dataAparitie;
	}

	public JurnalRevistaDTO(Long id, String titlu, String filepath, int numberOfDownloads, Date date, AppUserDTO owner,
			List<AppUserDTO> coauthors, String[] tags, Long idTipArticol, String tipArticol,
			String titluJurnal, int numar, int volum, int paginaStart, int paginaSfarsit, String issn,
			String isbn, Date dataAparitie) {
		super(id, titlu, filepath, numberOfDownloads, date, owner, coauthors, tags, idTipArticol, tipArticol);
		this.titluJurnal = titluJurnal;
		this.numar = numar;
		this.volum = volum;
		this.paginaStart = paginaStart;
		this.paginaSfarsit = paginaSfarsit;
		this.issn = issn;
		this.isbn = isbn;
		this.dataAparitie = dataAparitie;
	}

	public String getTitluJurnal() {
		return titluJurnal;
	}

	public void setTitluJurnal(String titluJurnal) {
		this.titluJurnal = titluJurnal;
	}

	public int getNumar() {
		return numar;
	}

	public void setNumar(int numar) {
		this.numar = numar;
	}

	public int getVolum() {
		return volum;
	}

	public void setVolum(int volum) {
		this.volum = volum;
	}

	public int getPaginaStart() {
		return paginaStart;
	}

	public void setPaginaStart(int paginaStart) {
		this.paginaStart = paginaStart;
	}

	public int getPaginaSfarsit() {
		return paginaSfarsit;
	}

	public void setPaginaSfarsit(int paginaSfarsit) {
		this.paginaSfarsit = paginaSfarsit;
	}

	public String getIssn() {
		return issn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Date getDataAparitie() {
		return dataAparitie;
	}

	public void setDataAparitie(Date dataAparitie) {
		this.dataAparitie = dataAparitie;
	}
	

}
