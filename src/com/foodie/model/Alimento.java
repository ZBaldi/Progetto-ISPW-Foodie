package com.foodie.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

//IGNORO TUTTI QUESTI CAMPI CHE MI VENGONO RESTITUITI IN FORMATO JSON DALL'API NUTRIXIONIX
@JsonIgnoreProperties({"serving_unit", "tag_name", "serving_qty", "common_type", "tag_id", "photo", "locale"})

public class Alimento{  //VIENE CREATO ATTRAVERSO LA LIBRERIA JSON DATABIND
	
	@JsonProperty("food_name")  //MI INTERESSA SOLO QUESTO CAMPO
    private String nome;
	
    @JsonCreator
    public Alimento(@JsonProperty("food_name") String nome) {  //INIZIALIZZO IL NOME DAL CAMPO DELLA STRINGA JSON 
        this.nome = nome;
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