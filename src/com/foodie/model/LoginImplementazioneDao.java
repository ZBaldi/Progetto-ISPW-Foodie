package com.foodie.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginImplementazioneDao implements LoginDao{
	
	private static LoginImplementazioneDao istanza;
	private static final String UTENTE = "root";
    private static final String PASSWORD = "root"; 
    private static final String DATABASEURL = "jdbc:mysql://localhost:3306/user_credentials";
    private static final String DRIVERMYSQL = "com.mysql.jdbc.Driver";
    
    private LoginImplementazioneDao() {
    }
    
    public static synchronized LoginImplementazioneDao ottieniIstanza() { //METODO PER OTTENERE L'ISTANZA
		if(istanza==null) {
			istanza=new LoginImplementazioneDao();
		}
		return istanza;
	}
    
	@Override
	public int validazioneLogin(String username, String pwd) throws Exception {  //EFFETTUA LOGIN
		Statement dichiarazione = null;
        ResultSet risultati= null;
        try(Connection connessione = DriverManager.getConnection(DATABASEURL, UTENTE, PASSWORD)) {
            Class.forName(DRIVERMYSQL);
            dichiarazione = connessione.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            risultati=QuerySQLLogin.effettuaLogin(dichiarazione, username, pwd);
            while(risultati.next()) {
				if(risultati.getInt(1) == 1) {
					risultati.close();
					risultati=QuerySQLLogin.controllaTipo(dichiarazione, username);
					while(risultati.next()) {
						int tipo= risultati.getInt(1);
						if(tipo == 0) {
							System.out.println("utente base");
						}
						else if(tipo==1){
							System.out.println("utente chef");
						}
						else {
							System.out.println("utente Moderatore");
						}
						risultati.close();
						dichiarazione.close();
						connessione.close();
						return tipo;
					}
				}
            }
            risultati.close();
            dichiarazione.close();
            connessione.close();
            return -1;
        } finally {       	
                if(dichiarazione != null)
                    dichiarazione.close();
                if(risultati != null)
                	risultati.close();
        }
	}
	
	public int controllaUsername(String username) throws Exception {  //CONTROLLA L'USERNAME 
		Statement dichiarazione = null;
        ResultSet risultati= null;
        try(Connection connessione = DriverManager.getConnection(DATABASEURL, UTENTE, PASSWORD)){
            Class.forName(DRIVERMYSQL);
            dichiarazione = connessione.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            risultati=QuerySQLLogin.controllaUsername(dichiarazione, username);
            while(risultati.next()) {
            	int count = risultati.getInt(1);
            	if(count > 0) {
            		return 0;
            	}
            }
            risultati.close();
            dichiarazione.close();
            connessione.close();
            return 1;
        } finally {       	
                if(dichiarazione != null)
                    dichiarazione.close();
                if(risultati != null)
                	risultati.close();
        }
	}
	
	public void registraUtente(String nome,String cognome,String username,int ruolo,String pwd) throws Exception {  //REGISTRA L'UTENTE
		Statement dichiarazione = null;
        try(Connection connessione = DriverManager.getConnection(DATABASEURL, UTENTE, PASSWORD)) {
            Class.forName(DRIVERMYSQL);
            dichiarazione = connessione.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            if(QuerySQLLogin.registraUtente(dichiarazione, nome, cognome, username, ruolo, pwd)==1) {
            	System.out.println("Utente registrato");
            }
            else {
            	System.out.println("Utente non registrato");
            }
            dichiarazione.close();
            connessione.close();
        } finally {       	
                if(dichiarazione != null)
                    dichiarazione.close();
        }
	}
}