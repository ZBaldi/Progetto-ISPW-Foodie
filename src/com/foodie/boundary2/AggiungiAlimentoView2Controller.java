package com.foodie.boundary2;

import com.foodie.boundary.DispensaUtenteViewController;
import com.foodie.boundary.LoginViewController;
import com.foodie.boundary.TrovaRicetteViewController;
import com.foodie.controller.LoginController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AggiungiAlimentoView2Controller {
	private static AggiungiAlimentoView2Controller istanza;
	private Stage primaryStage;
	private String username;
	
	private AggiungiAlimentoView2Controller() {
	}
	public static AggiungiAlimentoView2Controller ottieniIstanza() { //METODO PER OTTENERE L'ISTANZA
		if(istanza == null) {
			istanza = new AggiungiAlimentoView2Controller();
		}
		return istanza;
	}	
	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage= primaryStage;
	}
	
	public void setUsername(String username) {
		this.username=username;
	}
	
	@FXML
    private void tornaAlLogin(MouseEvent event) {
        try {
            
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/foodie/boundary/LoginView.fxml"));
            Parent root = loader.load();
            LoginViewController loginViewController = loader.getController();
            loginViewController.setPrimaryStage(primaryStage);
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
	
	@FXML
	private void caricaViewDispensa(ActionEvent event) {
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DispensaView2.fxml"));
            DispensaView2Controller dispensaController = DispensaView2Controller.ottieniIstanza();
            loader.setController(dispensaController);
            Parent root = loader.load();
            dispensaController.setPrimaryStage(primaryStage);
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
}
