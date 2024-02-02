package com.foodie.controller;

import com.foodie.model.Utente;
import com.foodie.model.UtenteBean;

public class LoginControllerAdapter extends ControllerAdapter{  //ADATTATORE CONCRETO DEL CONTROLLER LOGIN
	
	private static LoginControllerAdapter istanza;  //SINGLETON
	private static LoginController adattato; 
	
	private LoginControllerAdapter() {
	}
	
	public static synchronized LoginControllerAdapter ottieniIstanza(LoginController daAdattare) { //METODO PER OTTENERE L'ISTANZA
		if(istanza==null) {
			istanza=new LoginControllerAdapter();    //QUESTA CLASSE SI OCCUPA DI ADATTARE I METODI DEL LOGIN CONTROLLER
			adattato=daAdattare;							   //LA UI LAVORA CON I BEAN INVECE IL CONTROLLER NO
		}													   //QUINDI QUESTO ADATTATORE SI OCCUPA DI CONVERTIRE GLI OGGETTI BEAN IN NON-BEAN E VICEVERSA
		return istanza;										   //ALLE VARIE OCCORRENZE
	}
	
	@Override
	public UtenteBean ottieniUtente() {
		Utente utente= adattato.getUtente();
		UtenteBean utenteBean= new UtenteBean();
		utenteBean.setUsername(utente.getUsername());
		return utenteBean;
	}
	
}