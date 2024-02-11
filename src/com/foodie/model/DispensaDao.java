package com.foodie.model;

import java.util.ArrayList;
import java.util.Map;

public interface DispensaDao {  //DAO PER LA DISPENSA
	
	public void salvaDispensa(String username);  //SALVA LA DISPENSA SU DB
	
	public Map<String, ArrayList<AlimentoSerializzabile>> caricaDispense(); //CARICA GLI ALIMENTI DELLA DISPENSA DA DB
	
}