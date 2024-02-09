package com.foodie.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Logger;

public class CatalogoRicetteImplementazioneDao implements CatalogoRicetteChefDao{   //DAO PER LA CONNESSIONE CON MYSQL
	
	private static CatalogoRicetteImplementazioneDao istanza;    //SINGLETON
	private static String utente;
    private static String password; 
    private static final String DATABASEURL = "jdbc:mysql://localhost:3306/ricette";
    @SuppressWarnings("unused")
	private static final String DRIVERMYSQL = "com.mysql.jdbc.Driver";
	private static final String COLONNA_AUTORE= "autore";
	private static final Logger logger = Logger.getLogger(CatalogoRicetteImplementazioneDao.class.getName());
	
    private CatalogoRicetteImplementazioneDao(){
	}
    
	public static synchronized CatalogoRicetteImplementazioneDao ottieniIstanza() throws IOException { //METODO PER OTTENERE L'ISTANZA
		if(istanza==null) {
			istanza=new CatalogoRicetteImplementazioneDao();
		}
		if(utente==null && password==null) {  //CONFIGURO LE CREDENZIALI PER LA CONNESSIONE CON IL DATABASE
			try{
				provaConnessione();
			}catch(IOException e) {
				e.printStackTrace();
				logger.severe("FILE DI CONFIGURAZIONE NON TROVATO");
				throw e;
			}
		}
		return istanza;
	}
	
	private static void provaConnessione() throws IOException {
		int i=0;
		while(i<3) {
			Path currentPath = Paths.get("");
			Path projectPath = currentPath.toAbsolutePath().normalize();
			Path filePath = projectPath.resolve("DBMS.txt");
			String path = filePath.toString();
			try(BufferedReader lettore= new BufferedReader(new FileReader(path))){
				utente=lettore.readLine();
				password=lettore.readLine();
				break;
			}catch(IOException e) {   //TENTO 3 VOLTE IL COLLEGAMENTO CON IL FILE
				e.printStackTrace();
				logger.severe("FILE DI CONFIGURAZIONE NON TROVATO. RIPROVO!");
				i++;
				if(i==3) {
					throw e;
				}
			}
		}
	}  
	
	@Override
	public ArrayList<Ricetta> trovaRicette(Dispensa dispensa, int difficolta, String autore2) throws SQLException,ClassNotFoundException{ //TROVA LE RICETTE NEL DB O PER ALIMENTI-DIFFICOLTA' O PER AUTORE
		ArrayList<Ricetta> ricetteTrovate=new ArrayList<>();
		Statement dichiarazione=null; 
		ResultSet risultati=null;
		if(dispensa!=null && dispensa.getAlimenti().isEmpty()) { //CONTROLLO SE LA DISPENSA è VUOTA SE GLIELA FORNISCO
			logger.info("Dispensa vuota!!! Riempila prima");
			return new ArrayList<>();
		}
		try(Connection connessione= DriverManager.getConnection(DATABASEURL, utente,password)) {//APRO LA CONNESSIONE
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
		        String autore = risultati.getString(COLONNA_AUTORE);
		        Ricetta ricetta = new Ricetta(nomeRicetta, descrizione, difficoltaRicetta, new ArrayList<>(), autore, new ArrayList<>());
		        do {
		            String alimento = risultati.getString("alimento");
		            String quantita = risultati.getString("quantita");
		            ricetta.aggiungiIngrediente(new Alimento(alimento), quantita);
		        } while (risultati.next() && nomeRicetta.equals(risultati.getString("nome")));
		        ricetteTrovate.add(ricetta);
		        if (!risultati.isAfterLast()) {  //SE POSIZIONATO OLTRE L'ULTIMA RIGA ALLORA LA RIPORTA INDIETRO PER GESTIRE TUTTE LE RIGHE CORRETTAMENTE
		            risultati.previous();  //SE LA RIGA DOPO NON HA LO STESSO NOME NEL DO WHILE ALLORA DEVO RIPORTARMI INDIETRO PER GESTIRE L'AVANZAMENTO NEL WHILE ESTERNO(SE NO DI 2 AVANTI!).
		        }
		    }
			dichiarazione.close();  //CHIUDO LA CONNESSIONE
			risultati.close();
			if(ricetteTrovate.isEmpty()) {
				logger.info("Nessuna ricetta trovata");
				return new ArrayList<>();
			}
			logger.info("Ricette Trovate");
			return ricetteTrovate;
		}finally {  //IN OGNI CASO CHIUDO LA CONNESSIONE
			if (dichiarazione != null)
                dichiarazione.close();
            if (risultati != null)
                risultati.close();
		}
	}
	
	@Override
	public void aggiungiRicetta(Ricetta ricetta) throws SQLException,ClassNotFoundException, RicettaDuplicataException { //AGGIUNGI LA RICETTA AL DB
		Statement dichiarazione = null;
        ResultSet risultati= null;
        int codiceDiRitorno=0;
        try(Connection connessione= DriverManager.getConnection(DATABASEURL, utente,password)) {  //APRO LA CONNESSIONE
            dichiarazione = connessione.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            risultati = QuerySQLChef.selezionaRicetteDalNomeAutore(dichiarazione);  //CONTROLLO SE GIA' ESISTE UNA RICETTA CON LA STESSA DESCRIZIONE
            while (risultati.next()) {  //CONTROLLO I RISULTATI
                String nome = risultati.getString("nome");
                String autore= risultati.getString(COLONNA_AUTORE);
                if (nome.equals(ricetta.getNome()) && autore.equals(ricetta.getAutore())){
                	throw new RicettaDuplicataException("Ricetta già esistente nel database!");
                }
            }
            risultati.close();
            dichiarazione.close();  
            dichiarazione = connessione.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            if((codiceDiRitorno=QuerySQLChef.inserisciRicetta(dichiarazione, ricetta))==1) {  //EFFETTUO LA QUERY PER INSERIRE LA RICETTA
            	logger.info("Ricetta aggiunta al database");  //SE 1 OK
            }
            else if(codiceDiRitorno==0){
            	logger.info("Ricetta non aggiunta al database");  //SE 0 ERRORE
            }
            else {
            	eliminaRicetta(ricetta.getNome(),ricetta.getAutore());  //SE ALTRO SIGNIFICA CHE NON HA RIEMPITO ENTRAMBI I DB E QUINDI PROCEDE AD ELIMINARLA DAL PRIMO(IL DBMS GESTISCE L'ELIMINAZIONE A CASCATA)
            	logger.info("Ricetta aggiunta al database solo parzialmente-->procedo a eliminarla");
            }
        } finally {   //IN OGNI CASO CHIUDO LA CONNESSIONE  	
                if (dichiarazione != null)
                    dichiarazione.close();
                if(risultati != null)
                	risultati.close();
        }
	}
	
	@Override
	public void eliminaRicetta(String nome, String autore) throws SQLException,ClassNotFoundException {  //ELIMINA LA RICETTA DAL DB
		Statement dichiarazione = null;
        try(Connection connessione= DriverManager.getConnection(DATABASEURL, utente,password)) { //APRO LA CONNESSIONE
            dichiarazione = connessione.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            if(QuerySQLChef.rimuoviRicetta(dichiarazione, nome,autore)>0) {  //QUERY PER ELIMINARE LA RICETTA DAL DB
            	logger.info("Ricetta eliminata dal database");  
            }
            else {
            	logger.info("Ricetta non eliminata dal database o non presente");
            }
            dichiarazione.close();
        } finally {     //IN OGNI CASO CHIUDO LA CONNESSIONE  	
                if (dichiarazione != null)
                    dichiarazione.close();
        }
	}

	@Override
	public Ricetta ottieniDatiRicetta(String nome,String autore) throws SQLException,ClassNotFoundException {  //CARICA I DATI DELLA RICETTA CERCATA
		Statement dichiarazione=null;
		ResultSet risultati=null;
		Ricetta ricetta= new Ricetta();
		try(Connection connessione= DriverManager.getConnection(DATABASEURL, utente,password)) {  //APRO LA CONNESSIONE
			dichiarazione = connessione.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			risultati= QuerySQLUtente.ottieniRicetta(dichiarazione, nome ,autore);  //QUERY PER OTTENERE LA RICETTA
			while(risultati.next()) {  //SCORRO I RISULTATI E CREO LA RICETTA
		        String nomeRicetta = risultati.getString("nome");
		        String descrizione = risultati.getString("descrizione");
		        int difficoltaRicetta = risultati.getInt("difficolta");
		        String autoreRicetta = risultati.getString(COLONNA_AUTORE);
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
			risultati.close();
			return ricetta;
		}finally {  //IN OGNI CASO CHIUDO LA CONNESSIONE
			if (dichiarazione != null)
                dichiarazione.close();
            if (risultati != null)
                risultati.close();
		}
	}
	
}