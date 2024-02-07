package com.foodie.model;

import java.util.List;

public class RicettaBean {  //BEAN DELLA CLASSE RICETTA
	
	private String nome;
	private String descrizione;
	private int difficolta;
	private List<AlimentoBean> ingredienti;
	private String autore;
	private List<String> quantita;
	
	public String getNome() {  //GETTERS E SETTERS DEGLI ATTRIBUTI
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public int getDifficolta() {
		return difficolta;
	}
	
	public void setDifficolta(int difficolta) {
		this.difficolta = difficolta;
	}
	
	public List<AlimentoBean> getIngredienti() {
		return ingredienti;
	}
	
	public void setIngredienti(List<AlimentoBean> ingredienti) {
		this.ingredienti = ingredienti;
	}
	
	public String getAutore() {
		return autore;
	}
	
	public void setAutore(String autore) {
		this.autore = autore;
	}
	
	public List<String> getQuantita() {
		return quantita;
	}
	
	public void setQuantita(List<String> quantita) {
		this.quantita = quantita;
	}
	
}