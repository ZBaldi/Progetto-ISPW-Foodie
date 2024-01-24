package com.foodie.model;

import java.util.ArrayList;

public class Dispensa extends SubjectPatternObserver{ //SINGLETON, LA DISPENSA DEVE AVERE SOLO 1 ISTANZA!
	private static Dispensa istanza;                  //ESTENDE LA SUBJECT DEL PATTERN OBSERVER
	private static ArrayList<Alimento> lista;
	private Dispensa(){
	}
	public static Dispensa ottieniIstanza() { //METODO PER OTTENERE L'ISTANZA
		if(istanza==null) {
			istanza=new Dispensa();
			lista=new ArrayList<Alimento>();
		}
		return istanza;
	}	
	public void aggiungiAlimento(Alimento alimento) {
		if(!lista.contains(alimento)) {
			lista.add(alimento);
			System.out.println("alimento aggiunto alla dispensa");
			notifica();
		}
		else {
			System.out.println("alimento già presente nella dispensa");
		}
	}	
	public void eliminaAlimento(Alimento alimento) {
		lista.remove(alimento);
		System.out.println("alimento rimosso dalla dispensa");
		notifica();
	}
	public void svuotaDispensa() {
		lista.clear();
		System.out.println("dispensa svuotata");
		notifica();
	}
	public ArrayList<Alimento> getAlimenti(){
		return lista;
	}
}