package com.foodie.controller;


import java.util.ArrayList;


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
	
	public ArrayList<Ricetta> trovaRicette(int difficolta, String autore){  //METODO PER TROVARE LE RICETTE
		ArrayList<Ricetta> ricetteTrovate= null;
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
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERRORE NELL'OTTENIMENTO DELLE RICETTE");
			System.out.println("Problema con il DB");
			return null;
		}
	}
	
	private void mostraRicette(ArrayList<Ricetta> ricette) {  //METODO PRIVATO PER STAMPARE SU CONSOLE TUTTE LE RICETTE
		for(Ricetta r: ricette) {  //UTILIZZATO PER COMODITA' NEL PROGETTO
			System.out.println("nome: "+r.getNome()+"\ndescrizione: "+r.getDescrizione()+"\ndifficolta: "+r.getDifficolta()+"\nautore: "+r.getAutore()+"\nIngredienti: ");
			for(Alimento a: r.getIngredienti()) {
				System.out.println(a.getNome());
			}
			System.out.println("Quantità: ");
			for(String s: r.getQuantita()) {
				System.out.println(s);
			}
		}
	}
	
	public ArrayList<Alimento> trovaAlimenti(String nomeAlimento) {  //METODO PER TROVARE GLI ALIMENTI
		ArrayList<Alimento> alimentiTrovati=null;
		alimentiTrovati=databaseAlimenti.trovaAlimenti(nomeAlimento);
		if(alimentiTrovati!=null && !alimentiTrovati.isEmpty()) {
			mostraAlimenti(alimentiTrovati);
			return alimentiTrovati;
		}
		else {
			return null;
		}
	}
	
	private void mostraAlimenti(ArrayList<Alimento> alimenti) {  //METODO PRIVATO PER STAMPARE SU CONSOLE TUTTI GLI ALIMENTI
		for(Alimento a: alimenti) {   //UTILIZZATO PER COMODITA' NEL PROGETTO
			System.out.println(a.getNome());
		}
	}
	
	public ArrayList<Alimento> mostraDispensa(){  //METODO PER OTTENERE GLI ALIMENTI NELLA DISPENSA
		ArrayList<Alimento> alimentiInDispensa=null;
		alimentiInDispensa=dispensa.getAlimenti();
		if(alimentiInDispensa!=null && !alimentiInDispensa.isEmpty()) {
			return alimentiInDispensa;
		}
		else {
			return null;
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
			System.err.println("ERRORE NELL'OTTENIMENTO DEI DATI DELLA RICETTA");
			System.out.println("Problema con il DB");
			return null;
		}
	}
	
}