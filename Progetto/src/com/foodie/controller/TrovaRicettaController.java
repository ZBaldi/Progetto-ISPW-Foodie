package com.foodie.controller;

import java.util.ArrayList;

import com.foodie.model.Alimento;
import com.foodie.model.CatalogoRicetteChefDao;
import com.foodie.model.CatalogoRicetteChefImplementazioneDao;
import com.foodie.model.Dispensa;
import com.foodie.model.Ricetta;

public class TrovaRicettaController {
	private Dispensa dispensa;
	private CatalogoRicetteChefDao database;
	public TrovaRicettaController() {
		dispensa= Dispensa.ottieniIstanza();
		database= CatalogoRicetteChefImplementazioneDao.ottieniIstanza();
	}
	public void aggiornaDispensa(Alimento alimento,int x) {
		if(x==0) {
			dispensa.aggiungiAlimento(alimento);
		}
		else {
			dispensa.eliminaAlimento(alimento);
		}
	}
	public void trovaRicette(int difficolta) {
		ArrayList<Ricetta> ricetteTrovate;
		if((ricetteTrovate= database.trovaRicetta(dispensa,difficolta))!=null) {
			mostraRicette(ricetteTrovate);
		}
	}
	private void mostraRicette(ArrayList<Ricetta> ricette) {
		for(Ricetta r:ricette) {
			System.out.println("Nome Ricetta: "+r.getNome()+"\nDescrizione Ricetta: "+r.getDescrizione()+"\nAutore: "+r.getAutore()+"\nDifficoltà: "+r.getDifficolta());
			for(Alimento a: r.getIngredienti()) {
				System.out.println(a.getNome());
			}
		}
	}
}