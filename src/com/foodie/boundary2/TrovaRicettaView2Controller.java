package com.foodie.boundary2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TrovaRicettaView2Controller {
	private static TrovaRicettaView2Controller istanza;
	private Stage primaryStage;
	@FXML
	private RadioButton facile;
	@FXML
	private RadioButton medio;
	@FXML
	private RadioButton difficile;

	
	private TrovaRicettaView2Controller() {	
	}
	
	public static TrovaRicettaView2Controller ottieniIstanza() { //SINGLETON
		if(istanza == null) {
			istanza = new TrovaRicettaView2Controller();
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
	private void disabilitaPulsanti(ActionEvent event) {
		if (facile.isSelected()) {
		    medio.setDisable(true);
		    difficile.setDisable(true);
		} else if (medio.isSelected()) {
		    facile.setDisable(true);
		    difficile.setDisable(true);
		} else if (difficile.isSelected()) {
		    medio.setDisable(true);
		    facile.setDisable(true);
		} else {
		    medio.setDisable(false);
		    difficile.setDisable(false);
		    facile.setDisable(false);
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
	
	

}
