package com.foodie.model;

import java.util.ArrayList;

public interface CatalogoRicetteChefDao{
	public ArrayList<Ricetta> trovaRicetta(Dispensa dispensa,int difficolta) throws Exception;
	public void aggiungiRicetta(Ricetta ricetta) throws Exception;
	public void eliminaRicetta(String nome, String autore) throws Exception;
	public void aggiornaRicetta(Ricetta ricetta) throws Exception;
	public Ricetta ottieniDatiRicetta(String nome,String autore) throws Exception;
	public ArrayList<Ricetta> caricaRicetteChef(String autore2) throws Exception;
}