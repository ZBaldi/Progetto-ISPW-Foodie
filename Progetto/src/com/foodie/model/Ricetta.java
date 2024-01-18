package com.foodie.model;

import java.util.ArrayList;

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
}