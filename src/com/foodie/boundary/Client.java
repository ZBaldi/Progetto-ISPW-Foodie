package com.foodie.boundary;

import java.util.ArrayList;

import com.foodie.controller.TrovaRicettaController;
import com.foodie.model.Alimento;
import com.foodie.model.CatalogoRicetteImplementazioneDao;
import com.foodie.model.Dispensa;
import com.foodie.model.Ricetta;
import com.foodie.model.*;

public class Client {

	public static void main(String[] args) {
		TrovaRicettaController controller = new TrovaRicettaController();
		Dispensa dispensa= Dispensa.ottieniIstanza();
		controller.aggiornaDispensa(new Alimento("uova"), 0);
		controller.aggiornaDispensa(new Alimento("uova"), 0);
		controller.aggiornaDispensa(new Alimento("pasta"), 0);
		controller.aggiornaDispensa(new Alimento("carne"), 0);
		controller.aggiornaDispensa(new Alimento("salmone"), 0);
		CatalogoRicetteChefDao ricette= CatalogoRicetteImplementazioneDao.ottieniIstanza();
		ArrayList<Alimento> lista= new ArrayList<Alimento>();
		lista.add(new Alimento("uova"));
		lista.add(new Alimento("salmone"));
		ArrayList<String> lista2= new ArrayList<String>();
		lista2.add("350g");
		lista2.add("nb");
		try {
			ricette.aggiungiRicetta(new Ricetta("cacca","ciao1",3,lista,"pino",lista2));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//controller.trovaRicette(3);
		//controller.trovaRicette(1);
		//ricette.eliminaRicetta(new Ricetta("cacca","ciao1",3,lista,"pino",lista2));
		//controller.trovaRicette(3);
		//controller.trovaAlimenti("steak");
	}

}
