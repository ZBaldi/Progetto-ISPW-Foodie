package com.foodie.Applicazione;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import com.foodie.boundary.AreaPersonaleViewController;
import com.foodie.boundary.DispensaUtenteViewController;
import com.foodie.boundary.ModeratoreViewController;
import com.foodie.boundary2.AggiungiAlimentoView2Controller;
import com.foodie.boundary2.AreaPersonaleView2Controller;
import com.foodie.boundary2.ModeratoreView2Controller;
import com.foodie.controller.LoginController;
import com.foodie.controller.PubblicaRicettaController;
import com.foodie.model.Dispensa;

public class LoginViewController {
	
	private static LoginViewController istanza;  //SINGLETON
	private int interfaccia;
	private Stage primaryStage;
	private LoginController controller = LoginController.ottieniIstanza();
	private PubblicaRicettaController controller2 = PubblicaRicettaController.ottieniIstanza();
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
    @FXML
    private ToggleGroup userTypeToggleGroup;
	
    private LoginViewController() {
    }
    
    public static synchronized LoginViewController ottieniIstanza() { //METODO PER OTTENERE L'ISTANZA
		if(istanza==null) {
			istanza=new LoginViewController();
		}
		return istanza;
	}
    
	@FXML
    private void initialize() {  //METODO PER INIZIALIZZARE
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
	
    private void closeApplication() {  //CHIUDI L'APP
        Stage stage = (Stage)chiudiLabel.getScene().getWindow();
        stage.close();
    }
	
	@FXML
	public void loginButtonOnAction(ActionEvent event) {  //SE CLICCATO CHIAMO IL METODO
		
		if(usernameTextField.getText().isBlank() == false && enterPasswordField.getText().isBlank() == false) {
			validazioneLogin();
		} else {
			loginMessageLabel.setText("Inserisci username e password");
		}
		
	}
	
	@FXML
	public void registratiButtonOnAction(ActionEvent event) {  //SE CLICCATO CHIAMO IL METODO
		creaAccount();
	}
	
	public void validazioneLogin() {  //METODO PER FARE IL LOGIN E VIENE IMPOSTATO L'UTENTE CORRENTE
		String username=usernameTextField.getText();
		String password=enterPasswordField.getText();
		int tipo=controller.effettuaLogin(username, password);  //CONTROLLO SE CORRETTO
		String ruolo=""; //TIPO DI UTENTE
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
			LoginController.setUtente(username.toLowerCase(), ruolo); //METTO USERNAME IN MINUSCOLE PER EVITARE INCONGRUENZE NEL CARICAMENTO DELLA DISPENSA O AREA PERSONALE
			if(interfaccia1RadioButton.isSelected()) { //VEDI LA GUI DA AVVIARE
				interfaccia = 1;
			} else {
				interfaccia = 2;
			}
			FXMLLoader loader = new FXMLLoader(getClass().getResource(controller.ottieniView(interfaccia)));  // SFRUTTI POLIMORFISMO NEL CONTROLLER PER OTTENERE LA VIEW CORRETTA
			try {
	            Parent root=null;	            
	            switch (ruolo) { 
	                case "Standard":  //SE STANDARD CARICHI LE INTERFACCE RELATIVE PER L'UTENTE STANDARD
	                	Dispensa dispensa = Dispensa.ottieniIstanza();
	                	dispensa.svuotaDispensa();
	                    if (interfaccia == 1) {
	                    	DispensaUtenteViewController controllerDispensa = DispensaUtenteViewController.ottieniIstanza();
	                        loader.setController(controllerDispensa);
	                        controller2.registraOsservatore(controllerDispensa, 1);
	                        root = loader.load();
	                        controllerDispensa.setPrimaryStage(primaryStage);
	                    } else {
	                        AggiungiAlimentoView2Controller controllerAlimenti = AggiungiAlimentoView2Controller.ottieniIstanza();
	                        loader.setController(controllerAlimenti);
	                        root = loader.load();
	                        controllerAlimenti.setPrimaryStage(primaryStage);
	                    }
	                    controller.caricaDispense();
	                    break;
	                case "Chef": //SE CHEF CARICHI LE INTERFACCE RELATIVE PER L'UTENTE CHEF
	                    if (interfaccia == 1) {
	                    	AreaPersonaleViewController controllerAreaPersonale = AreaPersonaleViewController.ottieniIstanza();
	                        loader.setController(controllerAreaPersonale);
	                        root = loader.load();
	                        controllerAreaPersonale.setPrimaryStage(primaryStage);
	                        controllerAreaPersonale.caricaAreaPersonale();
	                        controllerAreaPersonale.aggiornaView();
	                    } else {
	                        AreaPersonaleView2Controller areaPersonaleController = AreaPersonaleView2Controller.ottieniIstanza();
	                        loader.setController(areaPersonaleController);
	                        root = loader.load();
	                        areaPersonaleController.setPrimaryStage(primaryStage);
	                        areaPersonaleController.caricaAreaPersonale();
	                        areaPersonaleController.aggiornaView();
	                    }
	                    break;
	                case "Moderatore": //SE MODERATORE CARICHI LE INTERFACCE RELATIVE PER L'UTENTE MODERATORE    
	                	if (interfaccia == 1) {
	                    	ModeratoreViewController controllerModeratore= ModeratoreViewController.ottieniIstanza();
	                        loader.setController(controllerModeratore);
	                        controller2.registraOsservatore(controllerModeratore,3);
	                        root = loader.load();
	                        controllerModeratore.setPrimaryStage(primaryStage);
	                        controllerModeratore.aggiornaView();
	                    } else {
	                    	ModeratoreView2Controller controllerModeratore2= ModeratoreView2Controller.ottieniIstanza();
	                        loader.setController(controllerModeratore2);
	                        controller2.registraOsservatore(controllerModeratore2,3);
	                        root = loader.load();
	                        controllerModeratore2.setPrimaryStage(primaryStage);
	                        controllerModeratore2.aggiornaView();
	                    }
	                    break;
	            }  
	            Scene scene = new Scene(root);
	            primaryStage.setScene(scene);
	            primaryStage.show();
	        }catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		else {  	//SE TIPO ==-1 ERRORE NEL LOGIN
			loginMessageLabel.setText("credenziali errate. Se non hai ancora un account, registrati.");
		}
	}
	
	public void creaAccount() {  //CARICA LA VIEW PER LA PAGINA DI REGISTRAZIONE
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistratiView.fxml"));
			RegistratiViewController controllerRegistrazione= RegistratiViewController.ottieniIstanza();
			loader.setController(controllerRegistrazione);
			Parent root = loader.load();
			controllerRegistrazione.setPrimaryStage(primaryStage);
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();
		
		} catch (Exception e) {
			e.printStackTrace();
			e.getCause();
		}
	}
	
	public void setPrimaryStage(Stage primaryStage) { //PASSO LO STAGE
		this.primaryStage=primaryStage;
	}
	
}