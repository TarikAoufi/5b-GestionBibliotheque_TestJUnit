package fr.aoufi.testDao;

import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import fr.aoufi.clientServer.IServiceFacade;
import fr.aoufi.clientServer.UserException;
import fr.aoufi.entity.Auteur;
import fr.aoufi.entity.Document;
import fr.aoufi.entity.Localisation;
import fr.aoufi.entity.Theme;
import fr.aoufi.ressources.Param;


/**
 * Test de la couche Dao en direct (sans passer par les services facade ...)
 *
 */
public class TestDocumentThemeTODO {

	private static IServiceFacade serviceFacade;

	public static void main(String[] args)  {
		try {

			Context context = new InitialContext();
			serviceFacade 			= (IServiceFacade) context.lookup(Param.EJB_SERVICE_FACADE);

			// delete des donnees à partir des tables
			try {

				serviceFacade.removeDocument();
				serviceFacade.removeLocalisation();
				serviceFacade.removeAuteur();
				serviceFacade.removeTheme();

			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			Theme 			theme 	= null;

			/**
			 * MERGE
			 */
			// RG : on ne peut pas passer par le document pour modifier un theme
			// on ne peut que modifier le theme affecté au document
			// Pour modifier un theme, il faut passer par le theme lui meme

			
			// TODO ceci ne fonctionne pas
			/* 
			 * Il faut revoir les regles de gestion de la creation des doc et des themes
			 * pb de gestion de la double collection
			 * 
			 */
			// ================   Modification d'un theme existant avec document
			System.out.println("******Rx : Modification d'un theme existant avec document");

			theme = null;
			try {
				serviceFacade.remove(new Theme("T1", "theme1", "description1"));
			} catch (UserException e) {
			}
			
			try {
				theme 	= new Theme("T1", "theme", "description");
				
				// Les documents doivent etre crees pour etre associes aux themes
				serviceFacade.ajouter(creerDoc1());
				serviceFacade.ajouter(creerDoc2());
				serviceFacade.ajouter(creerDoc3());
				
				theme.add(creerDoc1());
				theme.add(creerDoc2());
				serviceFacade.add(theme);
				
				// on modifie le theme
				theme.setNom("themexxx");
				theme.setDescription("descriptionxxx");
				theme.add(creerDoc3());

				Theme themez = serviceFacade.update(theme);
				System.out.println("AT : merge T1 : " + themez);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			System.out.println("\n****  Fin ***********");

		} catch (NamingException e) {
			System.out.println("***AT Erreur InitBdD - NamingException");
			e.printStackTrace();
		} 
	}
	
	private static Document creerDoc1() {
		Document 		doc = new Document ("001", "titre1", "descriptif1", 10);
		doc.setLocalisation(new Localisation("lieu1", "emp1"));
		doc.setAuteur(new Auteur("A1", "nomAuteur1", "prenomAuteur1", "Fr", null));
		doc.addTheme(new Theme("T1", "theme", "description"));
		doc.addTheme(new Theme("T2", "theme2", "description2"));
		return doc;
	}

	private static Document creerDoc2() {
		Document 		doc = new Document ("002", "titre2", "descriptif2", 10);
		doc.setLocalisation(new Localisation("lieu2", "emp2"));
		doc.setAuteur(new Auteur("A2", "nomAuteur2", "prenomAuteur2", "Fr", new Date()));
		doc.addTheme(new Theme("T1", "theme", "description"));
		doc.addTheme(new Theme("T3", "theme3", "description3"));
		return doc;
	}
	
	private static Document creerDoc3() {
		Document 		doc = new Document ("003", "titre3", "descriptif3", 10);
		doc.setLocalisation(new Localisation("lieu_3", "emp_3"));
		doc.setAuteur(new Auteur("A2", "nomAuteur2", "prenomAuteur2", "Fr", new Date()));
		doc.addTheme(new Theme("T1", "theme", "description"));
		doc.addTheme(new Theme("T4", "theme4", "description4"));
		return doc;
	}

}
