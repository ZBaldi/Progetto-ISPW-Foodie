package com.foodie.controller;

import java.util.ArrayList;

import com.foodie.model.Alimento;
import com.foodie.model.AlimentoBean;
import com.foodie.model.Ricetta;
import com.foodie.model.RicettaBean;

public class TrovaRicetteControllerAdapter extends ControllerAdapter{  //ADATTATORE CONCRETO DEL CONTROLLER TROVARICETTE
	private static TrovaRicetteControllerAdapter istanza;  //SINGLETON
	private static TrovaRicettaController adattato; 
	
	public static synchronized TrovaRicetteControllerAdapter ottieniIstanza(TrovaRicettaController daAdattare) { //METODO PER OTTENERE L'ISTANZA
		if(istanza==null) {
			istanza=new TrovaRicetteControllerAdapter();    //QUESTA CLASSE SI OCCUPA DI ADATTARE I METODI DEL TROVA RICETTE CONTROLLER
			adattato=daAdattare;							//LA UI LAVORA CON I BEAN INVECE IL CONTROLLER NO
		}													//QUINDI QUESTO ADDATTATORE SI OCCUPA DI CONVERTIRE GLI OGGETTI BEAN IN NON-BEAN E VICEVERSA
		return istanza;										//ALLE VARIE OCCORRENZE
	}
	
	@Override
	public void ModificaDispensa(AlimentoBean alimentoBean, int x) {  
		Alimento alimento=new Alimento(alimentoBean.getNome());
		adattato.aggiornaDispensa(alimento, x);
	}

	@Override
	public ArrayList<RicettaBean> trovaLeRicette(int difficolta, String autore) {
		ArrayList<Ricetta> ricetteTrovate= null;
		ricetteTrovate= adattato.trovaRicette(difficolta, autore);
		if(ricetteTrovate!=null) {
			ArrayList<RicettaBean> ricetteTrovateBean= new ArrayList<RicettaBean>();
			for(Ricetta r:ricetteTrovate) {
				RicettaBean ricettaBean=new RicettaBean();
				ricettaBean.setNome(r.getNome());
				ricettaBean.setDescrizione(r.getDescrizione());
				ricettaBean.setDifficolta(r.getDifficolta());
				ArrayList<AlimentoBean> alimentiTrovatiBean=new ArrayList<AlimentoBean>();
				ArrayList<Alimento> alimentiTrovati=r.getIngredienti();
				for(Alimento a:alimentiTrovati) {
					AlimentoBean alimentoBean= new AlimentoBean();
					alimentoBean.setNome(a.getNome());
					alimentiTrovatiBean.add(alimentoBean);
				}
				ricettaBean.setIngredienti(alimentiTrovatiBean);
				ricettaBean.setAutore(r.getAutore());
				ricettaBean.setQuantita(r.getQuantita());
				ricetteTrovateBean.add(ricettaBean);
			}	
			return ricetteTrovateBean;
		}
		else {
			return null;
		}
	}

	@Override
	public ArrayList<AlimentoBean> trovaGliAlimenti(String nomeAlimento) {
		ArrayList<Alimento> alimentiTrovati=null;
		ArrayList<AlimentoBean> alimentiTrovatiBean=null;
		alimentiTrovati=adattato.trovaAlimenti(nomeAlimento);
		if(alimentiTrovati!=null && !alimentiTrovati.isEmpty()) {
			alimentiTrovatiBean=new ArrayList<AlimentoBean>();
			for(Alimento a:alimentiTrovati) {
				AlimentoBean alimentoBean= new AlimentoBean();
				alimentoBean.setNome(a.getNome());
				alimentiTrovatiBean.add(alimentoBean);
			}
			return alimentiTrovatiBean;
		}
		else {
			return null;
		}
	}

	@Override
	public ArrayList<AlimentoBean> mostraLaDispensa() {
		ArrayList<Alimento> alimentiInDispensa=null;
		alimentiInDispensa=adattato.mostraDispensa();
		if(alimentiInDispensa!=null && !alimentiInDispensa.isEmpty()) {
			ArrayList<AlimentoBean> alimentiInDispensaBean =new ArrayList<AlimentoBean>();
			for(Alimento a: alimentiInDispensa) {
				AlimentoBean alimentoBean=new AlimentoBean();
				alimentoBean.setNome(a.getNome());
				alimentiInDispensaBean.add(alimentoBean);
			}
			return alimentiInDispensaBean;
		}
		else {
			return null;
		}
	}

	@Override
	public RicettaBean apriLaRicetta(String nome,String autore) {
		Ricetta ricetta=null;
		ricetta=adattato.ottieniRicetta(nome, autore);
		if(ricetta!=null) {
			RicettaBean ricettaBean=new RicettaBean();
			ricettaBean.setNome(ricetta.getNome());
			ricettaBean.setDescrizione(ricetta.getDescrizione());
			ricettaBean.setDifficolta(ricetta.getDifficolta());
			ArrayList<AlimentoBean> alimentiTrovatiBean=new ArrayList<AlimentoBean>();
			ArrayList<Alimento> alimentiTrovati=ricetta.getIngredienti();
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
		else {
			return null;
		}
	}

}