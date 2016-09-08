package fr.aoufi.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class Theme implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String nom;
	private String description;
	private Collection<Document> documents = new ArrayList<Document>();
	
	public Theme() { }
	
	public Theme(String id, String nom, String description) {
		this.id = id;
		this.nom = nom;
		this.description = description;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getNom() {
		return nom;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
	public Collection<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(Collection<Document> documents) {
		this.documents = documents;
	}

	public void add(Document document) {
		if (document != null) {
			if (documents == null) documents = new ArrayList<Document>();
			if (!documents.contains(document)) {
				documents.add(document);
				document.addTheme(this);
			} 
		}
	}
	
	@Override
	public String toString() {
		String chaine = ""; 
		chaine += "Theme [" + getId() + ", " + getNom() + ", "
				+ getDescription();
		
		if (documents   != null) {
			chaine += ", Document [";
			for (Document document : documents) {
				chaine = chaine + document.getCote() + " " + document.getTitre() + " " + document.getDescriptif() + "," ;
			}
		}
		chaine += "]";
		return chaine;
	}

	
	// Voir classe Document pour les explications
	public Theme getDto () {
		
		Theme themeDto = new Theme(this.getId(), this.getNom(), this.getDescription());
		
		// on ajoute les documents du persistantBag dans le nouveau themeDto mais on ne charge pas
		// les themes des documents 
		for (Document document : this.getDocuments()) {
			Document doc = new Document(document.getCote(), document.getTitre(), document.getDescriptif(),
					document.getNbExemplaireDispo());
			doc.setLocalisation(document.getLocalisation());
			doc.setAuteur(document.getAuteur());
			themeDto.add(doc);
		}
		return themeDto;
	}

}
