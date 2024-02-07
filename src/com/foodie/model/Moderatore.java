package com.foodie.model;

import java.util.ArrayList;

public class Moderatore extends SubjectPatternObserver implements Utente { //MODERATORE IMPLEMENTA UTENTE
	
	private static Moderatore istanza;  //SINGLETON, ABBIAMO SOLO 1 MODERATORE
	private static ArrayList<Ricetta> ricetteDaVerificare=null;
	private String username;
	
	private Moderatore(){
	}
	
	private Moderatore(String username){
		this.username=username;
	}
	
	public static synchronized Moderatore ottieniIstanza(String username) { //METODO PER OTTENERE L'ISTANZA
		if(istanza==null) {
			istanza=new Moderatore(username);
			ricetteDaVerificare =new ArrayList<Ricetta>();
		}
		return istanza;
	}
	
	public static Moderatore ottieniIstanza() { //METODO PER OTTENERE L'ISTANZA 
		if(istanza==null) {
			istanza=new Moderatore();
			ricetteDaVerificare =new ArrayList<Ricetta>();
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
			System.out.println("Ricetta da Verificare aggiunta");
			notifica();
		}
		else {
			System.out.println("Ricetta già in corso di verifica");
		}
	}
	
	public void ricettaVerificata(Ricetta ricetta) {  //RIMUOVE LA RICETTA SE VERIFICATA
		if(ricetteDaVerificare.remove(ricetta)==true) {
			System.out.println("Ricetta verificata");
			notifica();
		}
		else {
			System.out.println("Ricetta già verificata o non inviata al moderatore");
		}
	}
	
	public static ArrayList<Ricetta> getRicetteDaVerificare(){  //OTTIENI LE RICETTE DA VERIFICARE
		if(ricetteDaVerificare!=null && !ricetteDaVerificare.isEmpty()) {
			System.out.println("Ecco le ricette da verificare");
			return ricetteDaVerificare;
		}
		else {
			System.out.println("Nessuna ricetta da verificare");
			return new ArrayList<Ricetta>();
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