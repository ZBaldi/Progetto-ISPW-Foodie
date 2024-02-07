package com.foodie.boundary;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import com.foodie.applicazione.LoginViewController;
import com.foodie.controller.AdattatoreFactory;
import com.foodie.controller.ControllerAdapter;
import com.foodie.controller.PubblicaRicettaController;
import com.foodie.model.RicettaBean;
import com.foodie.model.UtenteBean;

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
	
	private static GestisciRicetteViewController istanza;
	private AdattatoreFactory factory = AdattatoreFactory.ottieniIstanza();
	private PubblicaRicettaController controller = PubblicaRicettaController.ottieniIstanza();
	private ControllerAdapter adattatoreTrovaRicettaController= factory.creaTrovaRicettaAdapter();
	private ControllerAdapter adattatoreLoginController = factory.creaLoginAdapter();
	private boolean bottoneModifica = true;
	private Stage primaryStage;
	private static final String FORMATO = "Arial";
	private static final Logger logger = Logger.getLogger(GestisciRicetteViewController.class.getName());
	@FXML
	private VBox contenitoreRicette;
	@FXML
	private Label eliminaLabel;
	
	private GestisciRicetteViewController() {		
	}
	
	public static GestisciRicetteViewController ottieniIstanza() { //SINGLETON
		if(istanza == null) {
			istanza = new GestisciRicetteViewController();
		}
		return istanza;
	}
	
	public void setPrimaryStage(Stage primaryStage) {  //PASSO LO STAGE
		this.primaryStage= primaryStage;
	}
	
	public void aggiornaView() {  //TROVA TUTTE LE RICETTE DELLO CHEF E LE MOSTRA GRAFICAMENTE
		List<RicettaBean> ricetteTrovate= null;
		contenitoreRicette.getChildren().clear();
		UtenteBean utenteBean=adattatoreLoginController.ottieniUtente();
		ricetteTrovate=adattatoreTrovaRicettaController.trovaLeRicette(0,utenteBean.getUsername());  //TROVO PER USERNAME CHEF
		if(ricetteTrovate!=null) {
			for(RicettaBean r: ricetteTrovate) {  //CREO VARIE PARTI GRAFICHE
				HBox contenitoreRicettaSingola = new HBox();
				contenitoreRicettaSingola.setAlignment(Pos.CENTER_LEFT);
				contenitoreRicettaSingola.setStyle("-fx-background-color: rgba(217, 217, 217, 0.75);-fx-border-color: black;");
				contenitoreRicettaSingola.setMinHeight(110);
				contenitoreRicettaSingola.setMaxWidth(Double.MAX_VALUE);
				Label labelNome = new Label(r.getNome());
				labelNome.setMinWidth(313);
				labelNome.setMinHeight(65);
				labelNome.setFont(Font.font(FORMATO,20));
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
					default:
						logger.severe("difficoltà non riconosciuta");
						contenitoreRicette.getChildren().clear();
						return;
				}
				Label labelDifficolta = new Label(difficolta);
				labelDifficolta.setMinWidth(313);
				labelDifficolta.setMinHeight(65);
				labelDifficolta.setFont(Font.font(FORMATO,20));
				labelDifficolta.setAlignment(Pos.CENTER);
				contenitoreRicettaSingola.getChildren().addAll(labelNome,labelDifficolta);
				contenitoreRicette.getChildren().add(contenitoreRicettaSingola);
				
			}
			impostaHBox(); //RENDO CLICCABILI
		}
		if(contenitoreRicette.getChildren().isEmpty() && !bottoneModifica) { //PER EVITARE CHE SE LE RICETTE è VUOTA RIMANGA ATTIVO IL BOTTONE E IL TESTO DELLA LABEL
			bottoneModifica=true;
			eliminaLabel.setFont(Font.font(FORMATO,30));//ESEMPIO PREMI MODIFICA CANCELLI L'ULTIMA RICETTA ALLORA SI DEVE DISATTIVARE LA MODIFICA
			eliminaLabel.setText("");
		}
	}
	
	public void caricaViewContenutoRicetta(String nomeRicetta) {  //CARICA VIEW CONTENUTO RICETTA
		UtenteBean utenteBean=adattatoreLoginController.ottieniUtente();
		RicettaBean ricettaSelezionata = adattatoreTrovaRicettaController.apriLaRicetta(nomeRicetta,utenteBean.getUsername());
		try {
			if(!bottoneModifica) { //resettare il bottone modifica se attivo
				bottoneModifica=true;
				eliminaLabel.setFont(Font.font(FORMATO,30));
				eliminaLabel.setText("");
			}
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ContenutoRicettaChef.fxml"));
	        ContenutoRicettaChefViewController contenutoRicettaChefViewController = ContenutoRicettaChefViewController.ottieniIstanza();
	        loader.setController(contenutoRicettaChefViewController);
	        Parent root = loader.load();
	        caricaDatiRicetta(ricettaSelezionata,contenutoRicettaChefViewController);  //METODO PER POPOLARE GRAFICAMENTE I DATI DELLA RICETTA
	        contenutoRicettaChefViewController.setPrimaryStage(primaryStage);
	        Scene nuovaScena = new Scene(root);
	        primaryStage.setScene(nuovaScena);
	        primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void caricaDatiRicetta(RicettaBean ricettaBean, ContenutoRicettaChefViewController contenutoRicettaChefViewController) {
		Label labelNome=contenutoRicettaChefViewController.getNome();  //METODO CHIAMATO PER CARICARE I DATI DELLA RICETTA
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
			label.setFont(Font.font(FORMATO,20));
			contenitoreIngredienti.getChildren().add(label);
		}
	}
	
	@FXML
	private void modifica(ActionEvent event) {  //GESTISCE IL PULSANTE ELIMINA
		if(bottoneModifica && !contenitoreRicette.getChildren().isEmpty()) {
			bottoneModifica=false;
			eliminaLabel.setFont(Font.font(FORMATO,20));
			eliminaLabel.setText("CLICCA LA RICETTA DA ELIMINARE");
		}
		else if(!bottoneModifica && !contenitoreRicette.getChildren().isEmpty()) {
			bottoneModifica=true;
			eliminaLabel.setFont(Font.font(FORMATO,30));
			eliminaLabel.setText("");
		}
		impostaHBox();
	}
	
	private void eliminaRicetta(String nome,String autore) {  //ELIMINA RICETTA 
		if(!contenitoreRicette.getChildren().isEmpty()) {
			contenitoreRicette.getChildren().clear();
		}
		controller.eliminaRicetta(nome, autore);
		aggiornaView();
	}
	
	private void impostaHBox() {  //IMPOSTA RICETTE CLICCABILI
		if(!bottoneModifica) {  //IMPOSTO DI ELIMINARE LE RICETTE
			if(!contenitoreRicette.getChildren().isEmpty()) {
				scorriLabel();  //LE RENDE CLICCABILI, EVITI LO SMELL DELLA COMPLESSITA'
			}
		}
		else {  //IMPOSTO DI APRIRE LE RICETTE CLICCATE
			contenitoreRicette.getChildren().forEach(node -> {
		        HBox contenitoreRicetta = (HBox) node;
		        contenitoreRicetta.setOnMouseClicked(event -> {
		            int indiceLabel=1;
		            popolaLabel(indiceLabel,contenitoreRicetta);
		        });
		    });
		}
	}
	
	private void scorriLabel() {  //SCORRE LE LABEL E LE RENDE CLICCABILI
		for(Node nodo1: contenitoreRicette.getChildren()) {
			int indice=1;
			HBox hBoxRicetta= (HBox)nodo1;
			for(Node nodo: hBoxRicetta.getChildren()) {
				Label labelNome=(Label)nodo;  //PRENDO SOLO IL NOME 
				UtenteBean utenteBean=adattatoreLoginController.ottieniUtente();
				hBoxRicetta.setOnMouseClicked(event->eliminaRicetta(labelNome.getText(),utenteBean.getUsername()));
				if(indice==1) {
					break;
				}
				indice++;
			}
		}
	}
	
	private void popolaLabel(int indiceLabel,HBox contenitoreRicetta) {
		String nomeRicetta="";
		for (Node labelNode : contenitoreRicetta.getChildren()) {	  //POPOLA LABEL USATO PER EVITARE SMELL COMPLESSITA'        
            Label label = (Label) labelNode;
            if(indiceLabel==1)
            	nomeRicetta=label.getText();
            else {
            	break;
            }
            indiceLabel++;
		}
		caricaViewContenutoRicetta(nomeRicetta);
	}
	
	@FXML
    private void tornaAlLogin() {  //CARICA VIEW LOGIN
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
	private void caricaViewRicetta() {  //CARICA VIEW NUOVA RICETTA DA CREARE
		PubblicaRicettaController.creaRicetta(); //QUANDO ENTRO NELLA RICETTA CREO L'ISTANZA DELLA RICETTA
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NuovaRicettaView.fxml"));
            NuovaRicettaViewController nuovaRicettaViewController= NuovaRicettaViewController.ottieniIstanza();
            loader.setController(nuovaRicettaViewController);
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
	private void caricaViewAreaPersonale() {  	//CARICA VIEW AREA PERSONALE	
		AreaPersonaleViewController controllerAreaPersonale = AreaPersonaleViewController.ottieniIstanza();
		try {
			FXMLLoader loader= new FXMLLoader(getClass().getResource("AreaPersonaleView.fxml"));
			loader.setController(controllerAreaPersonale);
			Parent root = loader.load();
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
