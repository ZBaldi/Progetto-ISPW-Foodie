package com.foodie.controller;

import com.foodie.model.Chef;
import com.foodie.model.Moderatore;
import com.foodie.model.Standard;
import com.foodie.model.Utente;

public class LoginController {
	Utente utente;
	public LoginController(String username,String tipo) {
		if(tipo.equals("Standard")) {
			this.utente= new Standard(username);
		}
		else if(tipo.equals("Chef")) {
			this.utente= new Chef(username);
		}
		else {
			this.utente= new Moderatore(username);
		}
	}
	public String ottieniView() {
		return utente.getViewIniziale();
	}
}
