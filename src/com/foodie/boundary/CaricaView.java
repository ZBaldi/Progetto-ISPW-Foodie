package com.foodie.boundary;

import java.io.IOException;

import com.foodie.applicazione.LoginViewController;
import com.foodie.controller.PubblicaRicettaController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CaricaView {  //CLASSE CON METODI PER CARICARE LE VIEW
	
	private static CaricaView istanza;
	
	private CaricaView() {	
	}
	
	public static synchronized CaricaView ottieniIstanza() { //SINGLETON	
		if(istanza == null) {
			istanza = new CaricaView();
		}
		return istanza;
	}
	
	public void caricaViewGestisciRicette(Stage primaryStage) {  //CARICA VIEW GESTISCI RICETTE
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("GestisciRicetteView.fxml"));
            GestisciRicetteViewController gestisciRicetteViewController = GestisciRicetteViewController.ottieniIstanza();
            loader.setController(gestisciRicetteViewController);
            Parent root = loader.load();
            gestisciRicetteViewController.setPrimaryStage(primaryStage);
            gestisciRicetteViewController.aggiornaView();
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
	
    public void tornaAlLogin(Stage primaryStage) { //CARICA VIEW LOGIN
        try { 
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/foodie/Applicazione/LoginView.fxml"));
            LoginViewController loginViewController = LoginViewController.ottieniIstanza();
            loader.setController(loginViewController);
            Parent root = loader.load();
            loginViewController.setPrimaryStage(primaryStage);
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
	
	public void caricaViewRicetta(Stage primaryStage) {  //CARICA VIEW DELLA RICETTA NUOVA DA CREARE
		PubblicaRicettaController.creaRicetta(); //QUANDO ENTRO NELLA RICETTA CREO L'ISTANZA DELLA RICETTA
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NuovaRicettaView.fxml"));
            NuovaRicettaViewController nuovaRicettaViewController= NuovaRicettaViewController.ottieniIstanza();
            loader.setController(nuovaRicettaViewController);
            Parent root = loader.load();
            nuovaRicettaViewController.setPrimaryStage(primaryStage);
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
	}
	
	public void caricaViewAreaPersonale(Stage primaryStage) {  //CARICA VIEW AREA PERSONALE	
		AreaPersonaleViewController controllerAreaPersonale = AreaPersonaleViewController.ottieniIstanza();
		try {
			FXMLLoader loader= new FXMLLoader(getClass().getResource("AreaPersonaleView.fxml"));
			loader.setController(controllerAreaPersonale);
			Parent root = loader.load();
			controllerAreaPersonale.setPrimaryStage(primaryStage);
			controllerAreaPersonale.caricaAreaPersonale();
			controllerAreaPersonale.aggiornaView();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void caricaViewDispensa(Stage primaryStage) {  //CARICA VIEW DISPENSA
		try {
			DispensaUtenteViewController dispensaUtenteViewController = DispensaUtenteViewController.ottieniIstanza();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DispensaUtenteView.fxml"));
            loader.setController(dispensaUtenteViewController);
            Parent root = loader.load();
            dispensaUtenteViewController.setPrimaryStage(primaryStage);
            dispensaUtenteViewController.aggiornaView();
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
	}
	
}
