package com.foodie.boundary;

import java.util.ArrayList;

import com.foodie.controller.TrovaRicettaController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.foodie.model.AlimentoBean;

public class DispensaUtenteViewController {
	private TrovaRicettaController controller = new TrovaRicettaController();
	private ArrayList<AlimentoBean> alimentiBeanTrovati= new ArrayList<AlimentoBean>();
	private Stage primaryStage;
	@FXML
	private TextField barraDiRicerca;
	@FXML
	private VBox contenitoreAlimentiTrovati;
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage= primaryStage;
	}
	@FXML
	private void svuotaDispensa(ActionEvent event) {
		controller.svuotaDispensa();
	}
	@FXML
	private void caricaViewTrovaRicetta(ActionEvent event) {
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TrovaRicetteView.fxml"));
            Parent root = loader.load();
            TrovaRicetteViewController trovaRicetteViewController=loader.getController();
            trovaRicetteViewController.setPrimaryStage(primaryStage);
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
	}
	@FXML
	private void caricaViewLogin(MouseEvent event) {
		//DA COLLEGARE AL LOGIN
	}
	@FXML
	private void trovaAlimenti(KeyEvent event) {
		if(event.getCode() == KeyCode.ENTER) {  //GETCODE() TI RESTITUISCE IL TASTO PREMUTO
			alimentiBeanTrovati=controller.trovaAlimenti(barraDiRicerca.getText());
			for(AlimentoBean a: alimentiBeanTrovati) {
				Label labelAlimento = new Label(a.getNome());
				labelAlimento.setOnMouseClicked(event2->EliminaRisultati());
	            contenitoreAlimentiTrovati.getChildren().add(labelAlimento);
			}
		}
	}
	private void EliminaRisultati() {
		contenitoreAlimentiTrovati.getChildren().clear();
	}
}
