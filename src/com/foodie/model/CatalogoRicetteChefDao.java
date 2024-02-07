package com.foodie.model;

import java.sql.SQLException;
import java.util.List;

public interface CatalogoRicetteChefDao{  //DAO PER LE RICETTE
	
	public List<Ricetta> trovaRicette(Dispensa dispensa,int difficolta, String autore) throws SQLException,ClassNotFoundException;
	
	public void aggiungiRicetta(Ricetta ricetta) throws SQLException,ClassNotFoundException,RicettaDuplicataException;
	
	public void eliminaRicetta(String nome, String autore) throws SQLException,ClassNotFoundException;
	
	public Ricetta ottieniDatiRicetta(String nome,String autore) throws SQLException,ClassNotFoundException;
	
}