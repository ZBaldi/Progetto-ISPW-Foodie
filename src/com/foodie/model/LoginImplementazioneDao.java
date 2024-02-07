package com.foodie.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class LoginImplementazioneDao implements LoginDao{
	
	private static LoginImplementazioneDao istanza;
	private static final String UTENTE = "root";
    private static final String PASSWORD = "root"; 
    private static final String DATABASEURL = "jdbc:mysql://localhost:3306/user_credentials";
    @SuppressWarnings("unused")
	private static final String DRIVERMYSQL = "com.mysql.jdbc.Driver";
    private static final Logger logger = Logger.getLogger(LoginImplementazioneDao.class.getName());
    
    private LoginImplementazioneDao() {
    }
    
    public static synchronized LoginImplementazioneDao ottieniIstanza() { //METODO PER OTTENERE L'ISTANZA
		if(istanza==null) {
			istanza=new LoginImplementazioneDao();
		}
		return istanza;
	}
    
	@Override
	public int validazioneLogin(String username, String pwd) throws SQLException,ClassNotFoundException {  //EFFETTUA LOGIN
		Statement dichiarazione = null;
        ResultSet risultati= null;
        try(Connection connessione = DriverManager.getConnection(DATABASEURL, UTENTE, PASSWORD)) {
            dichiarazione = connessione.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            risultati=QuerySQLLogin.effettuaLogin(dichiarazione, username, pwd);
            while(risultati.next()) {
				if(risultati.getInt(1) == 1) {
					risultati.close();
					risultati=QuerySQLLogin.controllaTipo(dichiarazione, username);
					while(risultati.next()) {
						int tipo= risultati.getInt(1);
						if(tipo == 0) {
							logger.info("utente base");
						}
						else if(tipo==1){
							logger.info("utente chef");
						}
						else {
							logger.info("utente Moderatore");
						}
						risultati.close();
						dichiarazione.close();
						return tipo;
					}
				}
            }
            risultati.close();
            dichiarazione.close();
            return -1;
        } finally {       	
                if(dichiarazione != null)
                    dichiarazione.close();
                if(risultati != null)
                	risultati.close();
        }
	}
	
	public int controllaUsername(String username) throws SQLException,ClassNotFoundException {  //CONTROLLA L'USERNAME 
		Statement dichiarazione = null;
        ResultSet risultati= null;
        try(Connection connessione = DriverManager.getConnection(DATABASEURL, UTENTE, PASSWORD)){
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
            return 1;
        } finally {       	
                if(dichiarazione != null)
                    dichiarazione.close();
                if(risultati != null)
                	risultati.close();
        }
	}
	
	public void registraUtente(String nome,String cognome,String username,int ruolo,String pwd) throws SQLException,ClassNotFoundException {  //REGISTRA L'UTENTE
		Statement dichiarazione = null;
        try(Connection connessione = DriverManager.getConnection(DATABASEURL, UTENTE, PASSWORD)) {
            dichiarazione = connessione.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            if(QuerySQLLogin.registraUtente(dichiarazione, nome, cognome, username, ruolo, pwd)==1) {
            	logger.info("Utente registrato");
            }
            else {
            	logger.info("Utente non registrato");
            }
            dichiarazione.close();
        } finally {       	
                if(dichiarazione != null)
                    dichiarazione.close();
        }
	}
}