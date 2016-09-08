package fr.aoufi.junit;


import static org.junit.Assert.assertEquals;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.aoufi.clientServer.IServiceFacade;
import fr.aoufi.clientServer.UserException;
import fr.aoufi.entity.Auteur;
import fr.aoufi.entity.Document;
import fr.aoufi.entity.Localisation;
import fr.aoufi.ressources.Param;

/**
 * Classe de test Junit pour tester Document et Auteur
 *
 */

/*
 * Rappel Junit			assertEquals(message, expected, obtain) 
 * 2 documents sont identiques si ils ont la même cote et le meme titre
 */
public class TestDocumentAuteur {

	private static IServiceFacade serviceFacade;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Context context = new InitialContext();
		serviceFacade 			= (IServiceFacade) context.lookup(Param.EJB_SERVICE_FACADE);
	}
	
	@Before
	public void setUpBefore() throws Exception {
		serviceFacade.removeDocumentNative();
		serviceFacade.removeLocalisationNative();
		serviceFacade.removeAuteurNative();
	}
	
	// ================   Test add
	@Test
	public void testAddDocumentAvecAuteurInexistantEnBdd() throws UserException {
		Document 		document 		= new Document ("001", "titre", "descriptif", 10);
		Localisation 	localisation 	= new Localisation("lieu1", "emp1");
		Auteur 			auteur	 		= new Auteur("A1", "nomAuteur1", "prenomAuteur1", "Fr", null);
		document.setLocalisation(localisation);
		document.setAuteur(auteur);
		assertEquals("le doc est : ",document,serviceFacade.ajouter(document));
	}
	
	@Test
	public void testAddDocumentAvecAuteurExistantEnBdd() throws UserException {
		Document document 			= new Document ("001", "titre1", "descriptif1", 10);
		Localisation localisation 	= new Localisation("lieu1", "emp1");
		Auteur auteur	 			= new Auteur("A1", "nomAuteur1", "prenomAuteur1", "Fr", null);
		document.setLocalisation(localisation);
		document.setAuteur(auteur);
		serviceFacade.ajouter(auteur);
		assertEquals("le doc est : ",document,serviceFacade.ajouter(document));		
	}
	
	@Test
	public void testAddDocumentAvecAuteurNull() throws UserException {
		Document document 			= new Document ("001", "titre1", "descriptif1", 10);
		Localisation localisation 	= new Localisation("lieu1", "emp1");
		document.setLocalisation(localisation);
		document.setAuteur(null);
		assertEquals("le doc est : ",document,serviceFacade.ajouter(document));	
	}
	
	@Test
	public void testMergeDocumentInexistantSansLocalisationNiAuteur() throws UserException {
		Document document 			= new Document ("001", "titre1", "descriptif1", 10);
		assertEquals("le doc est : ",document,serviceFacade.update(document));	
	}
	
	@Test
	public void testMergeDocumentExistantSansLocalisationNiAuteur() throws UserException {
		Document document 			= new Document ("001", "titre1", "descriptif1", 10);
		serviceFacade.ajouter(document);
		document.setTitre("titrexxx");
		serviceFacade.update(document);
		assertEquals("le doc est : ","titrexxx",serviceFacade.getDocument("001").getTitre());	
	}
	
	@Test
	public void testMergeDocumentInexistantAvecLocalisationSansAuteur() throws UserException {
		Document document 			= new Document ("001", "titre1", "descriptif1", 10);
		Localisation localisation 	= new Localisation("lieu1", "emp1");
		document.setLocalisation(localisation);
		document.setAuteur(null);
		serviceFacade.update(document);
		assertEquals("le doc est : ",document,serviceFacade.getDocument("001"));	
	}
	
	@Test
	public void testMergeDocumentExistantAvecLocalisationSansAuteur() throws UserException {
		Document document 			= new Document ("001", "titre1", "descriptif1", 10);
		Localisation localisation 	= new Localisation("lieu1", "emp1");
		document.setLocalisation(localisation);
		document.setAuteur(null);
		serviceFacade.ajouter(document);
		document.setTitre("titrexxx");
		serviceFacade.update(document);
		assertEquals("le doc est : ","titrexxx",serviceFacade.getDocument("001").getTitre());	

	}
	
	@Test
	public void testMergeDocumentInexistantAvecLocalisationEtAuteurExistant() throws UserException {
	}
		
	@Test
	public void testMergeDocumentExistantAvecLocalisationEtAuteurInexistant() throws UserException {
	}
	
	@Test
	public void testMergeDocumentNull() throws UserException {
	}
	
//	@Test
//	public void test() throws UserException {
//	}
//	@Test
//	public void test() throws UserException {
//	}
//	@Test
//	public void test() throws UserException {
//	}
//	@Test
//	public void test() throws UserException {
//	}
//	@Test
//	public void test() throws UserException {
//	}
//	@Test
//	public void test() throws UserException {
//	}
//	@Test
//	public void test() throws UserException {
//	}
//	@Test
//	public void test() throws UserException {
//	}
//	@Test
//	public void test() throws UserException {
//	}
//	@Test
//	public void test() throws UserException {
//	}
//	@Test
//	public void test() throws UserException {
//	}
//	@Test
//	public void test() throws UserException {
//	}
//	@Test
//	public void test() throws UserException {
//	}
//	@Test
//	public void test() throws UserException {
//	}
//	@Test
//	public void test() throws UserException {
//	}
//	@Test
//	public void test() throws UserException {
//	}
//	@Test
//	public void test() throws UserException {
//	}
//	@Test
//	public void test() throws UserException {
//	}
//	@Test
//	public void test() throws UserException {
//	}
//	@Test
//	public void test() throws UserException {
//	}
//	@Test
//	public void test() throws UserException {
//	}
//	@Test
//	public void test() throws UserException {
//	}
//	@Test
//	public void test() throws UserException {
//	}
//	@Test
//	public void test() throws UserException {
//	}
//	@Test
//	public void test() throws UserException {
//	}

}
