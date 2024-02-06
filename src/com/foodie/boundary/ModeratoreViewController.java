package com.foodie.boundary;

import java.util.ArrayList;

import com.foodie.controller.PubblicaRicettaController;
import com.foodie.controller.PubblicaRicettaControllerAdapter;
import com.foodie.model.AlimentoBean;
import com.foodie.model.Observer;
import com.foodie.model.RicettaBean;
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
	private PubblicaRicettaController controller= PubblicaRicettaController.ottieniIstanza();
	private ControllerAdapter adattatorePubblicaRicettaController= PubblicaRicettaControllerAdapter.ottieniIstanza(controller);
	private Stage primaryStage;
	private ArrayList<RicettaBean> ricetteBean=null;
	@FXML
	private VBox contenitoreRicetteDaApprovare;
	@FXML
	private VBox contenitoreContenutoRicetta;
	
	private ModeratoreViewController() {
	}
	
	public static ModeratoreViewController ottieniIstanza() {
		if(istanza == null) {
			istanza = new ModeratoreViewController();
		}
		return istanza;
	}
	@Override
	public void aggiornaView() {
		contenitoreRicetteDaApprovare.getChildren().clear();
		contenitoreContenutoRicetta.getChildren().clear();
		ricetteBean =adattatorePubblicaRicettaController.mostraLeRicetteDaApprovare();
		if(ricetteBean!=null) {
			for(RicettaBean r: ricetteBean) {
				Label labelRicetta = new Label(r.getNome());
				labelRicetta.setStyle("-fx-background-color: white;");
				labelRicetta.setMaxWidth(Double.MAX_VALUE);
				labelRicetta.setMinHeight(50);
				labelRicetta.setWrapText(true);
				labelRicetta.setFont(Font.font("Arial",20));
				labelRicetta.setAlignment(Pos.CENTER);
				labelRicetta.setOnMouseClicked(event->{apriContenuto(r);});
				contenitoreRicetteDaApprovare.getChildren().add(labelRicetta);
			}
		}
	}
	private void apriContenuto(RicettaBean ricettaBean) {
		contenitoreContenutoRicetta.getChildren().clear();
		Label labelNome= new Label(ricettaBean.getNome());
		labelNome.setStyle("-fx-background-color: white;");
		labelNome.setMaxWidth(Double.MAX_VALUE);
		labelNome.setMinHeight(50);
		labelNome.setWrapText(true);
		labelNome.setFont(Font.font("Arial",20));
		Label labelAutore= new Label(ricettaBean.getAutore());
		labelAutore.setStyle("-fx-background-color: white;");
		labelAutore.setMaxWidth(Double.MAX_VALUE);
		labelAutore.setMinHeight(50);
		labelAutore.setWrapText(true);
		labelAutore.setFont(Font.font("Arial",20));
		Label labelDescrizione= new Label(ricettaBean.getDescrizione());
		labelDescrizione.setStyle("-fx-background-color: white;");
		labelDescrizione.setMaxWidth(Double.MAX_VALUE);
		labelDescrizione.setMinHeight(200);
		labelDescrizione.setWrapText(true);
		labelDescrizione.setFont(Font.font("Arial",20));
		contenitoreContenutoRicetta.getChildren().addAll(labelNome,labelAutore,labelDescrizione);
	}
	@FXML
	private void pubblica(ActionEvent event) {
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
	private void scarta(ActionEvent event) {
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
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage= primaryStage;
	}
	@FXML
	private void caricaViewLogin(MouseEvent event) {
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
            Parent root = loader.load();
            LoginViewController loginViewController=loader.getController();  //RENDILO SINGLETON
            loginViewController.setPrimaryStage(primaryStage);
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
	}
}
