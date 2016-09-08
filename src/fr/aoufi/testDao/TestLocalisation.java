package fr.aoufi.testDao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import fr.aoufi.clientServer.IServiceFacade;
import fr.aoufi.clientServer.UserException;
import fr.aoufi.entity.Localisation;
import fr.aoufi.ressources.Param;

/**
 * Insertion de quelques documents
 *
 */
public class TestLocalisation {

	public static void main(String[] args)  {
		try {

			Context context = new InitialContext();

			IServiceFacade serviceFacade 			= (IServiceFacade) context.lookup(Param.EJB_SERVICE_FACADE);

			// delete des donnees à partir des tables
			serviceFacade.removeDocumentNative();
			serviceFacade.removeLocalisationNative();

			Localisation localisation = null;

			// initialisation
			
			try {
				localisation = new Localisation("lieu1", "emp1");
				localisation = serviceFacade.ajouter(localisation);
				System.out.println("** AT localisation add : " + localisation);
				
				localisation = new Localisation("lieu1", "emp2");
				localisation = serviceFacade.ajouter(localisation);
				System.out.println("** AT localisation add : " + localisation);

				localisation = new Localisation("lieu1", "emp3");
				localisation = serviceFacade.ajouter(localisation);
				System.out.println("** AT localisation add : " + localisation);

				localisation = new Localisation("lieu1", "emp4");
				localisation = serviceFacade.ajouter(localisation);
				System.out.println("** AT localisation add : " + localisation);
				
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}			
			
			// on ne peut pas ajouter 2 fois la meme localisation
			try {
				localisation = new Localisation("lieu1", "emp1");
				localisation = serviceFacade.ajouter(localisation);
				System.out.println("** AT localisation add : " + localisation);
				
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}

			// recherche d'une localisation existante
			try {
				localisation = null;
				localisation = serviceFacade.getLocalisation("lieu1", "emp2");
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}
			System.out.println("** AT localisation get(\"lieu1\", \"emp2\") : " + localisation);

			// recherche d'une localisation inexistante
			try {
				localisation = null;
				localisation = serviceFacade.getLocalisation("lieux", "emp2");
			} catch (UserException e) {
				System.out.println("***  ERREUR AT : " + e.getCode() + " " + e.getMessage());
			}
			System.out.println("** AT localisation get(\"lieux\", \"emp2\") : " + localisation);


			// =========================================================
			System.out.println("\n****  Fin ***********");

		} catch (NamingException e) {
			System.out.println("*** AT Erreur InitBdD - NamingException");
			e.printStackTrace();
		} 

	}

}
