package com.foodie.boundary;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.foodie.controller.PubblicaRicettaController;
import com.foodie.model.RicettaBean;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class NuovaRicettaViewController {
	private PubblicaRicettaController controller=new PubblicaRicettaController();
	private Stage primaryStage;
	@FXML
	private ToggleButton facile;
	@FXML
	private ToggleButton medio;
	@FXML
	private ToggleButton difficile;
	@FXML
	private TextField nome;
	@FXML
	private TextField descrizione;
	@FXML
	private Button pubblica;
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage= primaryStage;
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
		try {
			InserisciIngredienteViewController inserisciIngredienteViewController = InserisciIngredienteViewController.ottieniIstanza();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("InserisciIngredienteView.fxml"));
            loader.setController(inserisciIngredienteViewController);
            Parent root = loader.load();
            inserisciIngredienteViewController.setPrimaryStage(primaryStage);
            //inserisciIngredienteViewController.aggiornaView();
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
	}
	@FXML
	private void compilaRicetta(ActionEvent event) {
		//QUESTA ROBA SI DEVE FARE SOLO SE INGREDIENTI != EMPTY
		RicettaBean ricettaBean= new RicettaBean();
		String testo = nome.getText().trim();
		if(!testo.isEmpty()) {
			ricettaBean.setDescrizione(nome.getText());
		}
		else {
			nome.setPromptText("INSERISCI NOME");
			ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	        // Creare una task da eseguire dopo 2 secondi
	        Runnable task = () -> {
	            nome.setPromptText("Nome Ricetta");
	        };
	        // Programmare la task per essere eseguita dopo 2 secondi
	        scheduler.schedule(task, 2, TimeUnit.SECONDS);
	        // Chiudere il thread scheduler dopo l'esecuzione della task
	        scheduler.shutdown();	
	        return;
		}
		ricettaBean.setNome(nome.getText());
		testo = descrizione.getText().trim();
		if(!testo.isEmpty()) {
			ricettaBean.setDescrizione(descrizione.getText());
		}
		else {
			descrizione.setPromptText("INSERISCI DESCRIZIONE");
			ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	        // Creare una task da eseguire dopo 2 secondi
	        Runnable task = () -> {
	            descrizione.setPromptText("Descrizione");
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
			ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	        // Creare una task da eseguire dopo 2 secondi
	        Runnable task = () -> {
	            pubblica.setText("Pubblica");
	        };
	        // Programmare la task per essere eseguita dopo 2 secondi
	        scheduler.schedule(task, 2, TimeUnit.SECONDS);
	        // Chiudere il thread scheduler dopo l'esecuzione della task
	        scheduler.shutdown();	
	        return;
		}
		ricettaBean.setDifficolta(diff);
		ricettaBean.setAutore("NOME CHEF DA FARE");
		controller.compilaRicetta(ricettaBean);
	}
}
