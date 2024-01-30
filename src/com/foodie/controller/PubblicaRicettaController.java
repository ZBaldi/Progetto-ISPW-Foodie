package com.foodie.controller;

import java.util.ArrayList;

import com.foodie.boundary.InserisciIngredienteViewController;
import com.foodie.model.Alimento;
import com.foodie.model.AlimentoBean;
import com.foodie.model.CatalogoAlimentiDao;
import com.foodie.model.CatalogoAlimentiNutrixionixImplementazioneDao;
import com.foodie.model.CatalogoRicetteChefDao;
import com.foodie.model.CatalogoRicetteImplementazioneDao;
import com.foodie.model.Moderatore;
import com.foodie.model.Ricetta;
import com.foodie.model.RicettaBean;

public class PubblicaRicettaController {
	private static PubblicaRicettaController istanza;
	private static CatalogoRicetteChefDao database;
	private Moderatore moderatore=Moderatore.ottieniIstanza();
	//private CatalogoAlimentiDao databaseAlimenti;
	private Ricetta ricetta=null;
	private PubblicaRicettaController() {
	}
	public static PubblicaRicettaController ottieniIstanza() { //METODO PER OTTENERE L'ISTANZA
		if(istanza==null) {
			database= CatalogoRicetteImplementazioneDao.ottieniIstanza();
			//databaseAlimenti=CatalogoAlimentiNutrixionixImplementazioneDao.ottieniIstanza();
			istanza=new PubblicaRicettaController();
		}
		return istanza;
	}
	public void creaRicetta() {
		ricetta=new Ricetta();
	}
	public Ricetta getRicetta() {  //non se potrebbe lo devi sistema! ricettabean devi passare
		return ricetta;
	}
	public void compilaRicetta(RicettaBean ricettaBean) {
		ricetta.setNome(ricettaBean.getNome());
		ricetta.setDescrizione(ricettaBean.getDescrizione());
		ricetta.setDifficolta(ricettaBean.getDifficolta());
		ricetta.setAutore(ricettaBean.getAutore());
		System.out.println("ricetta compilata");
		notificaModeratore();
	}
	/*public void trovaAlimenti(String nomeAlimento) {  GIA FATTO NEL TROVA RICETTA CONTROLLER
		ArrayList<Alimento> alimentiTrovati;
		if((alimentiTrovati=databaseAlimenti.trovaAlimenti(nomeAlimento))!=null) {
			//mostraAlimenti(alimentiTrovati);
		}	
	}*/
	public ArrayList<AlimentoBean> mostraAlimentiRicetta() {
		ArrayList<Alimento> alimentiRicetta=ricetta.getIngredienti();
		if(!alimentiRicetta.isEmpty()) {
			ArrayList<AlimentoBean> alimentiRicettaBean =new ArrayList<AlimentoBean>();
			for(Alimento a: alimentiRicetta) {
				AlimentoBean alimentoBean=new AlimentoBean();
				alimentoBean.setNome(a.getNome());
				alimentiRicettaBean.add(alimentoBean);
			}
			return alimentiRicettaBean;
		}
		else {
			return null;
		}
	}
	public void aggiungiIngredientiRicetta(AlimentoBean alimentoBean,String quantita,int x) {  //CHIAMATO DOPO AVER SELEZIONATO L'ALIMENTO
		Alimento alimento=new Alimento(alimentoBean.getNome());
		if(x==0) {
			ricetta.aggiungiIngrediente(alimento, quantita);
			System.out.println("ingrediente aggiunto");
		}
		else {
			ricetta.eliminaIngrediente(alimento);
			System.out.println("ingrediente eliminato");
		}
	}
	private void notificaModeratore() {
		System.out.println("MODERATORE NOTIFICATO");
		moderatore.aggiungiRicettaDaVerificare(ricetta);
		ricetta=null;
	}
	private void notificaChef(boolean bool) {
		System.out.println("CHEF NOTIFICATO: "+bool);
		//DAFARE
	}
	public void pubblicaRicetta(String nome,String autore,boolean bool) {
		Ricetta ricettaDaPubblicare=Moderatore.ottieniRicetta(nome, autore);
		try {
			if(bool==true) {
				database.aggiungiRicetta(ricettaDaPubblicare);
			}
			moderatore.ricettaVerificata(ricettaDaPubblicare);
			notificaChef(bool);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void eliminaRicetta(String nome, String autore) {  
		
		try {
			database.eliminaRicetta(nome,autore);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void aggiornaRicettaPubblicata(Ricetta ricetta) {  //BEAN BOH FORSE SI ELIMINA
		try {
			database.aggiornaRicetta(ricetta);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ArrayList<RicettaBean> mostraRicetteDaApprovare() {
		ArrayList<Ricetta> ricette=Moderatore.getRicetteDaVerificare();
		if(ricette!=null && !ricette.isEmpty()) {
			ArrayList<RicettaBean> ricetteBean = new ArrayList<RicettaBean>();
			for(Ricetta r: ricette) {
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
				ricetteBean.add(ricettaBean);
			}
			System.out.println("Ecco le ricette da verificare");
			return ricetteBean;
		}
		else {
			System.out.println("Nessuna ricetta da verificare");
			return null;
		}
	}
}