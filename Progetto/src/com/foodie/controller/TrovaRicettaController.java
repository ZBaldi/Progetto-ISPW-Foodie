package com.foodie.controller;

import com.foodie.model.Alimento;
import com.foodie.model.Dispensa;

public class TrovaRicettaController {
	private Dispensa dispensa;
	public TrovaRicettaController() {
		this.dispensa= Dispensa.ottieniIstanza();
	}
	public void aggiornaDispensa(Alimento alimento,int x) {
		if(x==0) {
			dispensa.aggiungiAlimento(alimento);
		}
		else {
			dispensa.eliminaAlimento(alimento);
		}
	}
}
