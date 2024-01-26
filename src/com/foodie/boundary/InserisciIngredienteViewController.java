package com.foodie.boundary;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class InserisciIngredienteViewController {
	private static InserisciIngredienteViewController istanza;
	private Stage primaryStage;
	private InserisciIngredienteViewController() {
	}
	public static InserisciIngredienteViewController ottieniIstanza() { //METODO PER OTTENERE L'ISTANZA
		if(istanza==null) {
			istanza=new InserisciIngredienteViewController();
		}
		return istanza;
	}
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage= primaryStage;
	}
}
