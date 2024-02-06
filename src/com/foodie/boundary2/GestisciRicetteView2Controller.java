package com.foodie.boundary2;

import java.io.IOException;
import java.util.ArrayList;

import com.foodie.Applicazione.LoginViewController;
import com.foodie.controller.AdattatoreFactory;
import com.foodie.controller.ControllerAdapter;
import com.foodie.controller.PubblicaRicettaController;
import com.foodie.model.AlimentoBean;
import com.foodie.model.Ricetta;
import com.foodie.model.RicettaBean;
import com.foodie.model.UtenteBean;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GestisciRicetteView2Controller {
	private static GestisciRicetteView2Controller istanza;
	private Stage primaryStage;
	private AdattatoreFactory factory= AdattatoreFactory.ottieniIstanza();
	private ControllerAdapter adattatoreTrovaRicettaController= factory.creaTrovaRicettaAdapter();
	private ControllerAdapter adattatoreLoginController = factory.creaLoginAdapter();
	private PubblicaRicettaController controller = PubblicaRicettaController.ottieniIstanza();
	private PubblicaRicettaController controller2 = PubblicaRicettaController.ottieniIstanza();
	@FXML
	private VBox contenitoreRicette;
	@FXML
	private Label eliminaLabel;
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage= primaryStage;
	}
	
	private GestisciRicetteView2Controller() {
	}
	
	public static synchronized GestisciRicetteView2Controller ottieniIstanza() {
		if(istanza == null) {
			istanza = new GestisciRicetteView2Controller();
		}
		return istanza;
	}
	
	public void aggiornaView() {
		ArrayList<RicettaBean> ricetteTrovate= null;
		contenitoreRicette.getChildren().clear();
		UtenteBean utenteBean=adattatoreLoginController.ottieniUtente();
		ricetteTrovate=adattatoreTrovaRicettaController.trovaLeRicette(0,utenteBean.getUsername());
		if(ricetteTrovate!=null) {
			contenitoreRicette.getChildren().clear();
			for(RicettaBean r: ricetteTrovate) {
				String difficolta="";
				VBox contenitoreRicetta = new VBox();
				contenitoreRicetta.setStyle("-fx-background-color: rgba(217, 217, 217, 0.75);-fx-border-color: black;");
				contenitoreRicetta.setSpacing(10);
				
			    Label labelNome = new Label(r.getNome());
			    labelNome.setFont(Font.font("Arial", 20));

			    Label labelAutore = new Label(r.getAutore());
			    labelAutore.setFont(Font.font("Arial", 20));
			    
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
			impostaHBox();
		}
	}
	
	private void eliminaRicetta(VBox contenitoreRicetta) {
		if(!contenitoreRicette.getChildren().isEmpty()) {
			contenitoreRicette.getChildren().clear();
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
		controller.eliminaRicetta(nome, autore);
		aggiornaView();
	}
	
	private void impostaHBox() {
		 if (!contenitoreRicette.getChildren().isEmpty()) {
		        contenitoreRicette.getChildren().forEach(node -> {
		            VBox contenitoreRicetta = (VBox) node;
		            contenitoreRicetta.setOnMouseClicked(event -> eliminaRicetta(contenitoreRicetta));
		        });
		    }
	}
	
	@FXML
    private void tornaAlLogin() {
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
	private void caricaViewRicetta() {
		controller.creaRicetta(); //QUANDO ENTRO NELLA RICETTA CREO L'ISTANZA DELLA RICETTA
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NuovaRicettaView2.fxml"));
            NuovaRicettaView2Controller nuovaRicettaViewController = NuovaRicettaView2Controller.ottieniIstanza();
            loader.setController(nuovaRicettaViewController);
            Ricetta ricetta =controller2.getRicetta();
			ricetta.registra(nuovaRicettaViewController);
            Parent root = loader.load();
            nuovaRicettaViewController.setPrimaryStage(primaryStage);
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
	}
	
	@FXML
	private void caricaViewAreaPersonale() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AreaPersonaleView2.fxml"));
		AreaPersonaleView2Controller controllerAreaPersonale = AreaPersonaleView2Controller.ottieniIstanza();
		loader.setController(controllerAreaPersonale);
		Parent root;		
		try {
			root = loader.load();
			controllerAreaPersonale.setPrimaryStage(primaryStage);
			controllerAreaPersonale.caricaAreaPersonale();
		    controllerAreaPersonale.aggiornaView();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}