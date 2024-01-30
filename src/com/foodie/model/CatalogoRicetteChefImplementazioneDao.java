package com.foodie.model;


import java.util.ArrayList;

public class CatalogoRicetteChefImplementazioneDao implements CatalogoRicetteChefDao{ //SINGLETON
	private static CatalogoRicetteChefImplementazioneDao istanza; 
	private static ArrayList<Ricetta> database;
	private CatalogoRicetteChefImplementazioneDao(){
	}
	public static CatalogoRicetteChefImplementazioneDao ottieniIstanza() { //METODO PER OTTENERE L'ISTANZA
		if(istanza==null) {
			istanza=new CatalogoRicetteChefImplementazioneDao();
			database=new ArrayList<Ricetta>();
		}
		return istanza;
	}
	@Override
	public ArrayList<Ricetta> trovaRicetta(Dispensa dispensa, int difficolta) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void aggiungiRicetta(Ricetta ricetta) throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void eliminaRicetta(String nome , String autore) throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void aggiornaRicetta(Ricetta ricetta) throws Exception {
		// TODO Auto-generated method stub
		
	}
	/*@Override
	public ArrayList<Ricetta> trovaRicetta(Dispensa dispensa,int difficolta) {
		ArrayList<Ricetta> ricetteValide =new ArrayList<Ricetta>();
		ArrayList<Alimento> appoggioDispensa= dispensa.getAlimenti();
		for(Ricetta i: database) {
			ArrayList<Alimento> appoggioRicetta= i.getIngredienti();
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
	@Override
	public void aggiornaRicetta(Ricetta ricetta) throws Exception {
		// TODO Auto-generated method stub
		
	}	*/
	@Override
	public Ricetta ottieniDatiRicetta(String nome, String autore) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<Ricetta> caricaRicetteChef(String autore2) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
