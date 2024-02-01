package com.foodie.model;

import java.util.ArrayList;

public interface CatalogoRicetteChefDao{
	public ArrayList<Ricetta> trovaRicette(Dispensa dispensa,int difficolta, String autore) throws Exception;
	public void aggiungiRicetta(Ricetta ricetta) throws Exception;
	public void eliminaRicetta(String nome, String autore) throws Exception;
	public Ricetta ottieniDatiRicetta(String nome,String autore) throws Exception;
}