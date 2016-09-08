package fr.aoufi.testDao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import fr.aoufi.clientServer.IServiceFacade;
import fr.aoufi.clientServer.UserException;
import fr.aoufi.entity.Auteur;
import fr.aoufi.entity.Document;
import fr.aoufi.entity.Localisation;
import fr.aoufi.ressources.Param;


/**
 * Test de la couche Dao en direct (sans passer par les services facade ...)
 *
 */
public class TestDocumentAuteur {

	public static void main(String[] args)  {
		try {
			
			Context context = new InitialContext();

			IServiceFacade serviceFacade 			= (IServiceFacade) context.lookup(Param.EJB_SERVICE_FACADE);

			// delete des donnees à partir des tables
			serviceFacade.removeDocumentNative();
			serviceFacade.removeLocalisationNative();

			// ================   Test merge d'un document inexistant 	avec localisation et auteur existant
			System.out.println("******Rx : modifier le document 008 inexistant 	avec localisation et auteur existant");	
			Document document 		= new Document ("008", "titre8", "descriptif8", 10);
			Localisation localisation 	= new Localisation("lieu6", "emp6");
			Auteur auteur	 		= new Auteur("A4", "nomAuteur4", "prenomAuteur4", "Fr", null);
			document.setLocalisation(localisation);
			document.setAuteur(auteur);
			Document document1 = null;
			System.out.println("AT : document 008 : " + document);
			try {
				document1 	= serviceFacade.update(document);
				System.out.println("AT : document 008 : " + document1);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}		

			// ================   Test merge d'un document existant avec localisation et auteur existant
			System.out.println("******Rx : modifier le document 008 existant avec localisation et auteur existant");	
			document 		= new Document ("008", "titre8", "descriptif8", 10);
			localisation 	= new Localisation("lieu6", "emp6");
			auteur	 		= new Auteur("A3", "nomAuteur3", "prenomAuteur3", "Fr", null);
			document.setLocalisation(localisation);
			document.setAuteur(auteur);
			document1 = null;
			System.out.println("AT : document 008 : " + document);
			try {
				document.setTitre("titre8xxx");
				document.setDescriptif("descriptif8xxx");
				document1 	= serviceFacade.update(document);
				System.out.println("AT : document 008 : " + document1);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}		

			// ================   Test merge d'un document null
			System.out.println("******Rx : modifier le document null");	
			document = null;
			System.out.println("AT : document null : " + document);
			try {
				document1 	= serviceFacade.update(document);
				System.out.println("AT : document null : " + document1);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			/**
			 * RECHERCHE
			 */
			// ================   Test recherche d'un document existant par son identifiant
			// ================   Test recherche d'un auteur existant par son identifiant
			// ================   Test recherche d'un auteur par son identifiant null
			// ================   Test recherche d'un auteur par son identifiant empty
			// ================   Test recherche d'un auteur par son identifiant inexistant

			// ================   Test recherche d'un document existant par son identifiant
			System.out.println("******Rx : recherche d'un document par son identifiant 001");
			try {
				document = serviceFacade.getDocument("001");
				System.out.println("AT : document 001 : " + document);
			} catch  (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			// ================   Test recherche d'un auteur existant par son identifiant
			System.out.println("******Rx : recherche d'un auteur par son identifiant A2");
			try {
				auteur = serviceFacade.getAuteur("A2");
				System.out.println("AT : auteur A2 : " + auteur);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			// ================   Test recherche d'un auteur par son identifiant null
			System.out.println("******Rx : recherche d'un auteur par son identifiant null");
			try {
				auteur = serviceFacade.getAuteur(null);
				System.out.println("AT : auteur null : " + auteur);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}
			
			// ================   Test recherche d'un auteur par son identifiant empty
			System.out.println("******Rx : recherche d'un auteur par son identifiant \"\"");
			try {
				auteur = serviceFacade.getAuteur("");
				System.out.println("AT : auteur \"\" : " + auteur);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			// ================   Test recherche d'un auteur par son identifiant inexistant
			System.out.println("******Rx : recherche d'un auteur par son identifiant A999 inexistant");
			try {
				auteur = serviceFacade.getAuteur("A999");
				System.out.println("AT : auteur A999 inexistant : " + auteur);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}


			/**
			 * AJOUT
			 */
			// ================   Test ajout d'un auteur inexistant
			// ================   Test ajout d'un auteur doublon
			// ================   Test ajout d'un auteur avec id null
			// ================   Test ajout d'un auteur avec id empty


			// ================   Test ajout d'un auteur inexistant A5
			System.out.println("******Rx : ajouter un auteur A5");
			auteur	 		= new Auteur("A5", "nomAuteur5", "prenomAuteur5", "Fr", null);
			try {
				Auteur auteur1 = serviceFacade.ajouter(auteur);
				System.out.println("AT : ajouter auteur A5 : " + auteur1);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			// ================   Test ajout d'un auteur A5 doublon
			System.out.println("******Rx : ajouter un auteur A5 doublon");
			auteur	 		= new Auteur("A5", "nomAuteur5", "prenomAuteur5", "Fr", null);
			try {
				Auteur auteur1 = serviceFacade.ajouter(auteur);
				System.out.println("AT : ajouter auteur A5 doublon : " + auteur1);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			// ================   Test ajout d'un auteur A6 avec id null
			System.out.println("******Rx : ajouter un auteur A6 avec id null");
			auteur	 		= new Auteur(null, "nomAuteur6", "prenomAuteur6", "Fr", null);
			try {
				Auteur auteur1 = serviceFacade.ajouter(auteur);
				System.out.println("AT : ajouter auteur A6 : " + auteur1);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			// ================   Test ajout d'un auteur A7 avec id empty
			System.out.println("******Rx : ajouter un auteur A7 avec id empty");
			auteur	 		= new Auteur("", "nomAuteur7", "prenomAuteur7", "Fr", null);
			try {
				Auteur auteur1 = serviceFacade.ajouter(auteur);
				System.out.println("AT : ajouter auteur A7 : " + auteur1);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			/**
			 * REMOVE
			 * on ne verifie pas l'existance de l'auteur
			 */
			// remove d'un document existant avec un auteur
			// remove d'un auteur non reference dans un document
			// remove d'un auteur reference dans un document
			// remove d'un auteur inexistant
			// remove d'un auteur null
			// remove d'un auteur avec id null
			// remove d'un auteur avec id empty

			// remove d'un document existant avec un auteur
			System.out.println("******Rx : remove d'un document existant avec un auteur");
			document 		= new Document ("009", "titre9", "descriptif9", 10);
			localisation 	= new Localisation("lieu7", "emp7");
			auteur	 		= new Auteur("A5", "nomAuteur5", "prenomAuteur5", "Fr", null);
			document.setLocalisation(localisation);
			document.setAuteur(auteur);

			try {
				System.out.println("** AT auteur add : " + auteur);
				document = serviceFacade.ajouter(document);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			// on supprime
			try {
				System.out.println("** AT document remove : " + document);
				serviceFacade.remove(document);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			// remove d'un auteur non reference dans un document
			System.out.println("******Rx : remove d'un auteur non reference dans un document");
			auteur	 		= new Auteur("A6", "nomAuteur6", "prenomAuteur6", "Fr", null);
			try {
				System.out.println("** AT auteur add : " + auteur);
				serviceFacade.ajouter(auteur);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}
			// on supprime
			try {
				System.out.println("** AT auteur remove : " + auteur);
				serviceFacade.remove(auteur);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			// remove d'un auteur reference dans un document
			System.out.println("******Rx : remove d'un auteur reference dans un document");
			document 		= new Document ("010", "titre10", "descriptif10", 10);
			localisation 	= new Localisation("lieu8", "emp8");
			auteur	 		= new Auteur("A7", "nomAuteur7", "prenomAuteur7", "Fr", null);
			document.setLocalisation(localisation);
			document.setAuteur(auteur);

			try {
				System.out.println("** AT document add : " + document);
				document = serviceFacade.ajouter(document);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			// on supprime l'auteur
			try {
				System.out.println("** AT auteur remove : " + auteur);
				serviceFacade.remove(auteur);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			// remove d'un auteur inexistant
			System.out.println("******Rx : remove d'un auteur inexistant");
			auteur	 		= new Auteur("XXX", "nomAuteur7", "prenomAuteur7", "Fr", null);
			try {
				System.out.println("** AT auteur remove : " + auteur);
				serviceFacade.remove(auteur);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			// remove d'un auteur null
			System.out.println("******Rx : remove d'un auteur null");
			auteur	 		= null;
			try {
				System.out.println("** AT auteur remove : " + auteur);
				serviceFacade.remove(auteur);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			// remove d'un auteur avec id null
			System.out.println("******Rx : remove d'un auteur avec id null");
			auteur	 		= new Auteur(null, "nomAuteur2", "prenomAuteur2", "Fr", null);
			try {
				System.out.println("** AT auteur remove : " + auteur);
				serviceFacade.remove(auteur);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			// remove d'un auteur avec id empty
			System.out.println("******Rx : remove d'un auteur avec id empty");
			auteur	 		= new Auteur("", "nomAuteur2", "prenomAuteur2", "Fr", null);
			try {
				System.out.println("** AT auteur remove : " + auteur);
				serviceFacade.remove(auteur);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}
			
			/**
			 * DIVERS
			 */
			// remove document par cote
			System.out.println("******Rx : remove document par cote");
			document 		= new Document ("011", "titre11", "descriptif11", 10);
			document1 		= new Document ("012", "titre12", "descriptif12", 10);
			localisation 	= new Localisation("lieu9", "emp9");
			Localisation localisation1 	= new Localisation("lieu10", "emp10");
			auteur	 		= new Auteur("A7", "nomAuteur7", "prenomAuteur7", "Fr", null);
			document.setLocalisation(localisation);
			document.setAuteur(auteur);
			document1.setLocalisation(localisation1);
			document1.setAuteur(auteur);

			try {
				System.out.println("** AT document add : " + document);
				System.out.println("** AT document add : " + document1);
				document = serviceFacade.ajouter(document);
				document = serviceFacade.ajouter(document1);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			// on supprime le document 11
			try {
				System.out.println("** AT document remove 011 : " + document);
				serviceFacade.removeDocument("011");
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			// remove tous les auteurs
			System.out.println("******Rx : remove de tous les auteurs - erreur affecte");
			try {
				serviceFacade.removeAuteur();
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			// remove tous les auteurs apres avoir remove documents
			System.out.println("******Rx : remove de tous les documents puis auteurs");
			try {
				serviceFacade.removeDocument();
				serviceFacade.removeAuteur();
				System.out.println("** AT document Tout est supprime");
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			System.out.println("\n****  Fin ***********");

		} catch (NamingException e) {
			System.out.println("***AT Erreur InitBdD - NamingException");
			e.printStackTrace();
		} 
	}

}
