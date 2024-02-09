package com.foodie.model;

import java.util.logging.Logger;

public class RicettaDuplicataException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(RicettaDuplicataException.class.getName());
	
	public RicettaDuplicataException(String s) {
		super(s);
	}
	
	public void suggerimento() {
        logger.info("Suggerimento: Prova a inserirne una nuova con un nome diverso.");
    }

}
