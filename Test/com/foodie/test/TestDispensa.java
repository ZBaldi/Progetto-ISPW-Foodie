package com.foodie.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.foodie.model.Alimento;
import com.foodie.model.Dispensa;

public class TestDispensa {
	
	@Before
	public void riempiDispensa() {
		Dispensa dispensa = Dispensa.ottieniIstanza();
		Alimento alimentoProva= new Alimento("Pizza");
		dispensa.aggiungiAlimento(alimentoProva);
	}
	
	
	@Test
	public void testAggiungiAlimentoNonPresente() {
		Dispensa dispensa= Dispensa.ottieniIstanza();
		int oldSize= dispensa.getAlimenti().size();
		Alimento alimentoProva= new Alimento("Pasta");
		dispensa.aggiungiAlimento(alimentoProva);
		assertTrue(dispensa.getAlimenti().contains(alimentoProva));  //CONTROLLO SE L'ALIMENTO E' STATO AGGIUNTO
		assertEquals(dispensa.getAlimenti().size(), oldSize+1);
		dispensa.eliminaAlimento(alimentoProva);  //COSI LA RIPORTO ALLO STATO INIZIALE PER FARE GLI ALTRI TEST
	}
	
	@Test
	public void testAggiungiAlimentoPresente() {
		Dispensa dispensa= Dispensa.ottieniIstanza();
		int oldSize= dispensa.getAlimenti().size();
		Alimento alimentoProva= new Alimento("Pizza");
		dispensa.aggiungiAlimento(alimentoProva);
		assertEquals(dispensa.getAlimenti().size(),oldSize);   //CONTROLLO SE LA DISPENSA IN DIMENSIONI E' RIMASTA INVARIATA
	}
	
	@Test
	public void testEliminaAlimentoNonPresente() {
		Dispensa dispensa= Dispensa.ottieniIstanza();
		int oldSize= dispensa.getAlimenti().size();
		Alimento alimentoProva= new Alimento("Pasta");
		dispensa.eliminaAlimento(alimentoProva);
		assertFalse(dispensa.getAlimenti().contains(alimentoProva));
		assertEquals(dispensa.getAlimenti().size(),oldSize);
	}
	
	@Test
	public void testEliminaAlimentoPresente() {
		Dispensa dispensa= Dispensa.ottieniIstanza();
		int oldSize= dispensa.getAlimenti().size();
		Alimento alimentoProva= new Alimento("Pizza");
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
