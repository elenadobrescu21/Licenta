package com.aii.platform.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Table(name="carte_capitol")
@Entity(name="carte_capitol")
public class CarteCapitol {
	
	@Id
	private Long id;
	
	@Column(name="titlu")
	private String titlu;
	
	@Column(name="autori_carte")
	private String autoriCarte;
	
	@Column(name="editori_carte")
	private String editoriCarte;
	
	@Column(name="nume_capitol")
	private String numeCapitol;
	
	@Column(name="pagina_inceput")
	private int paginaInceput;
	
	@Column(name="pagina_sfarsit")
	private int paginaSfarsit;
	
	@Column(name="an_publicare")
	private int anPublicare;
	
	@Column(name="editura")
	private String editura;
	
	@Column(name="editie")
	private String editie;
	
	@Column(name="isbn")
	@Size(min=13)
	private String isbn;
	
	@Column(name="issn")
	@Size(min=9)
	private String issn;
	
	@OneToOne
	@MapsId
	private UploadedArticle uploadedArticle;
	
	public CarteCapitol() {
		
	}

	public CarteCapitol(String titlu, String autoriCarte, String editoriCarte, String numeCapitol, int paginaInceput,
			int paginaSfarsit, int anPublicare,String editura, String editie, String isbn, String issn) {
		super();
		this.titlu = titlu;
		this.autoriCarte = autoriCarte;
		this.editoriCarte = editoriCarte;
		this.numeCapitol = numeCapitol;
		this.paginaInceput = paginaInceput;
		this.paginaSfarsit = paginaSfarsit;
		this.anPublicare = anPublicare;
		this.editura = editura;
		this.editie = editie;
		this.isbn = isbn;
		this.issn = issn;
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

	public String getAutoriCarte() {
		return autoriCarte;
	}

	public void setAutoriCarte(String autoriCarte) {
		this.autoriCarte = autoriCarte;
	}

	public String getEditoriCarte() {
		return editoriCarte;
	}

	public void setEditoriCarte(String editoriCarte) {
		this.editoriCarte = editoriCarte;
	}

	public String getNumeCapitol() {
		return numeCapitol;
	}

	public void setNumeCapitol(String numeCapitol) {
		this.numeCapitol = numeCapitol;
	}

	public int getPaginaInceput() {
		return paginaInceput;
	}

	public void setPaginaInceput(int paginaInceput) {
		this.paginaInceput = paginaInceput;
	}

	public int getPaginaSfarsit() {
		return paginaSfarsit;
	}

	public void setPaginaSfarsit(int paginaSfarsit) {
		this.paginaSfarsit = paginaSfarsit;
	}

	public int getAnPublicare() {
		return anPublicare;
	}

	public void setAnPublicare(int anPublicare) {
		this.anPublicare = anPublicare;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getIssn() {
		return issn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	public UploadedArticle getUploadedArticle() {
		return uploadedArticle;
	}

	public void setUploadedArticle(UploadedArticle uploadedArticle) {
		this.uploadedArticle = uploadedArticle;
	}
	
	
}
