package com.foodie.model;

import java.util.ArrayList;

public abstract class SubjectPatternObserver {
	protected ArrayList<Observer> viewRegistrate;
	public SubjectPatternObserver() {
		this.viewRegistrate=new ArrayList<Observer>();
	}
	public void registra(Observer o) {
		this.viewRegistrate.add(o);
	}
	public void cancella(Observer o) {
		this.viewRegistrate.remove(o);
	}
	protected void notifica() {
		for(Observer o : viewRegistrate) {
			o.aggiornaView();
		}
	}
}
