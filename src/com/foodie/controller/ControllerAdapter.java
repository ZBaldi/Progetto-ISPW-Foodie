package com.foodie.controller;

import java.util.ArrayList;

import com.foodie.model.AlimentoBean;
import com.foodie.model.RicettaBean;
import com.foodie.model.UtenteBean;

public abstract class ControllerAdapter {
	
	public void ModificaDispensa(AlimentoBean alimentoBean,int x) {
	}
	
	public ArrayList<RicettaBean> trovaLeRicette(int difficolta, String autore) {
		System.out.println("autore:"+autore);  //PER EVITARE SMELL
		System.out.println("difficolta:"+difficolta);
		return new ArrayList<RicettaBean>();
	}
	
	public ArrayList<AlimentoBean> trovaGliAlimenti(String nomeAlimento) {
		System.out.println("nomeAlimento:"+nomeAlimento);
		return new ArrayList<AlimentoBean>();
	}
	
	public ArrayList<AlimentoBean> mostraLaDispensa() {
		return new ArrayList<AlimentoBean>();
	}
	
	public RicettaBean apriLaRicetta(String nome,String autore) {
		System.out.println("autore:"+autore);  //PER EVITARE SMELL
		System.out.println("nome:"+nome);
		return null;
	}
	
	public RicettaBean ottieniLaRicetta() {
		return null;
	}
	
	public void compilaLaRicetta(RicettaBean ricettaBean) {
	}
	
	public ArrayList<AlimentoBean> mostraIngredientiRicetta() {
		return new ArrayList<AlimentoBean>();
	}
	
	public void aggiungiIngredienteRicetta(AlimentoBean alimentoBean,String quantita,int x) {
	}
	
	public ArrayList<RicettaBean> mostraLeRicetteDaApprovare() {
		return new ArrayList<RicettaBean>();
	}
	
	public UtenteBean ottieniUtente() {
		return null;
	}
	
}