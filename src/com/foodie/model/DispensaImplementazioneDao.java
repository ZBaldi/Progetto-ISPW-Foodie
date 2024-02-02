package com.foodie.model;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DispensaImplementazioneDao implements DispensaDao{  //IMPLEMENTAZIONE DAO 
	
	private static DispensaImplementazioneDao istanza;
	private Utente utente=null;
	
	private DispensaImplementazioneDao() {
    }
	
    public static synchronized DispensaImplementazioneDao ottieniIstanza() { //METODO PER OTTENERE L'ISTANZA
		if(istanza==null) {
			istanza=new DispensaImplementazioneDao();
		}
		return istanza;
	}
	
    @Override
	public void setUtente(Utente utente) {  //METODO PER IMPOSTARE L'UTENTE
		this.utente=utente;
	}
	
	@Override
	public void salvaDispensa(String username) {  //SALVA SU FILE UNA HASHMAP USERNAME-ARRAYLIST DI ALIMENTI,OGNI VOLTA LA CARICA E AGGIUNGE NUOVI ELEMENTI
		Map<String, ArrayList<AlimentoSerializzabile>> dispensaMap;
		ArrayList<AlimentoSerializzabile> alimentiSerializzabili=new ArrayList<AlimentoSerializzabile>();
		if((dispensaMap=caricaDispense())==null) {
			dispensaMap=new HashMap<String, ArrayList<AlimentoSerializzabile>>();
		}
		for(Alimento a: Dispensa.ottieniIstanza().getAlimenti()) {
			AlimentoSerializzabile alimento=new AlimentoSerializzabile();
			alimento.setNome(a.getNome());
			alimentiSerializzabili.add(alimento);
		}
		dispensaMap.put(username, alimentiSerializzabili);
		try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("C:\\Users\\valba\\OneDrive\\Desktop\\Progetto\\Classi serializzate\\dispensa_data.ser"))) {
			objectOutputStream.writeObject(dispensaMap);
            System.out.println("Dispensa salvata");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	@Override
	public Map<String, ArrayList<AlimentoSerializzabile>> caricaDispense() {  //CARICA L'HASHMAP DA FILE
		ObjectInputStream objectInputStream=null;
		try {
			objectInputStream = new ObjectInputStream(new FileInputStream("C:\\Users\\valba\\OneDrive\\Desktop\\Progetto\\Classi serializzate\\dispensa_data.ser"));
			Map<String, ArrayList<AlimentoSerializzabile>> dispensaMap = (Map<String, ArrayList<AlimentoSerializzabile>>)objectInputStream.readObject();// lo è per forza
			ArrayList<AlimentoSerializzabile> dispensaOld=new ArrayList<AlimentoSerializzabile>();
			Dispensa dispensa= Dispensa.ottieniIstanza();
			dispensaOld=dispensaMap.get(utente.getUsername());
			if(dispensaOld!=null) {
				for(AlimentoSerializzabile a: dispensaOld) {
					dispensa.aggiungiAlimento(new Alimento(a.getNome()));
				}
				System.out.println("dispensa caricata");
			}
			if(objectInputStream!=null) {
				objectInputStream.close();
			}
			return dispensaMap;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}catch (EOFException e) {
            System.err.println("nessuna dispensa salvata");
            return null;
        }catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}