package com.foodie.boundary;

import java.util.ArrayList;
import com.foodie.Applicazione.LoginViewController;
import com.foodie.controller.AdattatoreFactory;
import com.foodie.controller.ControllerAdapter;
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
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TrovaRicetteViewController {
	
	private static TrovaRicetteViewController istanza;
	private AdattatoreFactory factory = AdattatoreFactory.ottieniIstanza();
	private TrovaRicettaController controller = TrovaRicettaController.ottieniIstanza();
	private ControllerAdapter adattatoreTrovaRicettaController= factory.creaTrovaRicettaAdapter();
	private Stage primaryStage;
	private static final String FORMATO = "Arial";
	@FXML
	private VBox contenitoreRicette;
	@FXML
	private MenuItem facile;
	@FXML
	private MenuItem media;
	@FXML
	private MenuItem difficile;
	
	private TrovaRicetteViewController() {	
	}
	
	public static synchronized TrovaRicetteViewController ottieniIstanza() { //SINGLETON	
		if(istanza == null) {
			istanza = new TrovaRicetteViewController();
		}
		return istanza;
	}
	
	public void setPrimaryStage(Stage primaryStage) {  //PASSO LO STAGE
		this.primaryStage= primaryStage;
	}
	
	public VBox getContenitoreRicette() {  //RESTITUISCO IL VBOX
		return contenitoreRicette;
	}
	
	@FXML
	private void tornaAlLogin(MouseEvent event) {;  //CARICA VIEW LOGIN
		controller.svuotaDispensa();
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
			DispensaUtenteViewController dispensaUtenteViewController = DispensaUtenteViewController.ottieniIstanza();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DispensaUtenteView.fxml"));
            loader.setController(dispensaUtenteViewController);
            Parent root = loader.load();
            dispensaUtenteViewController.setPrimaryStage(primaryStage);
            dispensaUtenteViewController.aggiornaView();
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
	}
	
	@FXML
	private void filtraRicette(ActionEvent event) {  //SE VOGLIO UNA DIFFICOLTA' SPECIFICA RIFACCIO LA RICERCA CON QUELLA DIFFICOLTA'
		contenitoreRicette.getChildren().clear();
		MenuItem item= (MenuItem) event.getSource();
		String difficolta=item.getText();
		switch(difficolta){
			case "Facile":	
					trovaRicette(1);
					break;
			case "Media":	
					trovaRicette(2);
					break;
			case "Difficile":	
					trovaRicette(3);
					break;
			default:
				System.err.println("difficoltà non riconosciuta");
				contenitoreRicette.getChildren().clear();
		}
	}
	
	private void trovaRicette(int difficoltaInt) { //CHIAMA METODO PER TROVARE LE RICETTE E CREARE LE VARIE PARTI GRAFICHE
		if(creaRicetteTrovate(difficoltaInt)==-1) {  //SE NESSUN RISULTATO
				Label label=new Label("NESSUN RISULTATO");
				label.setStyle("-fx-background-color: rgba(217, 217, 217, 0.75);-fx-border-color: black;");
				label.setMaxWidth(Double.MAX_VALUE);
				label.setMinHeight(110);
				label.setWrapText(true);
				label.setFont(Font.font(FORMATO,50));
				label.setAlignment(Pos.CENTER);
				contenitoreRicette.getChildren().add(label);
		}
	}
	
	public int creaRicetteTrovate(int difficoltaInt) {  //TROVA LE RICETTE E LE MOSTRA GRAFICAMENTE
		ArrayList<RicettaBean> ricetteTrovate= null;
		ricetteTrovate=adattatoreTrovaRicettaController.trovaLeRicette(difficoltaInt,null);
		if(ricetteTrovate!=null) {
			for(RicettaBean r: ricetteTrovate) {  //PER OGNI RICETTA CREA PARTI GRAFICHE
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
				Label labelAutore = new Label(r.getAutore());
				labelAutore.setMinWidth(313);
				labelAutore.setMinHeight(65);
				labelAutore.setFont(Font.font(FORMATO,20));
				labelAutore.setAlignment(Pos.CENTER);
				String difficolta="";
				switch(difficoltaInt) {
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
					System.err.println("difficoltà non riconosciuta");
					contenitoreRicette.getChildren().clear();
					return -1;
				}
				Label labelDifficolta = new Label(difficolta);
				labelDifficolta.setMinWidth(313);
				labelDifficolta.setMinHeight(65);
				labelDifficolta.setFont(Font.font(FORMATO,20));
				labelDifficolta.setAlignment(Pos.CENTER);
				contenitoreRicettaSingola.getChildren().addAll(labelNome,labelAutore,labelDifficolta);
				contenitoreRicette.getChildren().add(contenitoreRicettaSingola);
			}
			impostaHBox();  //RENDO RICETTE CLICCABILI
			return 1;  //SE TROVATA ALMENO 1
		}
		else {
			return -1; //SE NON TROVATE
		}
	}
	
	private void impostaHBox() {  //METODO CHE IMPOSTA CLICCABILI LE RICETTE TROVATE
		contenitoreRicette.getChildren().forEach(node -> {  //E' LA PARTE DI SETTAGGIO DELLE LABEL CLICCABILI
	        HBox contenitoreRicetta = (HBox) node;
	        contenitoreRicetta.setOnMouseClicked(event2 -> {
	            String nomeRicetta="";
	            String autoreRicetta="";
	            String difficoltaRicetta="";
	            int indiceLabel=1;
	            for (Node labelNode : contenitoreRicetta.getChildren()) {	        
	                    Label label = (Label) labelNode;
	                    if(indiceLabel==1)
	                    	nomeRicetta=label.getText();
	                    else if(indiceLabel==2)
	                    	autoreRicetta=label.getText();
	                    else {
	                    	difficoltaRicetta=label.getText();
	                    }
	                    indiceLabel++;
	            }
	            caricaViewRicetta(nomeRicetta, autoreRicetta, difficoltaRicetta);  //SE CLICCATA LA APRE
	        });
	    });
	}
	
	public void caricaViewRicetta(String nomeRicetta,String autoreRicetta,String difficoltaRicetta) {  //CARICA VIEW CONTENUTO RICETTA
		RicettaBean ricettaSelezionata = adattatoreTrovaRicettaController.apriLaRicetta(nomeRicetta,autoreRicetta);
		FXMLLoader loader;
		try {
			loader = new FXMLLoader(getClass().getResource("ContenutoRicettaView.fxml"));
	        ContenutoRicettaViewController contenutoRicettaViewController = ContenutoRicettaViewController.ottieniIstanza();
	        loader.setController(contenutoRicettaViewController);
	        Parent root = loader.load();
	        caricaDatiRicetta(ricettaSelezionata,contenutoRicettaViewController); //METODO PER POPOLARE GRAFICAMENTE LA VIEW CON I DATI DELLA RICETTA
	        contenutoRicettaViewController.setPrimaryStage(primaryStage);
	        Scene nuovaScena = new Scene(root);
	        primaryStage.setScene(nuovaScena);
	        primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void caricaDatiRicetta(RicettaBean ricettaBean, ContenutoRicettaViewController contenutoRicettaViewController) {  //POPOLA GRAFICAMENTE IL CONTENUTO DELLA RICETTA NEL FXML
		Label labelNome=contenutoRicettaViewController.getNome();
		Label labelDescrizione = contenutoRicettaViewController.getDescrizione();
		VBox contenitoreIngredienti= contenutoRicettaViewController.getContenitoreIngredienti();
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
	
}