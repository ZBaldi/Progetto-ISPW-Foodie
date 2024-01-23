package com.foodie.controller;

import java.util.ArrayList;

import com.foodie.model.Alimento;
import com.foodie.model.CatalogoAlimentiDao;
import com.foodie.model.CatalogoAlimentiNutrixionixImplementazioneDao;
import com.foodie.model.CatalogoRicetteChefDao;
import com.foodie.model.CatalogoRicetteImplementazioneDao;
import com.foodie.model.Ricetta;
import com.foodie.model.RicettaBean;

public class PubblicaRicettaController {
	private CatalogoRicetteChefDao database;
	private CatalogoAlimentiDao databaseAlimenti;
	private Ricetta ricetta=null;
	public PubblicaRicettaController() {
		this.database= CatalogoRicetteImplementazioneDao.ottieniIstanza();
		this.databaseAlimenti=CatalogoAlimentiNutrixionixImplementazioneDao.ottieniIstanza();
	}
	public RicettaBean creaRicetta() {
		ricetta=new Ricetta();
		return new RicettaBean();  //MANDO ALLA BOUNDARY L'ISTANZA DEL BEAN PER COMPILARLO
	}
	public void compilaRicetta(RicettaBean ricettaBean) {
		ricetta.setNome(ricettaBean.getNome());
		ricetta.setDescrizione(ricettaBean.getDescrizione());
		ricetta.setDifficolta(ricettaBean.getDifficolta());
		ricetta.setAutore(ricettaBean.getAutore());
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
	public void aggiungiIngredientiRicetta(Alimento alimento,String quantita,int x) {  //CHIAMATO DOPO AVER SELEZIONATO L'ALIMENTO
		if(x==0) {
			ricetta.aggiungiIngrediente(alimento, quantita);
		}
		else {
			ricetta.eliminaIngrediente(alimento);
		}
	}
	public void notificaModeratore() {
		//DAFARE
	}
	public void notificaChef() {
		//DAFARE
	}
	public void pubblicaRicetta(Ricetta ricetta) {
		try {
			database.aggiungiRicetta(ricetta);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void eliminaRicetta(Ricetta ricetta) {
		try {
			database.eliminaRicetta(ricetta);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void aggiornaRicettaPubblicata(Ricetta ricetta) {
		try {
			database.aggiornaRicetta(ricetta);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}