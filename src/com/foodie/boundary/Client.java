package com.foodie.boundary;


import com.foodie.model.CatalogoRicetteImplementazione2Dao;
import com.foodie.model.CatalogoRicetteImplementazioneDao;
import com.foodie.model.Ricetta;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

import com.foodie.model.Alimento;
import  com.foodie.model.CatalogoRicetteChefDao;

public class Client {//extends Application {
	/*@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader= new FXMLLoader(getClass().getResource("LoginView.fxml"));
		Parent root= loader.load();
		LoginViewController loginViewController=loader.getController();
		loginViewController.setPrimaryStage(primaryStage);
		primaryStage.setResizable(false);  //NON ZOOMABILE
		Scene scene= new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();  
	}*/

	public static void main(String[] args) {
		//launch(args);
		CatalogoRicetteChefDao a= CatalogoRicetteImplementazione2Dao.ottieniIstanza();
		ArrayList<Alimento> ali= new ArrayList<Alimento>();
		ArrayList<String> qua= new ArrayList<String>();
		ali.add(new Alimento("pino"));
		ali.add(new Alimento("pino2"));
		qua.add("pino11");
		qua.add("pino22");
		Ricetta ricettaDiEsempio = new Ricetta("a","b",3,ali,"caccone", qua);
	    try {
			a.aggiungiRicetta(ricettaDiEsempio);
			//a.eliminaRicetta("a","caccone");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
