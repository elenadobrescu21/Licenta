package com.aii.platform.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

@Table(name="jurnal_revista")
@Entity(name="jurnal_revista")
public class JurnalRevista {
	
	@Id
	private Long id;
	
	@Column(name="titlu")
	private String titlu;
	
	@Column(name="numar")
	private int numar;
	
	@Column(name="volum")
	private int volum;
	
	@Column(name="pagina_start")
	private int paginaStart;
	
	@Column(name="pagina_sfarsit")
	private int paginaSfarsit;
	
	@Column(name="ISSN")
	@Size(min=9)
	private int issn;
	
	@Column(name="ISBN")
	@Size(min=13)
	private int isbn;
	
	@Column(name="data")
	@Temporal(TemporalType.DATE)
	private Date dataAparitie = new Date();
	
	@OneToOne
	@MapsId
	private UploadedArticle uploadedArticle;
	
	public JurnalRevista() {
		
	}

	public JurnalRevista(String titlu, int numar, int volum, int paginaStart, int paginaSfarsit, int issn, int isbn,
			Date dataAparitie) {
		super();
		this.titlu = titlu;
		this.numar = numar;
		this.volum = volum;
		this.paginaStart = paginaStart;
		this.paginaSfarsit = paginaSfarsit;
		this.issn = issn;
		this.isbn = isbn;
		this.dataAparitie = dataAparitie;
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

	public int getIssn() {
		return issn;
	}

	public void setIssn(int issn) {
		this.issn = issn;
	}

	public int getIsbn() {
		return isbn;
	}

	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}

	public Date getDataAparitie() {
		return dataAparitie;
	}

	public void setDataAparitie(Date dataAparitie) {
		this.dataAparitie = dataAparitie;
	}

	public UploadedArticle getUploadedArticle() {
		return uploadedArticle;
	}

	public void setUploadedArticle(UploadedArticle uploadedArticle) {
		this.uploadedArticle = uploadedArticle;
	}
	
	

}
