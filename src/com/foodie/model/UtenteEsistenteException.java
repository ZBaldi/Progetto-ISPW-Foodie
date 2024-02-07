package com.foodie.model;

public class UtenteEsistenteException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UtenteEsistenteException(String s) {
		super(s);
	}
	
	public void suggerimento() {
        System.out.println("Suggerimento: Prova a inserire un altro username");
    }

}
