package com.foodie.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.foodie.model.Alimento;

public class TestAlimento { //BIRU MICHELE
	
	private static final String NOME1 = "Arancia";
	private static final String NOME2 = "Mela";
	
	@Test
    public void testGetNomeUguale() {
        Alimento alimento=new Alimento(NOME1);
       
        //VERIFICA CHE IL METODO RESTITUISCA IL NOME CORRETTO
        assertEquals(NOME1, alimento.getNome());
    }
	
	@Test
	public void testEqualsStessoNome() {
		Alimento alimento1=new Alimento(NOME1);
        Alimento alimento2=new Alimento(NOME1);
       
        //VERIFICA CHE EQUALS RESTITUISCA TRUE QUANDO CONFRONTA DUE ISTANZE CON LO STESSO NOME
        assertTrue(alimento1.equals(alimento2));
	}
	
	 @Test
	 public void testHashCodeDiverso() {

	    Alimento alimento1=new Alimento(NOME1);
	    Alimento alimento2=new Alimento(NOME2);

	    //VERIFICA CHE IL LORO CODICE HASH SIA DIVERSO
	    assertNotEquals(alimento1.hashCode(), alimento2.hashCode());
	 }
}
