package com.foodie.boundary2;

import java.util.List;
import java.util.logging.Logger;
import com.foodie.controller.AdattatoreFactory;
import com.foodie.controller.ControllerAdapter;
import com.foodie.controller.PubblicaRicettaController;
import com.foodie.model.AlimentoBean;
import com.foodie.model.RicettaBean;
import com.foodie.model.UtenteBean;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GestisciRicetteView2Controller {
	private static GestisciRicetteView2Controller istanza;
	private AdattatoreFactory factory= AdattatoreFactory.ottieniIstanza();
	private ControllerAdapter adattatoreTrovaRicettaController= factory.creaTrovaRicettaAdapter();
	private ControllerAdapter adattatoreLoginController = factory.creaLoginAdapter();
	private PubblicaRicettaController controller = PubblicaRicettaController.ottieniIstanza();
	private CaricaView2 caricaView2= CaricaView2.ottieniIstanza();
	private Stage primaryStage;
	private static final String FORMATO = "Arial";
	private static final Logger logger = Logger.getLogger(GestisciRicetteView2Controller.class.getName());
	@FXML
	private VBox contenitoreRicette;
	@FXML
	private Label eliminaLabel;
	
	private GestisciRicetteView2Controller() {
	}
	
	public static synchronized GestisciRicetteView2Controller ottieniIstanza() {  //SINGLETON METODO PER OTTENERE L'ISTANZA
		if(istanza == null) {
			istanza = new GestisciRicetteView2Controller();
		}
		return istanza;
	}
	
	public void setPrimaryStage(Stage primaryStage) {  //PASSO LO STAGE
		this.primaryStage= primaryStage;
	}
	
	public void aggiornaView() {  //TROVA LE RICETTE DELLO CHEF E LE MOSTRA GIA' CON TUTTO IL CONTENUTO
		List<RicettaBean> ricetteTrovate= null;
		contenitoreRicette.getChildren().clear();
		UtenteBean utenteBean=adattatoreLoginController.ottieniUtente();
		ricetteTrovate=adattatoreTrovaRicettaController.trovaLeRicette(0,utenteBean.getUsername());
		if(!ricetteTrovate.isEmpty()) {
			contenitoreRicette.getChildren().clear();
			for(RicettaBean r: ricetteTrovate) {
				String difficolta="";
				VBox contenitoreRicetta = new VBox();
				contenitoreRicetta.setStyle("-fx-background-color: rgba(217, 217, 217, 0.75);-fx-border-color: black;");
				contenitoreRicetta.setSpacing(10);
				
			    Label labelNome = new Label(r.getNome());
			    labelNome.setFont(Font.font(FORMATO, 20));

			    Label labelAutore = new Label(r.getAutore());
			    labelAutore.setFont(Font.font(FORMATO, 20));
			    
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
				default:
					logger.severe("difficoltà non riconosciuta");
					contenitoreRicette.getChildren().clear();
					return;
				}
			    
			    Label labelDifficolta = new Label("Difficoltà: "+difficolta);
			    labelDifficolta.setFont(Font.font(FORMATO, 20));
			 
			    Label labelDescrizione = new Label("Descrizione: "+r.getDescrizione());
			    labelDescrizione.setFont(Font.font(FORMATO, 20));
			    labelDescrizione.setWrapText(true);
			    
			    VBox contenitoreIngredienti= new VBox();
			    Label inizio= new Label("Ingredienti:");
			    inizio.setFont(Font.font(FORMATO, 20));
			    contenitoreIngredienti.getChildren().add(inizio);
			    String ingredienti="";
			    for (int i = 0; i < r.getIngredienti().size(); i++) {
			        AlimentoBean alimentoBean = r.getIngredienti().get(i);
			        ingredienti = alimentoBean.getNome() + " : " + r.getQuantita().get(i);
			        Label labelIngredienti= new Label(ingredienti);
			        labelIngredienti.setFont(Font.font(FORMATO, 20));
			        contenitoreIngredienti.getChildren().add(labelIngredienti);
			    }
			    
			    contenitoreRicetta.getChildren().addAll(labelNome,labelAutore,labelDifficolta,labelDescrizione, contenitoreIngredienti);
			    contenitoreRicette.getChildren().add(contenitoreRicetta);
			}
			impostaVBox();  //IMPOSTA RICETTE CLICCABILI PER ELIMINARLE
		}
	}
	
	private void eliminaRicetta(VBox contenitoreRicetta) {  //ELIMINA RICETTA 
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
	
	private void impostaVBox() {  //RENDE CLICCABILI LE RICETTE
		 if (!contenitoreRicette.getChildren().isEmpty()) {
		        contenitoreRicette.getChildren().forEach(node -> {
		            VBox contenitoreRicetta = (VBox) node;
		            contenitoreRicetta.setOnMouseClicked(event -> eliminaRicetta(contenitoreRicetta));
		        });
		    }
	}
	
	@FXML
    private void tornaAlLogin() {  //CARICA VIEW LOGIN
        caricaView2.tornaAlLogin(primaryStage);
    }
	
	@FXML
	private void caricaViewRicetta() {  //CARICA VIEW RICETTA 
		caricaView2.caricaViewRicetta(primaryStage);
	}
	
	@FXML
	private void caricaViewAreaPersonale() {  //CARICA VIEW AREA PERSONALE
		caricaView2.caricaViewAreaPersonale(primaryStage);
	}
	
}