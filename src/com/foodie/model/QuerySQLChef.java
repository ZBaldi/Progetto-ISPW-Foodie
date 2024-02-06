package com.foodie.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QuerySQLChef {
	
	public static ResultSet selezionaRicetteDalNomeAutore(Statement dichiarazione) throws SQLException{
		String sqlQuery = "SELECT nome,autore FROM ricette";  //OTTIENI LE DESCRIZIONI DELLE RICETTE
		return dichiarazione.executeQuery(sqlQuery);
	}
	
	public static int inserisciRicetta(Statement dichiarazione, Ricetta ricetta) throws SQLException  {
		int codiceDiRitorno=0;  //INSERISCI NELLE 2 TABELLE LINKATE LA RICETTA
		String sqlInsert=String.format("INSERT INTO ricette (nome,descrizione,difficolta,autore) "
									 + "VALUES ( '%s', '%s', %d, '%s')", ricetta.getNome(), ricetta.getDescrizione(),ricetta.getDifficolta(), ricetta.getAutore());
        if(dichiarazione.executeUpdate(sqlInsert)==0) {
        	return 0;  //SE 0 SIGNIFICA FALLIMENTO SULLA PRIMA TABELLA DI CONSEGUENZA NON AGGIORNO LA SECONDA TABELLA
        }
        for(int i=0; i< ricetta.getIngredienti().size(); i++ ) {
        	sqlInsert=String.format("INSERT INTO ingredienti (nome_ricetta, autore_ricetta, alimento, quantita) "
        						  + "VALUES ('%s', '%s', '%s', '%s')", ricetta.getNome(),ricetta.getAutore(), ricetta.getIngredienti().get(i).getNome(), ricetta.getQuantita().get(i));
        	codiceDiRitorno=codiceDiRitorno+dichiarazione.executeUpdate(sqlInsert); //OGNI ALIMENTO CHE INSERISCO NELLA SECONDA TABELLA MI INCREMENTA DI 1 IL CONTATORE
        }
        if(codiceDiRitorno<ricetta.getIngredienti().size()) {
        	return -1; //SE IL CONTATORE è MINORE DEGLI ALIMENTI DA INSERIRE OVVIAMENTE HA FALLITO L'INSERIMENTO DEGLI ALIMENTI NELLA SECONDA TABELLA
        }
        return 1;
    }
	
	public static int rimuoviRicetta(Statement dichiarazione, String nome, String autore) throws SQLException{ //ELIMINA LA RICETTA DAL DB (DBMS GESTISCE ELIMINAZIONE A CASCATA)
		String sqlDelete= String.format("DELETE FROM  ricette  "
									 + "WHERE nome = '%s' AND autore = '%s'", nome, autore);
		return dichiarazione.executeUpdate(sqlDelete);
	}
	
	public static ResultSet ottieniRicettePersonali(Statement dichiarazione, String username) throws SQLException {  //OTTIENI TUTTE LE RICETTE DI UNO CHEF IN BASE AL SUO USERNAME
		String sqlQuery=String.format("SELECT a.nome, a.autore, a.descrizione, a.difficolta, b.alimento, b.quantita "
									+ "FROM ricette a LEFT JOIN ingredienti b ON a.nome = b.nome_ricetta AND a.autore = b.autore_ricetta "
									+ "WHERE a.autore = '%s'",username);
		return dichiarazione.executeQuery(sqlQuery);
	}
		
}