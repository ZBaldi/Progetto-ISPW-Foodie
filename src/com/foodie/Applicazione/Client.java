package com.foodie.Applicazione;

import com.foodie.boundary.DispensaUtenteViewController;
import com.foodie.boundary.ModeratoreViewController;
import com.foodie.boundary2.ModeratoreView2Controller;
import com.foodie.model.Dispensa;
import com.foodie.model.Moderatore;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Client extends Application {
	
	private Dispensa dispensa = Dispensa.ottieniIstanza();  //TUTTI GLI OBSERVER E OBSERVABLE
	private DispensaUtenteViewController controllerDispensa = DispensaUtenteViewController.ottieniIstanza();
	private ModeratoreViewController controllerModeratore= ModeratoreViewController.ottieniIstanza();
	private Moderatore moderatore = Moderatore.ottieniIstanza();
	private ModeratoreView2Controller controllerModeratore2= ModeratoreView2Controller.ottieniIstanza();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader= new FXMLLoader(getClass().getResource("LoginView.fxml"));
		LoginViewController loginViewController=LoginViewController.ottieniIstanza();
		loader.setController(loginViewController);
		Parent root= loader.load();
		loginViewController.setPrimaryStage(primaryStage);
		primaryStage.setResizable(false);  //NON ZOOMABILE
		
		dispensa.registra(controllerDispensa);  //DA QUI REGISTRO TUTTI GLI OBSERVER
		moderatore.registra(controllerModeratore);
		moderatore.registra(controllerModeratore2);
		
		Scene scene= new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();  
		//QUI FAI LE REGISTRAZIONI DEGLI OBSERVER!LEVALI DALLA GUI
	}

	public static void main(String[] args) {
		launch(args);
	}
}
