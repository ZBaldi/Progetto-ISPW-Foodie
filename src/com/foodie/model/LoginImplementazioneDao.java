package com.foodie.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginImplementazioneDao implements LoginDao{
	private static LoginImplementazioneDao istanza;
	private static String utente = "root";
    private static String password = "root"; 
    private static String databaseUrl = "jdbc:mysql://localhost:3306/user_credentials";
    private static String driverMySql = "com.mysql.jdbc.Driver";
    private LoginImplementazioneDao() {
    }
    public static LoginImplementazioneDao ottieniIstanza() { //METODO PER OTTENERE L'ISTANZA
		if(istanza==null) {
			istanza=new LoginImplementazioneDao();
		}
		return istanza;
	}
	@Override
	public int validazioneLogin(String username, String pwd) throws Exception {
		Statement dichiarazione = null;
        Connection connessione = null;
        ResultSet risultati= null;
        try {
            Class.forName(driverMySql);
            connessione = DriverManager.getConnection(databaseUrl, utente, password);
            dichiarazione = connessione.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            risultati=querySQL.effettuaLogin(dichiarazione, username, pwd);
            while(risultati.next()) {
				if(risultati.getInt(1) == 1) {
					risultati.close();
					risultati=querySQL.controllaTipo(dichiarazione, username);
					while(risultati.next()) {
						int tipo= risultati.getInt(1);
						if(tipo == 0) {
							System.out.println("utente base");
						}
						else {
							System.out.println("utente chef");
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
                if(connessione != null)
                    connessione.close();
                if(risultati != null)
                	risultati.close();
        }
	}
	public int controllaUsername(String username) throws Exception {
		Statement dichiarazione = null;
        Connection connessione = null;
        ResultSet risultati= null;
        try {
            Class.forName(driverMySql);
            connessione = DriverManager.getConnection(databaseUrl, utente, password);
            dichiarazione = connessione.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            risultati=querySQL.controllaUsername(dichiarazione, username);
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
                if(connessione != null)
                    connessione.close();
                if(risultati != null)
                	risultati.close();
        }
	}
	public void registraUtente(String nome,String cognome,String username,int ruolo,String pwd) throws Exception {
		Statement dichiarazione = null;
        Connection connessione = null;
        try {
            Class.forName(driverMySql);
            connessione = DriverManager.getConnection(databaseUrl, utente, password);
            dichiarazione = connessione.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            if(querySQL.registraUtente(dichiarazione, nome, cognome, username, ruolo, pwd)==1) {
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
                if(connessione != null)
                    connessione.close();
        }
	}
}
