package fr.aoufi.junit;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.BeforeClass;
import org.junit.Test;

import fr.aoufi.clientServer.IServiceFacade;
import fr.aoufi.clientServer.UserException;
import fr.aoufi.entity.Document;
import fr.aoufi.entity.Localisation;
import fr.aoufi.ressources.Param;

/**
 * Classe de test Junit pour tester Document
 *
 */
/*
 * Rappel Junit			assertEquals(message, expected, obtain)
 * 
 * 2 documents sont identiques si ils ont la même cote et le meme titre
 */
public class TestDocument {

	private static IServiceFacade serviceFacade;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Context context = new InitialContext();
		serviceFacade 			= (IServiceFacade) context.lookup(Param.EJB_SERVICE_FACADE);
		serviceFacade.removeDocumentNative();
		serviceFacade.removeLocalisationNative();
	}
	
	@Test
	public void testAddDocumentNull() throws UserException {
		Document document = null;
		assertNull("R1 : ajouter un document null ",serviceFacade.ajouter(document));

	}
	
	@Test
	public void testAddDocumentSansLocalisation() throws UserException {
		Document document = new Document ("001", "titre", "descriptif", 10);
		assertEquals("le doc est : ",document,serviceFacade.ajouter(document));
	}
	
	//Les 3 tests suivants n'en forme que un seul - A Revoir
	//===================================================================================
	@Test
	public void testAddDocumentAvecLocalisationInexistanteEnBdd1() throws UserException {
		Document document = new Document ("002", "titre2", "descriptif2", 10);
		Localisation localisation 	= new Localisation("lieu2", "emp2");
		document.setLocalisation(localisation);
		assertEquals("le doc est : ",document,serviceFacade.ajouter(document));
	}
	
	@Test
	public void testAddDocumentAvecLocalisationInexistanteEnBdd2() throws UserException {
		Document document = new Document ("002a", "titre2a", "descriptif2a", 10);
		Localisation localisation 	= new Localisation("lieu2a", "emp2a");
		document.setLocalisation(localisation);
		assertEquals("le doc est : ",document.getLocalisation().getLieu(),serviceFacade.ajouter(document).getLocalisation().getLieu());
	}
	
	@Test
	public void testAddDocumentAvecLocalisationInexistanteEnBdd3() throws UserException {
		Document document = new Document ("002b", "titre2b", "descriptif2b", 10);
		Localisation localisation 	= new Localisation("lieu2b", "emp2b");
		document.setLocalisation(localisation);
		assertEquals("le doc est : ",document.getLocalisation().getEmp(),serviceFacade.ajouter(document).getLocalisation().getEmp());
	}
	//===================================================================================
	
	@Test
	public void testAddDocumentAvecLocalisationExistanteEnBdd() throws UserException {
		
		Document document 			= new Document ("003", "titre 3", "descriptif 3", 15);
		Localisation localisation 	= new Localisation("lieu3", "emp3");
		// la localisation est detachee, on la met dans le contexte de persistance
		localisation = serviceFacade.ajouter(localisation);
		document.setLocalisation(localisation);
		assertEquals("le doc est : ",document,serviceFacade.ajouter(document));	
	}
	
	@Test 
	public void testAddDocumentAvecLocalisationDejaAffectee() throws UserException {
		Document document4 		= new Document ("004","titre 4", "descriptif 4", 20);
		Document document5 		= new Document ("005","titre 5", "descriptif 5", 15);
		Localisation localisation1 	= new Localisation("lieu1", "emp1");
		document4.setLocalisation(localisation1);
		document5.setLocalisation(localisation1);
		int erreur = 0;
		// ne doit pas faire d'erreur
		document4 = serviceFacade.ajouter(document4);
		
		// Doit renvoyer une erreur DOC_LOC_AFFECTEE(2) - Voir ressources.Erreur
		try {
			document5 = serviceFacade.ajouter(document5);
		} catch (UserException e) {
			erreur = e.getCode();
		}
		assertEquals("le code erreur est : ",2,erreur);	
	}
	
	@Test
	public void testDocumentDoublon() throws UserException {
		Document document 		= new Document ("006","titre 6", "descriptif 6", 20);
		Document document1 		= new Document ("006","titre 6", "descriptif 6", 20);
		int erreur = 0;
		// ne doit pas faire d'erreur
		document = serviceFacade.ajouter(document);
		
		// Doit renvoyer une erreur DOC_DOUBLON(1), - Voir ressources.Erreur
		try {
			document1 = serviceFacade.ajouter(document1);
		} catch (UserException e) {
			erreur = e.getCode();
		}
		assertEquals("le code erreur est : ",1,erreur);	
	}
	
	@Test
	public void testRechercheDocumentInexistant() throws UserException {
		Document document = new Document ("007","titre 7", "descriptif 7", 20);
		serviceFacade.ajouter(document);
		assertEquals("le doc est : ",document,serviceFacade.getDocument("007"));		
	}
	
	@Test
	public void RechercheDocumentInexistant() {
		int erreur = 0;
		// Doit renvoyer une erreur DOC_INEXISTANT(3) - Voir ressources.Erreur
		try {
			serviceFacade.getDocument("999");
		} catch (UserException e) {
			erreur = e.getCode();
		}
		assertEquals("le code erreur est : ",3,erreur);	
	}
	
	@Test
	public void testDocumentCoteObligatoireAvecNull() throws UserException {
		Document document 		= new Document (null,"titre 8", "descriptif 8", 20);
		int erreur = 0;
		// Doit renvoyer une erreur DOC_COTE_OBLIGATOIRE(4), - Voir ressources.Erreur
		try {
			serviceFacade.ajouter(document);
		} catch (UserException e) {
			erreur = e.getCode();
		}
		assertEquals("le code erreur est : ",4,erreur);	
	}
	
	@Test
	public void testDocumentCoteObligatoireAvecUnEspace() throws UserException {
		Document document 		= new Document ("","titre 8", "descriptif 8", 20);
		int erreur = 0;
		// Doit renvoyer une erreur DOC_COTE_OBLIGATOIRE(4), - Voir ressources.Erreur
		try {
			serviceFacade.ajouter(document);
		} catch (UserException e) {
			erreur = e.getCode();
		}
		assertEquals("le code erreur est : ",4,erreur);	
	}
	
	@Test
	public void testDocumentCoteObligatoireAvecDeuxEspaces() throws UserException {
		Document document 		= new Document (" ","titre 8", "descriptif 8", 20);
		int erreur = 0;
		// Doit renvoyer une erreur DOC_COTE_OBLIGATOIRE(4), - Voir ressources.Erreur
		try {
			serviceFacade.ajouter(document);
		} catch (UserException e) {
			erreur = e.getCode();
		}
		assertEquals("le code erreur est : ",4,erreur);	
	}
	
	@Test
	public void testDocumentTitreObligatoireAvecNull() throws UserException {
		Document document 		= new Document ("008",null, "descriptif 8", 20);
		int erreur = 0;
		// Doit renvoyer une erreur 	DOC_TITRE_OBLIGATOIRE(5), - Voir ressources.Erreur
		try {
			serviceFacade.ajouter(document);
		} catch (UserException e) {
			erreur = e.getCode();
		}
		assertEquals("le code erreur est : ",5,erreur);	
	}
	
	@Test
	public void testDocumentTitreObligatoireAvecUnEspace() throws UserException {
		Document document 		= new Document ("008","", "descriptif 8", 20);
		int erreur = 0;
		// Doit renvoyer une erreur 	DOC_TITRE_OBLIGATOIRE(5), - Voir ressources.Erreur
		try {
			serviceFacade.ajouter(document);
		} catch (UserException e) {
			erreur = e.getCode();
		}
		assertEquals("le code erreur est : ",5,erreur);	
	}
	
	@Test
	public void testDocumentTitreObligatoireAvecDeuxEspaces() throws UserException {
		Document document 		= new Document ("008"," ", "descriptif 8", 20);
		int erreur = 0;
		// Doit renvoyer une erreur 	DOC_TITRE_OBLIGATOIRE(5), - Voir ressources.Erreur
		try {
			serviceFacade.ajouter(document);
		} catch (UserException e) {
			erreur = e.getCode();
		}
		assertEquals("le code erreur est : ",5,erreur);	
	}

}
