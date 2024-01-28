package com.foodie.boundary;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.foodie.controller.LoginController;
import com.foodie.model.Alimento;
import com.foodie.model.AlimentoSerializzabile;
import com.foodie.model.Dispensa;
import com.foodie.model.Moderatore;

public class LoginViewController {
	
	@FXML
	private Button registratiButton;
	@FXML
	private Button loginButton;
	@FXML
	private Label loginMessageLabel;
	@FXML 
	private TextField usernameTextField;
	@FXML
	private PasswordField enterPasswordField;
	@FXML
	private Label chiudiLabel;
	private Stage primaryStage;
	private LoginController controller =new LoginController();
	private DispensaUtenteViewController controllerDispensa =DispensaUtenteViewController.ottieniIstanza();
	private ModeratoreViewController controllerModeratore= ModeratoreViewController.ottieniIstanza();
	@FXML
    private void initialize() {
        chiudiLabel.setOnMouseClicked(event -> closeApplication());
    }

    private void closeApplication() {
        Stage stage = (Stage)chiudiLabel.getScene().getWindow();
        stage.close();
    }
	
	
	public void loginButtonOnAction(ActionEvent event) {
		
		if(usernameTextField.getText().isBlank() == false && enterPasswordField.getText().isBlank() == false) {
			validazioneLogin();
		} else {
			loginMessageLabel.setText("Inserisci username e password");
		}
		
	}
	
	public void registratiButtonOnAction(ActionEvent event) {
		creaAccount();
	}
	
	public void validazioneLogin() {
		String username=usernameTextField.getText();
		String password=enterPasswordField.getText();
		int tipo=controller.effettuaLogin(username, password);
		String ruolo;
		if(tipo!= -1) {
			if(tipo==0) {
				ruolo="Standard";		
			}
			else if(tipo==1){
				ruolo="Chef";
			}
			else {
				ruolo="Moderatore";
			}
			controller.setUtente(username, ruolo);
			FXMLLoader loader= new FXMLLoader(getClass().getResource(controller.ottieniView()));
			if(ruolo.equals("Standard")) {
				controllerDispensa.setUsername(username);
				loader.setController(controllerDispensa);
				Parent root;
				try {
					root = loader.load();
					controllerDispensa.setPrimaryStage(primaryStage);
					Dispensa dispensa=Dispensa.ottieniIstanza(); //SICURAMENTE L'OSSERVATORE SI AGGIUNGERA NEL CLIENT
					dispensa.svuotaDispensa();
					dispensa.registra(controllerDispensa);//QUESTO
					controller.caricaDispense();  //Carico dispensa se esiste
					Scene scene= new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else if(ruolo.equals("Chef")){
				loginMessageLabel.setText("chef da fare");
			}
			else {
				loader.setController(controllerModeratore);
				Parent root;
				try {
					root = loader.load();
					controllerModeratore.setPrimaryStage(primaryStage);
					Moderatore moderatore= Moderatore.ottieniIstanza();
					moderatore.registra(controllerModeratore);
					controllerModeratore.aggiornaView();
					Scene scene= new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		else {
			loginMessageLabel.setText("credenziali errate. Se non hai ancora un account, registrati.");
		}
	}
	
	public void creaAccount() {
		
		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistratiView.fxml"));
            Parent root = loader.load();
            RegistratiViewController registratiViewController=loader.getController();
            registratiViewController.setPrimaryStage(primaryStage);
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();
		
		} catch (Exception e) {
			e.printStackTrace();
			e.getCause();
		}
	}
	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage=primaryStage;
	}
}
