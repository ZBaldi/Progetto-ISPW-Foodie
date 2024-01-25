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


import com.foodie.controller.LoginController;

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
		if(controller.effettuaLogin(username, password)==1) {
			loginMessageLabel.setText("daje");
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
