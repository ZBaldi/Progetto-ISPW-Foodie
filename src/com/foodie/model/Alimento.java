package com.foodie.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties({"serving_unit", "tag_name", "serving_qty", "common_type", "tag_id", "photo", "locale"})
public class Alimento {
	@JsonProperty("food_name")
    private String name;
    @JsonCreator
    public Alimento(@JsonProperty("food_name") String name) {
        this.name = name;
    }
	public String getNome() {
		return this.name;
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
        return Objects.equals(name, alimento.name);
	}
	@Override
	 public int hashCode() {
	        return Objects.hash(name);
	    }
}