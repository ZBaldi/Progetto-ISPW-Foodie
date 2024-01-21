package com.foodie.model;

import java.util.ArrayList;

public interface CatalogoAlimentiDao {
	public ArrayList<Alimento> trovaAlimenti(String nome);
}
