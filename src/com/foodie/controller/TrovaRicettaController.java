package com.foodie.controller;


import java.util.ArrayList;


import com.foodie.model.Alimento;
import com.foodie.model.AlimentoBean;
import com.foodie.model.CatalogoAlimentiDao;
import com.foodie.model.CatalogoAlimentiNutrixionixImplementazioneDao;
import com.foodie.model.CatalogoRicetteChefDao;
import com.foodie.model.CatalogoRicetteImplementazioneDao;
import com.foodie.model.Dispensa;
import com.foodie.model.Ricetta;
import com.foodie.model.RicettaBean;

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
			databaseAlimenti=CatalogoAlimentiNutrixionixImplementazioneDao.ottieniIstanza();
		}
		return istanza;
	}
	
	public void aggiornaDispensa(AlimentoBean alimentoBean,int x) {  //METODO PER AGGIORNARE LA DISPENSA
		Alimento alimento=new Alimento(alimentoBean.getNome());
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
	
	public ArrayList<RicettaBean> trovaRicette(int difficolta, String autore){  //METODO PER TROVARE LE RICETTE
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
				ArrayList<RicettaBean> ricetteTrovateBean= new ArrayList<RicettaBean>();
				for(Ricetta r:ricetteTrovate) {
					RicettaBean ricettaBean=new RicettaBean();
					ricettaBean.setNome(r.getNome());
					ricettaBean.setDescrizione(r.getDescrizione());
					ricettaBean.setDifficolta(r.getDifficolta());
					ArrayList<AlimentoBean> alimentiTrovatiBean=new ArrayList<AlimentoBean>();
					ArrayList<Alimento> alimentiTrovati=r.getIngredienti();
					for(Alimento a:alimentiTrovati) {
						AlimentoBean alimentoBean= new AlimentoBean();
						alimentoBean.setNome(a.getNome());
						alimentiTrovatiBean.add(alimentoBean);
					}
					ricettaBean.setIngredienti(alimentiTrovatiBean);
					ricettaBean.setAutore(r.getAutore());
					ricettaBean.setQuantita(r.getQuantita());
					ricetteTrovateBean.add(ricettaBean);
				}	
				return ricetteTrovateBean;
			}
			else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	
	public ArrayList<AlimentoBean> trovaAlimenti(String nomeAlimento) {  //METODO PER TROVARE GLI ALIMENTI
		ArrayList<Alimento> alimentiTrovati=null;
		ArrayList<AlimentoBean> alimentiTrovatiBean=null;
		alimentiTrovati=databaseAlimenti.trovaAlimenti(nomeAlimento);
		if(alimentiTrovati!=null && !alimentiTrovati.isEmpty()) {
			alimentiTrovatiBean=new ArrayList<AlimentoBean>();
			mostraAlimenti(alimentiTrovati);
			for(Alimento a:alimentiTrovati) {
				AlimentoBean alimentoBean= new AlimentoBean();
				alimentoBean.setNome(a.getNome());
				alimentiTrovatiBean.add(alimentoBean);
			}
			return alimentiTrovatiBean;
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
	
	public ArrayList<AlimentoBean> mostraDispensa(){  //METODO PER OTTENERE GLI ALIMENTI NELLA DISPENSA
		ArrayList<Alimento> alimentiInDispensa=null;
		alimentiInDispensa=dispensa.getAlimenti();
		if(alimentiInDispensa!=null && !alimentiInDispensa.isEmpty()) {
			ArrayList<AlimentoBean> alimentiInDispensaBean =new ArrayList<AlimentoBean>();
			for(Alimento a: alimentiInDispensa) {
				AlimentoBean alimentoBean=new AlimentoBean();
				alimentoBean.setNome(a.getNome());
				alimentiInDispensaBean.add(alimentoBean);
			}
			return alimentiInDispensaBean;
		}
		else {
			return null;
		}
	}
	
	public RicettaBean ottieniRicetta(String nome,String autore) {  //METODO PER OTTENERE I DATI DI UNA RICETTA IN FUNZIONE DEL NOME-AUTORE
		Ricetta r=null; 
		try {
			r=database.ottieniDatiRicetta(nome,autore);
			if(r!=null) {
				RicettaBean ricettaBean=new RicettaBean();
				ricettaBean.setNome(r.getNome());
				ricettaBean.setDescrizione(r.getDescrizione());
				ricettaBean.setDifficolta(r.getDifficolta());
				ArrayList<AlimentoBean> alimentiTrovatiBean=new ArrayList<AlimentoBean>();
				ArrayList<Alimento> alimentiTrovati=r.getIngredienti();
				for(Alimento a:alimentiTrovati) {
					AlimentoBean alimentoBean= new AlimentoBean();
					alimentoBean.setNome(a.getNome());
					alimentiTrovatiBean.add(alimentoBean);
				}
				ricettaBean.setIngredienti(alimentiTrovatiBean);
				ricettaBean.setAutore(r.getAutore());
				ricettaBean.setQuantita(r.getQuantita());
				return ricettaBean;
			}
			else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}