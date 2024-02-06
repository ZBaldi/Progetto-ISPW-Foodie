package com.foodie.model;

import java.util.ArrayList;

public interface CatalogoAlimentiDao {  //DAO PER GLI ALIMENTI
	
	public ArrayList<Alimento> trovaAlimenti(String nome);
	
}