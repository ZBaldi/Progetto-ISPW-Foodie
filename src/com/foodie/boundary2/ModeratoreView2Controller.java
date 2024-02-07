package com.foodie.boundary2;

import java.util.ArrayList;
import com.foodie.Applicazione.LoginViewController;
import com.foodie.controller.AdattatoreFactory;
import com.foodie.controller.ControllerAdapter;
import com.foodie.controller.PubblicaRicettaController;
import com.foodie.model.Observer;
import com.foodie.model.RicettaBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ModeratoreView2Controller implements Observer{
	
	private AdattatoreFactory factory= AdattatoreFactory.ottieniIstanza();
	private PubblicaRicettaController controller= PubblicaRicettaController.ottieniIstanza();
	private ControllerAdapter adattatorePubblicaRicettaController= factory.creaPubblicaRicettaAdapter();
	private static ModeratoreView2Controller istanza;
	private Stage primaryStage;
	private static final String FORMATO = "Arial";
	@FXML
	private TextArea descrizioneTextArea;
	@FXML
	private Label usernameLabel;
	@FXML
	private VBox contenitoreRicetteDaApprovare;
	
	private ModeratoreView2Controller() {
	}
	
	public static synchronized ModeratoreView2Controller ottieniIstanza() {  //SINGLETON METODO PER OTTENERE L'ISTANZA
		if(istanza == null) {
			istanza = new ModeratoreView2Controller();
		}
		return istanza;
	}
	
	public void setPrimaryStage(Stage primaryStage) {  //PASSO LO STAGE
		this.primaryStage= primaryStage;
	}
	
	@FXML
	public void tornaAlLogin(MouseEvent event) {  //CARICA VIEW LOGIN
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/foodie/Applicazione/LoginView.fxml"));
            LoginViewController loginViewController = LoginViewController.ottieniIstanza();
            loader.setController(loginViewController);
            Parent root = loader.load();
            loginViewController.setPrimaryStage(primaryStage);
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();;
        } catch (Exception e) {
            e.printStackTrace(); 
        }
	}

	@Override
	public void aggiornaView() {  //AGGIORNA VIEW IN FUZNIONE DELLE RICETTE DA APPROVARE , LE MOSTRA SUBITO COMPLETE 
		contenitoreRicetteDaApprovare.getChildren().clear();
		ArrayList<RicettaBean> ricetteBean =adattatorePubblicaRicettaController.mostraLeRicetteDaApprovare();
		if(ricetteBean!=null) {
			for(RicettaBean r: ricetteBean) {
				VBox contenitoreRicetta = new VBox();
				contenitoreRicetta.setStyle("-fx-background-color: rgba(217, 217, 217, 0.75);-fx-border-color: black;");
				contenitoreRicetta.setSpacing(10);
				
			    Label labelNome = new Label(r.getNome());
			    labelNome.setFont(Font.font(FORMATO, 20));

			    Label labelAutore = new Label(r.getAutore());
			    labelAutore.setFont(Font.font(FORMATO, 20));			    
			 
			    Label labelDescrizione = new Label("Descrizione: "+r.getDescrizione());
			    labelDescrizione.setFont(Font.font(FORMATO, 20));
			    labelDescrizione.setWrapText(true);
			    
			    contenitoreRicetta.getChildren().addAll(labelNome,labelAutore,labelDescrizione);
			    contenitoreRicetteDaApprovare.getChildren().add(contenitoreRicetta);
			}
		}
		impostaVBox();  //LE RENDE CLICCABILI
	}
	
	private void impostaVBox() {  //RENDE CLICCABILI LE RICETTE PER APPROVARLE
		 if (!contenitoreRicetteDaApprovare.getChildren().isEmpty()) {
			 contenitoreRicetteDaApprovare.getChildren().forEach(node -> {
		            VBox contenitoreRicetta = (VBox) node;
		            contenitoreRicetta.setOnMouseClicked(event -> pubblicaRicetta(contenitoreRicetta));
		        });
		    }
	}
	
	private void pubblicaRicetta(VBox contenitoreRicetta) {  //PUBBLICA LA RICETTA
		if(!contenitoreRicetteDaApprovare.getChildren().isEmpty()) {
			contenitoreRicetteDaApprovare.getChildren().clear();
		}
		String nome="";
		String autore="";
		int index=1;
		for(Node nodo: contenitoreRicetta.getChildren()) {
			if(index==1) {
				nome=((Label)nodo).getText();
			}
			else if(index==2) {
				autore=((Label)nodo).getText();
			}
			else {
				break;
			}
			index++;
		}
		controller.pubblicaRicetta(nome,autore,true); 
	}
	
	@FXML
	private void scarta(ActionEvent event) {  //SE CLICCATO CHIUDI SCARTA TUTTE LE RICETTE
		if(!contenitoreRicetteDaApprovare.getChildren().isEmpty()) {
			contenitoreRicetteDaApprovare.getChildren().clear();
		}
		String nome="";
		String autore="";
		for(Node nodo: contenitoreRicetteDaApprovare.getChildren()) {
			int index=1;
			VBox contenitoreRicetta = (VBox) nodo;
			for(Node nodo2: contenitoreRicetta.getChildren()) {
				if(index==1) {
					nome=((Label)nodo2).getText();
				}
				else if(index==2) {
					autore=((Label)nodo2).getText();
				}
				else {
					break;
				}
				index++;
			}
			controller.pubblicaRicetta(nome,autore,false);
		}
	}
	
}