package com.foodie.controller;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.foodie.model.Alimento;
import com.foodie.model.AlimentoSerializzabile;
import com.foodie.model.Chef;
import com.foodie.model.Dispensa;
import com.foodie.model.LoginDao;
import com.foodie.model.LoginImplementazioneDao;
import com.foodie.model.Moderatore;
import com.foodie.model.Standard;
import com.foodie.model.Utente;

public class LoginController {
	private static Utente utente;
	private LoginDao database = LoginImplementazioneDao.ottieniIstanza();
	public void setUtente(String username, String tipo) {
		if(tipo.equals("Standard")) {
			utente= new Standard(username);
		}
		else if(tipo.equals("Chef")) {
			utente= new Chef(username);
		}
		else {
			utente= Moderatore.ottieniIstanza(username);
		}
	}
	public String ottieniView() {
		return utente.getViewIniziale();
	}
	public int effettuaLogin(String username, String pwd) {
		try {
			return database.validazioneLogin(username, pwd);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	public int controllaUsername(String username) {
		try {
			return database.controllaUsername(username);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	public void registraUtente(String nome,String cognome,String username,int ruolo,String password) {
		try {
			database.registraUtente(nome, cognome, username, ruolo, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void salvaDispensa(String username) {
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
	public Map<String, ArrayList<AlimentoSerializzabile>> caricaDispense() {
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
	public Utente getUtente() {
		return utente;
	}
	public void salvaAreaPersonale(String username,String descrizione) {
		Map<String,String> areaPersonaleMap;
		if((areaPersonaleMap=caricaAreaPersonale())==null) {
			areaPersonaleMap=new HashMap<String,String>();
		}
		areaPersonaleMap.put(username, descrizione);
		try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("C:\\Users\\valba\\OneDrive\\Desktop\\Progetto\\Classi serializzate\\areapersonale_data.ser"))) {
			objectOutputStream.writeObject(areaPersonaleMap);
            System.out.println("Area Personale salvata");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	public Map<String,String> caricaAreaPersonale() {
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
	            System.err.println("nessuna area personale salvata");
	            return null;
	        }catch (IOException e) {
				e.printStackTrace();
				return null;
			}
    }
}
