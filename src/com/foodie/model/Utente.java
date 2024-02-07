package com.foodie.model;

public interface Utente {  //OGNI UTENTE DEVE IMPLEMENTARE QUESTE OPERAZIONI STANDARD
	
	public String getUsername();  //IMPOSTA L'USERNAME
	
	public String getViewIniziale(int i);  //OTTIENI LA VIEW INIZIALE DA CARICARE
	
}