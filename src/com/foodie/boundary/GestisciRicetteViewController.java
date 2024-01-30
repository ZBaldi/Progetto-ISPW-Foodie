package com.foodie.boundary;

import java.util.ArrayList;

import com.foodie.controller.LoginController;
import com.foodie.controller.PubblicaRicettaController;
import com.foodie.controller.TrovaRicettaController;
import com.foodie.model.RicettaBean;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GestisciRicetteViewController {
	private Stage primaryStage;
	private TrovaRicettaController controller = new TrovaRicettaController();
	private LoginController controller2= new LoginController();
	private PubblicaRicettaController controller3= PubblicaRicettaController.ottieniIstanza();
	@FXML
	private VBox contenitoreRicette;
	@FXML
	private Label eliminaLabel;
	private boolean bottoneModifica = true;
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage= primaryStage;
	}
	public void aggiornaView() {
		ArrayList<RicettaBean> ricetteTrovate= null;
		contenitoreRicette.getChildren().clear();
		ricetteTrovate=controller.caricaRicette(controller2.getUtente().getUsername());
		if(ricetteTrovate!=null) {
			for(RicettaBean r: ricetteTrovate) {
				HBox contenitoreRicettaSingola = new HBox();
				contenitoreRicettaSingola.setAlignment(Pos.CENTER_LEFT);
				contenitoreRicettaSingola.setStyle("-fx-background-color: rgba(217, 217, 217, 0.75);-fx-border-color: black;");
				contenitoreRicettaSingola.setMinHeight(110);
				contenitoreRicettaSingola.setMaxWidth(Double.MAX_VALUE);
				Label labelNome = new Label(r.getNome());
				labelNome.setMinWidth(313);
				labelNome.setMinHeight(65);
				labelNome.setFont(Font.font("Arial",20));
				labelNome.setAlignment(Pos.CENTER);
				String difficolta="";
				switch(r.getDifficolta()) {
				case 1:
						difficolta="facile";
						break;
				case 2:
						difficolta="intermedio";
						break;
				case 3:
						difficolta="difficile";
						break;
				}
				Label labelDifficolta = new Label(difficolta);
				labelDifficolta.setMinWidth(313);
				labelDifficolta.setMinHeight(65);
				labelDifficolta.setFont(Font.font("Arial",20));
				labelDifficolta.setAlignment(Pos.CENTER);
				contenitoreRicettaSingola.getChildren().addAll(labelNome,labelDifficolta);
				contenitoreRicette.getChildren().add(contenitoreRicettaSingola);
				
			}
			contenitoreRicette.getChildren().forEach(node -> {
		        HBox contenitoreRicetta = (HBox) node;
		        contenitoreRicetta.setOnMouseClicked(event -> {
		            String nomeRicetta="";
		            String difficoltaRicetta="";
		            int indiceLabel=1;
		            for (Node labelNode : contenitoreRicetta.getChildren()) {	        
		                    Label label = (Label) labelNode;
		                    if(indiceLabel==1)
		                    	nomeRicetta=label.getText();
		                    else {
		                    	difficoltaRicetta=label.getText();
		                    }
		                    indiceLabel++;
		            }
		            caricaViewRicetta(nomeRicetta,difficoltaRicetta);
		        });
		    });
			impostaHBox();
		}
		if(contenitoreRicette.getChildren().isEmpty() && bottoneModifica==false) { //PER EVITARE CHE SE LE RICETTE è VUOTA RIMANGA ATTIVO IL BOTTONE E IL TESTO DELLA LABEL
			bottoneModifica=true;
			eliminaLabel.setFont(Font.font("Arial",30));//ESEMPIO PREMI MODIFICA CANCELLI L'ULTIMA RICETTA ALLORA SI DEVE DISATTIVARE LA MODIFICA
			eliminaLabel.setText("");
		}
	}
	public void caricaViewRicetta(String nomeRicetta,String difficoltaRicetta) {
		RicettaBean ricettaSelezionata = controller.ottieniRicetta(nomeRicetta,controller2.getUtente().getUsername());
		FXMLLoader loader;
		try {
			if(bottoneModifica==false) { //resettare il bottone modifica se attivo
				bottoneModifica=true;
				eliminaLabel.setFont(Font.font("Arial",30));
				eliminaLabel.setText("");
			}
			loader = new FXMLLoader(getClass().getResource("ContenutoRicettaChef.fxml"));
			Parent root = loader.load();
	        ContenutoRicettaChefViewController contenutoRicettaChefViewController=loader.getController();
	        caricaDatiRicetta(ricettaSelezionata,contenutoRicettaChefViewController);
	        contenutoRicettaChefViewController.setPrimaryStage(primaryStage);
	        Scene nuovaScena = new Scene(root);
	        primaryStage.setScene(nuovaScena);
	        primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void caricaDatiRicetta(RicettaBean ricettaBean, ContenutoRicettaChefViewController contenutoRicettaChefViewController) {
		Label labelNome=contenutoRicettaChefViewController.getNome();
		Label labelDescrizione = contenutoRicettaChefViewController.getDescrizione();
		VBox contenitoreIngredienti= contenutoRicettaChefViewController.getContenitoreIngredienti();
		labelNome.setText(ricettaBean.getNome());
		labelDescrizione.setText(ricettaBean.getDescrizione());
		for(int i=0;i<ricettaBean.getIngredienti().size();i++) {
			Label label=new Label(ricettaBean.getIngredienti().get(i).getNome()+": "+ricettaBean.getQuantita().get(i));
			label.setStyle("-fx-background-color: white;");
			label.setMaxWidth(Double.MAX_VALUE);
			label.setMinHeight(50);
			label.setWrapText(true);
			label.setFont(Font.font("Arial",20));
			contenitoreIngredienti.getChildren().add(label);
		}
	}
	@FXML
	private void modifica(ActionEvent e) {
		if(bottoneModifica==true && !contenitoreRicette.getChildren().isEmpty()) {
			bottoneModifica=false;
			eliminaLabel.setFont(Font.font("Arial",20));
			eliminaLabel.setText("CLICCA L'ALIMENTO DA ELIMINARE");
			impostaHBox();
		}
		else if(bottoneModifica==false && !contenitoreRicette.getChildren().isEmpty()) {
			bottoneModifica=true;
			eliminaLabel.setFont(Font.font("Arial",30));
			eliminaLabel.setText("");
			impostaHBox();
		}
	}
	private void eliminaRicetta(String nome,String autore) {
		if(!contenitoreRicette.getChildren().isEmpty()) {
			contenitoreRicette.getChildren().clear();
		}
		controller3.eliminaRicetta(nome, autore);
		aggiornaView();
	}
	private void impostaHBox() {
		if(bottoneModifica==false) {
			if(!contenitoreRicette.getChildren().isEmpty()) {
				contenitoreRicette.getChildren().forEach(node->{
					HBox hBoxRicetta= (HBox)node;
					Label labelNome;
					for(Node nodo: hBoxRicetta.getChildren()) {
						labelNome=(Label)nodo;  //PRENDO SOLO IL NOME 
						hBoxRicetta.setOnMouseClicked(event->{eliminaRicetta(labelNome.getText(),controller2.getUtente().getUsername());});
						break;
					}
				});
			}
		}
		else {
			if(!contenitoreRicette.getChildren().isEmpty()) {
				contenitoreRicette.getChildren().forEach(node->{
					HBox hBoxRicetta= (HBox)node;
					hBoxRicetta.setOnMouseClicked(null);
				});
			}
		}
	}
}
