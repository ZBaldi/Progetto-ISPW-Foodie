package com.foodie.boundary2;

import java.util.ArrayList;

import com.foodie.boundary.LoginViewController;
import com.foodie.controller.AdattatoreFactory;
import com.foodie.controller.ControllerAdapter;
import com.foodie.model.AlimentoBean;
import com.foodie.model.RicettaBean;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TrovaRicettaView2Controller {
	
	private static TrovaRicettaView2Controller istanza;
	private AdattatoreFactory factory= AdattatoreFactory.ottieniIstanza();
	private ControllerAdapter adattatoreTrovaRicettaController= factory.creaTrovaRicettaAdapter();
	private Stage primaryStage;
	@FXML
	private RadioButton facile;
	@FXML
	private RadioButton medio;
	@FXML
	private RadioButton difficile;
	@FXML
	private VBox contenitoreRicette;
	
	private TrovaRicettaView2Controller() {	
	}
	
	public static synchronized TrovaRicettaView2Controller ottieniIstanza() { //SINGLETON
		if(istanza == null) {
			istanza = new TrovaRicettaView2Controller();
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
	private void caricaViewDispensa(ActionEvent event) {
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DispensaView2.fxml"));
            DispensaView2Controller dispensaController = DispensaView2Controller.ottieniIstanza();
            loader.setController(dispensaController);
            Parent root = loader.load();
            dispensaController.setPrimaryStage(primaryStage);
            dispensaController.aggiornaView();
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
	}
	
	@FXML
	private void disabilitaPulsanti(ActionEvent event) {
		if (facile.isSelected()) {
		    medio.setDisable(true);
		    difficile.setDisable(true);
		} else if (medio.isSelected()) {
		    facile.setDisable(true);
		    difficile.setDisable(true);
		} else if (difficile.isSelected()) {
		    medio.setDisable(true);
		    facile.setDisable(true);
		} else {
		    medio.setDisable(false);
		    difficile.setDisable(false);
		    facile.setDisable(false);
		}
	}
	
	@FXML
    private void caricaViewAlimenti(ActionEvent event) {
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
	private void trovaRicette(ActionEvent event) {
		contenitoreRicette.getChildren().clear();
		ArrayList<RicettaBean> ricetteTrovate= null;
		String difficolta="";
		if (facile.isSelected()) {
			ricetteTrovate=adattatoreTrovaRicettaController.trovaLeRicette(1,null);
			difficolta="facile";
		} 
		else if (medio.isSelected()) {
			ricetteTrovate=adattatoreTrovaRicettaController.trovaLeRicette(2,null);
			difficolta="intermedia";
		} 
		else if (difficile.isSelected()) {
			ricetteTrovate=adattatoreTrovaRicettaController.trovaLeRicette(3,null);
			difficolta="difficile";
		}
		else {
			Label label=new Label("SCEGLI DIFFICOLTA'");
			label.setStyle("-fx-background-color: rgba(217, 217, 217, 0.75);-fx-border-color: black;");
			label.setMaxWidth(Double.MAX_VALUE);
			label.setMinHeight(110);
			label.setWrapText(true);
			label.setFont(Font.font("Arial",50));
			label.setAlignment(Pos.CENTER);
			contenitoreRicette.getChildren().add(label);
			return;
		}
		if(ricetteTrovate!=null) {
			contenitoreRicette.getChildren().clear();
			for(RicettaBean r: ricetteTrovate) {
				VBox contenitoreRicetta = new VBox();
				contenitoreRicetta.setStyle("-fx-background-color: rgba(217, 217, 217, 0.75);-fx-border-color: black;");
				contenitoreRicetta.setSpacing(10);
				
			    Label labelNome = new Label("Nome: "+r.getNome());
			    labelNome.setFont(Font.font("Arial", 20));

			    Label labelAutore = new Label("Autore: "+r.getAutore());
			    labelAutore.setFont(Font.font("Arial", 20));

			    Label labelDifficolta = new Label("Difficoltà: "+difficolta);
			    labelDifficolta.setFont(Font.font("Arial", 20));
			 
			    Label labelDescrizione = new Label("Descrizione: "+r.getDescrizione());
			    labelDescrizione.setFont(Font.font("Arial", 20));
			    labelDescrizione.setWrapText(true);
			    
			    VBox contenitoreIngredienti= new VBox();
			    Label inizio= new Label("Ingredienti:");
			    inizio.setFont(Font.font("Arial", 20));
			    contenitoreIngredienti.getChildren().add(inizio);
			    String ingredienti="";
			    for (int i = 0; i < r.getIngredienti().size(); i++) {
			        AlimentoBean alimentoBean = r.getIngredienti().get(i);
			        ingredienti = alimentoBean.getNome() + " : " + r.getQuantita().get(i);
			        Label labelIngredienti= new Label(ingredienti);
			        labelIngredienti.setFont(Font.font("Arial", 20));
			        contenitoreIngredienti.getChildren().add(labelIngredienti);
			    }
			    
			    contenitoreRicetta.getChildren().addAll(labelNome,labelAutore,labelDifficolta,labelDescrizione, contenitoreIngredienti);
			    contenitoreRicette.getChildren().add(contenitoreRicetta);
			}
		}
		else {
			Label label=new Label("NESSUN RISULTATO");
			label.setStyle("-fx-background-color: rgba(217, 217, 217, 0.75);-fx-border-color: black;");
			label.setMaxWidth(Double.MAX_VALUE);
			label.setMinHeight(110);
			label.setWrapText(true);
			label.setFont(Font.font("Arial",50));
			label.setAlignment(Pos.CENTER);
			contenitoreRicette.getChildren().add(label);
		}
	}

}