package com.foodie.applicazione;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.util.Duration;
import com.foodie.controller.LoginController;


public class RegistratiViewController {
	
	private static RegistratiViewController istanza;  //SINGLETON
	// Variabile per memorizzare il ruolo, 0 per l'utente base, 1 per lo chef
    private int ruolo;
	private Stage primaryStage;
    private LoginController controller = LoginController.ottieniIstanza();
	@FXML
    private RadioButton baseRadioButton;
    @FXML
    private RadioButton chefRadioButton;
    private ToggleGroup userTypeToggleGroup;
    @FXML
    private Button indietroButton;
    @FXML
    private Button registratiButton;
    @FXML
    private TextField nomeTextField;
    @FXML
    private TextField cognomeTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField setPasswordField;
    @FXML
    private Label esitoRegistrazioneLabel;
    
    private RegistratiViewController() {
    }
    
    public static synchronized RegistratiViewController ottieniIstanza() { //METODO PER OTTENERE L'ISTANZA
		if(istanza==null) {
			istanza=new RegistratiViewController();
		}
		return istanza;
	}
    
    @FXML
    public void initialize() {  //METODO PER INIZIALIZZARE
       
    	// Creazione del Toggle Group
    	userTypeToggleGroup = new ToggleGroup();
        
    	// Associazione dei Radio Button al Toggle Group
    	baseRadioButton.setToggleGroup(userTypeToggleGroup);
        chefRadioButton.setToggleGroup(userTypeToggleGroup);
        
        // Imposto primo bottone come quello selezionato di default
        userTypeToggleGroup.selectToggle(baseRadioButton);
        
        // Impostazione inizale del ruolo 
        ruolo = 0;    
    }

    public void handleRegistration() {  //CONTROLLA IL TIPO CHE SI REGISTRA
        RadioButton selectedRadioButton = (RadioButton) userTypeToggleGroup.getSelectedToggle();
        	
        	if(selectedRadioButton == baseRadioButton) {
        		ruolo = 0;
        	} else if (selectedRadioButton == chefRadioButton) {
        		ruolo = 1;
        	}
        }
    
    @FXML
    public void indietroButtonOnAction(ActionEvent event) {  //RITORNA AL LOGIN
    	
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
			LoginViewController loginViewController=LoginViewController.ottieniIstanza();
			loader.setController(loginViewController);
            Parent root = loader.load();
            loginViewController.setPrimaryStage(primaryStage);
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();
    		
    	}catch (Exception e) {
    		e.printStackTrace();
    		e.getCause();
    	}
    	
   }
   
   @FXML
   public void registratiButtonOnAction(ActionEvent event) {  //CHIAMA I METODI PER REGISTRARSI
    	handleRegistration();
    	registrazioneUser();
    	
   }
    
   public void registrazioneUser() {  //METODO PER REGISTRARE L'UTENTE
    	    	
    	// Prendo input utente e verifico che non sia vuoto
    	String nome = nomeTextField.getText().trim();
    	String cognome = cognomeTextField.getText().trim();
    	String username = usernameTextField.getText().trim();
    	String password = setPasswordField.getText().trim();
    	int role = ruolo;
    	
    	if (nome.isEmpty() || cognome.isEmpty() || username.isEmpty() || password.isEmpty()) {
            // Uno o più campi sono vuoti
    		esitoRegistrazioneLabel.setText("Per favore, completa tutti i campi");
            return; // Interrompi l'esecuzione se ci sono campi vuoti
        }
    	
    	// Controllo se l'username è già presente nella base di dati
        if (usernameEsistente(username)==0) {
            esitoRegistrazioneLabel.setText("L'username è già in uso. Scegli un altro username.");
            return; // Interrompi l'esecuzione se l'username è già in uso
        }
    	controller.registraUtente(nome, cognome, username, role, password);
    	
    	esitoRegistrazioneLabel.setText("Registrazione andata a buon fine.");
    	PauseTransition pause = new PauseTransition(Duration.seconds(2));
    	pause.setOnFinished(event -> {
    		
    		try {
    			LoginViewController loginViewController=LoginViewController.ottieniIstanza();
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
                loader.setController(loginViewController);
    			Parent root = loader.load();
                loginViewController.setPrimaryStage(primaryStage);
                Scene nuovaScena = new Scene(root);
                primaryStage.setScene(nuovaScena);
                primaryStage.show();
        		
        	} catch (Exception e) {
        		e.printStackTrace();
        		e.getCause();
        	}
    	
    	});
    
    		pause.play();
    }

	private int usernameEsistente(String username) {  //METODO CHE CONTROLLA SE L'USERNAME E' ESISTENTE
		return controller.controllaUsername(username);
	}
	
	public void setPrimaryStage(Stage primaryStage) {  //PASSO LO STAGE
		this.primaryStage=primaryStage;
	}
	
}