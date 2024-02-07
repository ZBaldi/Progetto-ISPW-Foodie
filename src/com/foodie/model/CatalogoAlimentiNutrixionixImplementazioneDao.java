package com.foodie.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.logging.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CatalogoAlimentiNutrixionixImplementazioneDao implements CatalogoAlimentiDao{  //IMPLEMENTAZIONE DAO CHE USA UN API PER COMUNICARE CON IL DB DI NUTRIXIONIX
	
	private static CatalogoAlimentiNutrixionixImplementazioneDao istanza;  //SINGLETON 
	private static final String API_URL = "https://trackapi.nutritionix.com/v2/search/instant";
	private static final String APP_ID = "103947a7";
	private static final String API_KEY = "726892d58f85bf82f8f8ab4171671c42";
	private static final Logger logger = Logger.getLogger(CatalogoAlimentiNutrixionixImplementazioneDao.class.getName());
	
	private CatalogoAlimentiNutrixionixImplementazioneDao(){
	}
	
	public static CatalogoAlimentiNutrixionixImplementazioneDao ottieniIstanza() { //METODO PER OTTENERE L'ISTANZA
		if(istanza==null) {
			istanza=new CatalogoAlimentiNutrixionixImplementazioneDao();
		}
		return istanza;
	}
	
	private ArrayList<Alimento> estraiFoodName(String stringa) {  //INIZIALIZZO I VARI ALIMENTI DAL CAMPO FOOD NAME ESTRATTO DAL JSON OTTENUTO
		ObjectMapper mapper = new ObjectMapper(); 
		try {
			JsonNode nodo = mapper.readTree(stringa).get("common");  //prendo ramo common del vettore json di elementi
			//Alimento[] alimenti = mapper.readValue(nodo.toString(), Alimento[].class); modo brutto
			//modo buono://li metto in un arrayList di alimenti ignorando tutti i campi tranne food_name
			return mapper.readValue(nodo.toString(), mapper.getTypeFactory().constructCollectionType(ArrayList.class, Alimento.class));
		} catch (JsonMappingException e) {
			e.printStackTrace();
			logger.severe("PROBLEMA LIBRERIA JSON MAPPING");
			return new ArrayList<>();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			logger.severe("PROBLEMA LIBRERIA JSON");
			return new ArrayList<>();
		}
	}
	
	@Override
	public ArrayList<Alimento> trovaAlimenti(String nome) {
		try {
			ArrayList<Alimento> alimentiTrovati=null;
			String nomeCodificato = URLEncoder.encode(nome, "UTF-8");//da stringa codificato per l'url
			String urlQuery=API_URL+"?query="+nomeCodificato; //url per fare query all'api di nutrixionix
			URI url= new URI(urlQuery);  //creo l'URI associato a quell'url
			HttpURLConnection connessione = (HttpURLConnection) url.toURL().openConnection(); //converto l'URI ad URl e apro una connessione http
			// imposto il modo di richiedere come definito nella documentazione dell'API di Nutixionix
			connessione.setRequestMethod("GET");
            connessione.setRequestProperty("x-app-id", APP_ID);
            connessione.setRequestProperty("x-app-key", API_KEY);
            int codiceDiRisposta= connessione.getResponseCode(); //ottengo codice di risposta di http
            if(codiceDiRisposta== HttpURLConnection.HTTP_OK) {//controllo se ==200 per capire se la connessione http ha ottenuto esito positivo
            	BufferedReader lettore = new BufferedReader(new InputStreamReader(connessione.getInputStream()));
                String stringa;  //converto i dati ottenuti in una stringa
                StringBuilder risposta = new StringBuilder();
                while ((stringa = lettore.readLine()) != null) {
                    risposta.append(stringa);
                }
                lettore.close();
                alimentiTrovati=estraiFoodName(risposta.toString());  // applico il metodo che deserializza la stringa json ottenuta per ottenere i nomi degli alimenti
                connessione.disconnect();
                if(!alimentiTrovati.isEmpty()) {
                	return alimentiTrovati;
                }
                else {
                	return new ArrayList<>();
                }
            }
            else {
            	String warning= "Errore: codice di risposta " + codiceDiRisposta;
            	logger.warning(warning);
            	connessione.disconnect();
            	return new ArrayList<>();
            }
		}catch(Exception e) {
			e.printStackTrace();
			logger.severe("PROBLEMA CON LA CONNESSIONE HTTML PER L'UTILIZZO DELL'API NUTRIXIONIX");
			logger.info("L'API sta dando problemi... riprova in seguito");
			return new ArrayList<>();
		}
	}

}