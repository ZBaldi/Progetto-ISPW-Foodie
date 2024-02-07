package com.foodie.boundary;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ContenutoRicettaViewController {
	
	private static ContenutoRicettaViewController istanza;
	private Stage primaryStage;
	private CaricaView caricaView= CaricaView.ottieniIstanza();
	@FXML
	private Label nome;
	@FXML
	private Label descrizione;
	@FXML
	private VBox contenitoreIngredienti;
	
	private ContenutoRicettaViewController() {	
	}
	
	public static synchronized ContenutoRicettaViewController ottieniIstanza() { //SINGLETON
		if(istanza == null) {
			istanza = new ContenutoRicettaViewController();
		}
		return istanza;
	}
	
	public void setPrimaryStage(Stage primaryStage) {  //PASSO LO STAGE
		this.primaryStage = primaryStage;
	}
	
	@FXML
	public void caricaViewDispensa(ActionEvent event) {  //CARICA VIEW DISPENSA
		caricaView.caricaViewDispensa(primaryStage);
	}
	
	@FXML
	public void tornaAlLogin(MouseEvent event) {  //CARICA VIEW LOGIN
		caricaView.tornaAlLogin(primaryStage);
	}
	
	public Label getNome() { //RESTITUICONO LABEL E INFINE VBOX
		return nome;
	}
	
	public Label getDescrizione() {
		return descrizione;
	}
	
	public VBox getContenitoreIngredienti() {
		return contenitoreIngredienti;
	}
	
}