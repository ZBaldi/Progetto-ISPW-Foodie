package com.foodie.boundary2;

import java.util.Map;

import com.foodie.boundary.AreaPersonaleViewController;
import com.foodie.boundary.GestisciRicetteViewController;
import com.foodie.boundary.LoginViewController;
import com.foodie.boundary.NuovaRicettaViewController;
import com.foodie.controller.ControllerAdapter;
import com.foodie.controller.LoginController;
import com.foodie.controller.LoginControllerAdapter;
import com.foodie.controller.PubblicaRicettaController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AreaPersonaleView2Controller {
	
	private static AreaPersonaleView2Controller istanza;
	private Stage primaryStage;
	private PubblicaRicettaController controller2 = PubblicaRicettaController.ottieniIstanza();
	LoginController controller = LoginController.ottieniIstanza();
	private ControllerAdapter adattatoreLoginController= LoginControllerAdapter.ottieniIstanza(controller);
	
	@FXML
	private TextArea descrizioneTextArea;
	@FXML
	private Label usernameLabel;
	
	private AreaPersonaleView2Controller() {
		
	}
	
	public static AreaPersonaleView2Controller ottieniIstanza() { //SINGLETON
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
           // gestisciRicetteViewController.aggiornaView();
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
		usernameLabel.setText(controller.getUtente().getUsername());
	}
	
	/*
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
	
	private void salvaAreaPersonale() {
		controller.salvaAreaPersonale(controller.getUtente().getUsername(), descrizioneTextArea.getText());
	}
	*/
	
	public void caricaAreaPersonale() {
		Map<String,String> areaPersonaleMap=controller.caricaAreaPersonale();
		String descrizione="";
		if(areaPersonaleMap!=null) {
			descrizione= areaPersonaleMap.get(controller.getUtente().getUsername());
		}
		descrizioneTextArea.setText(descrizione);
	}
	
	
	
	
	
}
