package com.foodie.model;

import java.sql.*;
import java.sql.SQLException;

public class querySQL {
	public static ResultSet selezionaRicetteDallaDescrizione(Statement dichiarazione) throws SQLException{
		String sqlQuery = "SELECT DISTINCT descrizione FROM ricette ;";
		return dichiarazione.executeQuery(sqlQuery);
	}
	public static int inserisciRicetta(Statement dichiarazione, Ricetta ricetta) throws SQLException  {
		StringBuilder stringaIngredienti = new StringBuilder();
		StringBuilder stringaQuantita = new StringBuilder();
		int indice=0;
		for(Alimento a: ricetta.getIngredienti()) {
			stringaIngredienti.append(a.getNome());
			if(indice<ricetta.getIngredienti().size()-1) {
				stringaIngredienti.append(", ");
			}
			indice++;
		}
		String ingredienti = stringaIngredienti.toString();
		indice=0;
		for(String s: ricetta.getQuantita()) {
			stringaQuantita.append(s);
			if(indice<ricetta.getQuantita().size()-1) {
				stringaQuantita.append(", ");
			}
			indice++;
		}
		String quantita= stringaQuantita.toString();
        String sqlInsert = String.format("INSERT INTO ricette (nome, descrizione, difficolta, ingredienti, autore, quantita) VALUES ('%s', '%s', %d, '%s', '%s', '%s')", ricetta.getNome(), ricetta.getDescrizione(), ricetta.getDifficolta(), ingredienti, ricetta.getAutore(), quantita);
        return dichiarazione.executeUpdate(sqlInsert);
    }
}
