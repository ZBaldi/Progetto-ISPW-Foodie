package com.foodie.model;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class AreaPersonaleImplementazioneDao implements AreaPersonaleDao{  //IMPLEMENTAZIONE DAO DELL'AREA PERSONALE SU FILE SYSTEM

	private static AreaPersonaleImplementazioneDao istanza;
	
	private AreaPersonaleImplementazioneDao() {
    }
	
    public static synchronized AreaPersonaleImplementazioneDao ottieniIstanza() { //METODO PER OTTENERE L'ISTANZA
		if(istanza==null) {
			istanza=new AreaPersonaleImplementazioneDao();
		}
		return istanza;
	}
	
	@Override
	public void salvaAreaPersonale(String username, String descrizione) {  //SALVA SU FILE UNA HASHMAP USERNAME-DESCRIZIONE
		Map<String,String> areaPersonaleMap;							   //OGNI VOLTA LA CARICA E AGGIUNGE NUOVI ELEMENTI
		if((areaPersonaleMap=caricaAreaPersonale())==null) {
			areaPersonaleMap=new HashMap<String,String>();
		}
		areaPersonaleMap.put(username, descrizione);
		try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("C:\\Users\\valba\\OneDrive\\Desktop\\Progetto\\Classi serializzate\\areapersonale_data.ser"))) {
			objectOutputStream.writeObject(areaPersonaleMap);
            System.out.println("Area Personale salvata");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ERRORE NEL SALVATAGGIO SU FILE DELL'AREA PERSONALE");
            System.out.println("Problema con il file, riprova o controlla se è nella directory");
        }
	}

	@SuppressWarnings({ "resource", "unchecked" })  //RIMUOVO I WARNINGS
	@Override
	public Map<String, String> caricaAreaPersonale() {     //CARICA L'HASHMAP DA FILE
		ObjectInputStream objectInputStream=null;
		try {
			objectInputStream = new ObjectInputStream(new FileInputStream("C:\\Users\\valba\\OneDrive\\Desktop\\Progetto\\Classi serializzate\\areapersonale_data.ser"));
			Map<String,String> areaPersonaleMap = (Map<String,String>)objectInputStream.readObject();// lo è per forza
			if(objectInputStream!=null) {
				objectInputStream.close();
			}
			return areaPersonaleMap;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}catch (EOFException e) {
            System.err.println("NESSUNA AREA PERSONALE SALVATA");
            return null;
        }catch (IOException e) {
			e.printStackTrace();
			System.err.println("ERRORE NEL CARICAMENTO DA FILE DELL'AREA PERSONALE");
            System.out.println("Problema con il file, riprova o controlla se è nella directory");
			return null;
		}
	}

}