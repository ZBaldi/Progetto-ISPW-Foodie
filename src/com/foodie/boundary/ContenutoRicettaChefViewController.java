package com.foodie.boundary;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

import com.foodie.controller.PubblicaRicettaController;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ContenutoRicettaChefViewController {
	private Stage primaryStage;
	@FXML
	private Label nome;
	@FXML
	private Label descrizione;
	@FXML
	private VBox contenitoreIngredienti;
	
	private PubblicaRicettaController controller = PubblicaRicettaController.ottieniIstanza();
	private AreaPersonaleViewController controllerAreaPersonale = AreaPersonaleViewController.ottieniIstanza();
	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage= primaryStage;
	}
	public Label getNome() {
		return nome;
	}
	public Label getDescrizione() {
		return descrizione;
	}
	public VBox getContenitoreIngredienti() {
		return contenitoreIngredienti;
	}
	
	
	@FXML
    private void tornaAlLogin(MouseEvent event) {
        try {
            
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
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
    private void caricaViewGestisciRicette(ActionEvent event) {
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("GestisciRicetteView.fxml"));
            Parent root = loader.load();
            GestisciRicetteViewController gestisciRicetteViewController = loader.getController();
            gestisciRicetteViewController.setPrimaryStage(primaryStage);
            gestisciRicetteViewController.aggiornaView();
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	@FXML
	private void caricaViewRicetta(ActionEvent event) {
		controller.creaRicetta(); //QUANDO ENTRO NELLA RICETTA CREO L'ISTANZA DELLA RICETTA
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
	
	@FXML
	private void caricaViewAreaPersonale(ActionEvent event) {
		FXMLLoader loader= new FXMLLoader(getClass().getResource("AreaPersonaleView.fxml"));
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
