package com.foodie.boundary;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Client extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader= new FXMLLoader(getClass().getResource("LoginView.fxml"));
		Parent root= loader.load();
		LoginViewController loginViewController=loader.getController();
		loginViewController.setPrimaryStage(primaryStage);
		primaryStage.setResizable(false);  //NON ZOOMABILE
		Scene scene= new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();  
		
		
		
		
		/*DispensaUtenteViewController dispensaUtenteViewController= DispensaUtenteViewController.ottieniIstanza();
			Utente utente= new Standard("pino");
			FXMLLoader loader= new FXMLLoader(getClass().getResource(utente.getViewIniziale()));
			loader.setController(dispensaUtenteViewController);
			Parent root= loader.load();
			dispensaUtenteViewController.setPrimaryStage(primaryStage);
			Dispensa dispensa=Dispensa.ottieniIstanza();
			dispensa.registra(dispensaUtenteViewController);
			Scene scene= new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();*/
	}

	public static void main(String[] args) {
		launch(args);
	}
}
