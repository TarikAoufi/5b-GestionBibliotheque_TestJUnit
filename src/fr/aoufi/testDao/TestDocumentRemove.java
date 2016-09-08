package fr.aoufi.testDao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import fr.aoufi.clientServer.IServiceFacade;
import fr.aoufi.clientServer.UserException;
import fr.aoufi.entity.Document;
import fr.aoufi.entity.Localisation;
import fr.aoufi.ressources.Param;

/**
 * Insertion de quelques documents
 *
 */
public class TestDocumentRemove {

	public static void main(String[] args)  {
		try {

			Context context = new InitialContext();

			IServiceFacade serviceFacade = (IServiceFacade) context.lookup(Param.EJB_SERVICE_FACADE);

			// delete des donnees à partir des tables
			serviceFacade.removeDocument();
			serviceFacade.removeLocalisation();

			Document document = null;
			Localisation localisation;
			String[] listeCote = new String[10];					
			try {
				for (int i=0; i<10; i++) {
					// pas d'id - geres par les sequences
					document = new Document(""+i, "titre"+i, "descriptif"+i, 10+i);
					localisation = new Localisation("lieu1", "emp" + i);
					document.setLocalisation((Localisation)localisation);
					
					document = serviceFacade.ajouter(document);
					listeCote[i] = document.getCote();
					System.out.println("InitBdD - main : document : " + document);
				} 

			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			for (int i=0; i<5; i++ ) {
				localisation = new Localisation("lieu2", "emp" + i);
				try {
					localisation = serviceFacade.ajouter(localisation);
				} catch (UserException e) {
					System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
				}
				System.out.println("** AT localisation add : " + localisation);
			}

			// suppression d'un document existant recupere depuis find
			try {
				document = serviceFacade.getDocument(listeCote[1]);
				System.out.println("** AT document remove(document) à supprimer : " + document);
				serviceFacade.remove(document);
				System.out.println("** AT document remove(document) après remove: " + document);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}
			
			// suppression d'un document existant avec detached object
			try {
				document = new Document("2","toto");
				document.setCote(listeCote[2]);
				serviceFacade.remove(document);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}
			System.out.println("** AT document remove(document) supprime    : " + document);

			// suppression d'un document par cote
			System.out.println("** AT document remove(id) à supprimer       : " + listeCote[3]);
			try {
				serviceFacade.removeDocument(listeCote[3]);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			// suppression d'une localisation affectee à un document 
			System.out.println("** AT document remove d'une localisation affectee à un document");
			Localisation localisation5;
			try {
				localisation5 = serviceFacade.getLocalisation("lieu1", "emp5");
				serviceFacade.remove(localisation5);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			// suppression de tout 
	//		serviceFacade.removeDocument();
			
			// =========================================================
			System.out.println("\n****  Fin ***********");

		} catch (NamingException e) {
			System.out.println("*** AT Erreur InitBdD - NamingException");
			e.printStackTrace();
		} 
	}

}
