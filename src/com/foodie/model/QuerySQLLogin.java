package com.foodie.model;

import java.sql.*;

public class QuerySQLLogin {  
	
	private static final String FROM = "FROM user_account ";
	private static final String WHERE = "WHERE username = '";
	
	public static ResultSet effettuaLogin(Statement dichiarazione, String username, String password) throws SQLException {
		String sqlQuery=String.format("SELECT count(1) "        
									+ FROM
									+ WHERE + username + "' AND password ='" + password + "'");
		return dichiarazione.executeQuery(sqlQuery);  //QUERY PER EFFETTUARE IL LOGIN SE REGISTRATO
	}
	
	public static ResultSet controllaUsername(Statement dichiarazione,String username) throws SQLException {
		String sqlQuery= String.format("SELECT COUNT(*) "
									 + FROM
									 + WHERE + username + "'");
		return dichiarazione.executeQuery(sqlQuery);  //QUERY PER CONTROLLARE L'USERNAME SE REGISTRATO
	}
	
	public static int registraUtente(Statement dichiarazione, String nome,String cognome,String username,int ruolo,String password) throws SQLException {
		String insertFields = "INSERT INTO user_account(nome,cognome,username,ruolo,password) VALUES ('";
    	String insertValues = nome + "','" + cognome + "','" + username + "'," + ruolo + ",'" + password + "');";
    	String sqlInsert = String.format(insertFields+insertValues);
    	return dichiarazione.executeUpdate(sqlInsert);  //QUERY PER REGISTRARE L'UTENTE
	}
	
	public static ResultSet controllaTipo(Statement dichiarazione, String username) throws SQLException {
		String sqlQuery= String.format("SELECT ruolo "
									 + FROM
									 + WHERE+username+"' ");
		return dichiarazione.executeQuery(sqlQuery);  //QUERY PER OTTENERE IL TIPO D'UTENTE CHE SI è LOGGATO
	}
	
}