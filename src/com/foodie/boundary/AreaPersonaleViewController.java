package com.foodie.boundary;

import java.util.Map;
import com.foodie.controller.LoginController;
import com.foodie.model.UtenteBean;
import com.foodie.controller.AdattatoreFactory;
import com.foodie.controller.ControllerAdapter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AreaPersonaleViewController{
	
	private static AreaPersonaleViewController istanza;
	private AdattatoreFactory factory = AdattatoreFactory.ottieniIstanza();
	private LoginController controller = LoginController.ottieniIstanza();
	private ControllerAdapter adattatoreLoginController = factory.creaLoginAdapter();
	private CaricaView caricaView= CaricaView.ottieniIstanza();
	private Stage primaryStage;
	@FXML
    private ImageView tornaAlLoginImageView;
	@FXML
	private TextArea descrizioneTextField;
	@FXML
	private Label usernameLabel;
	
	private AreaPersonaleViewController() {	
	}
	
	public static synchronized AreaPersonaleViewController ottieniIstanza() { //SINGLETON	
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
        caricaView.tornaAlLogin(primaryStage);
    }
	
	@FXML
    private void caricaViewGestisciRicette(ActionEvent event) {  //CARICA VIEW GESTISCI RICETTE
        caricaView.caricaViewGestisciRicette(primaryStage);
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
		caricaView.caricaViewRicetta(primaryStage);
	}
	
	public void aggiornaView() {  //AGGIORNA LABEL CON IL PROPRIO USERNAME
		UtenteBean utenteBean=adattatoreLoginController.ottieniUtente();
		usernameLabel.setText(utenteBean.getUsername());
	}
	
	private void salvaAreaPersonale() {  //SALVA L'AREA PERSONALE(DESCRIZIONE)
		controller.salvaAreaPersonale(descrizioneTextField.getText());
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