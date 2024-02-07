package com.foodie.boundary;

import java.util.List;

import com.foodie.controller.PubblicaRicettaController;
import com.foodie.model.Observer;
import com.foodie.model.RicettaBean;
import com.foodie.applicazione.LoginViewController;
import com.foodie.controller.AdattatoreFactory;
import com.foodie.controller.ControllerAdapter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ModeratoreViewController implements Observer{
	
	private static ModeratoreViewController istanza;
	private AdattatoreFactory factory = AdattatoreFactory.ottieniIstanza();
	private PubblicaRicettaController controller = PubblicaRicettaController.ottieniIstanza();
	private ControllerAdapter adattatorePubblicaRicettaController = factory.creaPubblicaRicettaAdapter();
	private Stage primaryStage;
	private static final String FORMATO = "Arial";
	private static final String SFONDOBIANCO = "-fx-background-color: white;";
	@FXML
	private VBox contenitoreRicetteDaApprovare;
	@FXML
	private VBox contenitoreContenutoRicetta;
	
	private ModeratoreViewController() {
	}
	
	public static synchronized ModeratoreViewController ottieniIstanza() {
		if(istanza == null) {
			istanza = new ModeratoreViewController();
		}
		return istanza;
	}
	
	@Override
	public void aggiornaView() {  //AGGIORNA VIEW IN BASE ALLE RICHIESTE DA APPROVARE DEL MODERATORE
		contenitoreRicetteDaApprovare.getChildren().clear();
		contenitoreContenutoRicetta.getChildren().clear();
		List<RicettaBean> ricetteBean =adattatorePubblicaRicettaController.mostraLeRicetteDaApprovare();
		if(!ricetteBean.isEmpty()) {
			for(RicettaBean r: ricetteBean) {
				Label labelRicetta = new Label(r.getNome());
				labelRicetta.setStyle(SFONDOBIANCO);
				labelRicetta.setMaxWidth(Double.MAX_VALUE);
				labelRicetta.setMinHeight(50);
				labelRicetta.setWrapText(true);
				labelRicetta.setFont(Font.font(FORMATO,20));
				labelRicetta.setAlignment(Pos.CENTER);
				labelRicetta.setOnMouseClicked(event->apriContenuto(r));  //RENDE CLICCABILI PER APRIRE IL CONTENUTO
				contenitoreRicetteDaApprovare.getChildren().add(labelRicetta);
			}
		}
	}
	
	private void apriContenuto(RicettaBean ricettaBean) {  //APRE IL COTNENUTO DELLA RICETTA CLICCATA
		contenitoreContenutoRicetta.getChildren().clear();
		Label labelNome= new Label(ricettaBean.getNome());
		labelNome.setStyle(SFONDOBIANCO);
		labelNome.setMaxWidth(Double.MAX_VALUE);
		labelNome.setMinHeight(50);
		labelNome.setWrapText(true);
		labelNome.setFont(Font.font(FORMATO,20));
		Label labelAutore= new Label(ricettaBean.getAutore());
		labelAutore.setStyle(SFONDOBIANCO);
		labelAutore.setMaxWidth(Double.MAX_VALUE);
		labelAutore.setMinHeight(50);
		labelAutore.setWrapText(true);
		labelAutore.setFont(Font.font(FORMATO,20));
		Label labelDescrizione= new Label(ricettaBean.getDescrizione());
		labelDescrizione.setStyle(SFONDOBIANCO);
		labelDescrizione.setMaxWidth(Double.MAX_VALUE);
		labelDescrizione.setMinHeight(200);
		labelDescrizione.setWrapText(true);
		labelDescrizione.setFont(Font.font(FORMATO,15));
		contenitoreContenutoRicetta.getChildren().addAll(labelNome,labelAutore,labelDescrizione);
	}
	
	@FXML
	private void pubblica(ActionEvent event) {  //PUBBLICA LA RICETTA 
		if(!contenitoreContenutoRicetta.getChildren().isEmpty()) {
			int indiceLabel=1;
			String nome="";
			String autore="";
			for (Node labelNode : contenitoreContenutoRicetta.getChildren()) {	        
                Label label = (Label) labelNode;
                if(indiceLabel==1)
                	nome=label.getText();
                else if(indiceLabel==2)
                	autore=label.getText();
                indiceLabel++;
			}
			controller.pubblicaRicetta(nome,autore,true);
		}
	}
	
	@FXML
	private void scarta(ActionEvent event) {  //SCARTA LA RICETTA
		if(!contenitoreContenutoRicetta.getChildren().isEmpty()) {
			int indiceLabel=1;
			String nome="";
			String autore="";
			for (Node labelNode : contenitoreContenutoRicetta.getChildren()) {	        
                Label label = (Label) labelNode;
                if(indiceLabel==1)
                	nome=label.getText();
                else if(indiceLabel==2)
                	autore=label.getText();
                indiceLabel++;
			}
			controller.pubblicaRicetta(nome,autore,false);
		}
	}
	
	public void setPrimaryStage(Stage primaryStage) {  //PASSA LO STAGE
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
	
}