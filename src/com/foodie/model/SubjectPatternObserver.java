package com.foodie.model;

import java.util.ArrayList;

public abstract class SubjectPatternObserver {        //CLASSE ASTRATTA DEL PATTERN OBSERVER CHE METTE 
													  //A DISPOSIZIONE METODI PER GESTIRE GLI 
	protected ArrayList<Observer> viewRegistrate; 	  //OSSERVATORI DI CUI NON SI SA NULLA
	
	public SubjectPatternObserver() {
		this.viewRegistrate=new ArrayList<Observer>();
	}
	
	public void registra(Observer o) {  //REGISTRA L'OSSERVATORE NELLA PROPRIA LISTA SE NON PRESENTE
		if(!viewRegistrate.contains(o)) {
			this.viewRegistrate.add(o);
			System.out.println("Osservatore registrato");
		}
		else {
			System.out.println("Osservatore già registrato");
		}
	}
	
	public void cancella(Observer o) {  //CANCELLA L'OSSERVATORE DALLA PROPRIA LISTA SE PRESENTE
		if(this.viewRegistrate.remove(o)==true) {
			System.out.println("Osservatore rimosso");
		}
		else {
			System.out.println("Osservatore non registrato");
		}
	}
	
	protected void notifica() {         //NOTIFICA TUTTI GLI OSSERVATORI DI UNA EVENTUALE MODIFICA DELL'OSSERVATO
		if(!viewRegistrate.isEmpty()) {
			for(Observer o : viewRegistrate) {
				o.aggiornaView();
			}
			System.out.println("Osservatori notificati");
		}
		else {
			System.out.println("Nessun osservatore da notificare");
		}
	}
	
}