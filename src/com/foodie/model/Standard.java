package com.foodie.model;

public class Standard implements Utente{
	private String username;
	public Standard(String username) {
		this.username=username;
	}
	@Override
	public String getUsername() {
		return username;
	}
	@Override
	public String getViewIniziale() {
		return "DispensaUtenteView.fxml";
	}
}
