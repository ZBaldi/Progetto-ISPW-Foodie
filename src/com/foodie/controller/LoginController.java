package com.foodie.controller;

import com.foodie.model.Chef;
import com.foodie.model.LoginDao;
import com.foodie.model.LoginImplementazioneDao;
import com.foodie.model.Moderatore;
import com.foodie.model.Standard;
import com.foodie.model.Utente;

public class LoginController {
	private Utente utente;
	private LoginDao database = LoginImplementazioneDao.ottieniIstanza();
	public void setUtente(String username, String tipo) {
		if(tipo.equals("Standard")) {
			this.utente= new Standard(username);
		}
		else if(tipo.equals("Chef")) {
			this.utente= new Chef(username);
		}
		else {
			this.utente= Moderatore.ottieniIstanza(username);
		}
	}
	public String ottieniView() {
		return utente.getViewIniziale();
	}
	public int effettuaLogin(String username, String pwd) {
		try {
			return database.validazioneLogin(username, pwd);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	public int controllaUsername(String username) {
		try {
			return database.controllaUsername(username);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	public void registraUtente(String nome,String cognome,String username,int ruolo,String password) {
		try {
			database.registraUtente(nome, cognome, username, ruolo, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
