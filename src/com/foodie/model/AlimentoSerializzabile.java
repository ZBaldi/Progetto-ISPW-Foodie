package com.foodie.model;

import java.io.Serializable;

public class AlimentoSerializzabile implements Serializable{  //COME CLASSE ALIMENTO MA E' POSSIBILE SERIALIZZARLO

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome;
	
	public void setNome(String nome) {
		this.nome=nome;
	}
	
	public String getNome() {
		return this.nome;
	}
	
}