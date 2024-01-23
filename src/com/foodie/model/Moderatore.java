package com.foodie.model;

public class Moderatore implements Utente{
	private String username;
	public Moderatore(String username) {
		this.username=username;
	}
	@Override
	public String getUsername() {
		return username;
	}
	@Override
	public String getViewIniziale() {
		return null;
	}
}
