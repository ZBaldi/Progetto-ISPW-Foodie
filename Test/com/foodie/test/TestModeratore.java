package com.foodie.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import com.foodie.model.Moderatore;
import com.foodie.model.Ricetta;

public class TestModeratore {   //VALERIO BALDAZZI
	
	private static final String NOME1= "Pasta alla Carbonara";
	private static final String NOME2= "Pasta alla Gricia";
	private static final String AUTORE= "Barbieri";
	
	@Before
	public void riempiLista() {
		Moderatore moderatore = Moderatore.ottieniIstanza();
		Ricetta ricettaDiProva= new Ricetta();
		ricettaDiProva.setNome(NOME1);
		ricettaDiProva.setAutore(AUTORE);
		moderatore.aggiungiRicettaDaVerificare(ricettaDiProva);	
	}
	
	@Test
	public void testGetViewInizialeTipo1() {
		Moderatore moderatore=Moderatore.ottieniIstanza();
		String s1=moderatore.getViewIniziale(1);
		String s2= "/com/foodie/boundary/ModeratoreView.fxml"; //STRINGA CHE MI ASPETTO
		assertEquals(s1,s2);  //CONTROLLO SE COINCIDONO
	}
	
	@Test
	public void testGetViewInizialeTipo2() {
		Moderatore moderatore=Moderatore.ottieniIstanza();
		String s1=moderatore.getViewIniziale(2);
		String s2= "/com/foodie/boundary2/ModeratoreView2.fxml"; //STRINGA CHE MI ASPETTO
		assertEquals(s1,s2);  //CONTROLLO SE COINCIDONO
	}
	
	@Test
	public void testAggiungiRicettaDaVerificareNonPresente() {
		Moderatore moderatore=Moderatore.ottieniIstanza();
		int oldSize= Moderatore.getRicetteDaVerificare().size();
		Ricetta ricettaDiProva= new Ricetta();
		ricettaDiProva.setNome(NOME2);
		ricettaDiProva.setAutore(AUTORE);
		moderatore.aggiungiRicettaDaVerificare(ricettaDiProva);
		assertTrue(Moderatore.getRicetteDaVerificare().contains(ricettaDiProva));  //CONTROLLO SE LA RICETTA E' STATA AGGIUNTA
		assertEquals(Moderatore.getRicetteDaVerificare().size(), oldSize+1);
		moderatore.ricettaVerificata(ricettaDiProva);  //COSI' LA RIPORTO ALLO STATO INIZIALE PER FARE GLI ALTRI TEST
	}
	
	@Test
	public void testAggiungiRicettaDaVerificarePresente() {
		Moderatore moderatore=Moderatore.ottieniIstanza();
		int oldSize= Moderatore.getRicetteDaVerificare().size();
		Ricetta ricettaDiProva= new Ricetta();
		ricettaDiProva.setNome(NOME1);
		ricettaDiProva.setAutore(AUTORE);
		moderatore.aggiungiRicettaDaVerificare(ricettaDiProva);
		assertEquals(Moderatore.getRicetteDaVerificare().size(), oldSize);  //CONTROLLO SE LA LISTA E' RIMASTA INVARIATA
	}
	
	@Test
	public void testRicettaVerificataNonPresente() {
		Moderatore moderatore=Moderatore.ottieniIstanza();
		int oldSize= Moderatore.getRicetteDaVerificare().size();
		Ricetta ricettaDiProva= new Ricetta();
		ricettaDiProva.setNome(NOME2);
		ricettaDiProva.setAutore(AUTORE);
		moderatore.ricettaVerificata(ricettaDiProva);
		assertFalse(Moderatore.getRicetteDaVerificare().contains(ricettaDiProva));  //CONTROLLO CHE NON E' STATA AGGIUNTA
		assertEquals(Moderatore.getRicetteDaVerificare().size(),oldSize);   //CONTROLLO CHE LA DIMENSIONE E' RIMASTA INVARIATA
	}
	
	@Test
	public void testRicettaVerificataPresente() {
		Moderatore moderatore=Moderatore.ottieniIstanza();
		int oldSize= Moderatore.getRicetteDaVerificare().size();
		Ricetta ricettaDiProva= new Ricetta();
		ricettaDiProva.setNome(NOME1);
		ricettaDiProva.setAutore(AUTORE);
		moderatore.ricettaVerificata(ricettaDiProva);
		assertFalse(Moderatore.getRicetteDaVerificare().contains(ricettaDiProva));  //CONTROLLO CHE NON E' STATA AGGIUNTA
		assertEquals(Moderatore.getRicetteDaVerificare().size(),oldSize-1); 
		moderatore.aggiungiRicettaDaVerificare(ricettaDiProva);  //RIPRISTINO LO STATO INIZIALE DELLA RICETTA
	}
	
	@Test
	public void testOttieniRicetta() {
		Ricetta ricettaDiProva= new Ricetta();
		ricettaDiProva.setNome(NOME1);
		ricettaDiProva.setAutore(AUTORE);
		assertEquals(Moderatore.ottieniRicetta(NOME1, AUTORE),ricettaDiProva);  //CONTROLLA SE OTTENGO LA RICETTA ASPETTATA
	}
	
}