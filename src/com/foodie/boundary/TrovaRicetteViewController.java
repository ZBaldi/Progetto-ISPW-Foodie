package com.foodie.boundary;

import java.util.ArrayList;

import com.foodie.controller.TrovaRicettaController;
import com.foodie.model.Dispensa;
import com.foodie.model.RicettaBean;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TrovaRicetteViewController {
	private Stage primaryStage;
	private TrovaRicettaController controller = new TrovaRicettaController();
	@FXML
	private VBox contenitoreRicette;
	@FXML
	private MenuItem facile;
	@FXML
	private MenuItem media;
	@FXML
	private MenuItem difficile;
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage= primaryStage;
	}
	public VBox getContenitoreRicette() {
		return contenitoreRicette;
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
	private void caricaViewDispensa(ActionEvent event) {
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
	private void filtraRicette(ActionEvent event) {
		contenitoreRicette.getChildren().clear();
		MenuItem item= (MenuItem) event.getSource();
		String difficolta=item.getText();
		switch(difficolta){
			case "Facile":	
					trovaRicette(1);
					break;
			case "Media":	
					trovaRicette(2);
					break;
			case "Difficile":	
					trovaRicette(3);
					break;
		}
	}
	private void trovaRicette(int difficoltaInt) {  //SI PUò TROVARE UN MODO DI RICICLARE STA PARTE
		ArrayList<RicettaBean> ricetteTrovate= null;
		ricetteTrovate=controller.trovaRicette(difficoltaInt);
		if(ricetteTrovate!=null) {
			for(RicettaBean r: ricetteTrovate) {
				HBox contenitoreRicettaSingola = new HBox();
				contenitoreRicettaSingola.setAlignment(Pos.CENTER_LEFT);
				contenitoreRicettaSingola.setStyle("-fx-background-color: rgba(217, 217, 217, 0.75);-fx-border-color: black;");
				contenitoreRicettaSingola.setMinHeight(110);
				contenitoreRicettaSingola.setMaxWidth(Double.MAX_VALUE);
				Label labelNome = new Label(r.getNome());
				labelNome.setMinWidth(313);
				labelNome.setMinHeight(65);
				labelNome.setFont(Font.font("Arial",20));
				labelNome.setAlignment(Pos.CENTER);
				Label labelAutore = new Label(r.getAutore());
				labelAutore.setMinWidth(313);
				labelAutore.setMinHeight(65);
				labelAutore.setFont(Font.font("Arial",20));
				labelAutore.setAlignment(Pos.CENTER);
				String difficolta="";
				switch(difficoltaInt) {
				case 1:
						difficolta="facile";
						break;
				case 2:
						difficolta="intermedio";
						break;
				case 3:
						difficolta="difficile";
						break;
				}
				Label labelDifficolta = new Label(difficolta);
				labelDifficolta.setMinWidth(313);
				labelDifficolta.setMinHeight(65);
				labelDifficolta.setFont(Font.font("Arial",20));
				labelDifficolta.setAlignment(Pos.CENTER);
				contenitoreRicettaSingola.getChildren().addAll(labelNome,labelAutore,labelDifficolta);
				contenitoreRicette.getChildren().add(contenitoreRicettaSingola);
			}
		}
		else{
				Label label=new Label("NESSUN RISULTATO");
				label.setStyle("-fx-background-color: rgba(217, 217, 217, 0.75);-fx-border-color: black;");
				label.setMaxWidth(Double.MAX_VALUE);
				label.setMinHeight(110);
				label.setWrapText(true);
				label.setFont(Font.font("Arial",50));
				label.setAlignment(Pos.CENTER);
				contenitoreRicette.getChildren().add(label);
		}
	}
}