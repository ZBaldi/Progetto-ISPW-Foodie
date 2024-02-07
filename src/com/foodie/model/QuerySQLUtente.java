package com.foodie.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class QuerySQLUtente {
	
	private QuerySQLUtente() {
	}
	
	public static ResultSet trovaRicette(Statement dichiarazione, ArrayList<Alimento> alimenti, int difficolta) throws SQLException {
		StringBuilder stringBuilder= new StringBuilder();   //QUERY PER OTTENERE LE RICETTE DAL DB CHE HANNO COME INGREDIENTI
		int indice=0;										//UN SOTTOINSIEME DI QUELLI CONTENUTI NELLA DISPENSA
		for(Alimento a: alimenti) {								
			stringBuilder.append("'"+a.getNome()+"' ");		//MI COSTRUISCO LA STRINGA CON IL NOME DEGLI ALIMENTI PER LA QUERY
			if(indice<alimenti.size()-1) {
				stringBuilder.append(", ");
			}
			indice++;
		}
		String queryParziale= stringBuilder.toString();
		String query="SELECT r.nome, r.descrizione, r.difficolta, r.autore, i.alimento, i.quantita "
				+ "FROM Ricette r JOIN Ingredienti i ON r.nome = i.nome_ricetta AND r.autore = i.autore_ricetta "
				+ "WHERE r.nome NOT IN ( "
				+ "SELECT nome_ricetta "
				+ "FROM Ingredienti "
				+ "WHERE alimento NOT IN ( "+queryParziale+")"
				+ ") AND r.difficolta = %d";
		String sqlQuery=String.format(query,difficolta);
		return dichiarazione.executeQuery(sqlQuery);
	}
	
	public static ResultSet ottieniRicetta(Statement dichiarazione,String nome,String autore) throws SQLException { //QUERY PER OTTENERE I DATI DELLA RICETTA SCELTA 
		String query="SELECT a.nome, a.autore, a.descrizione, a.difficolta, b.alimento, b.quantita "
				+ "FROM ricette a LEFT JOIN ingredienti b ON a.nome = b.nome_ricetta AND a.autore = b.autore_ricetta "
				+ "WHERE a.nome = '%s' AND a.autore = '%s'";
		String sqlQuery=String.format(query,nome,autore);
		return dichiarazione.executeQuery(sqlQuery);
	}
	
}