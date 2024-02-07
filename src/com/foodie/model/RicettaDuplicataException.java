package com.foodie.model;

public class RicettaDuplicataException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RicettaDuplicataException(String s) {
		super(s);
	}
	
	public void suggerimento() {
        System.out.println("Suggerimento: Prova a inserirne una nuova con un nome diverso.");
    }

}
