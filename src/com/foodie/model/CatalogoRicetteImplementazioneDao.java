package com.foodie.model;

import java.sql.*;
import java.util.ArrayList;

public class CatalogoRicetteImplementazioneDao implements CatalogoRicetteChefDao{   //DAO PER LA CONNESSIONE CON MYSQL
	
	private static CatalogoRicetteImplementazioneDao istanza;    //SINGLETON
	private static String utente = "root";
    private static String password = "root"; 
    private static String databaseUrl = "jdbc:mysql://localhost:3306/ricette";
    private static String driverMySql = "com.mysql.jdbc.Driver";
	
    private CatalogoRicetteImplementazioneDao(){
	}
    
	public static synchronized CatalogoRicetteImplementazioneDao ottieniIstanza() { //METODO PER OTTENERE L'ISTANZA
		if(istanza==null) {
			istanza=new CatalogoRicetteImplementazioneDao();
		}
		return istanza;
	}
	
	@Override
	public ArrayList<Ricetta> trovaRicette(Dispensa dispensa, int difficolta, String autore2) throws Exception{ //TROVA LE RICETTE NEL DB O PER ALIMENTI-DIFFICOLTA' O PER AUTORE
		ArrayList<Ricetta> ricetteTrovate=new ArrayList<Ricetta>();
		Statement dichiarazione=null; 
		Connection connessione=null;
		ResultSet risultati=null;
		if(dispensa!=null && dispensa.getAlimenti().isEmpty()) { //CONTROLLO SE LA DISPENSA è VUOTA SE GLIELA FORNISCO
			System.out.println("Dispensa vuota!!! Riempila prima");
			return null;
		}
		try {
			Class.forName(driverMySql);
			connessione= DriverManager.getConnection(databaseUrl, utente,password);  //APRO LA CONNESSIONE
			dichiarazione = connessione.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			if(dispensa!=null) { //SE FORNISCO LA DISPENSA SIGNIFICA CHE VOGLIO FARE LA QUERY PER ALIMENTI
				risultati= QuerySQLUtente.trovaRicette(dichiarazione,dispensa.getAlimenti(),difficolta);
			}
			else { //SE NON LA FORNISCO E DO UN AUTORE SIGNIFICA CHE VOGLIO FARE LA QUERY PER AUTORE
				risultati= QuerySQLChef.ottieniRicettePersonali(dichiarazione, autore2);
			}
			while (risultati.next()) {  //SCORRO I RISULTATI E CREO LE RICETTE CHE HO TROVATO
		        String nomeRicetta = risultati.getString("nome");
		        String descrizione = risultati.getString("descrizione");
		        int difficoltaRicetta = risultati.getInt("difficolta");
		        String autore = risultati.getString("autore");
		        Ricetta ricetta = new Ricetta(nomeRicetta, descrizione, difficoltaRicetta, new ArrayList<Alimento>(), autore, new ArrayList<String>());
		        do {
		            String alimento = risultati.getString("alimento");
		            String quantita = risultati.getString("quantita");
		            ricetta.aggiungiIngrediente(new Alimento(alimento), quantita);
		        } while (risultati.next() && nomeRicetta.equals(risultati.getString("nome")));
		        ricetteTrovate.add(ricetta);
		    }
			dichiarazione.close();  //CHIUDO LA CONNESSIONE
			connessione.close();
			risultati.close();
			if(ricetteTrovate.isEmpty()) {
				System.out.println("Nessuna ricetta trovata");
				return null;
			}
			System.out.println("Ricette Trovate");
			return ricetteTrovate;
		}finally {  //IN OGNI CASO CHIUDO LA CONNESSIONE
			if (dichiarazione != null)
                dichiarazione.close();
            if (connessione != null)
                connessione.close();
            if (risultati != null)
                connessione.close();
		}
	}
	
	@Override
	public void aggiungiRicetta(Ricetta ricetta) throws Exception { //AGGIUNGI LA RICETTA AL DB
		Statement dichiarazione = null;
        Connection connessione = null;
        ResultSet risultati= null;
        int codiceDiRitorno=0;
        try {
            Class.forName(driverMySql);  
            connessione = DriverManager.getConnection(databaseUrl, utente, password);  //APRO LA CONNESSIONE
            dichiarazione = connessione.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            risultati = QuerySQLChef.selezionaRicetteDallaDescrizione(dichiarazione);  //CONTROLLO SE GIA' ESISTE UNA RICETTA CON LA STESSA DESCRIZIONE
            while (risultati.next()) {  //CONTROLLO I RISULTATI
                String descrizione = risultati.getString("descrizione");
                System.out.println("Trovata descrizione: "+ descrizione);
                if (descrizione.equals(ricetta.getDescrizione())){
                	ricettaDuplicataException eccezione= new ricettaDuplicataException("Ricetta già esistente nel database!");
                	throw eccezione;
                }
            }
            risultati.close();
            dichiarazione.close();  
            dichiarazione = connessione.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            if((codiceDiRitorno=QuerySQLChef.inserisciRicetta(dichiarazione, ricetta))==1) {  //EFFETTUO LA QUERY PER INSERIRE LA RICETTA
            	System.out.println("Ricetta aggiunta al database");  //SE 1 OK
            }
            else if(codiceDiRitorno==0){
            	System.out.println("Ricetta non aggiunta al database");  //SE 0 ERRORE
            }
            else {
            	eliminaRicetta(ricetta.getNome(),ricetta.getAutore());  //SE ALTRO SIGNIFICA CHE NON HA RIEMPITO ENTRAMBI I DB E QUINDI PROCEDE AD ELIMINARLA DAL PRIMO(IL DBMS GESTISCE L'ELIMINAZIONE A CASCATA)
            	System.out.println("Ricetta aggiunta al database solo parzialmente-->procedo a eliminarla");
            }
        } finally {   //IN OGNI CASO CHIUDO LA CONNESSIONE  	
                if (dichiarazione != null)
                    dichiarazione.close();
                if (connessione != null)
                    connessione.close();
                if(risultati != null)
                	risultati.close();
        }
	}
	
	@Override
	public void eliminaRicetta(String nome, String autore) throws Exception {  //ELIMINA LA RICETTA DAL DB
		Statement dichiarazione = null;
        Connection connessione = null;
        try {
            Class.forName(driverMySql);
            connessione = DriverManager.getConnection(databaseUrl, utente, password);  //APRO LA CONNESSIONE
            dichiarazione = connessione.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            if(QuerySQLChef.rimuoviRicetta(dichiarazione, nome,autore)>0) {  //QUERY PER ELIMINARE LA RICETTA DAL DB
            	System.out.println("Ricetta eliminata dal database");  
            }
            else {
            	System.out.println("Ricetta non eliminata dal database o non presente");
            }
            dichiarazione.close();
            connessione.close();
        } finally {     //IN OGNI CASO CHIUDO LA CONNESSIONE  	
                if (dichiarazione != null)
                    dichiarazione.close();
                if (connessione != null)
                    connessione.close();
        }
	}

	@Override
	public Ricetta ottieniDatiRicetta(String nome,String autore) throws Exception {  //CARICA I DATI DELLA RICETTA CERCATA
		Statement dichiarazione=null;
		Connection connessione=null;
		ResultSet risultati=null;
		Ricetta ricetta= new Ricetta();
		try {
			Class.forName(driverMySql);
			connessione= DriverManager.getConnection(databaseUrl, utente,password); //APRO LA CONNESSIONE
			dichiarazione = connessione.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			risultati= QuerySQLUtente.ottieniRicetta(dichiarazione, nome ,autore);  //QUERY PER OTTENERE LA RICETTA
			while(risultati.next()) {  //SCORRO I RISULTATI E CREO LA RICETTA
		        String nomeRicetta = risultati.getString("nome");
		        String descrizione = risultati.getString("descrizione");
		        int difficoltaRicetta = risultati.getInt("difficolta");
		        String autoreRicetta = risultati.getString("autore");
		        ricetta.setNome(nomeRicetta);
		        ricetta.setDescrizione(descrizione);
		        ricetta.setDifficolta(difficoltaRicetta);
		        ricetta.setAutore(autoreRicetta);
		        do {
		            String alimento = risultati.getString("alimento");
		            String quantita = risultati.getString("quantita");
		            ricetta.aggiungiIngrediente(new Alimento(alimento), quantita);
		        } while (risultati.next());
		    }
			dichiarazione.close();
			connessione.close();
			risultati.close();
			return ricetta;
		}finally {  //IN OGNI CASO CHIUDO LA CONNESSIONE
			if (dichiarazione != null)
                dichiarazione.close();
            if (connessione != null)
                connessione.close();
            if (risultati != null)
                connessione.close();
		}
	}
	
}