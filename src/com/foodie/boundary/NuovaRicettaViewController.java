package com.foodie.boundary;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.foodie.controller.LoginController;
import com.foodie.controller.LoginControllerAdapter;
import com.foodie.controller.PubblicaRicettaController;
import com.foodie.controller.PubblicaRicettaControllerAdapter;
import com.foodie.controller.ControllerAdapter;
import com.foodie.model.Dispensa;
import com.foodie.model.Ricetta;
import com.foodie.model.RicettaBean;
import com.foodie.model.UtenteBean;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NuovaRicettaViewController {
	private static NuovaRicettaViewController istanza;
	private PubblicaRicettaController controller=PubblicaRicettaController.ottieniIstanza();
	private ControllerAdapter adattatorePubblicaRicettaController= PubblicaRicettaControllerAdapter.ottieniIstanza(controller);
	private LoginController loginController= LoginController.ottieniIstanza();
	private ControllerAdapter adattatoreLoginController= LoginControllerAdapter.ottieniIstanza(loginController);
	private Stage primaryStage;
	@FXML
	private RadioButton facile;
	@FXML
	private RadioButton medio;
	@FXML
	private RadioButton difficile;
	@FXML
	private TextField nome;
	@FXML
	private TextField descrizione;
	@FXML
	private Button pubblica;
	
	private NuovaRicettaViewController() {
	}
	
	public static NuovaRicettaViewController ottieniIstanza() { //METODO PER OTTENERE L'ISTANZA
		if(istanza == null) {
			istanza = new NuovaRicettaViewController();
		}
		return istanza;
	}
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage= primaryStage;
	}
	public void aggiornaView(String nome, String Descrizione, int diff) {
		this.nome.setText(nome);
		this.descrizione.setText(Descrizione);
		switch(diff) {
		case 1:
				facile.setSelected(true);
				disabilitaPulsanti(null);
				break;
		case 2:
				medio.setSelected(true);
				disabilitaPulsanti(null);
				break;
		case 3:
				difficile.setSelected(true);
				disabilitaPulsanti(null);
				break;
		}
	}
	@FXML
	private void caricaViewLogin(MouseEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
			Parent root = loader.load();
			LoginViewController loginViewController=loader.getController();
			loginViewController.setPrimaryStage(primaryStage);
			Scene nuovaScena = new Scene(root);
			primaryStage.setScene(nuovaScena);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}
	@FXML
	private void caricaViewIngredienti(ActionEvent event) {
		InserisciIngredienteViewController inserisciIngredienteViewController =InserisciIngredienteViewController.ottieniIstanza();
		if(nome.getText()!=null) {
			inserisciIngredienteViewController.setNome(nome.getText());
		}
		if(descrizione.getText()!=null) {
			inserisciIngredienteViewController.setDescrizione(descrizione.getText());
		}
		if(facile.isSelected()) {
			inserisciIngredienteViewController.setDifficolta(1);
		}
		else if(medio.isSelected()) {
			inserisciIngredienteViewController.setDifficolta(2);
		}
		else if(difficile.isSelected()) {
			inserisciIngredienteViewController.setDifficolta(3);
		}
		try {
			inserisciIngredienteViewController = InserisciIngredienteViewController.ottieniIstanza();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("InserisciIngredienteView.fxml"));
            loader.setController(inserisciIngredienteViewController);
            Ricetta ricetta =controller.getRicetta();
			ricetta.registra(inserisciIngredienteViewController);
            Parent root = loader.load();
            inserisciIngredienteViewController.setPrimaryStage(primaryStage);
            inserisciIngredienteViewController.aggiornaView();
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
	}
	@FXML
	private void compilaRicetta(ActionEvent event) {
		RicettaBean ricettaBean= new RicettaBean();
		String testo = nome.getText().trim();
		if(!testo.isEmpty()) {
			ricettaBean.setNome(nome.getText());
		}
		else {
			nome.setPromptText("INSERISCI NOME");
			 // Creazione di un oggetto ScheduledExecutorService
	        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	        // Creare una task da eseguire dopo 2 secondi
	        Runnable task = () -> {
	            // Codice da eseguire dopo 2 secondi
	            Platform.runLater(() -> nome.setPromptText("Nome Ricetta"));
	        };

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
	        Runnable task = () -> {
	            // Codice da eseguire dopo 2 secondi
	            Platform.runLater(() -> descrizione.setPromptText("Descrizione"));
	        };

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
	        Runnable task = () -> {
	            // Codice da eseguire dopo 2 secondi
	            Platform.runLater(() -> pubblica.setText("Pubblica"));
	        };

	        // Programmare la task per essere eseguita dopo 2 secondi
	        scheduler.schedule(task, 2, TimeUnit.SECONDS);

	        // Chiudere il thread scheduler dopo l'esecuzione della task
	        scheduler.shutdown();	
	        return;
		}
		ricettaBean.setDifficolta(diff);
		UtenteBean utenteBean=adattatoreLoginController.ottieniUtente();
		ricettaBean.setAutore(utenteBean.getUsername());
		VBox ingredienti= InserisciIngredienteViewController.ottieniIstanza().getContenitoreIngredienti();
        if(ingredienti!=null && !(ingredienti.getChildren().isEmpty())) { 
        	adattatorePubblicaRicettaController.compilaLaRicetta(ricettaBean);
		}
		else {
			pubblica.setText("INGREDIENTI?");
			 // Creazione di un oggetto ScheduledExecutorService
	        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	        // Creare una task da eseguire dopo 2 secondi
	        Runnable task = () -> {
	            // Codice da eseguire dopo 2 secondi
	            Platform.runLater(() -> pubblica.setText("Pubblica"));
	        };

	        // Programmare la task per essere eseguita dopo 2 secondi
	        scheduler.schedule(task, 2, TimeUnit.SECONDS);

	        // Chiudere il thread scheduler dopo l'esecuzione della task
	        scheduler.shutdown();	
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
	private void caricaViewAreaPersonale(ActionEvent event) {
		FXMLLoader loader= new FXMLLoader(getClass().getResource("AreaPersonaleView.fxml"));
		AreaPersonaleViewController controllerAreaPersonale = AreaPersonaleViewController.ottieniIstanza();
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
    private void caricaViewGestisciRicette(ActionEvent event) {
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("GestisciRicetteView.fxml"));
            GestisciRicetteViewController gestisciRicetteViewController = GestisciRicetteViewController.ottieniIstanza();
            loader.setController(gestisciRicetteViewController);
            Parent root = loader.load();
            gestisciRicetteViewController.setPrimaryStage(primaryStage);
            gestisciRicetteViewController.aggiornaView();
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
}
