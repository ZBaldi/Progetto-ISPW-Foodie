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

public class TrovaRicettaController {
	private Dispensa dispensa;
	private CatalogoRicetteChefDao database;
	private CatalogoAlimentiDao databaseAlimenti;
	public TrovaRicettaController() {
		this.dispensa= Dispensa.ottieniIstanza();
		this.database= CatalogoRicetteImplementazioneDao.ottieniIstanza();
		this.databaseAlimenti=CatalogoAlimentiNutrixionixImplementazioneDao.ottieniIstanza();
	}
	public void aggiornaDispensa(AlimentoBean alimentoBean,int x) {
		Alimento alimento=new Alimento(alimentoBean.getNome());
		if(x==0) {
			dispensa.aggiungiAlimento(alimento);
		}
		else {
			dispensa.eliminaAlimento(alimento);
		}
	}
	public void svuotaDispensa() {
		dispensa.svuotaDispensa();
	}
	public ArrayList<RicettaBean> trovaRicette(int difficolta){
		ArrayList<Ricetta> ricetteTrovate= null;
		try {
			ricetteTrovate = database.trovaRicetta(dispensa, difficolta);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(ricetteTrovate!=null) {
			System.out.println("Ricette Trovate!");
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
			System.out.println("nessuna ricetta trovata");
			return null;
		}
	}
	private void mostraRicette(ArrayList<Ricetta> ricette) {
		for(Ricetta r: ricette) {
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
	public ArrayList<AlimentoBean> trovaAlimenti(String nomeAlimento) {
		ArrayList<Alimento> alimentiTrovati=null;
		ArrayList<AlimentoBean> alimentiTrovatiBean=null;
		if((alimentiTrovati=databaseAlimenti.trovaAlimenti(nomeAlimento))!=null) {
			alimentiTrovatiBean=new ArrayList<AlimentoBean>();
			//mostraAlimenti(alimentiTrovati);
			for(Alimento a:alimentiTrovati) {
				AlimentoBean alimentoBean= new AlimentoBean();
				alimentoBean.setNome(a.getNome());
				alimentiTrovatiBean.add(alimentoBean);
			}
			return alimentiTrovatiBean;
		}
		return null;
	}
	public ArrayList<AlimentoBean> mostraDispensa(){
		ArrayList<Alimento> alimentiInDispensa=dispensa.getAlimenti();
		if(!alimentiInDispensa.isEmpty()) {
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
	/*private void mostraAlimenti(ArrayList<Alimento> alimenti) {
		for(Alimento a: alimenti) {
			System.out.println(a.getNome());
		}
		alimenti.clear();   //si occupa di consigliare al garbage collector di rimuovere le istanze non necessarie
		alimenti.trimToSize();  //ovviamente tra queste ci sono tutti gli alimenti non selezionati
		System.gc();
	}*/
}