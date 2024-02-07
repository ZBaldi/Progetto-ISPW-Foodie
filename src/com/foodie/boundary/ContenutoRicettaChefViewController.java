package com.foodie.boundary;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ContenutoRicettaChefViewController {
	
	private static ContenutoRicettaChefViewController istanza;
	private Stage primaryStage;
	private CaricaView caricaView= CaricaView.ottieniIstanza();
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
        caricaView.tornaAlLogin(primaryStage);
    }
	
	@FXML
    private void caricaViewGestisciRicette(ActionEvent event) {  //CARICA VIEW GESTISCI RICETTE
        caricaView.caricaViewGestisciRicette(primaryStage);
    }
	
	@FXML
	private void caricaViewRicetta(ActionEvent event) {  //CARICA VIEW NUOVA RICETTA DA CREARE
		caricaView.caricaViewRicetta(primaryStage);
	}
	
	@FXML
	private void caricaViewAreaPersonale(ActionEvent event) {  //CARICA VIEW AREA PERSONALE	
		caricaView.caricaViewAreaPersonale(primaryStage);
	}
	
}