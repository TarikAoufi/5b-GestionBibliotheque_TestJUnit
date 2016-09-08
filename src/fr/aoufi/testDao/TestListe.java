package fr.aoufi.testDao;

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
 *  Lancer TestDocumentAuteur
 */
public class TestListe {

	public static void main(String[] args)  {
		try {

			Context context = new InitialContext();

			IServiceFacade serviceFacade 			= (IServiceFacade) context.lookup(Param.EJB_SERVICE_FACADE);
			
			// delete des donnees à partir des tables
			serviceFacade.removeDocumentNative();
			serviceFacade.removeLocalisationNative();
			serviceFacade.removeAuteurNative();
			serviceFacade.removeThemeNative();
			
			/**
			 * Creation des donnees
			 */

			System.out.println("******Rx : ajouter les documents");
			Document 		document1 		= new Document ("001", "titre1", "descriptif1", 10);
			Document 		document2 		= new Document ("002", "titre2", "descriptif2", 10);
			Document 		document3 		= new Document ("003", "titre3", "descriptif3", 10);
			Document 		document4 		= new Document ("004", "titre4", "descriptif4", 10);
			Document 		document5 		= new Document ("005", "titre5", "descriptif5", 10);
			Document 		document6 		= new Document ("006", "titre6", "descriptif6", 10);
			Document 		document7 		= new Document ("007", "titre7", "descriptif7", 10);
			Document 		document8 		= new Document ("008", "titre8", "descriptif8", 10);
			Document 		document1a 		= new Document ("001a", "titre1a", "descriptif1a", 10);
			Document 		document3a 		= new Document ("003a", "titre3a", "descriptif3a", 10);
			Document 		document5a 		= new Document ("005a", "titre5a", "descriptif5a", 10);
			Document 		document7a 		= new Document ("007a", "titre7a", "descriptif7a", 10);
			
			
			Localisation    localisation1 	= new Localisation("lieu1", "emp1");
			Localisation    localisation2 	= new Localisation("lieu2", "emp2");
			Localisation    localisation3 	= new Localisation("lieu3", "emp3");
			Localisation    localisation4 	= new Localisation("lieu4", "emp4");
			Localisation    localisation1a 	= new Localisation("lieu1a", "emp1a");
			Localisation    localisation3a 	= new Localisation("lieu3a", "emp3a");
			
			Auteur 			auteur1	 		= new Auteur("A1", "nomAuteur1", "prenomAuteur1", "Fr", null);
			Auteur 			auteur2	 		= new Auteur("A2", "nomAuteur2", "prenomAuteur2", "Fr", null);
			Auteur 			auteur3	 		= new Auteur("A3", "nomAuteur3", "prenomAuteur3", "Fr", null);
			
			Theme 			theme1			= new Theme("T1", "nomT1", "descriptionT1");
			Theme 			theme2			= new Theme("T2", "nomT2", "descriptionT2");
			Theme 			theme3			= new Theme("T3", "nomT3", "descriptionT3");
			Theme 			theme4			= new Theme("T4", "anomT4", "descriptionT4");
			
//	document - localisation - auteur - theme(s)
//	1 	1 1 1		loc1 		A1 		T1
//	2 	1 1 0		loc2		A1			
//	3 	1 0 1		loc3				T1
//	4 	1 0 0		loc4
//	5 	0 1 1					A2		T2
//	6 	0 1 0					A1
//	7 	0 0 1							T1
//	8 	0 0 0
//	1a 	1 1 2		loc1a		A2		T2  T3
//	3a 	1 0 2		loc3a				T3	T4	
//	5a 	0 1 2					A3		T1	T3
//	7a 	0 0 2							T1	T4

//			1 	1 1 1	localisation - auteur - theme(s)
			document1.setLocalisation(localisation1);
			document1.setAuteur(auteur1);
			document1.addTheme(theme1);
//			2 	1 1 0			
			document2.setLocalisation(localisation2);
			document2.setAuteur(auteur1);
//			3 	1 0 1			
			document3.setLocalisation(localisation3);
			document3.addTheme(theme1);
//			4 	1 0 0
			document4.setLocalisation(localisation4);
			document4.setAuteur(auteur2);
//			5 	0 1 1	
			document5.setAuteur(auteur2);
			document5.addTheme(theme2);
//			6 	0 1 0
			document6.setAuteur(auteur1);
//			7 	0 0 1
			document7.addTheme(theme1);
//			8 	0 0 0
//			1a 	1 1 2
			document1a.setLocalisation(localisation1a);
			document1a.setAuteur(auteur2);
			document1a.addTheme(theme2);
			document1a.addTheme(theme3);
//			3a 	1 0 2
			document3a.setLocalisation(localisation3a);
			document3a.addTheme(theme3);
			document3a.addTheme(theme4);
//			5a 	0 1 2
			document5a.setAuteur(auteur3);
			document5a.addTheme(theme1);
			document5a.addTheme(theme3);
//			7a 	0 0 2
			document7a.addTheme(theme1);
			document7a.addTheme(theme4);
			
			try {
				document1 = serviceFacade.ajouter(document1);
				document2 = serviceFacade.ajouter(document2);
				document3 = serviceFacade.ajouter(document3);
				document4 = serviceFacade.ajouter(document4);
				document5 = serviceFacade.ajouter(document5);
				document6 = serviceFacade.ajouter(document6);
				document7 = serviceFacade.ajouter(document7);
				document8 = serviceFacade.ajouter(document8);
				document1a = serviceFacade.ajouter(document1a);
				document3a = serviceFacade.ajouter(document3a);
				document5a = serviceFacade.ajouter(document5a);
				document7a = serviceFacade.ajouter(document7a);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			
			// Les listes
			
			System.out.println("****  Liste des documents par cote ");
			System.out.println(serviceFacade.getAllDocumentByCote());

			System.out.println("****  Liste des documents par titre ");
			System.out.println(serviceFacade.getAllDocumentByTitre());

			System.out.println("****  Liste des documents pour un theme ");
			System.out.println(serviceFacade.getAllDocumentByTheme(theme1));
			
			System.out.println("****  Liste des localisations par id");
			System.out.println(serviceFacade.getAllLocalisationById());
			
			System.out.println("****  Liste des localisations par lieu");
			System.out.println(serviceFacade.getAllLocalisationByLieu());
			
			System.out.println("****  Liste des auteurs par id");
			System.out.println(serviceFacade.getAllAuteurById());
			
			System.out.println("****  Liste des auteurs par nom");
			System.out.println(serviceFacade.getAllAuteurByNom());
			
			System.out.println("****  Liste des themes par id");
			System.out.println(serviceFacade.getAllThemeById());
			
			System.out.println("****  Liste des themes par nom");
			System.out.println(serviceFacade.getAllThemeByNom());
			
			System.out.println("****  Liste des themes pour un document");
			System.out.println(serviceFacade.getAllThemeByDoc(document1a));
				

			System.out.println("\n****  Fin ***********");

		} catch (NamingException e) {
			System.out.println("*** AT Erreur InitBdD - NamingException");
			e.printStackTrace();
		} 
	}

}
