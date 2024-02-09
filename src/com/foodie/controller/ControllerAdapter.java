package com.foodie.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import com.foodie.model.AlimentoBean;
import com.foodie.model.RicettaBean;
import com.foodie.model.UtenteBean;

public abstract class ControllerAdapter {
	
	private static final Logger logger = Logger.getLogger(ControllerAdapter.class.getName());
	
	public void modificaDispensa(AlimentoBean alimentoBean,int x) {
	}
	
	public List<RicettaBean> trovaLeRicette(int difficolta, String autore) {
		String prima= "autore:"+autore;
		String seconda= "difficolta:"+difficolta; 
		logger.info(prima);  //PER EVITARE SMELL
		logger.info(seconda);
		return new ArrayList<>();
	}
	
	public List<AlimentoBean> trovaGliAlimenti(String nomeAlimento) {
		String prima= "nomeAlimento:"+nomeAlimento;
		logger.info(prima); //PER EVITARE SMELL
		return new ArrayList<>();
	}
	
	public List<AlimentoBean> mostraLaDispensa() {
		return new ArrayList<>();
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
	
	public List<AlimentoBean> mostraIngredientiRicetta() {
		return new ArrayList<>();
	}
	
	public void aggiungiIngredienteRicetta(AlimentoBean alimentoBean,String quantita,int x) {
	}
	
	public List<RicettaBean> mostraLeRicetteDaApprovare() {
		return new ArrayList<>();
	}
	
	public UtenteBean ottieniUtente() {
		return null;
	}
	
}