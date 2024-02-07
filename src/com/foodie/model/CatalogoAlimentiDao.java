package com.foodie.model;

import java.util.List;

public interface CatalogoAlimentiDao {  //DAO PER GLI ALIMENTI
	
	public List<Alimento> trovaAlimenti(String nome);
	
}