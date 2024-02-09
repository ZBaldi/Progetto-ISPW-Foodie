package com.foodie.model;

import java.util.Map;

public interface AreaPersonaleDao {  //DAO PER L'AREAPERSONALE 
	
	public void salvaAreaPersonale(String username,String descrizione);  //SALVA LA DESCRIZIONE
	
	public Map<String,String> caricaAreaPersonale();  //CARICA LA DESCRIZIONE
	
}