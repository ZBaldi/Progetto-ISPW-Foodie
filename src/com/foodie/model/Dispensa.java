package com.foodie.model;

import java.util.ArrayList;

public class Dispensa extends SubjectPatternObserver { //SINGLETON, LA DISPENSA DEVE AVERE SOLO 1 ISTANZA!

	private static Dispensa istanza;                  //OSSERVATO CONCRETO, ESTENDE SUBJECT PATTERN OBSERVER
	private static ArrayList<Alimento> lista;
	
	private Dispensa(){
	}
	
	public static synchronized Dispensa ottieniIstanza() { //METODO PER OTTENERE L'ISTANZA
		if(istanza==null) {
			istanza=new Dispensa();
			lista=new ArrayList<Alimento>();
		}
		return istanza;
	}
	
	public void aggiungiAlimento(Alimento alimento) { //AGGIUNGI L'ALIMENTO ALLA DISPENSA SE NON PRESENTE
		if(!lista.contains(alimento)) {
			lista.add(alimento);
			System.out.println("Alimento aggiunto alla dispensa");
			notifica();
		}
		else {
			System.out.println("Alimento già presente nella dispensa");
		}
	}	
	
	public void eliminaAlimento(Alimento alimento) {  //RIMUOVI L'ALIMENTO DALLA DISPENSA SE PRESENTE
		if(lista.remove(alimento)==true) {
			System.out.println("Alimento rimosso dalla dispensa");
			notifica();
		}
		else {
			System.out.println("Alimento non presente nella dispensa");
		}
	}
	
	public void svuotaDispensa() {  //SVUOTA LA DISPENSA SE NON LO E'
		if(!lista.isEmpty()) {
			lista.clear();
			System.out.println("Dispensa svuotata");
			notifica();
		}
		else {
			System.out.println("Dispensa già vuota");
		}
	}
	
	public ArrayList<Alimento> getAlimenti(){ //RESTITUISCE LA LISTA DEGLI ALIMENTI PRESENTI NELLA DISPENSA AL CHIAMANTE
		if(!lista.isEmpty()) {
			System.out.println("Restituisco gli alimenti nella dispensa");
			return lista;
		}
		else {
			System.out.println("La dispensa non ha alimenti");
			return null;
		}
	}
	
}