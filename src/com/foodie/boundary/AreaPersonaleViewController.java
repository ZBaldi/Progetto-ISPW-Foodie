package com.foodie.boundary;

import java.util.Map;
import com.foodie.controller.LoginController;
import com.foodie.controller.PubblicaRicettaController;
import com.foodie.model.UtenteBean;
import com.foodie.applicazione.LoginViewController;
import com.foodie.controller.AdattatoreFactory;
import com.foodie.controller.ControllerAdapter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AreaPersonaleViewController{
	
	private static AreaPersonaleViewController istanza;
	private AdattatoreFactory factory = AdattatoreFactory.ottieniIstanza();
	private LoginController controller = LoginController.ottieniIstanza();
	private ControllerAdapter adattatoreLoginController = factory.creaLoginAdapter();
	private Stage primaryStage;
	@FXML
    private ImageView tornaAlLoginImageView;
	@FXML
	private TextField descrizioneTextField;
	@FXML
	private Label usernameLabel;
	
	private AreaPersonaleViewController() {	
	}
	
	public static AreaPersonaleViewController ottieniIstanza() { //SINGLETON	
		if(istanza == null) {
			istanza = new AreaPersonaleViewController();
		}
		return istanza;
	}

	public void setPrimaryStage(Stage primaryStage) {  //PASSO LO STAGE
		this.primaryStage= primaryStage;
	}
	
	@FXML
    private void tornaAlLogin(MouseEvent event) { //CARICA VIEW LOGIN
        try { 
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/foodie/Applicazione/LoginView.fxml"));
            LoginViewController loginViewController = LoginViewController.ottieniIstanza();
            loader.setController(loginViewController);
            Parent root = loader.load();
            loginViewController.setPrimaryStage(primaryStage);
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
	
	@FXML
    private void caricaViewGestisciRicette(ActionEvent event) {  //CARICA VIEW GESTISCI RICETTE
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("GestisciRicetteView.fxml"));
            GestisciRicetteViewController gestisciRicetteViewController = GestisciRicetteViewController.ottieniIstanza();
            loader.setController(gestisciRicetteViewController);
            Parent root = loader.load();
            gestisciRicetteViewController.setPrimaryStage(primaryStage);
            gestisciRicetteViewController.aggiornaView();
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
	
	@FXML
	private void modificaProfilo(ActionEvent event) {  //PULSANTE MODIFICA
		if(!descrizioneTextField.isEditable()) {
			descrizioneTextField.setEditable(true);
		}
		else {
			descrizioneTextField.setEditable(false);  //QUANDO RIPREMUTO SALVA L'AREA PERSONALE
			salvaAreaPersonale();
		}
	}
	
	@FXML
	private void caricaViewRicetta(ActionEvent event) {  //CARICA VIEW DELLA RICETTA NUOVA DA CREARE
		PubblicaRicettaController.creaRicetta(); //QUANDO ENTRO NELLA RICETTA CREO L'ISTANZA DELLA RICETTA
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NuovaRicettaView.fxml"));
            NuovaRicettaViewController nuovaRicettaViewController= NuovaRicettaViewController.ottieniIstanza();
            loader.setController(nuovaRicettaViewController);
            Parent root = loader.load();
            nuovaRicettaViewController.setPrimaryStage(primaryStage);
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
	}
	
	public void aggiornaView() {  //AGGIORNA LABEL CON IL PROPRIO USERNAME
		UtenteBean utenteBean=adattatoreLoginController.ottieniUtente();
		usernameLabel.setText(utenteBean.getUsername());
	}
	
	private void salvaAreaPersonale() {  //SALVA L'AREA PERSONALE(DESCRIZIONE)
		UtenteBean utenteBean=adattatoreLoginController.ottieniUtente();
		controller.salvaAreaPersonale(utenteBean.getUsername(), descrizioneTextField.getText());
	}
	
	public void caricaAreaPersonale() { //CARICA L'AREA PERSONALE(DESCRIZIONE)
		UtenteBean utenteBean=adattatoreLoginController.ottieniUtente();
		Map<String,String> areaPersonaleMap=controller.caricaAreaPersonale();
		String descrizione="";
		if(!areaPersonaleMap.isEmpty()) {
			descrizione= areaPersonaleMap.get(utenteBean.getUsername());
		}
		descrizioneTextField.setText(descrizione);
	}
	
}