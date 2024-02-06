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
	public String getViewIniziale(int interfaccia) { //VIEW DI INIZIO
		if(interfaccia == 1) {
		return "/com/foodie/boundary/DispensaUtenteView.fxml";
		} else {
			return "/com/foodie/boundary2/AggiungiAlimentoView2.fxml";
		}
	}
	
}