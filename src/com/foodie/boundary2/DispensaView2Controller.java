package com.foodie.boundary2;

import java.util.ArrayList;

import com.foodie.Applicazione.LoginViewController;
import com.foodie.controller.AdattatoreFactory;
import com.foodie.controller.ControllerAdapter;
import com.foodie.controller.LoginController;
import com.foodie.controller.TrovaRicettaController;
import com.foodie.model.AlimentoBean;
import com.foodie.model.Observer;
import com.foodie.model.UtenteBean;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class DispensaView2Controller implements Observer{
	
	private static DispensaView2Controller istanza;
	private AdattatoreFactory factory= AdattatoreFactory.ottieniIstanza();
	private TrovaRicettaController controller = TrovaRicettaController.ottieniIstanza();
	private ControllerAdapter adattatoreLogin= factory.creaLoginAdapter();
	private LoginController loginController= LoginController.ottieniIstanza();
	private ControllerAdapter adattatoreTrovaRicettaController= factory.creaTrovaRicettaAdapter();
	private UtenteBean utenteBean = adattatoreLogin.ottieniUtente();
	private String username=utenteBean.getUsername();
	private Stage primaryStage;
	@FXML
	private VBox contenitoreDispensa;
	
	private DispensaView2Controller() {	
	}
	
	public static synchronized DispensaView2Controller ottieniIstanza() { //SINGLETON  METODO PER OTTENERE L'ISTANZA
		if(istanza == null) {
			istanza = new DispensaView2Controller();
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
    private void caricaViewAlimenti(ActionEvent event) {  //CARICA VIEW TROV ALIMENTI
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("AggiungiAlimentoView2.fxml"));
        	AggiungiAlimentoView2Controller aggiungiAlimentoController = AggiungiAlimentoView2Controller.ottieniIstanza();
        	loader.setController(aggiungiAlimentoController);
            Parent root = loader.load();
            aggiungiAlimentoController.setPrimaryStage(primaryStage);
            Scene nuovaScena = new Scene(root);
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
	private void svuotaDispensa(ActionEvent event) {  //SVUOTA DISPENSA
		controller.svuotaDispensa();
		loginController.salvaDispensa(username); //SALVO DISPENSA SU FILE IN AUTOMATICO
	}
	
	public void aggiornaView() {  //AGGIORNA LA VIEW IN FUNZIONE DELLA DISPENSA
		contenitoreDispensa.getChildren().clear();
		ArrayList<AlimentoBean> alimentiBeanDispensa =adattatoreTrovaRicettaController.mostraLaDispensa();
		if(alimentiBeanDispensa!=null) {
			for(AlimentoBean a: alimentiBeanDispensa) {
				Label labelAlimento = new Label(a.getNome());
				labelAlimento.setStyle("-fx-background-color: white;");
				labelAlimento.setMaxWidth(Double.MAX_VALUE);
				labelAlimento.setMinHeight(50);
				labelAlimento.setWrapText(true);
				labelAlimento.setFont(Font.font("Arial",20));
				labelAlimento.setAlignment(Pos.CENTER);
				contenitoreDispensa.getChildren().add(labelAlimento);
			}
			impostaLabel();
		}
	}
	
	private void impostaLabel() {  //IMPOSTA LABEL DISPENSA CLICCABILI
			if(!contenitoreDispensa.getChildren().isEmpty()) {
				contenitoreDispensa.getChildren().forEach(node->{
					Label labelAlimento= (Label)node;
					labelAlimento.setOnMouseClicked(event->{eliminaAlimento(labelAlimento.getText());});
				});
			}
		}
	
	private void eliminaAlimento(String nomeAlimento) {  //ELIMINA ALIMENTO DALLA DISPENSA
		AlimentoBean alimentoBean = new AlimentoBean();
		alimentoBean.setNome(nomeAlimento);
		adattatoreTrovaRicettaController.ModificaDispensa(alimentoBean, 1);
		loginController.salvaDispensa(username); //SALVO DISPENSA SU FILE IN AUTOMATICO
	}
	
}