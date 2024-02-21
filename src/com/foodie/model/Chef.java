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
	public String getViewIniziale(int i) {
		if(i == 1) {
		return "/com/foodie/boundary/AreaPersonaleView.fxml";  //VIEW DI INIZIO
		} else {
			return "/com/foodie/boundary2/AreaPersonaleView2.fxml";
		}
	}
	
}