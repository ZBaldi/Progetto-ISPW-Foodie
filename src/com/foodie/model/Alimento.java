package com.foodie.model;

import java.util.Objects;

public class Alimento {
	private String nome;
	
	public Alimento(String nome) {
		this.nome=nome;
	}
	
	public String getNome() {
		return this.nome;
	}
	@Override
	public boolean equals(Object o) { //QUESTI 2 OVERRIDE SERVONO PER CONFRONTARE 2 ISTANZE DIVERSE IN BASE ALL'ATTRIBUTO NOME
		if (this == o) {
			return true;
		}
        if (o == null || getClass() != o.getClass()) {
        	return false;
        }
        Alimento alimento = (Alimento) o;
        return Objects.equals(nome, alimento.nome);
	}
	@Override
	 public int hashCode() {
	        return Objects.hash(nome);
	    }
}