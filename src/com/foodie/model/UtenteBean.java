package com.foodie.model;

public class UtenteBean {  //BEAN DEL TIPO UTENTE NONOSTANTE IN REALTA' ESSA SIA UNA INTERFACCIA
	
	private String username;    //GETTERS E SETTERS PER LE PRORPIETA' DI UN UTENTE IN GENERALE
	
	public void setUsername(String username) { 
		this.username=username;
	}
	
	public String getUsername() {
		return username;
	}
}