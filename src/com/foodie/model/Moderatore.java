package com.foodie.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Moderatore extends SubjectPatternObserver implements Utente { //MODERATORE IMPLEMENTA UTENTE
	
	private static Moderatore istanza;  //SINGLETON, ABBIAMO SOLO 1 MODERATORE
	private static ArrayList<Ricetta> ricetteDaVerificare=null;
	private String username;
	private static final Logger logger = Logger.getLogger(Moderatore.class.getName());
	
	private Moderatore(){
	}
	
	private Moderatore(String username){
		this.username=username;
	}
	
	public static synchronized Moderatore ottieniIstanza(String username) { //METODO PER OTTENERE L'ISTANZA
		if(istanza==null) {
			istanza=new Moderatore(username);
			ricetteDaVerificare =new ArrayList<>();
		}
		return istanza;
	}
	
	public static Moderatore ottieniIstanza() { //METODO PER OTTENERE L'ISTANZA 
		if(istanza==null) {
			istanza=new Moderatore();
			ricetteDaVerificare =new ArrayList<>();
		}
		return istanza;
	}
	
	public void setUsername(String username) {
		this.username=username;
	}
	@Override
	public String getUsername() {
		return username;
	}
	
	@Override
	public String getViewIniziale(int i) {  //VIEW INIZIALE
		if(i==1) {
			return "/com/foodie/boundary/ModeratoreView.fxml";
		}
		else {
			return "/com/foodie/boundary2/ModeratoreView2.fxml";
		}
	}
	
	public void aggiungiRicettaDaVerificare(Ricetta ricetta) {  //AGGIUNGI LE RICETTE DA VERIFICARE
		if(ricetteDaVerificare!=null && !ricetteDaVerificare.contains(ricetta)) {
			ricetteDaVerificare.add(ricetta);
			logger.info("Ricetta da Verificare aggiunta");
			notifica();
		}
		else {
			logger.info("Ricetta già in corso di verifica");
		}
	}
	
	public void ricettaVerificata(Ricetta ricetta) {  //RIMUOVE LA RICETTA SE VERIFICATA
		if(ricetteDaVerificare.remove(ricetta)) {
			logger.info("Ricetta verificata");
			notifica();
		}
		else {
			logger.info("Ricetta già verificata o non inviata al moderatore");
		}
	}
	
	public static List<Ricetta> getRicetteDaVerificare(){  //OTTIENI LE RICETTE DA VERIFICARE
		if(ricetteDaVerificare!=null && !ricetteDaVerificare.isEmpty()) {
			logger.info("Ecco le ricette da verificare");
			return ricetteDaVerificare;
		}
		else {
			logger.info("Nessuna ricetta da verificare");
			return new ArrayList<>();
		}
	}
	
	public static Ricetta ottieniRicetta(String nome,String autore) {  //OTTIENE I DATI DELLA RICETTA CHE SI DEVE VERIFICARE
		if(ricetteDaVerificare!=null && !ricetteDaVerificare.isEmpty()) {
			for(Ricetta r: ricetteDaVerificare) {
				if(r.getNome().equals(nome) && r.getAutore().equals(autore)) {
					return r;
				}
			}
			return null;
		}
		else {
			return null;
		}
	}
}