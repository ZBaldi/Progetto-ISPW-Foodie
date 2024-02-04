package com.foodie.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CatalogoRicetteImplementazione2Dao implements CatalogoRicetteChefDao{  //DAO CON FILE SYSTEM
	
	private static CatalogoRicetteImplementazione2Dao istanza;    //SINGLETON
	private static final String PATH = "C:\\Users\\valba\\OneDrive\\Desktop\\Progetto\\Catalogo Ricette\\CatalogoRicette.txt";
	
	private CatalogoRicetteImplementazione2Dao(){
	}
	
	public static synchronized CatalogoRicetteImplementazione2Dao ottieniIstanza() { //METODO PER OTTENERE L'ISTANZA
		if(istanza==null) {
			istanza=new CatalogoRicetteImplementazione2Dao();
		}
		return istanza;
	}
	
	@Override
	public ArrayList<Ricetta> trovaRicette(Dispensa dispensa, int difficolta, String autore) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void aggiungiRicetta(Ricetta ricetta) throws Exception { //METODO PER AGGIUNGERE LA RICETTA SUL FILE
		BufferedWriter scrittore=null;    //FORMATTAZIONE NOME;AUTORE;DESCRIZIONE;DIFFICOLTA;ALIMENTO1,ALIMENTO2;QUANTITA'1,QUANTITA'2.
		try {
			scrittore= new BufferedWriter(new FileWriter(PATH,true));
			String nome=ricetta.getNome();
			String descrizione=ricetta.getDescrizione();
			String autore=ricetta.getAutore();
			int difficolta=ricetta.getDifficolta();
			String alimenti="";
			String quantita="";
			for(int i=0;i<ricetta.getIngredienti().size();i++) {
				Alimento alimento= ricetta.getIngredienti().get(i);
				alimenti=alimenti+alimento.getNome();
				quantita=quantita+ricetta.getQuantita().get(i);
				if(i!=ricetta.getIngredienti().size()-1) {
					alimenti=alimenti+",";
					quantita=quantita+",";
				}
			}
			scrittore.write(nome+";"+autore+";"+descrizione+";"+Integer.toString(difficolta)+";"+alimenti+";"+quantita+".");
			scrittore.newLine();
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			if(scrittore!=null)
				scrittore.close();
		}
	}

	@Override
	public void eliminaRicetta(String nome, String autore) throws Exception {
		BufferedReader lettore = null;
	    BufferedWriter scrittore = null;
	    ArrayList<String> lineeVecchie = new ArrayList<>();
        ArrayList<String> lineeNuove = new ArrayList<>();
	    try {
	        lettore = new BufferedReader(new FileReader(PATH));
	        String linea;
	        while ((linea = lettore.readLine()) != null) {
	            lineeVecchie.add(linea);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	            if (lettore != null)
	                lettore.close();
	    }
	    if (lineeVecchie != null && !lineeVecchie.isEmpty()) {
	        for (String s : lineeVecchie) {
	            String[] campi = s.split(";");
	            if (!campi[0].equals(nome) || !campi[1].equals(autore)) {
	                lineeNuove.add(s);
	            }
	        }
	    }
	    try {
	        scrittore = new BufferedWriter(new FileWriter(PATH));
	        if (lineeNuove != null && !lineeNuove.isEmpty()) {
	            for (String s : lineeNuove) {
	                scrittore.write(s);
	                scrittore.newLine();
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (scrittore != null)
	                scrittore.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}

	@Override
	public Ricetta ottieniDatiRicetta(String nome, String autore) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}