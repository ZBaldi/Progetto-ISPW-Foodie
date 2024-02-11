package com.foodie.model;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class DispensaImplementazioneDao implements DispensaDao{  //IMPLEMENTAZIONE DAO 
	
	private static DispensaImplementazioneDao istanza;
	private static Path currentPath = Paths.get("");
	private static Path projectPath = currentPath.toAbsolutePath().normalize();
	private static Path otherDirectory = projectPath.resolve("ClassiSerializzate");
	private static Path filePath = otherDirectory.resolve("dispensa_data.ser");
	private static final String PATH = filePath.toString();
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
	public void salvaDispensa(String username) {  //SALVA SU FILE UNA HASHMAP USERNAME-ARRAYLIST DI ALIMENTI,OGNI VOLTA LA CARICA E AGGIUNGE NUOVI ELEMENTI
		Map<String, ArrayList<AlimentoSerializzabile>> dispensaMap;
		ArrayList<AlimentoSerializzabile> alimentiSerializzabili=new ArrayList<>();
		if((dispensaMap=caricaDispense())==null) {  //FALSE SIGNIFICA CHE NON DEVE RICARICARE LA DISPENSA(EVITI LOOP)
			dispensaMap=new HashMap<>();
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
		try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(PATH))) {
			objectOutputStream.writeObject(dispensaMap);
			logger.info("Dispensa salvata");
        } catch (IOException e) {
            e.printStackTrace();
            logger.severe("ERRORE NEL SALVATAGGIO SU FILE DELLA DISPENSA");
            logger.info("Problema con il file, riprova o controlla se è nella directory");
        }
	}

	@SuppressWarnings({ "unchecked", "resource" })  //RIMUOVO I WARNING
	@Override
	public Map<String, ArrayList<AlimentoSerializzabile>> caricaDispense() {  //CARICA L'HASHMAP DA FILE
		ObjectInputStream objectInputStream=null;    //TRUE VIENE PASSATO QUANDO FAI IL LOGIN!CHE è NECESSARIO RICARICARE LA DISPENSA!
		try {
			objectInputStream = new ObjectInputStream(new FileInputStream(PATH));
			Map<String, ArrayList<AlimentoSerializzabile>> dispensaMap = (Map<String, ArrayList<AlimentoSerializzabile>>)objectInputStream.readObject();// lo è per forza
			objectInputStream.close();
			return dispensaMap;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return new HashMap<>();
		}catch (EOFException e) {
			logger.warning("NESSUNA DISPENSA SALVATA");
			return new HashMap<>();
        }catch (IOException e) {
			e.printStackTrace();
			logger.severe("ERRORE NEL CARICAMENTO DA FILE DELLA DISPENSA");
			logger.info("Problema con il file, riprova o controlla se è nella directory");
            return new HashMap<>();
		}
	}

}