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
import java.util.logging.Logger;

public class DispensaImplementazioneDao implements DispensaDao{  //IMPLEMENTAZIONE DAO 
	
	private static DispensaImplementazioneDao istanza;
	private Utente utente=null;
	private static final Logger logger = Logger.getLogger(DispensaImplementazioneDao.class.getName());
	
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
		if((dispensaMap=caricaDispense(false))==null) {  //FALSE SIGNIFICA CHE NON DEVE RICARICARE LA DISPENSA(EVITI LOOP)
			dispensaMap=new HashMap<String, ArrayList<AlimentoSerializzabile>>();
		}
		Dispensa dispensa=Dispensa.ottieniIstanza();
		if(dispensa.getAlimenti()!=null && !dispensa.getAlimenti().isEmpty()) {
			for(Alimento a: dispensa.getAlimenti()) {
				AlimentoSerializzabile alimento=new AlimentoSerializzabile();
				alimento.setNome(a.getNome());
				alimentiSerializzabili.add(alimento);
			}
		}
		dispensaMap.remove(username);
		if(!alimentiSerializzabili.isEmpty()) {
			dispensaMap.put(username, alimentiSerializzabili);
		}
		try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("C:\\Users\\valba\\OneDrive\\Desktop\\Progetto\\Classi serializzate\\dispensa_data.ser"))) {
			objectOutputStream.writeObject(dispensaMap);
            System.out.println("Dispensa salvata");
        } catch (IOException e) {
            e.printStackTrace();
            logger.severe("ERRORE NEL SALVATAGGIO SU FILE DELLA DISPENSA");
            System.out.println("Problema con il file, riprova o controlla se è nella directory");
        }
	}

	@SuppressWarnings({ "unchecked", "resource" })  //RIMUOVO I WARNING
	@Override
	public Map<String, ArrayList<AlimentoSerializzabile>> caricaDispense(boolean bool) {  //CARICA L'HASHMAP DA FILE
		ObjectInputStream objectInputStream=null;    //TRUE VIENE PASSATO QUANDO FAI IL LOGIN!CHE è NECESSARIO RICARICARE LA DISPENSA!
		try {
			objectInputStream = new ObjectInputStream(new FileInputStream("C:\\Users\\valba\\OneDrive\\Desktop\\Progetto\\Classi serializzate\\dispensa_data.ser"));
			Map<String, ArrayList<AlimentoSerializzabile>> dispensaMap = (Map<String, ArrayList<AlimentoSerializzabile>>)objectInputStream.readObject();// lo è per forza
			if(bool==true) {
				Dispensa dispensa= Dispensa.ottieniIstanza();
				ArrayList<AlimentoSerializzabile> dispensaOld=dispensaMap.get(utente.getUsername());
				if(dispensaOld!=null) {
					for(AlimentoSerializzabile a: dispensaOld) {
						dispensa.aggiungiAlimento(new Alimento(a.getNome()));
					}
					System.out.println("dispensa caricata");
				}
			}
			objectInputStream.close();
			return dispensaMap;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return new HashMap<String, ArrayList<AlimentoSerializzabile>>();
		}catch (EOFException e) {
			logger.severe("NESSUNA DISPENSA SALVATA");
			return new HashMap<String, ArrayList<AlimentoSerializzabile>>();
        }catch (IOException e) {
			e.printStackTrace();
			logger.severe("ERRORE NEL CARICAMENTO DA FILE DELLA DISPENSA");
            System.out.println("Problema con il file, riprova o controlla se è nella directory");
            return new HashMap<String, ArrayList<AlimentoSerializzabile>>();
		}
	}

}