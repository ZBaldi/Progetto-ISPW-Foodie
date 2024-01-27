package com.foodie.controller;

import java.util.ArrayList;

import com.foodie.boundary.InserisciIngredienteViewController;
import com.foodie.model.Alimento;
import com.foodie.model.AlimentoBean;
import com.foodie.model.CatalogoAlimentiDao;
import com.foodie.model.CatalogoAlimentiNutrixionixImplementazioneDao;
import com.foodie.model.CatalogoRicetteChefDao;
import com.foodie.model.CatalogoRicetteImplementazioneDao;
import com.foodie.model.Ricetta;
import com.foodie.model.RicettaBean;

public class PubblicaRicettaController {
	private static PubblicaRicettaController istanza;
	private static CatalogoRicetteChefDao database;
	//private CatalogoAlimentiDao databaseAlimenti;
	private Ricetta ricetta=null;
	private PubblicaRicettaController() {
	}
	public static PubblicaRicettaController ottieniIstanza() { //METODO PER OTTENERE L'ISTANZA
		if(istanza==null) {
			database= CatalogoRicetteImplementazioneDao.ottieniIstanza();
			//databaseAlimenti=CatalogoAlimentiNutrixionixImplementazioneDao.ottieniIstanza();
			istanza=new PubblicaRicettaController();
		}
		return istanza;
	}
	public void creaRicetta() {
		ricetta=new Ricetta();
	}
	public Ricetta getRicetta() {  //non se potrebbe lo devi sistema! ricettabean devi passare
		return ricetta;
	}
	public void compilaRicetta(RicettaBean ricettaBean) {
		ricetta.setNome(ricettaBean.getNome());
		ricetta.setDescrizione(ricettaBean.getDescrizione());
		ricetta.setDifficolta(ricettaBean.getDifficolta());
		ricetta.setAutore(ricettaBean.getAutore());
	}
	/*public void trovaAlimenti(String nomeAlimento) {  GIA FATTO NEL TROVA RICETTA CONTROLLER
		ArrayList<Alimento> alimentiTrovati;
		if((alimentiTrovati=databaseAlimenti.trovaAlimenti(nomeAlimento))!=null) {
			//mostraAlimenti(alimentiTrovati);
		}	
	}*/
	public ArrayList<AlimentoBean> mostraAlimentiRicetta() {
		ArrayList<Alimento> alimentiRicetta=ricetta.getIngredienti();
		if(!alimentiRicetta.isEmpty()) {
			ArrayList<AlimentoBean> alimentiRicettaBean =new ArrayList<AlimentoBean>();
			for(Alimento a: alimentiRicetta) {
				AlimentoBean alimentoBean=new AlimentoBean();
				alimentoBean.setNome(a.getNome());
				alimentiRicettaBean.add(alimentoBean);
			}
			return alimentiRicettaBean;
		}
		else {
			return null;
		}
	}
	public void aggiungiIngredientiRicetta(AlimentoBean alimentoBean,String quantita,int x) {  //CHIAMATO DOPO AVER SELEZIONATO L'ALIMENTO
		Alimento alimento=new Alimento(alimentoBean.getNome());
		if(x==0) {
			ricetta.aggiungiIngrediente(alimento, quantita);
			System.out.println("ingrediente aggiunto");
		}
		else {
			ricetta.eliminaIngrediente(alimento);
			System.out.println("ingrediente eliminato");
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