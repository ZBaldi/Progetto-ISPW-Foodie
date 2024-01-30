package com.foodie.boundary;

import java.util.Map;

import com.foodie.controller.LoginController;
import com.foodie.controller.PubblicaRicettaController;
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
	private PubblicaRicettaController controller2=PubblicaRicettaController.ottieniIstanza();
	LoginController controller = new LoginController();
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
            
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
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
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("GestisciRicetteView.fxml"));
            Parent root = loader.load();
            GestisciRicetteViewController gestisciRicetteViewController = loader.getController();
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
		usernameLabel.setText(controller.getUtente().getUsername());
	}
	private void salvaAreaPersonale() {
		controller.salvaAreaPersonale(controller.getUtente().getUsername(), descrizioneTextField.getText());
	}
	public void caricaAreaPersonale() {
		Map<String,String> areaPersonaleMap=controller.caricaAreaPersonale();
		String descrizione="";
		if(areaPersonaleMap!=null) {
			descrizione= areaPersonaleMap.get(controller.getUtente().getUsername());
		}
		descrizioneTextField.setText(descrizione);
	}
}