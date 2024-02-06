package com.foodie.controller;

public class AdattatoreFactory {  //SINGLETON, IL FACTORY DEVE AVERE SOLO 1 ISTANZA!
	
	private static AdattatoreFactory istanza=null;  
	
	private AdattatoreFactory() {
	}
	
	public static synchronized AdattatoreFactory ottieniIstanza() {  //METODO PER OTTENERE L'ISTANZA
		if(istanza==null) {
			istanza=new AdattatoreFactory();
		}
		return istanza;
	}
	
	public ControllerAdapter creaLoginAdapter() {  //CREA IL PRODOTTO CONCRETO OVVERO LOGIN ADAPTER
		return LoginControllerAdapter.ottieniIstanza(LoginController.ottieniIstanza());
	}
	
	public ControllerAdapter creaPubblicaRicettaAdapter() {  //CREA IL PRODOTTO CONCRETO OVVERO PUBBLICA RICETTA ADAPTER
		return PubblicaRicettaControllerAdapter.ottieniIstanza(PubblicaRicettaController.ottieniIstanza());
	}
	
	public ControllerAdapter creaTrovaRicettaAdapter() {  //CREA IL PRODOTTO CONCRETO OVVERO TROVA RICETTA ADAPTER
		return TrovaRicettaControllerAdapter.ottieniIstanza(TrovaRicettaController.ottieniIstanza());
	}
	
}