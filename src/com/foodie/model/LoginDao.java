package com.foodie.model;

import java.sql.SQLException;

public interface LoginDao {
	public int validazioneLogin(String username, String password) throws SQLException,ClassNotFoundException;
	public int controllaUsername(String username) throws SQLException,ClassNotFoundException;
	public void registraUtente(String nome,String cognome,String username,int ruolo,String password) throws SQLException,ClassNotFoundException;
}
