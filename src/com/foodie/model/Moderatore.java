package com.foodie.model;

import java.util.ArrayList;

public class Moderatore extends SubjectPatternObserver implements Utente {
	private static Moderatore istanza;
	private static ArrayList<Ricetta> ricetteDaVerificare=null;
	private String username;
	private Moderatore(){
	}
	private Moderatore(String username){
		this.username=username;
	}
	public static Moderatore ottieniIstanza(String username) { //METODO PER OTTENERE L'ISTANZA
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
	public String getViewIniziale() {
		return "ModeratoreView.fxml";
	}
	public void aggiungiRicettaDaVerificare(Ricetta ricetta) {
		ricetteDaVerificare.add(ricetta);
		System.out.println("Ricetta da Verificare aggiunta");
		notifica();
	}
	public void ricettaVerificata(Ricetta ricetta) {
		ricetteDaVerificare.remove(ricetta);
		notifica();
	}
	public static ArrayList<Ricetta> getRicetteDaVerificare(){
		return ricetteDaVerificare;
	}
	public static Ricetta ottieniRicetta(String nome,String autore) {
		if(ricetteDaVerificare!=null && !ricetteDaVerificare.isEmpty()) {
			for(Ricetta r: ricetteDaVerificare) {
				if(r.getNome().equals(nome) && r.getAutore().equals(autore)) {
					return r;
				}
			}
		}
		return null;
	}
}
