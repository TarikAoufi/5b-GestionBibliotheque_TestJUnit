package fr.aoufi.entity;

import java.io.Serializable;
import java.util.Date;

public class Auteur implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String nom;
	private String prenom;
	private String nationalite;
	private Date dateNaissance;
	
	// Auteur ne contient pas de collection de Document
	
	public Auteur() {
		super();
	}
	
	public Auteur(String id, String nom, String prenom, String nationalite,
			Date dateNaissance) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.nationalite = nationalite;
		this.dateNaissance = dateNaissance;
	}
	@Override
	public String toString() {
		return "Auteur [" + getId() + ", " + getNom() + ", " + getPrenom()
				+ ", " + getNationalite()
				+ ", " + getDateNaissance() + "]";
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getNationalite() {
		return nationalite;
	}
	public void setNationalite(String nationalite) {
		this.nationalite = nationalite;
	}
	public Date getDateNaissance() {
		return dateNaissance;
	}
	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean idem = false;
		if (obj instanceof Document) {
			Auteur auteur = (Auteur)obj;
			if (auteur.getId().equals(this.getId())) idem = true;
		}
		return idem;
	}
}
