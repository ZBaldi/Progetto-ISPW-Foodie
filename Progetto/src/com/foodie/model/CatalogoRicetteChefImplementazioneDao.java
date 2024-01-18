package com.foodie.model;

import java.util.ArrayList;

public class CatalogoRicetteChefImplementazioneDao implements CatalogoRicetteChefDao{ //SINGLETON
	private static CatalogoRicetteChefImplementazioneDao istanza; 
	private ArrayList<Ricetta> database =new ArrayList<Ricetta>();
	private CatalogoRicetteChefImplementazioneDao(){
	}
	public static CatalogoRicetteChefImplementazioneDao ottieniIstanza() { //METODO PER OTTENERE L'ISTANZA
		if(istanza==null) {
			istanza=new CatalogoRicetteChefImplementazioneDao();
		}
		return istanza;
	}
	@Override
	public ArrayList<Ricetta> trovaRicetta(Dispensa dispensa,int difficolta) {
		ArrayList<Ricetta> ricetteValide =new ArrayList<Ricetta>();
		for(Ricetta i: database) {
			ArrayList<Alimento> appoggioRicetta= i.getIngredienti();
			ArrayList<Alimento> appoggioDispensa= i.getIngredienti();
			if(appoggioDispensa.containsAll(appoggioRicetta) && i.getDifficolta()==difficolta) {
				ricetteValide.add(i); 
			}
		}
		if(ricetteValide.isEmpty()) {
			System.out.println("nessuna ricetta trovata");
			return null;
		}
		System.out.println("ecco tutte le ricette disponibili con quegli ingredienti");
		return ricetteValide;
	}
	@Override
	public void aggiungiRicetta(Ricetta ricetta) {
		if(!database.contains(ricetta)) {
			database.add(ricetta);
			System.out.println("ricetta inserita nel database");
		}
		else {
			System.out.println("ricetta già presente nel database");
		}
	}
	@Override
	public void eliminaRicetta(Ricetta ricetta) {
		database.remove(ricetta);
		System.out.println("ricetta rimossa dal database");
	}	
}