package com.foodie.model;

import java.util.ArrayList;

public interface CatalogoRicetteChefDao{
	public ArrayList<Ricetta> trovaRicetta(Dispensa dispensa,int difficolta);
	public void aggiungiRicetta(Ricetta ricetta) throws Exception;
	public void eliminaRicetta(Ricetta ricetta);
}