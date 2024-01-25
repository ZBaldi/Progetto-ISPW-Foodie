package com.foodie.model;

public interface LoginDao {
	public int validazioneLogin(String username, String password) throws Exception;
	public int controllaUsername(String username) throws Exception;
	public void registraUtente(String nome,String cognome,String username,int ruolo,String password) throws Exception;
}
