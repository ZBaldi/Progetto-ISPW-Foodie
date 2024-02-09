package com.foodie.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.foodie.model.Alimento;
import com.foodie.model.Ricetta;

public class TestRicetta {      //VALERIO BALDAZZI
	
	private static final String NOME1= "Pizza";
	private static final String NOME2= "Pasta";
	private static final String QUANTITA= "100 g";
	private Ricetta ricettaDiProva= new Ricetta();
	
	@Before
	public void riempiRicetta() {
		Alimento alimentoProva= new Alimento(NOME1);
		ricettaDiProva.aggiungiIngrediente(alimentoProva, QUANTITA);
	}
	
	
	@Test
	public void testAggiungiIngredienteNonPresente() {
		int oldSize= ricettaDiProva.getIngredienti().size();
		Alimento alimentoProva= new Alimento(NOME2);
		ricettaDiProva.aggiungiIngrediente(alimentoProva, QUANTITA);
		assertTrue(ricettaDiProva.getIngredienti().contains(alimentoProva));  //CONTROLLO SE L'INGREDIENTE E' STATO AGGIUNTO
		assertEquals(ricettaDiProva.getIngredienti().size(), oldSize+1);
		ricettaDiProva.eliminaIngrediente(alimentoProva);  //COSI' LA RIPORTO ALLO STATO INIZIALE PER FARE GLI ALTRI TEST
	}
	
	@Test
	public void testAggiungiIngredientePresente() {
		int oldSize= ricettaDiProva.getIngredienti().size();
		Alimento alimentoProva= new Alimento(NOME1);
		ricettaDiProva.aggiungiIngrediente(alimentoProva, QUANTITA);
		assertEquals(ricettaDiProva.getIngredienti().size(),oldSize);   //CONTROLLO SE LA RICETTA E' RIMASTA INVARIATA
	}
	
	@Test
	public void testEliminaIngredienteNonPresente() {
		int oldSize= ricettaDiProva.getIngredienti().size();
		Alimento alimentoProva= new Alimento(NOME2);
		ricettaDiProva.eliminaIngrediente(alimentoProva);
		assertFalse(ricettaDiProva.getIngredienti().contains(alimentoProva));  //CONTROLLO CHE NON E' STATO AGGIUNTO
		assertEquals(ricettaDiProva.getIngredienti().size(),oldSize);   //CONTROLLO CHE LA DIMENSIONE E' RIMASTA INVARIATA
	}
	
	@Test
	public void testEliminaIngredientePresente() {
		int oldSize= ricettaDiProva.getIngredienti().size();
		Alimento alimentoProva= new Alimento(NOME1);
		ricettaDiProva.eliminaIngrediente(alimentoProva);
		assertFalse(ricettaDiProva.getIngredienti().contains(alimentoProva));
		assertEquals(ricettaDiProva.getIngredienti().size(),oldSize-1);
		ricettaDiProva.aggiungiIngrediente(alimentoProva, QUANTITA); //RIPRISTINO LO STATO INIZIALE DELLA RICETTA
	}
	
}