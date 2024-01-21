package com.foodie.model;

import java.util.ArrayList;
import java.sql.*;

public class CatalogoRicetteImplementazioneDao implements CatalogoRicetteChefDao{
	private static CatalogoRicetteImplementazioneDao istanza; 
	private static String utente = "root";
    private static String password = "root"; 
    private static String databaseUrl = "jdbc:mysql://localhost:3306/ricette";
    private static String driverMySql = "com.mysql.jdbc.Driver";
	private CatalogoRicetteImplementazioneDao(){
	}
	public static CatalogoRicetteImplementazioneDao ottieniIstanza() { //METODO PER OTTENERE L'ISTANZA
		if(istanza==null) {
			istanza=new CatalogoRicetteImplementazioneDao();
		}
		return istanza;
	}
	@Override
	public ArrayList<Ricetta> trovaRicetta(Dispensa dispensa, int difficolta) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void aggiungiRicetta(Ricetta ricetta) throws Exception {
		Statement dichiarazione = null;
        Connection connessione = null;
        ResultSet risultati= null;
        try {
            Class.forName(driverMySql);
            connessione = DriverManager.getConnection(databaseUrl, utente, password);
            dichiarazione = connessione.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            risultati = querySQL.selezionaRicetteDallaDescrizione(dichiarazione);
            while (risultati.next()) {
                String descrizione = risultati.getString("descrizione");
                System.out.println("Trovata descrizione: "+ descrizione);
                if (descrizione.equals(ricetta.getDescrizione())){
                	ricettaDuplicataException eccezione= new ricettaDuplicataException("ricetta già esistente nel database!");
                	throw eccezione;
                }
            }
            risultati.close();
            dichiarazione.close();
            dichiarazione = connessione.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            if(querySQL.inserisciRicetta(dichiarazione, ricetta)>0) {
            	System.out.println("ricetta aggiunta al database");
            }
            else {
            	System.out.println("ricetta non aggiunta al database");
            }
        } finally {       	
                if (dichiarazione != null)
                    dichiarazione.close();
                if (connessione != null)
                    connessione.close();
                if(risultati != null)
                	risultati.close();
        }
	}

	@Override
	public void eliminaRicetta(Ricetta ricetta) {
		// TODO Auto-generated method stub
		
	}

}
