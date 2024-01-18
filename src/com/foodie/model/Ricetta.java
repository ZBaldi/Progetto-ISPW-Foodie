package com.foodie.model;

import java.util.ArrayList;
import java.util.Objects;

public class Ricetta {
	private String nome;
	private String descrizione;
	private int difficolta;
	private ArrayList<Alimento> ingredienti;
	private String autore;
	public Ricetta(String nome, String descrizione, int difficolta, ArrayList<Alimento> ingredienti, String autore){
		this.nome=nome;
		this.descrizione=descrizione;
		this.difficolta=difficolta;
		this.ingredienti= ingredienti;
		this.autore=autore;
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
	           Objects.equals(autore, ricetta.autore);
	}
	@Override
	public int hashCode() {
	    return Objects.hash(nome, descrizione, difficolta, ingredienti, autore);
	}
}