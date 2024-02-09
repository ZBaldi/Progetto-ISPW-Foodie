package com.foodie.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.foodie.model.Alimento;
import com.foodie.model.Dispensa;

public class TestDispensa {    //VALERIO BALDAZZI
	
	private static final String NOME1= "Pizza";
	private static final String NOME2= "Pasta";
	
	@Before
	public void riempiDispensa() {
		Dispensa dispensa = Dispensa.ottieniIstanza();
		Alimento alimentoProva= new Alimento(NOME1);
		dispensa.aggiungiAlimento(alimentoProva);
	}
	
	
	@Test
	public void testAggiungiAlimentoNonPresente() {
		Dispensa dispensa= Dispensa.ottieniIstanza();
		int oldSize= dispensa.getAlimenti().size();
		Alimento alimentoProva= new Alimento(NOME2);
		dispensa.aggiungiAlimento(alimentoProva);
		assertTrue(dispensa.getAlimenti().contains(alimentoProva));  //CONTROLLO SE L'ALIMENTO E' STATO AGGIUNTO
		assertEquals(dispensa.getAlimenti().size(), oldSize+1);
		dispensa.eliminaAlimento(alimentoProva);  //COSI' LA RIPORTO ALLO STATO INIZIALE PER FARE GLI ALTRI TEST
	}
	
	@Test
	public void testAggiungiAlimentoPresente() {
		Dispensa dispensa= Dispensa.ottieniIstanza();
		int oldSize= dispensa.getAlimenti().size();
		Alimento alimentoProva= new Alimento(NOME1);
		dispensa.aggiungiAlimento(alimentoProva);
		assertEquals(dispensa.getAlimenti().size(),oldSize);   //CONTROLLO SE LA DISPENSA IN DIMENSIONI E' RIMASTA INVARIATA
	}
	
	@Test
	public void testEliminaAlimentoNonPresente() {
		Dispensa dispensa= Dispensa.ottieniIstanza();
		int oldSize= dispensa.getAlimenti().size();
		Alimento alimentoProva= new Alimento(NOME2);
		dispensa.eliminaAlimento(alimentoProva);
		assertFalse(dispensa.getAlimenti().contains(alimentoProva));  //CONTROLLO CHE NON E' STATO AGGIUNTO 
		assertEquals(dispensa.getAlimenti().size(),oldSize);  //CONTROLLO CHE LA DIMENSIONE E' RIMASTA INVARIATA
	}
	
	@Test
	public void testEliminaAlimentoPresente() {
		Dispensa dispensa= Dispensa.ottieniIstanza();
		int oldSize= dispensa.getAlimenti().size();
		Alimento alimentoProva= new Alimento(NOME1);
		dispensa.eliminaAlimento(alimentoProva);
		assertFalse(dispensa.getAlimenti().contains(alimentoProva));
		assertEquals(dispensa.getAlimenti().size(),oldSize-1);
		dispensa.aggiungiAlimento(alimentoProva); //RIPRISTINO LO STATO INIZIALE DELLA DISPENSA
	}
	
	@Test
	public void testSvuotaDispensa() {
		Dispensa dispensa= Dispensa.ottieniIstanza();
		dispensa.svuotaDispensa();
		assertEquals(dispensa.getAlimenti().size(),0);
	}
	
}
