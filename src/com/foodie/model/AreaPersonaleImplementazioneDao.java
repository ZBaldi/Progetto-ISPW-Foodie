package com.foodie.model;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class AreaPersonaleImplementazioneDao implements AreaPersonaleDao{  //IMPLEMENTAZIONE DAO DELL'AREA PERSONALE SU FILE SYSTEM

	private static AreaPersonaleImplementazioneDao istanza;
	private static Path currentPath = Paths.get("");
	private static Path projectPath = currentPath.toAbsolutePath().normalize();
	private static Path otherDirectory = projectPath.resolve("ClassiSerializzate");
	private static Path filePath = otherDirectory.resolve("areapersonale_data.ser");
	private static final String PATH = filePath.toString();
	private static final Logger logger = Logger.getLogger(AreaPersonaleImplementazioneDao.class.getName());
	
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
			areaPersonaleMap=new HashMap<>();
		}
		areaPersonaleMap.put(username, descrizione);
		try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(PATH))) {
			objectOutputStream.writeObject(areaPersonaleMap);
            logger.info("Area Personale salvata");
        } catch (IOException e) {
            e.printStackTrace();
            logger.severe("ERRORE NEL SALVATAGGIO SU FILE DELL'AREA PERSONALE");
            logger.info("Problema con il file, riprova o controlla se è nella directory");
        }
	}

	@SuppressWarnings({ "resource", "unchecked" })  //RIMUOVO I WARNINGS
	@Override
	public Map<String, String> caricaAreaPersonale() {     //CARICA L'HASHMAP DA FILE
		ObjectInputStream objectInputStream=null;
		try {
			objectInputStream = new ObjectInputStream(new FileInputStream(PATH));
			Map<String,String> areaPersonaleMap = (Map<String,String>)objectInputStream.readObject();// lo è per forza
			objectInputStream.close();
			return areaPersonaleMap;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return new HashMap<>();
		}catch (EOFException e) {
            logger.warning("NESSUNA AREA PERSONALE SALVATA");
            return new HashMap<>();
        }catch (IOException e) {
			e.printStackTrace();
			logger.severe("ERRORE NEL CARICAMENTO DA FILE DELL'AREA PERSONALE");
            logger.info("Problema con il file, riprova o controlla se è nella directory");
            return new HashMap<>();
		}
	}

}