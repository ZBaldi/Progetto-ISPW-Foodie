package com.foodie.controller;

import java.util.ArrayList;

import com.foodie.model.AlimentoBean;
import com.foodie.model.Ricetta;
import com.foodie.model.RicettaBean;

public abstract class ControllerAdapter {
	
	public void ModificaDispensa(AlimentoBean alimentoBean,int x) {
	}
	
	public ArrayList<RicettaBean> trovaLeRicette(int difficolta, String autore) {
		return null;
	}
	
	public ArrayList<AlimentoBean> trovaGliAlimenti(String nomeAlimento) {
		return null;
	}
	
	public ArrayList<AlimentoBean> mostraLaDispensa() {
		return null;
	}
	
	public RicettaBean apriLaRicetta(String nome,String autore) {
		return null;
	}
	
	public RicettaBean getRicetta() {
		return null;
	}
	
	public void compilaRicetta(RicettaBean ricettaBean) {
	}
	
	public ArrayList<AlimentoBean> mostraAlimentiRicetta() {
		return null;
	}
	
	public void aggiungiIngredientiRicetta(AlimentoBean alimentoBean,String quantita,int x) {
	}
	
	public ArrayList<RicettaBean> mostraRicetteDaApprovare() {
		return null;
	}
	
}
