package fr.aoufi.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * bean metier
 * 
 *RG : on peut modifier l'auteur d'un livre (instance d'auteur) mais il ne faut pas
 *     modifier les données de l'auteur au travers du livre
 *     Il faut dans ce cas faire un merge de l'auteur
 *     
 *     la localisation peut etre modifiee a partir du livre
 */
public class Document implements Serializable {

	private static final long	serialVersionUID	= 1L;
	private 	String	cote;
	private 	String	titre;
	private 	String	descriptif;
	private 	int 	nbExemplaireDispo;
	private Localisation localisation;
	private Auteur auteur;
	private List<Theme> themes = new ArrayList<Theme>();

	public Document() {
		super();
	}

	public Document(String cote, String titre) {
		this.cote 				= cote;
		this.titre 				= titre;
		this.descriptif 		= "non renseigné";
		this.nbExemplaireDispo 	= 0;
	}

	public Document(String cote, String titre, String descriptif, int nbExemplaireDispo) {
		this(cote,titre);
		this.descriptif 		= descriptif;
		this.nbExemplaireDispo 	= nbExemplaireDispo;
	}

	public Document(String cote, String titre, String descriptif, int nbExemplaireDispo, Localisation localisation) {
		this(cote,titre,descriptif,nbExemplaireDispo);
		this.localisation = localisation;
	}

	public String getCote() {
		return cote;
	}
	public void setCote(String cote) {
		this.cote = cote;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getDescriptif() {
		return descriptif;
	}
	public void setDescriptif(String descriptif) {
		this.descriptif = descriptif;
	}
	public int getNbExemplaireDispo() {
		return nbExemplaireDispo;
	}
	public void setNbExemplaireDispo(int nbExemplaireDispo) {
		this.nbExemplaireDispo = nbExemplaireDispo;
	}
	public Localisation getLocalisation() {
		return localisation;
	}
	public void setLocalisation(Localisation localisation) {
		this.localisation = localisation;
	}
	public Auteur getAuteur() {
		return auteur;
	}

	public void setAuteur(Auteur auteur) {
		this.auteur = auteur;
	}

	public List<Theme> getThemes() {
		return themes;
	}

	public void setThemes(List<Theme> themes) {
		this.themes = themes;
	}

	public void addTheme(Theme theme) {
		if (theme != null) {
			if (themes == null) themes = new ArrayList<Theme>();
			if (!themes.contains(theme)) {
				themes.add(theme);
				theme.add(this);
			} 
		}
	}
	@Override
	public String toString() {
		String chaine = "Document [" + cote + ", " + titre + ", "
				+ descriptif + ", " + nbExemplaireDispo
				+ ", " + localisation + ", " + auteur + "]";
		if (themes   != null) {
			chaine += ", Theme [";
			for (Theme theme : themes) {
				chaine = chaine + theme.getId() + " " + theme.getNom() + " " + theme.getDescription() + "," ;
			}
		}
		chaine += "]";
		return chaine;
	}

	@Override
	public boolean equals(Object obj) {
		boolean idem = false;
		if (obj instanceof Document) {
			Document document = (Document)obj;
			if (document.getCote().equals(this.getCote()) && document.getTitre().equals(this.getTitre())) idem = true;
		}
		return idem;
	}

	// utilise pout abtenir un data Transfert Object
	// Neccessaire pour transformer un proxy (hibernate) en objet transiant (pas dans le contexte)
	// lors de la communication avec l'application cliente
	// sinon, hibernate gère les collections dans un type persistentBag propre a hibernate
	// Pour eviter la propagation du persistentBag (ArrayList<Theme>) dans la couche cliente qui ne le connait pas
	// Il faut le transformer en ArrayList
	public Document getDto () {
		
		Document docDto = new Document(this.getCote(), this.getTitre(), this.getDescriptif(),
				                this.getNbExemplaireDispo());
		docDto.setLocalisation(this.getLocalisation());
		docDto.setAuteur(this.getAuteur());
		
		
//		// on ajoute les themes du persistantBag dans le nouveau docDto
		for (Theme theme : this.getThemes()) {
			Theme themeDto = new Theme(theme.getId(), theme.getNom(), theme.getDescription());
//					themeDto.setDocuments(theme.getDocuments()); => persistentBag (la liste des documents dans le theme)
			docDto.addTheme(themeDto);
		}
		
		return docDto;
	}


}
