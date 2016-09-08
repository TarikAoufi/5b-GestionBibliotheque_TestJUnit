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

public class TestDocumentMerge {

	public static void main(String[] args) {
		try {

			Context context = new InitialContext();

			IServiceFacade serviceFacade = (IServiceFacade) context.lookup(Param.EJB_SERVICE_FACADE);

			// delete des donnees à partir des tables
			serviceFacade.removeDocumentNative();
			serviceFacade.removeLocalisationNative();

			Document document = null;
			Localisation localisation;
			String[] listeCote = new String[10];

			for (int i = 0; i < 10; i++) {
				try {
					// pas d'id - geres par les sequences
					document = new Document("00" + i, "titre" + i, "descriptif"	+ i, 10 + i);
					localisation = new Localisation("lieu1", "emp" + i);
					document.setLocalisation((Localisation) localisation);

					System.out.println("InitBdD - main : document : " + document);
					document = serviceFacade.ajouter(document);
					listeCote[i] = document.getCote();
				} catch (UserException e) {
					System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
				}

			}

			int idLoca = 0;
			for (int i = 0; i < 5; i++) {
				try {
					localisation = new Localisation("lieu2", "emp" + i);
					localisation = serviceFacade.ajouter(localisation);
					idLoca = localisation.getIdLocalisation();
					System.out.println("** AT localisation add : " + localisation);
				} catch (UserException e) {
					System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
				}
			}

			// pas de control sur doublon titre document

			// modification d'un document existant avec une localisation
			// recupere depuis find
			// doit modifier
			try {
				document = null;
				document = serviceFacade.getDocument(listeCote[1]);
				System.out.println("** AT document update(document) à modifier : " + document);
				document.setTitre("toto");
				document.setDescriptif("descr toto");
				document.setNbExemplaireDispo(100);
				document = serviceFacade.update(document);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}
			System.out.println("** AT document update(document) modifie   : " + document);

			// modification d'un document existant recupere depuis find auquel
			// on associe une localisation
			// existante dans un autre document => ne doit pas modifier
			try {
				document = null;
				document = serviceFacade.getDocument(listeCote[2]);
				System.out.println("** AT document update(document) à modifier : " + document);
				document.setTitre("toto");
				document.setDescriptif("descr toto");
				document.setNbExemplaireDispo(100);

				localisation = serviceFacade.getLocalisation("lieu1", "emp4");
				document.setLocalisation(localisation);
				document = serviceFacade.update(document);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			System.out.println("** AT document update(document) modifie   : " + document);

			// modification d'un document existant avec detached object
			try {
				document = new Document("3", "titre at");
				document.setCote(listeCote[3]);
				System.out.println("** AT document update(document) à modifier : " + document);
				document = serviceFacade.update(document);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}
			System.out.println("** AT document update(document) modifie   : " + document);

			// modification d'un document inexistant
			try {
				document = new Document("99", "titre dm1");
				System.out.println("** AT document update(document) à modifier : " + document);
				document = serviceFacade.update(document);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}
			System.out.println("** AT document update(document) modifie   : " + document);

			// modification de la localisation du document
			document = null;

			try {
				document = serviceFacade.getDocument(listeCote[4]);
				System.out.println("** AT document update(document) à modifier : " + document);
				document.getLocalisation().setLieu("lieu DM");
				document.getLocalisation().setEmp("emp DM");
				document = serviceFacade.update(document);

			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}
			System.out.println("** AT document update(document) modifie   : " + document);

			// changement de la localisation du document
			document = null;

			try {
				document = serviceFacade.getDocument(listeCote[5]);
				System.out.println("** AT document update(document) à modifier : " + document);
				localisation = serviceFacade.getLocalisation(idLoca);
				document.setLocalisation((Localisation) localisation);
				document = serviceFacade.update(document);

			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			System.out.println("** AT document update(document) modifie   : " + document);

			// verification de la possibilite de creer des doublons sur loca
			document = null;

			try {
				document = serviceFacade.getDocument(listeCote[6]);
				System.out.println("** AT document update(document) à modifier : "	+ document);
				localisation = serviceFacade.getLocalisation("lieu1", "emp8");
				localisation.setLieu(localisation.getLieu());
				localisation.setEmp(localisation.getEmp());
				document.setLocalisation((Localisation) localisation);
				document = serviceFacade.update(document);
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " "	+ e.getMessage());
			}

			System.out.println("** AT document update(document) modifie   : " + document);

			// =========================================================
			System.out.println("\n****  Fin ***********");

		} catch (NamingException e) {
			System.out.println("*** AT Erreur InitBdD - NamingException");
			e.printStackTrace();
		}

	}

}
