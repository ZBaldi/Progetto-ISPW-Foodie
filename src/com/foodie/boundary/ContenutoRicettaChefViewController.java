package com.foodie.boundary;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

import com.foodie.applicazione.LoginViewController;
import com.foodie.controller.PubblicaRicettaController;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ContenutoRicettaChefViewController {
	
	private static ContenutoRicettaChefViewController istanza;
	private Stage primaryStage;
	private AreaPersonaleViewController controllerAreaPersonale = AreaPersonaleViewController.ottieniIstanza();
	@FXML
	private Label nome;
	@FXML
	private Label descrizione;
	@FXML
	private VBox contenitoreIngredienti;
	
	private ContenutoRicettaChefViewController() {	
	}
	
	public static synchronized ContenutoRicettaChefViewController ottieniIstanza() { //SINGLETON	
		if(istanza == null) {
			istanza = new ContenutoRicettaChefViewController();
		}
		return istanza;
	}
	
	public void setPrimaryStage(Stage primaryStage) { //PASSO LO STAGE
		this.primaryStage= primaryStage;
	}
	
	public Label getNome() {  //RESTITUISCO LABEL E INFINE VBOX
		return nome;
	}
	
	public Label getDescrizione() {
		return descrizione;
	}
	
	public VBox getContenitoreIngredienti() {
		return contenitoreIngredienti;
	}
	
	@FXML  
    private void tornaAlLogin(MouseEvent event) {  //CARICA VIEW LOGIN
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
	
	@FXML
    private void caricaViewGestisciRicette(ActionEvent event) {  //CARICA VIEW GESTISCI RICETTE
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
	
	@FXML
	private void caricaViewRicetta(ActionEvent event) {  //CARICA VIEW NUOVA RICETTA DA CREARE
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
	
	@FXML
	private void caricaViewAreaPersonale(ActionEvent event) {  //CARICA VIEW AREA PERSONALE	
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
	
}