package com.foodie.boundary2;

import java.io.IOException;

import com.foodie.applicazione.LoginViewController;
import com.foodie.controller.PubblicaRicettaController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CaricaView2 {
	
	private static CaricaView2 istanza;
	
	private CaricaView2() {	
	}
	
	public static synchronized CaricaView2 ottieniIstanza() { //SINGLETON	
		if(istanza == null) {
			istanza = new CaricaView2();
		}
		return istanza;
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
	
	public void caricaViewDispensa(Stage primaryStage) {  //CARICA VIEW DISPENSA
		PubblicaRicettaController controller= PubblicaRicettaController.ottieniIstanza();
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DispensaView2.fxml"));
            DispensaView2Controller dispensaController = DispensaView2Controller.ottieniIstanza();
            loader.setController(dispensaController);
            controller.registraOsservatore(dispensaController, 1);
            Parent root = loader.load();
            dispensaController.setPrimaryStage(primaryStage);
            Scene nuovaScena = new Scene(root);
            dispensaController.aggiornaView();
            primaryStage.setScene(nuovaScena);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
	}
	
	public void caricaViewTrovaRicetta(Stage primaryStage) {  //CARICA VIEW TROVA RICETTA
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("TrovaRicettaView2.fxml"));
        	TrovaRicettaView2Controller trovaRicettaController = TrovaRicettaView2Controller.ottieniIstanza();
        	loader.setController(trovaRicettaController);
            Parent root = loader.load();
            trovaRicettaController.setPrimaryStage(primaryStage);
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
	
    public void caricaViewGestisciRicette(Stage primaryStage) {  //CARICA VIEW GESTISCI RICETTE
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("GestisciRicetteView2.fxml"));
        	GestisciRicetteView2Controller gestisciRicetteController = GestisciRicetteView2Controller.ottieniIstanza();
        	loader.setController(gestisciRicetteController);
            Parent root = loader.load();
            gestisciRicetteController.setPrimaryStage(primaryStage);
            gestisciRicetteController.aggiornaView();
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
	
    public void caricaViewRicetta(Stage primaryStage) {  //CARICA VIEW RICETTA
    	PubblicaRicettaController controller2 = PubblicaRicettaController.ottieniIstanza();
    	PubblicaRicettaController.creaRicetta(); //QUANDO ENTRO NELLA RICETTA CREO L'ISTANZA DELLA RICETTA
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NuovaRicettaView2.fxml"));
            NuovaRicettaView2Controller nuovaRicettaController= NuovaRicettaView2Controller.ottieniIstanza();
            loader.setController(nuovaRicettaController);
			controller2.registraOsservatore(nuovaRicettaController, 2);
            Parent root = loader.load();
            nuovaRicettaController.setPrimaryStage(primaryStage);
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
	}
    
    public void caricaViewAlimenti(Stage primaryStage) {  //CARICA VIEW TROV ALIMENTI
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("AggiungiAlimentoView2.fxml"));
        	AggiungiAlimentoView2Controller aggiungiAlimentoController = AggiungiAlimentoView2Controller.ottieniIstanza();
        	loader.setController(aggiungiAlimentoController);
            Parent root = loader.load();
            aggiungiAlimentoController.setPrimaryStage(primaryStage);
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
    
	public void caricaViewAreaPersonale(Stage primaryStage) {  //CARICA VIEW AREA PERSONALE
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AreaPersonaleView2.fxml"));
		AreaPersonaleView2Controller controllerAreaPersonale = AreaPersonaleView2Controller.ottieniIstanza();
		loader.setController(controllerAreaPersonale);
		Parent root;		
		try {
			root = loader.load();
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
    
}