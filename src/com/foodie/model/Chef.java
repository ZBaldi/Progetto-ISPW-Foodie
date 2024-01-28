package com.foodie.model;

public class Chef implements Utente{
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
		return "ChefAreaPersonaleView.fxml";
	}
}
