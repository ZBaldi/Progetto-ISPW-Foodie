package com.foodie.boundary2;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Client extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader= new FXMLLoader(getClass().getResource("LoginView2.fxml"));
		Parent root = loader.load();
		LoginView2Controller loginViewController = loader.getController();
		loginViewController.setPrimaryStage(primaryStage);
		primaryStage.setResizable(false);  // Finestra non espandibile
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();  
	}

	public static void main(String[] args) {
		launch(args);
	}
}
