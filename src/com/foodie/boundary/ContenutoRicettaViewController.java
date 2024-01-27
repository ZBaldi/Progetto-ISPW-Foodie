package com.foodie.boundary;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import com.foodie.controller.TrovaRicettaController;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ContenutoRicettaViewController {
	private Stage primaryStage;
	@FXML
	private Label nome;
	@FXML
	private Label descrizione;
	@FXML
	private VBox contenitoreIngredienti;
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage= primaryStage;
	}
	@FXML
	public void caricaViewDispensa(ActionEvent event) {
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
	@FXML
	public void caricaViewLogin(MouseEvent event) {
		TrovaRicettaController trovaRicettaController= new TrovaRicettaController();
		trovaRicettaController.svuotaDispensa();
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
            Parent root = loader.load();
            LoginViewController loginViewController=loader.getController();
            loginViewController.setPrimaryStage(primaryStage);
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
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
}