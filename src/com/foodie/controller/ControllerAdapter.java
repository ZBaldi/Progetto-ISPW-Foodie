package com.foodie.controller;

import java.util.ArrayList;

import com.foodie.model.AlimentoBean;
import com.foodie.model.RicettaBean;
import com.foodie.model.UtenteBean;

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
	
	public RicettaBean ottieniLaRicetta() {
		return null;
	}
	
	public void compilaLaRicetta(RicettaBean ricettaBean) {
	}
	
	public ArrayList<AlimentoBean> mostraIngredientiRicetta() {
		return null;
	}
	
	public void aggiungiIngredienteRicetta(AlimentoBean alimentoBean,String quantita,int x) {
	}
	
	public ArrayList<RicettaBean> mostraLeRicetteDaApprovare() {
		return null;
	}
	
	public UtenteBean ottieniUtente() {
		return null;
	}
	
}