package com.foodie.boundary2;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.foodie.applicazione.LoginViewController;
import com.foodie.controller.AdattatoreFactory;
import com.foodie.controller.ControllerAdapter;
import com.foodie.model.AlimentoBean;
import com.foodie.model.Observer;
import com.foodie.model.RicettaBean;
import com.foodie.model.UtenteBean;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class NuovaRicettaView2Controller implements Observer{
	
	private static NuovaRicettaView2Controller istanza;
	private AdattatoreFactory factory= AdattatoreFactory.ottieniIstanza();
	private ControllerAdapter adattatorePubblicaRicettaController= factory.creaPubblicaRicettaAdapter();
	private ControllerAdapter adattatoreLoginController= factory.creaLoginAdapter();
	private ControllerAdapter adattatoreTrovaRicettaController=factory.creaTrovaRicettaAdapter();
	private Stage primaryStage;
	private static final String FORMATO = "Arial";
	private static final String SFONDOBIANCO = "-fx-background-color: white;";
	@FXML
	private Button areaPersonaleButton;
	@FXML
	private RadioButton facile;
	@FXML
	private RadioButton medio;
	@FXML
	private RadioButton difficile;
	@FXML
	private TextField nome;
	@FXML
	private TextArea descrizione;
	@FXML
	private Button pubblica;
	@FXML
	private VBox ingredienti;
	@FXML
	private VBox contenitoreAlimentiTrovati;
	@FXML
	private TextField barraDiRicerca;
	@FXML
	private TextField quantita;
	
	private NuovaRicettaView2Controller() {
	}
	
	public static synchronized NuovaRicettaView2Controller ottieniIstanza() { //METODO PER OTTENERE L'ISTANZA
		if(istanza == null) {
			istanza = new NuovaRicettaView2Controller();
		}
		return istanza;
	}
	
	public void setPrimaryStage(Stage primaryStage) {  //PASSO LO STAGE
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
	
	@FXML
	private void disabilitaPulsanti(ActionEvent event) {  //GESTISCI PULSANTI DIFFICOLTA'
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
	private void caricaViewAreaPersonale(ActionEvent event) {  //CARICA VIEW AREAPERSONALE
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
	
	@FXML
    private void caricaViewGestisciRicette(ActionEvent event) {  //CARICA VIEW GESTISCI RICETTE
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("GestisciRicetteView2.fxml"));
            GestisciRicetteView2Controller gestisciRicetteController = GestisciRicetteView2Controller.ottieniIstanza();
            loader.setController(gestisciRicetteController);
            gestisciRicetteController.setPrimaryStage(primaryStage);
            Parent root = loader.load();
            gestisciRicetteController.aggiornaView();
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
	
	@FXML
	private void compilaRicetta(ActionEvent event) {  //METODO PER COMPILARE LA RICETTA
		RicettaBean ricettaBean= new RicettaBean();
		String testo = nome.getText().trim();
		if(!testo.isEmpty()) {
			ricettaBean.setNome(nome.getText());
		}
		else {  //TUTTI GLI ELSE SERVONO PER CREARE AVVERTIMENTI GRAFICI
			nome.setPromptText("INSERISCI NOME");
			 // Creazione di un oggetto ScheduledExecutorService
	        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	        // Creare una task da eseguire dopo 2 secondi
	        Runnable task = () -> 
	            // Codice da eseguire dopo 2 secondi
	            Platform.runLater(() -> nome.setPromptText("Nome Ricetta"));
	        

	        // Programmare la task per essere eseguita dopo 2 secondi
	        scheduler.schedule(task, 2, TimeUnit.SECONDS);

	        // Chiudere il thread scheduler dopo l'esecuzione della task
	        scheduler.shutdown();
		}
		testo = descrizione.getText().trim();
		if(!testo.isEmpty()) {
			ricettaBean.setDescrizione(descrizione.getText());
		}
		else {
			descrizione.setPromptText("INSERISCI DESCRIZIONE");
			 // Creazione di un oggetto ScheduledExecutorService
	        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	        // Creare una task da eseguire dopo 2 secondi
	        Runnable task = () -> 
	            // Codice da eseguire dopo 2 secondi
	            Platform.runLater(() -> descrizione.setPromptText("Descrizione"));
	        

	        // Programmare la task per essere eseguita dopo 2 secondi
	        scheduler.schedule(task, 2, TimeUnit.SECONDS);

	        // Chiudere il thread scheduler dopo l'esecuzione della task
	        scheduler.shutdown();	
	        return;
		}
		int diff=0;
		if(facile.isSelected()) {
			diff=1;
		}
		else if(medio.isSelected()) {
			diff=2;
		}
		else if(difficile.isSelected()) {
			diff=3;
		}
		else {
			pubblica.setText("DIFFICOLTA?");
			 // Creazione di un oggetto ScheduledExecutorService
	        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	        // Creare una task da eseguire dopo 2 secondi
	        Runnable task = () -> 
	            // Codice da eseguire dopo 2 secondi
	            Platform.runLater(() -> pubblica.setText("Pubblica"));
	        

	        // Programmare la task per essere eseguita dopo 2 secondi
	        scheduler.schedule(task, 2, TimeUnit.SECONDS);

	        // Chiudere il thread scheduler dopo l'esecuzione della task
	        scheduler.shutdown();	
	        return;
		}
		ricettaBean.setDifficolta(diff); 
		UtenteBean utenteBean=adattatoreLoginController.ottieniUtente();
		ricettaBean.setAutore(utenteBean.getUsername()); //NOME CHEF PRESO IN AUTOMATICO
        if(ingredienti!=null && !(ingredienti.getChildren().isEmpty())) { 
        	adattatorePubblicaRicettaController.compilaLaRicetta(ricettaBean); //RICHIESTA PUBBLICAZIONE
        	areaPersonaleButton.fire();  //SIMULA CLICK AREA PERSONALE
        	
		}
		else {  //AVVERTIMENTO GRAFICO INGREDIENTI
			pubblica.setText("INGREDIENTI?");  
			 // Creazione di un oggetto ScheduledExecutorService
	        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	        // Creare una task da eseguire dopo 2 secondi
	        Runnable task = () -> 
	            // Codice da eseguire dopo 2 secondi
	            Platform.runLater(() -> pubblica.setText("Pubblica"));
	        

	        // Programmare la task per essere eseguita dopo 2 secondi
	        scheduler.schedule(task, 2, TimeUnit.SECONDS);

	        // Chiudere il thread scheduler dopo l'esecuzione della task
	        scheduler.shutdown();	
		}
	}
	
	@FXML
	private void gestioneRisultati(KeyEvent event) {  //GESTISCE BARRA DI RICERCA ALIMENTI
		if(event.getCode() == KeyCode.ENTER) {//GETCODE() TI RESTITUISCE IL TASTO PREMUTO
			trovaAlimenti();
		}
		else if(event.getCode() == KeyCode.BACK_SPACE) {
			eliminaAlimenti();
		}
	}
	private void eliminaAlimenti() {  //ELIMINA ALIMENTI TROVATI
		if(!contenitoreAlimentiTrovati.getChildren().isEmpty()) {
			this.quantita.setDisable(true);
			contenitoreAlimentiTrovati.getChildren().clear();
		}
	}
	private void salvaAlimento(String nomeAlimento, String quantita) {  //SALVA ALIMENTO ALLA RICETTA
		if(!quantita.isEmpty()) {
			AlimentoBean alimentoBean = new AlimentoBean();
			alimentoBean.setNome(nomeAlimento);
			adattatorePubblicaRicettaController.aggiungiIngredienteRicetta(alimentoBean,quantita, 0);
			this.quantita.clear();
			this.quantita.setPromptText("Quantita");
			eliminaAlimenti();
		}
		else {
			this.quantita.setPromptText("QUANTITA?");
		}
	}
	
	private void trovaAlimenti() {  //TROVA GLI ALIMENTI
		List<AlimentoBean> alimentiBeanTrovati=adattatoreTrovaRicettaController.trovaGliAlimenti(barraDiRicerca.getText());
		if(alimentiBeanTrovati!=null) {
			quantita.setDisable(false);
			for(AlimentoBean a: alimentiBeanTrovati) {
				Label labelAlimento = new Label(a.getNome());
				labelAlimento.setStyle(SFONDOBIANCO);
				labelAlimento.setMaxWidth(Double.MAX_VALUE);
				labelAlimento.setMinHeight(30);
				labelAlimento.setWrapText(true);
				labelAlimento.setFont(Font.font(FORMATO));
				labelAlimento.setAlignment(Pos.CENTER);  //LI RENDE CLICCABILI
				labelAlimento.setOnMouseClicked(event2->salvaAlimento(labelAlimento.getText(),quantita.getText()));
				contenitoreAlimentiTrovati.getChildren().add(labelAlimento);
			}
		}
		else {  //NESSUN RISULTATO
			Label label = new Label("NESSUN RISULTATO");
			label.setStyle(SFONDOBIANCO);
			label.setMaxWidth(Double.MAX_VALUE);
			label.setMinHeight(30);
			label.setWrapText(true);
			label.setFont(Font.font(FORMATO));
			label.setAlignment(Pos.CENTER);
			contenitoreAlimentiTrovati.getChildren().add(label);
		}
	}
	@Override
	public void aggiornaView() {  //AGGIORNA GLI INGREDIENTI IN FUNZIONE DEI CAMBIAMENTI DELLA RICETTA
		ingredienti.getChildren().clear();
		List<AlimentoBean> alimentiBeanRicetta =adattatorePubblicaRicettaController.mostraIngredientiRicetta();
		if(alimentiBeanRicetta!=null) {
			for(AlimentoBean a: alimentiBeanRicetta) {
				Label labelAlimento = new Label(a.getNome());
				labelAlimento.setStyle(SFONDOBIANCO);
				labelAlimento.setMaxWidth(Double.MAX_VALUE);
				labelAlimento.setMinHeight(50);
				labelAlimento.setWrapText(true);
				labelAlimento.setFont(Font.font(FORMATO,20));
				labelAlimento.setAlignment(Pos.CENTER);
				ingredienti.getChildren().add(labelAlimento);
			}
			impostaLabel();
		}
	}
	
	private void impostaLabel() {  //IMPOSTA LABEL INGREDIENTI RICETTA CLICCABILI
		if(!ingredienti.getChildren().isEmpty()) {
			ingredienti.getChildren().forEach(node->{
				Label labelAlimento= (Label)node;
				labelAlimento.setOnMouseClicked(event->eliminaAlimento(labelAlimento.getText()));
			});
		}
	}
	
	private void eliminaAlimento(String nomeAlimento) {   //ELIMINA INGREDIENTE DALLA RICETTA 
		AlimentoBean alimentoBean = new AlimentoBean();
		alimentoBean.setNome(nomeAlimento);
		adattatorePubblicaRicettaController.aggiungiIngredienteRicetta(alimentoBean,null,1);
	}
	
}