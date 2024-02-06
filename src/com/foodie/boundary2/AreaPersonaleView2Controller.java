package com.foodie.boundary2;

import java.util.Map;

import com.foodie.Applicazione.LoginViewController;
import com.foodie.controller.AdattatoreFactory;
import com.foodie.controller.ControllerAdapter;
import com.foodie.controller.LoginController;
import com.foodie.controller.PubblicaRicettaController;
import com.foodie.model.Ricetta;
import com.foodie.model.UtenteBean;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AreaPersonaleView2Controller {
	
	private static AreaPersonaleView2Controller istanza;
	private Stage primaryStage;
	private PubblicaRicettaController controller2 = PubblicaRicettaController.ottieniIstanza();
	LoginController controller = LoginController.ottieniIstanza();
	private AdattatoreFactory factory= AdattatoreFactory.ottieniIstanza();
	private ControllerAdapter adattatoreLoginController= factory.creaLoginAdapter();
	
	@FXML
	private TextArea descrizioneTextArea;
	@FXML
	private Label usernameLabel;
	
	private AreaPersonaleView2Controller() {
		
	}
	
	public static synchronized AreaPersonaleView2Controller ottieniIstanza() { //SINGLETON
		if(istanza == null) {
			istanza = new AreaPersonaleView2Controller();
		}
		return istanza;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage= primaryStage;
	}
	
	@FXML
    private void tornaAlLogin(MouseEvent event) {
        try {
            
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/foodie/boundary/LoginView.fxml"));
            Parent root = loader.load();
            LoginViewController loginViewController = loader.getController();
            loginViewController.setPrimaryStage(primaryStage);
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
	
	@FXML
    private void caricaViewGestisciRicette(ActionEvent event) {
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("GestisciRicetteView2.fxml"));
        	GestisciRicetteView2Controller gestisciRicetteController = GestisciRicetteView2Controller.ottieniIstanza();
        	loader.setController(gestisciRicetteController);
            Parent root = loader.load();
            gestisciRicetteController.setPrimaryStage(primaryStage);
            gestisciRicetteController.aggiornaView();
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
	
	@FXML
	private void caricaViewRicetta(ActionEvent event) {
		controller2.creaRicetta(); //QUANDO ENTRO NELLA RICETTA CREO L'ISTANZA DELLA RICETTA
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NuovaRicettaView2.fxml"));
            NuovaRicettaView2Controller nuovaRicettaController= NuovaRicettaView2Controller.ottieniIstanza();
            loader.setController(nuovaRicettaController);
            Ricetta ricetta =controller2.getRicetta();//NON SI DEVE FARE QUI
			ricetta.registra(nuovaRicettaController);
            Parent root = loader.load();
            nuovaRicettaController.setPrimaryStage(primaryStage);
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
	}
	
	public void aggiornaView() {
		UtenteBean utenteBean= adattatoreLoginController.ottieniUtente();
		usernameLabel.setText(utenteBean.getUsername());
	}

	@FXML
	private void salvaAreaPersonale(KeyEvent event) {
		if(event.getCode() == KeyCode.ENTER) {
			UtenteBean utenteBean=adattatoreLoginController.ottieniUtente();
			controller.salvaAreaPersonale(utenteBean.getUsername(), descrizioneTextArea.getText());
		}
	}
	
	public void caricaAreaPersonale() {
		Map<String,String> areaPersonaleMap=controller.caricaAreaPersonale();
		String descrizione="";
		if(areaPersonaleMap!=null) {
			UtenteBean utenteBean=adattatoreLoginController.ottieniUtente();
			descrizione= areaPersonaleMap.get(utenteBean.getUsername());
		}
		descrizioneTextArea.setText(descrizione);
	}
		
}