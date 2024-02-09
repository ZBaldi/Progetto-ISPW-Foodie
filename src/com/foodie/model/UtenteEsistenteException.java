package com.foodie.model;

import java.util.logging.Logger;

public class UtenteEsistenteException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(UtenteEsistenteException.class.getName());
	
	public UtenteEsistenteException(String s) {
		super(s);
	}
	
	public void suggerimento() {
        logger.info("Suggerimento: Prova a inserire un altro username");
    }

}
