package com.foodie.model;

public class Standard implements Utente{  //STANDARD IMPLEMENTA UTENTE
	
	private String username;
	
	public Standard(String username) {
		this.username=username;
	}
	
	@Override
	public String getUsername() {
		return username;
	}
	
	@Override
	public String getViewIniziale() {  //VIEW DI INIZIO
		return "DispensaUtenteView.fxml";
	}
	
}