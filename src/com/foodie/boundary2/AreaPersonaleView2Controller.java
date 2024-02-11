package com.foodie.boundary2;

import java.util.Map;

import com.foodie.controller.AdattatoreFactory;
import com.foodie.controller.ControllerAdapter;
import com.foodie.controller.LoginController;
import com.foodie.model.UtenteBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AreaPersonaleView2Controller {
	
	private static AreaPersonaleView2Controller istanza;
	private AdattatoreFactory factory = AdattatoreFactory.ottieniIstanza();
	private LoginController controller = LoginController.ottieniIstanza();
	private ControllerAdapter adattatoreLoginController= factory.creaLoginAdapter();
	private CaricaView2 caricaView2= CaricaView2.ottieniIstanza();
	private Stage primaryStage;
	@FXML
	private TextArea descrizioneTextArea;
	@FXML
	private Label usernameLabel;
	
	private AreaPersonaleView2Controller() {
		
	}
	
	public static synchronized AreaPersonaleView2Controller ottieniIstanza() { //SINGLETON METODO PER OTTENERE L'ISTANZA
		if(istanza == null) {
			istanza = new AreaPersonaleView2Controller();
		}
		return istanza;
	}

	public void setPrimaryStage(Stage primaryStage) {  //PASSO LO STAGE
		this.primaryStage= primaryStage;
	}
	
	@FXML
    private void tornaAlLogin(MouseEvent event) { //CARICA VIEW LOGIN
        caricaView2.tornaAlLogin(primaryStage);
    }
	
	@FXML
    private void caricaViewGestisciRicette(ActionEvent event) {  //CARICA VIEW GESTISCI RICETTE
        caricaView2.caricaViewGestisciRicette(primaryStage);
    }
	
	@FXML
	private void caricaViewRicetta(ActionEvent event) {  //CARICA VIEW RICETTA
		caricaView2.caricaViewRicetta(primaryStage);
	}
	
	public void aggiornaView() {  //AGGIORNA USERNAME AUTOMATICAMENTE
		UtenteBean utenteBean= adattatoreLoginController.ottieniUtente();
		usernameLabel.setText(utenteBean.getUsername());
	}

	@FXML
	private void salvaAreaPersonale(KeyEvent event) {  //PREMUTO ENTER SALVA AREA PERSONALE(DESCRIZIONE)
		if(event.getCode() == KeyCode.ENTER) {
			controller.salvaAreaPersonale(descrizioneTextArea.getText());
		}
	}
	
	public void caricaAreaPersonale() {  //CARICA AREA PERSONALE(DESCRIZIONE)
		Map<String,String> areaPersonaleMap=controller.caricaAreaPersonale();
		String descrizione="";
		if(areaPersonaleMap!=null) {
			UtenteBean utenteBean=adattatoreLoginController.ottieniUtente();
			descrizione= areaPersonaleMap.get(utenteBean.getUsername());
		}
		descrizioneTextArea.setText(descrizione);
	}
		
}