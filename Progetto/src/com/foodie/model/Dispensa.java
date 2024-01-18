package com.foodie.model;

import java.util.ArrayList;

public class Dispensa { //SINGLETON, LA DISPENSA DEVE AVERE SOLO 1 ISTANZA!
	private static Dispensa istanza; 
	private ArrayList<Alimento> lista=new ArrayList<Alimento>();
	private Dispensa(){
	}
	public static Dispensa ottieniIstanza() { //METODO PER OTTENERE L'ISTANZA
		if(istanza==null) {
			istanza=new Dispensa();
		}
		return istanza;
	}	
	public void aggiungiAlimento(Alimento alimento) {
		if(!lista.contains(alimento)) {
			lista.add(alimento);
			System.out.println("alimento aggiunto alla dispensa");
		}
		else {
			System.out.println("alimento già presente nella dispensa");
		}
	}	
	public void eliminaAlimento(Alimento alimento) {
		lista.remove(alimento);
		System.out.println("alimento rimosso dalla dispensa");
	}
	public void svuotaDispensa() {
		lista.clear();
		System.out.println("dispensa svuotata");
	}
	public ArrayList<Alimento> getAlimenti(){
		return lista;
	}
}