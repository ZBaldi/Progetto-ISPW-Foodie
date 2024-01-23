package com.foodie.model;

import java.util.ArrayList;
import java.util.Objects;

public class Ricetta {
	private String nome;
	private String descrizione;
	private int difficolta;
	private ArrayList<Alimento> ingredienti;
	private String autore;
	private ArrayList<String> quantita;
	public Ricetta() {
	}
	public Ricetta(String nome, String descrizione, int difficolta, ArrayList<Alimento> ingredienti, String autore, ArrayList<String> quantita){
		this.nome=nome;
		this.descrizione=descrizione;
		this.difficolta=difficolta;
		this.ingredienti= ingredienti;
		this.autore=autore;
		this.quantita=quantita;
	}
	public String getNome() {
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
	public void setNome(String nome) {
		this.nome=nome;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public void setDifficolta(int difficolta) {
		this.difficolta = difficolta;
	}
	public void setIngredienti(ArrayList<Alimento> ingredienti) {
		this.ingredienti = ingredienti;
	}
	public void setAutore(String autore) {
		this.autore=autore;
	}
	public void setQuantita(ArrayList<String> quantita) {
		this.quantita = quantita;
	}
	public void aggiungiIngrediente(Alimento alimento, String quantita) {
		ingredienti.add(alimento);
		this.quantita.add(quantita);
	}
	public void eliminaIngrediente(Alimento alimento) {
		int indice=ingredienti.indexOf(alimento);
		ingredienti.remove(indice);
		quantita.remove(indice);
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