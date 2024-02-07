package com.foodie.boundary2;

import java.util.List;

import com.foodie.applicazione.LoginViewController;
import com.foodie.controller.AdattatoreFactory;
import com.foodie.controller.ControllerAdapter;
import com.foodie.controller.LoginController;
import com.foodie.controller.PubblicaRicettaController;
import com.foodie.model.AlimentoBean;
import com.foodie.model.UtenteBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AggiungiAlimentoView2Controller {
	
	private static AggiungiAlimentoView2Controller istanza;
	private AdattatoreFactory factory = AdattatoreFactory.ottieniIstanza();
	private ControllerAdapter adattatoreTrovaRicettaController = factory.creaTrovaRicettaAdapter();
	private ControllerAdapter adattatoreLoginController = factory.creaLoginAdapter();
	private PubblicaRicettaController controller= PubblicaRicettaController.ottieniIstanza();
	private LoginController loginController = LoginController.ottieniIstanza();
	private UtenteBean utenteBean = adattatoreLoginController.ottieniUtente();
	private String username = utenteBean.getUsername();
	private Stage primaryStage;
	@FXML
	private TextField barraDiRicerca;
	@FXML
	private VBox contenitoreAlimentiTrovati;
	
	private AggiungiAlimentoView2Controller() {
	}
	
	public static synchronized AggiungiAlimentoView2Controller ottieniIstanza() { //METODO PER OTTENERE L'ISTANZA
		if(istanza == null) {
			istanza = new AggiungiAlimentoView2Controller();
		}
		return istanza;
	}	
	
	public void setPrimaryStage(Stage primaryStage) {  //PASSO LO STAGE
		this.primaryStage= primaryStage;
	}
	
	@FXML
    private void tornaAlLogin(MouseEvent event) {  //CARICA VIEW LOGIN
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
	private void caricaViewDispensa(ActionEvent event) {  //CARICA VIEW DISPENSA
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DispensaView2.fxml"));
            DispensaView2Controller dispensaController = DispensaView2Controller.ottieniIstanza();
            loader.setController(dispensaController);
            controller.registraOsservatore(dispensaController, 1);
            Parent root = loader.load();
            dispensaController.setPrimaryStage(primaryStage);
            Scene nuovaScena = new Scene(root);
            dispensaController.aggiornaView();
            primaryStage.setScene(nuovaScena);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
	}
	
	@FXML
    private void caricaViewTrovaRicetta(ActionEvent event) {  //CARICA VIEW TROVA RICETTA
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("TrovaRicettaView2.fxml"));
        	TrovaRicettaView2Controller trovaRicettaController = TrovaRicettaView2Controller.ottieniIstanza();
        	loader.setController(trovaRicettaController);
            Parent root = loader.load();
            trovaRicettaController.setPrimaryStage(primaryStage);
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
	
	@FXML
	private void gestioneRisultati(KeyEvent event) {  //GESTISCE BARRA DI RICERCA
		if(event.getCode() == KeyCode.ENTER) {//GETCODE() TI RESTITUISCE IL TASTO PREMUTO
			trovaAlimenti();
		}
		else if(event.getCode() == KeyCode.BACK_SPACE) {
			eliminaAlimenti();
		}
	}
	
	private void trovaAlimenti() {  //METODO TROVA ALIMENTI
		List<AlimentoBean> alimentiBeanTrovati=adattatoreTrovaRicettaController.trovaGliAlimenti(barraDiRicerca.getText());
		if(!alimentiBeanTrovati.isEmpty()) {
			for(AlimentoBean a: alimentiBeanTrovati) {
				Label labelAlimento = new Label(a.getNome());
				labelAlimento.setStyle("-fx-background-color: white;");
				labelAlimento.setMaxWidth(Double.MAX_VALUE);
				labelAlimento.setMinHeight(30);
				labelAlimento.setWrapText(true);
				labelAlimento.setFont(Font.font("Arial"));
				labelAlimento.setAlignment(Pos.CENTER);  //LI RENDE CLICCABILI
				labelAlimento.setOnMouseClicked(event2->{salvaAlimento(labelAlimento.getText());eliminaAlimenti();});
				contenitoreAlimentiTrovati.getChildren().add(labelAlimento);
			}
		}
		else {
			Label label = new Label("NESSUN RISULTATO");
			label.setStyle("-fx-background-color: white;");
			label.setMaxWidth(Double.MAX_VALUE);
			label.setMinHeight(30);
			label.setWrapText(true);
			label.setFont(Font.font("Arial"));
			label.setAlignment(Pos.CENTER);
			contenitoreAlimentiTrovati.getChildren().add(label);
		}
		
	}
	
	private void salvaAlimento(String nomeAlimento) {  //SALVA ALIMENTO
		AlimentoBean alimentoBean = new AlimentoBean();
		alimentoBean.setNome(nomeAlimento);
		adattatoreTrovaRicettaController.modificaDispensa(alimentoBean, 0);
		loginController.salvaDispensa(username); //SALVO DISPENSA SU FILE IN AUTOMATICO
		
	}
	
	private void eliminaAlimenti() {  //ELIMINA ALIMENTI TROVATI
		if(!contenitoreAlimentiTrovati.getChildren().isEmpty()) {
			contenitoreAlimentiTrovati.getChildren().clear();
		}
	}
	
}