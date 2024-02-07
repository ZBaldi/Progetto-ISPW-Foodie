package com.foodie.controller;

import java.util.ArrayList;
import java.util.logging.Logger;
import com.foodie.model.AlimentoBean;
import com.foodie.model.RicettaBean;
import com.foodie.model.UtenteBean;

public abstract class ControllerAdapter {
	
	private static final Logger logger = Logger.getLogger(ControllerAdapter.class.getName());
	
	public void ModificaDispensa(AlimentoBean alimentoBean,int x) {
	}
	
	public ArrayList<RicettaBean> trovaLeRicette(int difficolta, String autore) {
		String prima= "autore:"+autore;
		String seconda= "difficolta:"+difficolta; 
		logger.info(prima);  //PER EVITARE SMELL
		logger.info(seconda);
		return new ArrayList<RicettaBean>();
	}
	
	public ArrayList<AlimentoBean> trovaGliAlimenti(String nomeAlimento) {
		String prima= "nomeAlimento:"+nomeAlimento;
		logger.info(prima); //PER EVITARE SMELL
		return new ArrayList<AlimentoBean>();
	}
	
	public ArrayList<AlimentoBean> mostraLaDispensa() {
		return new ArrayList<AlimentoBean>();
	}
	
	public RicettaBean apriLaRicetta(String nome,String autore) {
		String prima= "autore:"+autore;
		String seconda= "nome:"+nome;
		logger.info(prima);  //PER EVITARE SMELL
		logger.info(seconda);
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