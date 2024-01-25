package com.foodie.model;

import java.sql.*;
import java.util.ArrayList;

public class querySQL {
	public static ResultSet selezionaRicetteDallaDescrizione(Statement dichiarazione) throws SQLException{
		String sqlQuery = "SELECT DISTINCT descrizione FROM ricette";
		return dichiarazione.executeQuery(sqlQuery);
	}
	public static int inserisciRicetta(Statement dichiarazione, Ricetta ricetta) throws SQLException  {
		int codiceDiRitorno=0;
		String sqlInsert=String.format("INSERT INTO ricette (nome,descrizione,difficolta,autore) VALUES ( '%s', '%s', %d, '%s')", ricetta.getNome(), ricetta.getDescrizione(),ricetta.getDifficolta(), ricetta.getAutore());
        if(dichiarazione.executeUpdate(sqlInsert)==0) {
        	return 0;
        }
        for(int i=0; i< ricetta.getIngredienti().size(); i++ ) {
        	sqlInsert=String.format("INSERT INTO ingredienti (nome_ricetta, autore_ricetta, alimento, quantita) VALUES ('%s', '%s', '%s', '%s')", ricetta.getNome(),ricetta.getAutore(), ricetta.getIngredienti().get(i).getNome(), ricetta.getQuantita().get(i));
        	codiceDiRitorno=codiceDiRitorno+dichiarazione.executeUpdate(sqlInsert);
        }
        if(codiceDiRitorno<ricetta.getIngredienti().size()) {
        	return 0;
        }
        return 1;
    }
	public static int rimuoviRicetta(Statement dichiarazione, Ricetta ricetta) throws SQLException{
		String sqlDelete= String.format("DELETE FROM  ricette  WHERE nome = '%s' AND autore = '%s'", ricetta.getNome(), ricetta.getAutore());
		return dichiarazione.executeUpdate(sqlDelete);
	}
	public static ResultSet trovaRicette(Statement dichiarazione, ArrayList<Alimento> alimenti, int difficolta) throws SQLException {
		StringBuilder stringBuilder= new StringBuilder();
		int indice=0;
		for(Alimento a: alimenti) {
			stringBuilder.append("'"+a.getNome()+"' ");
			if(indice<alimenti.size()-1) {
				stringBuilder.append(", ");
			}
			indice++;
		}
		String queryParziale= stringBuilder.toString();
		String sqlQuery=String.format("SELECT r.nome, r.descrizione, r.difficolta, r.autore, i.alimento, i.quantita FROM Ricette r JOIN Ingredienti i ON r.nome = i.nome_ricetta AND r.autore = i.autore_ricetta WHERE r.nome NOT IN ( SELECT nome_ricetta FROM Ingredienti WHERE alimento NOT IN ( "+queryParziale+")) AND r.difficolta = %d",difficolta);
		//String sqlQuery= String.format("SELECT r.nome, r.descrizione, r.difficolta, r.autore, i.alimento, i.quantita FROM Ricette r JOIN Ingredienti i ON r.nome = i.nome_ricetta AND r.autore = i.autore_ricetta WHERE i.alimento IN ( "+queryParziale+") AND r.difficolta = %d",difficolta);
		return dichiarazione.executeQuery(sqlQuery);
	}
}