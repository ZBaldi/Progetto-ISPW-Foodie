package com.foodie.model;

import java.util.ArrayList;
import java.util.logging.Logger;

public abstract class SubjectPatternObserver {        //CLASSE ASTRATTA DEL PATTERN OBSERVER CHE METTE 
													  //A DISPOSIZIONE METODI PER GESTIRE GLI 
	protected ArrayList<Observer> viewRegistrate; 	  //OSSERVATORI DI CUI NON SI SA NULLA
	private static final Logger logger = Logger.getLogger(SubjectPatternObserver.class.getName());
	
	protected SubjectPatternObserver() {
		this.viewRegistrate=new ArrayList<>();
	}
	
	public void registra(Observer o) {  //REGISTRA L'OSSERVATORE NELLA PROPRIA LISTA SE NON PRESENTE
		if(!viewRegistrate.contains(o)) {
			this.viewRegistrate.add(o);
			logger.info("Osservatore registrato");
		}
		else {
			logger.info("Osservatore già registrato");
		}
	}
	
	public void cancella(Observer o) {  //CANCELLA L'OSSERVATORE DALLA PROPRIA LISTA SE PRESENTE
		if(this.viewRegistrate.remove(o)) {
			logger.info("Osservatore rimosso");
		}
		else {
			logger.info("Osservatore non registrato");
		}
	}
	
	protected void notifica() {         //NOTIFICA TUTTI GLI OSSERVATORI DI UNA EVENTUALE MODIFICA DELL'OSSERVATO
		if(!viewRegistrate.isEmpty()) {
			for(Observer o : viewRegistrate) {
				o.aggiornaView();
			}
			logger.info("Osservatori notificati");
		}
		else {
			logger.info("Nessun osservatore da notificare");
		}
	}
	
}