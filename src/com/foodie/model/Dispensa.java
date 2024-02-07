package com.foodie.model;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Dispensa extends SubjectPatternObserver { //SINGLETON, LA DISPENSA DEVE AVERE SOLO 1 ISTANZA!

	private static Dispensa istanza;                  //OSSERVATO CONCRETO, ESTENDE SUBJECT PATTERN OBSERVER
	private static ArrayList<Alimento> lista;
	private static final Logger logger = Logger.getLogger(Dispensa.class.getName());
	
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
			logger.info("Alimento aggiunto alla dispensa");
			notifica();
		}
		else {
			logger.info("Alimento già presente nella dispensa");
		}
	}	
	
	public void eliminaAlimento(Alimento alimento) {  //RIMUOVI L'ALIMENTO DALLA DISPENSA SE PRESENTE
		if(lista.remove(alimento)==true) {
			logger.info("Alimento rimosso dalla dispensa");
			notifica();
		}
		else {
			logger.info("Alimento non presente nella dispensa");
		}
	}
	
	public void svuotaDispensa() {  //SVUOTA LA DISPENSA SE NON LO E'
		if(!lista.isEmpty()) {
			lista.clear();
			logger.info("Dispensa svuotata");
			notifica();
		}
		else {
			logger.info("Dispensa già vuota");
		}
	}
	
	public ArrayList<Alimento> getAlimenti(){ //RESTITUISCE LA LISTA DEGLI ALIMENTI PRESENTI NELLA DISPENSA AL CHIAMANTE
		if(!lista.isEmpty()) {
			logger.info("Restituisco gli alimenti nella dispensa");
		}
		else {
			logger.info("La dispensa non ha alimenti");
		}
		return lista;
	}
	
}