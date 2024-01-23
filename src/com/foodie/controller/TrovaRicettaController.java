package com.foodie.controller;

import java.util.ArrayList;

import com.foodie.model.Alimento;
import com.foodie.model.CatalogoAlimentiDao;
import com.foodie.model.CatalogoAlimentiNutrixionixImplementazioneDao;
import com.foodie.model.CatalogoRicetteChefDao;
import com.foodie.model.CatalogoRicetteImplementazioneDao;
import com.foodie.model.Dispensa;
import com.foodie.model.Ricetta;

public class TrovaRicettaController {
	private Dispensa dispensa;
	private CatalogoRicetteChefDao database;
	private CatalogoAlimentiDao databaseAlimenti;
	public TrovaRicettaController() {
		this.dispensa= Dispensa.ottieniIstanza();
		this.database= CatalogoRicetteImplementazioneDao.ottieniIstanza();
		this.databaseAlimenti=CatalogoAlimentiNutrixionixImplementazioneDao.ottieniIstanza();
	}
	public void aggiornaDispensa(Alimento alimento,int x) {
		if(x==0) {
			dispensa.aggiungiAlimento(alimento);
		}
		else {
			dispensa.eliminaAlimento(alimento);
		}
	}
	public void trovaRicette(int difficolta){
		ArrayList<Ricetta> ricetteTrovate= null;
		try {
			ricetteTrovate = database.trovaRicetta(dispensa, difficolta);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(!ricetteTrovate.isEmpty()) {
			System.out.println("ricette trovate");
			mostraRicette(ricetteTrovate);
		}
		else {
			System.out.println("nessuna ricetta trovata");
		}
	}
	private void mostraRicette(ArrayList<Ricetta> ricette) {
		for(Ricetta r: ricette) {
			System.out.println("nome: "+r.getNome()+"\ndescrizione: "+r.getDescrizione()+"\ndifficolta: "+r.getDifficolta()+"\nautore: "+r.getAutore()+"\nIngredienti: ");
			for(Alimento a: r.getIngredienti()) {
				System.out.println(a.getNome());
			}
			System.out.println("Quantità: ");
			for(String s: r.getQuantita()) {
				System.out.println(s);
			}
		}
	}
	public void trovaAlimenti(String nomeAlimento) {
		ArrayList<Alimento> alimentiTrovati;
		if((alimentiTrovati=databaseAlimenti.trovaAlimenti(nomeAlimento))!=null) {
			mostraAlimenti(alimentiTrovati);
		}	
	}
	private void mostraAlimenti(ArrayList<Alimento> alimenti) {
		for(Alimento a: alimenti) {
			System.out.println(a.getNome());
		}
		alimenti.clear();   //si occupa di consigliare al garbage collector di rimuovere le istanze non necessarie
		alimenti.trimToSize();  //ovviamente tra queste ci sono tutti gli alimenti non selezionati
		System.gc();
	}
}