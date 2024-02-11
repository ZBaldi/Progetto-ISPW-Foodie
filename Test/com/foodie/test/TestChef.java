package com.foodie.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.foodie.model.Chef;

public class TestChef { // BIRU MICHELE

	private static final String USERNAME = "test_chef";

	@Test
    public void testGetUsernameUguale() {
        Chef chef = new Chef(USERNAME);
		// VERIFICA CHE IL METODO RESTITUISCA LO USERNAME CORRETTO
        assertEquals(USERNAME, chef.getUsername());
    }

    @Test
    public void testGetViewInizialeInterfaccia1() {
        Chef chef = new Chef(USERNAME);
        // VERIFICA CHE RESTITUISCA LA VIEW CORRETTA
        assertEquals("/com/foodie/boundary/AreaPersonaleView.fxml", chef.getViewIniziale(1));
    }

    @Test
    public void testGetViewInizialeInterfaccia2() {
    	Chef chef = new Chef(USERNAME);
        // VERIFICA CHE RESTITUISCA LA VIEW CORRETTA
        assertEquals("/com/foodie/boundary2/AreaPersonaleView2.fxml", chef.getViewIniziale(2));
	}

}
