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
public class TestDocumentTheme {

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

			Document 		document1;
			Document 		document2;
			Localisation 	localisation1;
			Localisation 	localisation2;
			Auteur 			auteur1;		
			Theme 			theme1;
			Theme 			theme2;

			Theme 			theme 	= null;
			Document 		doc 	= null;

			/**
			 * ADD
			 */
			// ajouter un document avec un theme inexistant
			// ajouter un document avec un theme existant en base
			// ajouter un document avec themes null
			// ajouter un document avec un theme null


			// ajouter un document avec un theme null

			// ================   ajouter un document avec un theme inexistant
			System.out.println("******R1 : ajouter un document avec 2 themes inexistants");
			removeDoc1Doc2();

			try {
				doc = serviceFacade.ajouter(creerDoc1());
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}
			System.out.println("** AT document add : " + doc);

			// ================   ajouter un document avec un theme existant
			System.out.println("******R2 : ajouter un document avec un theme existant");		
			// document2 contient T1 existant en base et T3 inexistant en base
			
			doc = null;
			try {
				doc = serviceFacade.ajouter(creerDoc2());
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}
			System.out.println("** AT document add : " + doc);

			// ================   ajouter un document avec themes null
			System.out.println("******R3 : ajouter un document avec themes null");
			removeDoc1Doc2();

			doc = null;
			try {				
				document1 = creerDoc1();
				document1.setThemes(null);
				doc = serviceFacade.ajouter(document1);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}
			System.out.println("** AT document add : " + doc);

			// ================   Test ajout d'un theme inexistant
			// ================   Test ajout d'un theme doublon
			// ================   Test ajout d'un theme avec id null
			// ================   Test ajout d'un theme avec id empty

			//  ================   Test ajout d'un theme inexistant T4
			System.out.println("******Rx : ajouter un theme inexistant T4");
			removeDoc1Doc2();

			Theme theme4	 		= new Theme("T4", "nom4", "description4");
			try {
				theme4 = serviceFacade.add(theme4);
				System.out.println("AT : ajouter theme T4 : " + theme4);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			//  ================   Test ajout d'un theme doublon T4
			System.out.println("******Rx : ajouter un theme doublon T4");
			removeDoc1Doc2();

			theme4	 		= new Theme("T4", "nom4", "description4");
			try {
				theme4 = serviceFacade.add(theme4);
				System.out.println("AT : ajouter theme doublon T4 : " + theme4);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			//  ================   Test ajout d'un theme T5 avec id null
			System.out.println("******Rx : ajouter un theme T5 avec id null");
			removeDoc1Doc2();

			Theme theme5	 		= new Theme(null, "nom5", "description5");
			try {
				theme5 = serviceFacade.add(theme5);
				System.out.println("AT : ajouter theme id null T5 : " + theme5);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			//  ================   Test ajout d'un theme T5 avec id empty
			System.out.println("******Rx : ajouter un theme T5 avec id empty");
			removeDoc1Doc2();

			theme5	 		= new Theme("", "nom5", "description5");
			try {
				theme5 = serviceFacade.add(theme5);
				System.out.println("AT : ajouter theme id empty T5 : " + theme5);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			/**
			 * MERGE
			 * 
			 * A FAIRE
			 * ================   Modification d'un theme existant 		avec document
			 */

			// RG : on ne peut pas passer par le document pour modifier un theme
			// on ne peut que modifier le theme affecté au document
			// Pour modifier un theme, il faut passer par le theme lui meme

			// ================   Modification d'un theme inexistant 	
			// ================   Modification d'un theme existant 		sans document
			// ================   Modification d'un theme existant 		avec document    A FAIRE !!!!!!!!
			// ================   Modification d'un theme null

			//  ================   Modification d'un theme inexistant
			// si le theme n'existe pas, il est cree
			System.out.println("******Rx : Modification d'un theme inexistant ");

			theme = new Theme("T1", "theme", "description");
			try {
				serviceFacade.remove(theme);
			} catch (UserException e) {
				
			}

			theme = serviceFacade.update(theme);
			System.out.println("AT : merge T1 : " + theme);
			
			//  ================   Modification d'un theme existant sans document
			System.out.println("******Rx : Modification d'un theme existant sans document");

			theme = null;
			try {

				try {
					theme = serviceFacade.getTheme("T1");
				} catch (UserException e) {
					// InexistantException
					theme 	= new Theme("T1", "theme", "description");
					serviceFacade.add(theme);
				}
				theme.setNom("themexxx");
				theme.setDescription("descriptionxxx");
				Theme themez = serviceFacade.update(theme);
				System.out.println("AT : merge T1 : " + themez);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			
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
			
			//  ================   Modification d'un theme null
			System.out.println("******Rx : Modification d'un theme null ");

			theme = null;
			theme = serviceFacade.update(theme);
			System.out.println("AT : merge theme null : " + theme);
			
			
			/**
			 * RECHERCHE
			 */
			// ================   Test recherche d'un document existant par son identifiant
			// ================   Test recherche d'un theme existant par son identifiant
			// ================   Test recherche d'un theme par son identifiant null
			// ================   Test recherche d'un theme par son identifiant empty
			// ================   Test recherche d'un theme par son identifiant inexistant

			// ================   Test recherche d'un document existant par son identifiant
			System.out.println("******Rx : recherche d'un document par son identifiant 001");
			doc = null;
			try {
				serviceFacade.ajouter(creerDoc1());
				doc = serviceFacade.getDocument("001");
				System.out.println("AT : document 001 : " + doc);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			// ================   Test recherche d'un theme existant par son identifiant
			System.out.println("******Rx : recherche d'un theme par son identifiant T1");
			try {
				theme = serviceFacade.getTheme("T1");
				System.out.println("AT : theme T1 : " + theme);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			// ================   Test recherche d'un theme par son identifiant null
			System.out.println("******Rx : recherche d'un theme par son identifiant null");
			theme = null;
			try {
				theme = serviceFacade.getTheme(null);
				System.out.println("AT : theme null : " + theme);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			// ================   Test recherche d'un theme par son identifiant empty
			System.out.println("******Rx : recherche d'un theme par son identifiant \"\"");
			theme = null;
			try {
				theme = serviceFacade.getTheme("");
				System.out.println("AT : theme \"\" : " + theme);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			// ================   Test recherche d'un theme par son identifiant inexistant
			System.out.println("******Rx : recherche d'un theme par son identifiant A999 inexistant");
			try {
				theme = serviceFacade.getTheme("A999");
				System.out.println("AT : theme A999 inexistant : " + theme);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			/**
			 * REMOVE
			 */
			// remove d'un document existant contenant des themes 
			// remove d'un theme existant contenant des documents 
			// remove d'un theme inexistant
			// remove d'un theme null
			// remove d'un theme avec id null
			// remove d'un theme avec id empty
			// remove d'un theme par id
			
			
			// ================   remove d'un doc existant contenant des themes 
			System.out.println("******Rx : remove d'un doc existant contenant des themes ");
			try {
//				daoGestion.add(creerDoc1());
				doc = serviceFacade.getDocument("001");
				System.out.println("AT : document 001 : " + doc);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}
//			catch (DoublonException e) {
//				System.out.println("**AT Erreur : DoublonException");
//			} catch (LocalisationAffecteeException e) {
//				System.out.println("**AT Erreur : LocalisationAffecteeException");
//			}
			removeDoc1Doc2();
			System.out.println("** AT document remove ");

			// ================   remove d'un theme existant contenant des documents 
			System.out.println("******Rx : remove d'un theme existant contenant des documents ");
			removeDoc1Doc2();
			
			document1 		= new Document ("001", "titre1", "descriptif1", 10);
			document2 		= new Document ("002", "titre2", "descriptif2", 10);
			localisation1 	= new Localisation("lieu1", "emp1");
			localisation2 	= new Localisation("lieu2", "emp2");
			auteur1	 		= new Auteur("A1", "nomAuteur1", "prenomAuteur1", "Fr", null);
			theme1 			= new Theme("T1", "theme", "description");
			theme2 			= new Theme("T2", "theme2", "description2");

			document1.setLocalisation(localisation1);
			document1.setAuteur(auteur1);
			document2.setLocalisation(localisation2);
			document2.setAuteur(auteur1);
			document1.addTheme(theme1);
			document1.addTheme(theme2);
			document2.addTheme(theme1);

			try {
				serviceFacade.ajouter(document1);
				serviceFacade.ajouter(document2);
				
				System.out.println("** Document 1 : " + serviceFacade.getDocument(document1.getCote()));
				System.out.println("** Document 2 : " + serviceFacade.getDocument(document2.getCote()));
				
				serviceFacade.remove(theme1);
				
				System.out.println("** Document 1 : " + serviceFacade.getDocument(document1.getCote()));
				System.out.println("** Document 2 : " + serviceFacade.getDocument(document2.getCote()));

			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			System.out.println("** AT remove : theme " + theme1);


			// ================   remove d'un theme inexistant
			System.out.println("******Rx : remove d'un theme inexistant ");
			removeDoc1Doc2();

			Theme theme999 = null;
			try {
				theme999 = new Theme("T999", "theme999", "description999");
				serviceFacade.remove(theme999);

			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			System.out.println("** AT remove theme inexistant " + theme999);

			// ================   remove d'un theme null
			System.out.println("******Rx : remove d'un theme null ");

			Theme themeNull = null;
			try {
				serviceFacade.remove(themeNull);

			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			System.out.println("** AT remove theme null " + themeNull);

			// ================   remove d'un theme avec id null
			System.out.println("******Rx : remove d'un theme avec id null ");

			Theme themeIdNull = null;
			try {
				themeIdNull = new Theme(null, "theme", "description");
				serviceFacade.remove(themeIdNull);

			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			System.out.println("** AT remove theme id null " + themeIdNull);

			// ================   remove d'un theme avec id empty
			System.out.println("******Rx : remove d'un theme avec id empty ");

			Theme themeIdEmpty = null;
			try {
				themeIdEmpty = new Theme("", "theme", "description");
				serviceFacade.remove(themeIdEmpty);

			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			System.out.println("** AT remove theme id empty " + themeIdEmpty);

			// ================   remove d'un theme par id
			System.out.println("******Rx : remove d'un theme par id ");
			removeDoc1Doc2();

			try {
				serviceFacade.ajouter(creerDoc1());

				serviceFacade.removeThemeById("T1");

			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			System.out.println("** AT remove theme par id T1" );

			// remove tous les themes
			System.out.println("******Rx : remove de tous les themes");
			serviceFacade.removeTheme();

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

	private static void removeDoc1Doc2() {
		try {
			serviceFacade.remove(creerDoc1());
			serviceFacade.remove(creerDoc2());
		} catch (UserException e) {
			
		}

	}


}
