package com.foodie.controller;

import java.util.ArrayList;
import java.util.logging.Logger;
import com.foodie.Applicazione.LoginViewController;
import com.foodie.model.Alimento;
import com.foodie.model.CatalogoRicetteChefDao;
import com.foodie.model.CatalogoRicetteImplementazione2Dao;
import com.foodie.model.CatalogoRicetteImplementazioneDao;
import com.foodie.model.Dispensa;
import com.foodie.model.Moderatore;
import com.foodie.model.Observer;
import com.foodie.model.Ricetta;
import com.foodie.model.RicettaDuplicataException;

@SuppressWarnings("unused")
public class PubblicaRicettaController {  //SINGLETON, IL CONTROLLER DEVE AVERE SOLO 1 ISTANZA!
	
	private static PubblicaRicettaController istanza;
	private static CatalogoRicetteChefDao database;
	private static Moderatore moderatore;
	private static Ricetta ricetta=null;
	private static final Logger logger = Logger.getLogger(PubblicaRicettaController.class.getName());
	
	private PubblicaRicettaController() {
	}
	
	public static PubblicaRicettaController ottieniIstanza() { //METODO PER OTTENERE L'ISTANZA
		if(istanza==null) {
			istanza=new PubblicaRicettaController();
			database= CatalogoRicetteImplementazioneDao.ottieniIstanza();
			//database= CatalogoRicetteImplementazione2Dao.ottieniIstanza(); SU FILE
			moderatore=Moderatore.ottieniIstanza();
		}
		return istanza;
	}
	
	public static void creaRicetta() {  //CREA LA RICETTA
		ricetta=new Ricetta();
	}
	
	public Ricetta getRicetta() {  //RESTITUISCE L'ISTANZA DELLA RICETTA
		return ricetta;
	}
	
	public void compilaRicetta(Ricetta ricettaCompilata) {  //COMPILA LA RICETTA
		if(ricetta!=null) {
			ricetta.setNome(ricettaCompilata.getNome());
			ricetta.setDescrizione(ricettaCompilata.getDescrizione());
			ricetta.setDifficolta(ricettaCompilata.getDifficolta());
			ricetta.setAutore(ricettaCompilata.getAutore());
			notificaModeratore();
		}
	}
	
	public ArrayList<Alimento> mostraAlimentiRicetta() {  //MOSTRA GLI INGREDIENTI DELLA RICETTA
		ArrayList<Alimento> alimentiRicetta=ricetta.getIngredienti();
		if(!alimentiRicetta.isEmpty()) {
			return alimentiRicetta;
		}
		else {
			return new ArrayList<Alimento>();
		}
	}
	
	public void aggiungiIngredientiRicetta(Alimento alimento,String quantita,int x) {  //AGGIUNGE INGREDIENTE ALLA RICETTA
		if(x==0) {
			ricetta.aggiungiIngrediente(alimento, quantita);
		}
		else {
			ricetta.eliminaIngrediente(alimento);
		}
	}
	
	private static void notificaModeratore() {  //NOTIFICA IL MODERATORE DOPO AVER COMPILATO
		System.out.println("MODERATORE NOTIFICATO");
		moderatore.aggiungiRicettaDaVerificare(ricetta);
		ricetta=null;
	}
	
	private void notificaChef(boolean bool) {  //NOTIFICA LO CHEF DOPO AVER APPROVATO LA RICETTA
		System.out.println("CHEF NOTIFICATO: "+bool);
	}
	
	public void pubblicaRicetta(String nome,String autore,boolean bool) {  //PUBBLICA LA RICETTA APPROVATA NEL DATABASE
		Ricetta ricettaDaPubblicare=Moderatore.ottieniRicetta(nome, autore);
		try {
			if(bool==true) {
				database.aggiungiRicetta(ricettaDaPubblicare);
			}
			moderatore.ricettaVerificata(ricettaDaPubblicare);
			notificaChef(bool);
		}catch(RicettaDuplicataException e) {
			e.suggerimento();
			logger.severe("RICETTA GIA' ESISTENTE");
		}catch (Exception e) {
			e.printStackTrace();
			logger.severe("ERRORE NELLA PUBBLICAZIONE DELLA RICETTA");
			System.out.println("Problema con il DB");
		}
	}
	
	public void eliminaRicetta(String nome, String autore) {    //ELIMINA LA RICETTA DAL DATABASE
		
		try {
			database.eliminaRicetta(nome,autore);
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe("ERRORE NELL'ELIMINAZIONE DELLA RICETTA");
			System.out.println("Problema con il DB");
		}
	}
	
	public ArrayList<Ricetta> mostraRicetteDaApprovare() {  //MOSTRA LE RICETTE CHE DEVONO ESSERE APPROVATE
		ArrayList<Ricetta> ricette=Moderatore.getRicetteDaVerificare();
		if(ricette!=null && !ricette.isEmpty()) {
			return ricette;
		}
		else {
			return new ArrayList<Ricetta>();
		}
	}
	
	public void registraOsservatore(Observer observer, int i) {  //REGISTRA GLI OSSERVATORI O ALLA DISPENSA O ALLA RICETTA O AL MODERATORE
		if(i==1) {
			registraOsservatoreDispensa(observer);
		}
		else if(i==2) {
			registraOsservatoreRicetta(observer);
		}
		else {
			registraOsservatoreModeratore(observer);
		}
	}
	
	private void registraOsservatoreDispensa(Observer observer) {
		Dispensa dispensa= Dispensa.ottieniIstanza();
		dispensa.registra(observer);
	}
	
	private void registraOsservatoreRicetta(Observer observer) {
		ricetta.registra(observer);
	}
	
	private void registraOsservatoreModeratore(Observer observer) {
		moderatore.registra(observer);
	} 
	
}