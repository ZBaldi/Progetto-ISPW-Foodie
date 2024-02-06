package com.foodie.controller;

import java.util.Map;
import com.foodie.model.AreaPersonaleImplementazioneDao;
import com.foodie.model.Chef;
import com.foodie.model.DispensaDao;
import com.foodie.model.AreaPersonaleDao;
import com.foodie.model.DispensaImplementazioneDao;
import com.foodie.model.LoginDao;
import com.foodie.model.LoginImplementazioneDao;
import com.foodie.model.Moderatore;
import com.foodie.model.Standard;
import com.foodie.model.Utente;

public class LoginController {  //SINGLETON, IL CONTROLLER DEVE AVERE SOLO 1 ISTANZA!
	
	private static LoginController istanza;
	private static Utente utente=null;
	private static LoginDao database = LoginImplementazioneDao.ottieniIstanza();
	private static DispensaDao databaseDispensa= DispensaImplementazioneDao.ottieniIstanza();
	private static AreaPersonaleDao databaseAreaPersonale= AreaPersonaleImplementazioneDao.ottieniIstanza();
	
	private LoginController() {
	}
	
	public static LoginController ottieniIstanza() { //METODO PER OTTENERE L'ISTANZA
		if(istanza == null) {
			istanza = new LoginController();
		}
		return istanza;
	}
	
	public void setUtente(String username, String tipo) {  //ISTANZIA L'UTENTE IN FUNZIONE DEL TIPO
		if(tipo.equals("Standard")) {
			utente= new Standard(username);
		}
		else if(tipo.equals("Chef")) {
			utente= new Chef(username);
		}
		else {
			utente= Moderatore.ottieniIstanza(username);
		}
		databaseDispensa.setUtente(utente);
	}
	
	public String ottieniView(int interfaccia) {  //RESTITUISCE LA VIEW DA CARICARE
		return utente.getViewIniziale(interfaccia);
	}
	
	public int effettuaLogin(String username, String pwd) {  //EFFETTUA IL LOGIN
		try {
			return database.validazioneLogin(username, pwd);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public int controllaUsername(String username) {  //CONTROLLA L'USERNAME SE ESISTE
		try {
			return database.controllaUsername(username);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public void registraUtente(String nome,String cognome,String username,int ruolo,String password) {  //REGISTRA L'UTENTE SUL DB
		try {
			database.registraUtente(nome, cognome, username, ruolo, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void salvaDispensa(String username) {  //SALVA GLI INGREDIENTI DELLA DISPENSA NEL DB
		databaseDispensa.salvaDispensa(username);
	}
	
	public void caricaDispense() {  //CARICA GLI INGREDIENTI DELLA DISPENSA DA DB
		databaseDispensa.caricaDispense(true);
    }
	
	public Utente getUtente() {
		return utente;
	}
	
	public void salvaAreaPersonale(String username,String descrizione) {  //SALVA LA DESCRIZIONE DELL'AREA PERSONALE NEL DB
		databaseAreaPersonale.salvaAreaPersonale(username, descrizione);
	}
	
	public Map<String,String> caricaAreaPersonale() {  //CARICA LA DESCRIZIONE DELL'AREA PERSONALE DA DB
		return databaseAreaPersonale.caricaAreaPersonale();
    }
	
}