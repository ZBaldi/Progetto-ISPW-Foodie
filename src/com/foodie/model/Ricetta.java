package com.foodie.model;

import java.util.ArrayList;
import java.util.Objects;

public class Ricetta extends SubjectPatternObserver{  //OSSERVATO CONCRETO, ESTENDE SUBJECT PATTERN OBSERVER
	
	private String nome;
	private String descrizione;
	private int difficolta;
	private ArrayList<Alimento> ingredienti;
	private String autore;
	private ArrayList<String> quantita;
	
	public Ricetta() {
		this.ingredienti= new ArrayList<Alimento>();
		this.quantita=new ArrayList<String>();
	}
	
	public Ricetta(String nome, String descrizione, int difficolta, ArrayList<Alimento> ingredienti, String autore, ArrayList<String> quantita){
		this.nome=nome;
		this.descrizione=descrizione;
		this.difficolta=difficolta;
		this.ingredienti= ingredienti;
		this.autore=autore;
		this.quantita=quantita;
	}
	
	public String getNome() {  //GETTERS DEGLI ATTRIBUTI DELLA CLASSE
		return this.nome;
	}
	
	public String getDescrizione() {
		return this.descrizione;
	}
	
	public int getDifficolta() {
		return this.difficolta;
	}
	
	public ArrayList<Alimento> getIngredienti(){
		return ingredienti;
	}
	
	public String getAutore() {
		return this.autore;
	}
	
	public ArrayList<String> getQuantita(){
		return this.quantita;
	}
	
	public void setNome(String nome) {  //SETTERS DEGLI ATTRIBUTI
		this.nome=nome;
		System.out.println("Nome ricetta impostato");
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
		System.out.println("Descrizione ricetta impostata");
	}
	
	public void setDifficolta(int difficolta) {
		this.difficolta = difficolta;
		System.out.println("Difficoltà ricetta impostata");
	}
	
	public void setIngredienti(ArrayList<Alimento> ingredienti) {
		this.ingredienti = ingredienti;
		System.out.println("Ingredienti ricetta impostati");
	}
	
	public void setAutore(String autore) {
		this.autore=autore;
		System.out.println("Autore ricetta impostato");
	}
	
	public void setQuantita(ArrayList<String> quantita) {
		this.quantita = quantita;
		System.out.println("Quantità ingredienti ricetta impostati");
	}
	
	public void aggiungiIngrediente(Alimento alimento, String quantita) {  //METODO PER AGGIUNGERE UN ALIMENTO CON LA RISPETTIVA QUANTITA' SE NON PRESENTE
		if(!ingredienti.contains(alimento)) {
			ingredienti.add(alimento);
			this.quantita.add(quantita);
			System.out.println("Ingrediente aggiunto alla ricetta");
			notifica();
		}
		else {
			System.out.println("Ingrediente già presente nella ricetta");
		}
	}
	
	public void eliminaIngrediente(Alimento alimento) {  //METODO PER ELIMINARE UN ALIMENTO CON LA RISPETTIVA QUANTITA' SE PRESENTE
		int indice=ingredienti.indexOf(alimento);
		if(indice!=-1) {
			ingredienti.remove(indice);
			quantita.remove(indice);
			System.out.println("Ingrediente rimosso dalla ricetta");
			notifica();
		}
		else {
			System.out.println("Ingrediente non presente nella ricetta");
		}
	}
	
	@Override
	public boolean equals(Object o) {  //QUESTI 2 OVERRIDE SERVONO PER CONFRONTARE 2 ISTANZE DIVERSE IN BASE AGLI ATTRIBUTI
	    if (this == o) {
	    	return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	    	return false;
	    }
	    Ricetta ricetta = (Ricetta) o;
	    return Objects.equals(nome, ricetta.nome) &&
	           Objects.equals(descrizione, ricetta.descrizione) &&
	           Objects.equals(difficolta, ricetta.difficolta) &&
	           Objects.equals(ingredienti, ricetta.ingredienti) &&
	           Objects.equals(autore, ricetta.autore) && 
	           Objects.equals(quantita, ricetta.quantita);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(nome, descrizione, difficolta, ingredienti, autore, quantita);
	}
	
}