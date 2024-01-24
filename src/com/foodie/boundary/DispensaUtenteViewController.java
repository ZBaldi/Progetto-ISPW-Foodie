package com.foodie.boundary;

import java.util.ArrayList;

import com.foodie.controller.TrovaRicettaController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import com.foodie.model.AlimentoBean;
import com.foodie.model.Observer;

public class DispensaUtenteViewController implements Observer {
	private TrovaRicettaController controller = new TrovaRicettaController();
	private ArrayList<AlimentoBean> alimentiBeanTrovati;
	private ArrayList<AlimentoBean> alimentiBeanDispensa;
	private Stage primaryStage;
	@FXML
	private TextField barraDiRicerca;
	@FXML
	private VBox contenitoreAlimentiTrovati;
	@FXML
	private VBox contenitoreDispensa;
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
	@FXML
	private void gestioneRisultati(KeyEvent event) {
		if(event.getCode() == KeyCode.ENTER) {//GETCODE() TI RESTITUISCE IL TASTO PREMUTO
			trovaAlimenti();
		}
		else if(event.getCode() == KeyCode.BACK_SPACE) {
			eliminaAlimenti();
		}
	}
	private void trovaAlimenti() {
			alimentiBeanTrovati=controller.trovaAlimenti(barraDiRicerca.getText());
			if(alimentiBeanTrovati!=null) {
				for(AlimentoBean a: alimentiBeanTrovati) {
					Label labelAlimento = new Label(a.getNome());
					labelAlimento.setStyle("-fx-background-color: white;");
					labelAlimento.setMaxWidth(Double.MAX_VALUE);
					labelAlimento.setMinHeight(30);
					labelAlimento.setWrapText(true);
					labelAlimento.setFont(Font.font("Arial"));
					labelAlimento.setAlignment(Pos.CENTER);
					labelAlimento.setOnMouseClicked(event2->{salvaAlimento(labelAlimento.getText());eliminaAlimenti();});
					contenitoreAlimentiTrovati.getChildren().add(labelAlimento);
				}
			}
			else {
				Label label = new Label("NESSUN RISULTATO");
				label.setStyle("-fx-background-color: white;");
				label.setMaxWidth(Double.MAX_VALUE);
				label.setMinHeight(30);
				label.setWrapText(true);
				label.setFont(Font.font("Arial"));
				label.setAlignment(Pos.CENTER);
				contenitoreAlimentiTrovati.getChildren().add(label);
			}
	}
	private void salvaAlimento(String nomeAlimento) {
		AlimentoBean alimentoBean = new AlimentoBean();
		alimentoBean.setNome(nomeAlimento);
		controller.aggiornaDispensa(alimentoBean, 0);
	}
	private void eliminaAlimenti() {
		if(!contenitoreAlimentiTrovati.getChildren().isEmpty()) {
			contenitoreAlimentiTrovati.getChildren().clear();
		}
	}
	public void aggiornaView() {
		contenitoreDispensa.getChildren().clear();
		alimentiBeanDispensa =controller.mostraDispensa();
		if(alimentiBeanDispensa!=null) {
			for(AlimentoBean a: alimentiBeanDispensa) {
				Label labelAlimento = new Label(a.getNome());
				labelAlimento.setStyle("-fx-background-color: white;");
				labelAlimento.setMaxWidth(Double.MAX_VALUE);
				labelAlimento.setMinHeight(50);
				labelAlimento.setWrapText(true);
				labelAlimento.setFont(Font.font("Arial",20));
				labelAlimento.setAlignment(Pos.CENTER);
				contenitoreDispensa.getChildren().add(labelAlimento);
			}
		}
	}
}