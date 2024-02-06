package com.foodie.boundary;

import java.util.Map;

import com.foodie.controller.LoginController;
import com.foodie.controller.LoginControllerAdapter;
import com.foodie.controller.PubblicaRicettaController;
import com.foodie.model.UtenteBean;
import com.foodie.Applicazione.LoginViewController;
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
	private Stage primaryStage;
	private PubblicaRicettaController controller2 = PubblicaRicettaController.ottieniIstanza();
	private LoginController controller = LoginController.ottieniIstanza();
	private AdattatoreFactory factory = AdattatoreFactory.ottieniIstanza();
	private ControllerAdapter adattatoreLoginController = factory.creaLoginAdapter();
	
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

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage= primaryStage;
	}
	
	@FXML
    private void tornaAlLogin(MouseEvent event) {
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
    private void caricaViewGestisciRicette(ActionEvent event) {
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
	private void modificaProfilo(ActionEvent event) {
		if(!descrizioneTextField.isEditable()) {
			descrizioneTextField.setEditable(true);
		}
		else {
			descrizioneTextField.setEditable(false);
			salvaAreaPersonale();
		}
	}
	@FXML
	private void caricaViewRicetta(ActionEvent event) {
		controller2.creaRicetta(); //QUANDO ENTRO NELLA RICETTA CREO L'ISTANZA DELLA RICETTA
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
	public void aggiornaView() {
		UtenteBean utenteBean=adattatoreLoginController.ottieniUtente();
		usernameLabel.setText(utenteBean.getUsername());
	}
	private void salvaAreaPersonale() {
		UtenteBean utenteBean=adattatoreLoginController.ottieniUtente();
		controller.salvaAreaPersonale(utenteBean.getUsername(), descrizioneTextField.getText());
	}
	public void caricaAreaPersonale() {
		UtenteBean utenteBean=adattatoreLoginController.ottieniUtente();
		Map<String,String> areaPersonaleMap=controller.caricaAreaPersonale();
		String descrizione="";
		if(areaPersonaleMap!=null) {
			descrizione= areaPersonaleMap.get(utenteBean.getUsername());
		}
		descrizioneTextField.setText(descrizione);
	}
}