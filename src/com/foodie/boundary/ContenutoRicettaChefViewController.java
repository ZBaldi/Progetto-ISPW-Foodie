package com.foodie.boundary;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;

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
}
