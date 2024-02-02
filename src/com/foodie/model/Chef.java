package com.foodie.model;

public class Chef implements Utente{  //CHEF IMPLEMENTA UTENTE
	
	private String username;
	
	public Chef(String username) {
		this.username=username;
	}
	
	@Override
	public String getUsername() {
		return username;
	}
	
	@Override
	public String getViewIniziale() {
		return "AreaPersonaleView.fxml";  //VIEW DI INIZIO
	}
	
}