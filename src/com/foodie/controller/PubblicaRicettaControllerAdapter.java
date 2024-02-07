package com.foodie.controller;

import java.util.ArrayList;
import java.util.List;

import com.foodie.model.Alimento;
import com.foodie.model.AlimentoBean;
import com.foodie.model.Ricetta;
import com.foodie.model.RicettaBean;

public class PubblicaRicettaControllerAdapter extends ControllerAdapter{  //ADATTATORE CONCRETO DEL CONTROLLER PUBBLICARICETTE
	
	private static PubblicaRicettaControllerAdapter istanza;  //SINGLETON
	private static PubblicaRicettaController adattato; 
	
	private PubblicaRicettaControllerAdapter() {
	}
	
	public static synchronized PubblicaRicettaControllerAdapter ottieniIstanza(PubblicaRicettaController daAdattare) { //METODO PER OTTENERE L'ISTANZA
		if(istanza==null) {
			istanza=new PubblicaRicettaControllerAdapter();    //QUESTA CLASSE SI OCCUPA DI ADATTARE I METODI DEL PUBBLICA RICETTA CONTROLLER
			adattato=daAdattare;							   //LA UI LAVORA CON I BEAN INVECE IL CONTROLLER NO
		}													   //QUINDI QUESTO ADATTATORE SI OCCUPA DI CONVERTIRE GLI OGGETTI BEAN IN NON-BEAN E VICEVERSA
		return istanza;										   //ALLE VARIE OCCORRENZE
	}
	
	@Override
	public RicettaBean ottieniLaRicetta() {
		Ricetta ricetta=adattato.getRicetta();
		RicettaBean ricettaBean= new RicettaBean();
		ricettaBean.setNome(ricetta.getNome());
		ricettaBean.setDescrizione(ricetta.getDescrizione());
		ricettaBean.setDifficolta(ricetta.getDifficolta());
		ArrayList<AlimentoBean> alimentiTrovatiBean=new ArrayList<AlimentoBean>();
		List<Alimento> alimentiTrovati=ricetta.getIngredienti();
		for(Alimento a:alimentiTrovati) {
			AlimentoBean alimentoBean= new AlimentoBean();
			alimentoBean.setNome(a.getNome());
			alimentiTrovatiBean.add(alimentoBean);
		}
		ricettaBean.setIngredienti(alimentiTrovatiBean);
		ricettaBean.setAutore(ricetta.getAutore());
		ricettaBean.setQuantita(ricetta.getQuantita());
		return ricettaBean;
	}
	
	@Override
	public void compilaLaRicetta(RicettaBean ricettaBean) {
			Ricetta ricettaCompilata =new Ricetta();
			ricettaCompilata.setNome(ricettaBean.getNome());
			ricettaCompilata.setDescrizione(ricettaBean.getDescrizione());
			ricettaCompilata.setDifficolta(ricettaBean.getDifficolta());
			ricettaCompilata.setAutore(ricettaBean.getAutore());
			adattato.compilaRicetta(ricettaCompilata);
	}
	
	@Override
	public ArrayList<AlimentoBean> mostraIngredientiRicetta() {
		List<Alimento> alimentiRicetta=adattato.mostraAlimentiRicetta();
		if(alimentiRicetta!=null && !alimentiRicetta.isEmpty()) {
			ArrayList<AlimentoBean> alimentiRicettaBean =new ArrayList<AlimentoBean>();
			for(Alimento a: alimentiRicetta) {
				AlimentoBean alimentoBean=new AlimentoBean();
				alimentoBean.setNome(a.getNome());
				alimentiRicettaBean.add(alimentoBean);
			}
			return alimentiRicettaBean;
		}
		else {
			return new ArrayList<>();
		}
	}
	
	@Override
	public void aggiungiIngredienteRicetta(AlimentoBean alimentoBean,String quantita,int x) {
		Alimento alimento=new Alimento(alimentoBean.getNome());
		adattato.aggiungiIngredientiRicetta(alimento, quantita, x);
	}
	
	@Override
	public ArrayList<RicettaBean> mostraLeRicetteDaApprovare() {
		List<Ricetta> ricette=adattato.mostraRicetteDaApprovare();
		if(ricette!=null && !ricette.isEmpty()) {
			ArrayList<RicettaBean> ricetteBean = new ArrayList<RicettaBean>();
			for(Ricetta r: ricette) {
				RicettaBean ricettaBean=new RicettaBean();
				ricettaBean.setNome(r.getNome());
				ricettaBean.setDescrizione(r.getDescrizione());
				ricettaBean.setDifficolta(r.getDifficolta());
				ArrayList<AlimentoBean> alimentiTrovatiBean=new ArrayList<AlimentoBean>();
				List<Alimento> alimentiTrovati=r.getIngredienti();
				for(Alimento a:alimentiTrovati) {
					AlimentoBean alimentoBean= new AlimentoBean();
					alimentoBean.setNome(a.getNome());
					alimentiTrovatiBean.add(alimentoBean);
				}
				ricettaBean.setIngredienti(alimentiTrovatiBean);
				ricettaBean.setAutore(r.getAutore());
				ricettaBean.setQuantita(r.getQuantita());
				ricetteBean.add(ricettaBean);
			}
			return ricetteBean;
		}
		else {
			return new ArrayList<>();
		}
	}
	
}