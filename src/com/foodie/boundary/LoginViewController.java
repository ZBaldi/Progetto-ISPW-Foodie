package com.foodie.boundary;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.foodie.boundary2.AggiungiAlimentoView2Controller;
import com.foodie.boundary2.AreaPersonaleView2Controller;
import com.foodie.boundary2.ModeratoreView2Controller;
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
	@FXML
    private RadioButton interfaccia1RadioButton;
    @FXML
    private RadioButton interfaccia2RadioButton;
    private ToggleGroup userTypeToggleGroup;
    
    private int interfaccia;
	private Stage primaryStage;
	private AreaPersonaleViewController controllerAreaPersonale = AreaPersonaleViewController.ottieniIstanza();
	private LoginController controller = LoginController.ottieniIstanza();
	private DispensaUtenteViewController controllerDispensa = DispensaUtenteViewController.ottieniIstanza();
	private ModeratoreViewController controllerModeratore= ModeratoreViewController.ottieniIstanza();
	private ModeratoreView2Controller controllerModeratore2= ModeratoreView2Controller.ottieniIstanza();
	@FXML
    private void initialize() {
        chiudiLabel.setOnMouseClicked(event -> closeApplication());
     
        // Creazione del Toggle Group
    	userTypeToggleGroup = new ToggleGroup();
        
    	// Associazione dei Radio Button al Toggle Group
    	interfaccia1RadioButton.setToggleGroup(userTypeToggleGroup);
    	interfaccia2RadioButton.setToggleGroup(userTypeToggleGroup);
        
        // Imposto primo bottone come quello selezionato di default
        userTypeToggleGroup.selectToggle(interfaccia1RadioButton);
        interfaccia = 1;
    }
	
//	@FXML
//    public void scegliInterfaccia() {
//        RadioButton selectedRadioButton = (RadioButton) userTypeToggleGroup.getSelectedToggle();
//        if(selectedRadioButton == interfaccia1RadioButton) {
//    		interfaccia = 1;
//    	} else if (selectedRadioButton == interfaccia2RadioButton) {
//    		interfaccia = 2;
//    	}
//	}
	
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
			controller.setUtente(username.toLowerCase(), ruolo); //METTO USERNAME IN MINUSCOLE PER EVITARE INCONGRUENZE NEL CARICAMENTO DELLA DISPENSA O AREA PERSONALE
			if(interfaccia1RadioButton.isSelected()) {
				interfaccia = 1;
			} else {
				interfaccia = 2;
			}
			FXMLLoader loader = new FXMLLoader(getClass().getResource(controller.ottieniView(interfaccia)));
			if(ruolo.equals("Standard") && interfaccia == 1) {
				controllerDispensa.setUsername(username);
				loader.setController(controllerDispensa);
				Parent root;
				try {
					root = loader.load();
					controllerDispensa.setPrimaryStage(primaryStage);
					Dispensa dispensa = Dispensa.ottieniIstanza(); //SICURAMENTE L'OSSERVATORE SI AGGIUNGERA NEL CLIENT
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
			else if (ruolo.equals("Standard") && interfaccia == 2) {
				AggiungiAlimentoView2Controller controllerAlimenti = AggiungiAlimentoView2Controller.ottieniIstanza();
				loader.setController(controllerAlimenti);
				Parent root;
				try {
					root = loader.load();
					controllerAlimenti.setPrimaryStage(primaryStage);
					Dispensa dispensa=Dispensa.ottieniIstanza(); //SICURAMENTE L'OSSERVATORE SI AGGIUNGERA NEL CLIENT
					dispensa.svuotaDispensa();
					//dispensa.registra(controllerDispensa);//QUESTO
					controller.caricaDispense();  //Carico dispensa se esiste
					Scene scene = new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else if(ruolo.equals("Chef") && interfaccia == 1){
				loader.setController(controllerAreaPersonale);
				Parent root;
				
				try {
					root = loader.load();
					controllerAreaPersonale.setPrimaryStage(primaryStage);
					controllerAreaPersonale.caricaAreaPersonale();
					controllerAreaPersonale.aggiornaView();
					Scene scene = new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.show();
					
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else if(ruolo.equals("Chef") && interfaccia == 2) {
				AreaPersonaleView2Controller areaPersonaleController = AreaPersonaleView2Controller.ottieniIstanza();
				loader.setController(areaPersonaleController);
				Parent root;
				
				try {
					root = loader.load();
					areaPersonaleController.setPrimaryStage(primaryStage);
					areaPersonaleController.caricaAreaPersonale();
					areaPersonaleController.aggiornaView();
					Scene scene = new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.show();
					
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else {
				if(interfaccia==1) {
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
				else {
					loader.setController(controllerModeratore2);
					Parent root;
					try {
						root = loader.load();
						controllerModeratore2.setPrimaryStage(primaryStage);
						Moderatore moderatore= Moderatore.ottieniIstanza();
						moderatore.registra(controllerModeratore2);
						controllerModeratore2.aggiornaView();
						Scene scene= new Scene(root);
						primaryStage.setScene(scene);
						primaryStage.show();
					} catch (IOException e) {
						e.printStackTrace();
					}
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