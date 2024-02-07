package com.foodie.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.foodie.model.Alimento;
import com.foodie.model.CatalogoAlimentiDao;
import com.foodie.model.CatalogoAlimentiNutrixionixImplementazioneDao;
import com.foodie.model.CatalogoRicetteChefDao;
import com.foodie.model.CatalogoRicetteImplementazione2Dao;
import com.foodie.model.CatalogoRicetteImplementazioneDao;
import com.foodie.model.Dispensa;
import com.foodie.model.Ricetta;

@SuppressWarnings("unused")
public class TrovaRicettaController {  //SINGLETON, IL CONTROLLER DEVE AVERE SOLO 1 ISTANZA!
	
	private static TrovaRicettaController istanza;
	private static Dispensa dispensa;
	private static CatalogoRicetteChefDao database;
	private static CatalogoAlimentiDao databaseAlimenti;
	private static final Logger logger = Logger.getLogger(TrovaRicettaController.class.getName());
	
	private TrovaRicettaController() {
	}
	
	public static synchronized TrovaRicettaController ottieniIstanza() { //METODO PER OTTENERE L'ISTANZA
		if(istanza==null) {
			istanza=new TrovaRicettaController();
			dispensa= Dispensa.ottieniIstanza();
			database= CatalogoRicetteImplementazioneDao.ottieniIstanza();
			//database= CatalogoRicetteImplementazione2Dao.ottieniIstanza(); //SU FILE
			databaseAlimenti=CatalogoAlimentiNutrixionixImplementazioneDao.ottieniIstanza();
		}
		return istanza;
	}
	
	public void aggiornaDispensa(Alimento alimento,int x) {  //METODO PER AGGIORNARE LA DISPENSA
		if(x==0) {
			dispensa.aggiungiAlimento(alimento);  //SE X E' 0 AGGIUNGO
		}
		else {
			dispensa.eliminaAlimento(alimento);  //SE X E' 1 ELIMINO
		}
	}
	
	public void svuotaDispensa() {  //METODO PER SVUOTARE LA DISPENSA
		dispensa.svuotaDispensa();
	}
	
	public List<Ricetta> trovaRicette(int difficolta, String autore){  //METODO PER TROVARE LE RICETTE
		List<Ricetta> ricetteTrovate= null;
		try {
			if(autore==null) {  //SE NON PASSO L'AUTORE VOGLIO EFFETTUARE LA RICERCA PER ALIMENTI-DIFFICOLTA'
				ricetteTrovate = database.trovaRicette(dispensa, difficolta, null);
			}
			else {  //SE PASSO L'AUTORE VOGLIO EFFETTUARE LA RICERCA PER AUTORE
				ricetteTrovate=database.trovaRicette(null,0,autore);
			}
			if(ricetteTrovate!=null) {
				mostraRicette(ricetteTrovate);
				return ricetteTrovate;
			}
			else {
				return new ArrayList<>();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe("ERRORE NELL'OTTENIMENTO DELLE RICETTE");
			logger.info("Problema con il DB");
			return new ArrayList<>();
		}
	}
	
	private void mostraRicette(List<Ricetta> ricette) {  //METODO PRIVATO PER STAMPARE SU CONSOLE TUTTE LE RICETTE
		for(Ricetta r: ricette) {  //UTILIZZATO PER COMODITA' NEL PROGETTO
			logger.info("nome: "+r.getNome()+"\ndescrizione: "+r.getDescrizione()+"\ndifficolta: "+r.getDifficolta()+"\nautore: "+r.getAutore()+"\nIngredienti: ");
			for(Alimento a: r.getIngredienti()) {
				logger.info(a.getNome());
			}
			logger.info("Quantità: ");
			for(String s: r.getQuantita()) {
				logger.info(s);
			}
		}
	}
	
	public List<Alimento> trovaAlimenti(String nomeAlimento) {  //METODO PER TROVARE GLI ALIMENTI
		List<Alimento> alimentiTrovati=null;
		alimentiTrovati=databaseAlimenti.trovaAlimenti(nomeAlimento);
		if(alimentiTrovati!=null && !alimentiTrovati.isEmpty()) {
			mostraAlimenti(alimentiTrovati);
			return alimentiTrovati;
		}
		else {
			return new ArrayList<>();
		}
	}
	
	private void mostraAlimenti(List<Alimento> alimenti) {  //METODO PRIVATO PER STAMPARE SU CONSOLE TUTTI GLI ALIMENTI
		for(Alimento a: alimenti) {   //UTILIZZATO PER COMODITA' NEL PROGETTO
			logger.info(a.getNome());
		}
	}
	
	public List<Alimento> mostraDispensa(){  //METODO PER OTTENERE GLI ALIMENTI NELLA DISPENSA
		List<Alimento> alimentiInDispensa=null;
		alimentiInDispensa=dispensa.getAlimenti();
		if(alimentiInDispensa!=null && !alimentiInDispensa.isEmpty()) {
			return alimentiInDispensa;
		}
		else {
			return new ArrayList<>();
		}
	}
	
	public Ricetta ottieniRicetta(String nome,String autore) {  //METODO PER OTTENERE I DATI DI UNA RICETTA IN FUNZIONE DEL NOME-AUTORE
		Ricetta ricetta=null; 
		try {
			ricetta=database.ottieniDatiRicetta(nome,autore);
			if(ricetta!=null) {
				return ricetta;
			}
			else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe("ERRORE NELL'OTTENIMENTO DEI DATI DELLA RICETTA");
			logger.info("Problema con il DB");
			return null;
		}
	}
	
}