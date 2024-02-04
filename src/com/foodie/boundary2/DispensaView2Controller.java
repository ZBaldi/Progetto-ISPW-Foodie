package com.foodie.boundary2;

import com.foodie.controller.TrovaRicettaController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class DispensaView2Controller {
	private static DispensaView2Controller istanza;
	private TrovaRicettaController controller = TrovaRicettaController.ottieniIstanza();
	private Stage primaryStage;
		
	private DispensaView2Controller() {	
	}
	
	public static DispensaView2Controller ottieniIstanza() { //SINGLETON
		if(istanza == null) {
			istanza = new DispensaView2Controller();
		}
		return istanza;
	}
	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage= primaryStage;
	}
	
	@FXML
    private void tornaAlLogin(MouseEvent event) {
        try {
            
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView2.fxml"));
            Parent root = loader.load();
            LoginView2Controller loginViewController = loader.getController();
            loginViewController.setPrimaryStage(primaryStage);
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
	
	@FXML
    private void caricaViewAlimenti(ActionEvent event) {
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("AggiungiAlimentoView2.fxml"));
        	AggiungiAlimentoView2Controller aggiungiAlimentoController = AggiungiAlimentoView2Controller.ottieniIstanza();
        	loader.setController(aggiungiAlimentoController);
            Parent root = loader.load();
            aggiungiAlimentoController.setPrimaryStage(primaryStage);
           // gestisciRicetteViewController.aggiornaView();
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
	
	@FXML
    private void caricaViewTrovaRicetta(ActionEvent event) {
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("TrovaRicettaView2.fxml"));
        	TrovaRicettaView2Controller trovaRicettaController = TrovaRicettaView2Controller.ottieniIstanza();
        	loader.setController(trovaRicettaController);
            Parent root = loader.load();
            trovaRicettaController.setPrimaryStage(primaryStage);
           // gestisciRicetteViewController.aggiornaView();
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
	
	@FXML
	private void svuotaDispensa(ActionEvent event) {
		controller.svuotaDispensa();
	}
	
}
