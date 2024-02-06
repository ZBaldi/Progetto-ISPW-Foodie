package com.foodie.boundary;

import java.io.IOException;
import java.util.ArrayList;

import com.foodie.Applicazione.LoginViewController;
import com.foodie.controller.ControllerAdapter;
import com.foodie.controller.PubblicaRicettaController;
import com.foodie.controller.PubblicaRicettaControllerAdapter;
import com.foodie.controller.TrovaRicettaController;
import com.foodie.controller.TrovaRicettaControllerAdapter;
import com.foodie.model.AlimentoBean;
import com.foodie.model.Observer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class InserisciIngredienteViewController implements Observer{
	private static InserisciIngredienteViewController istanza;
	private TrovaRicettaController controller= TrovaRicettaController.ottieniIstanza();
	private ControllerAdapter adattatoreTrovaRicettaController= TrovaRicettaControllerAdapter.ottieniIstanza(controller);
	private PubblicaRicettaController controller2=PubblicaRicettaController.ottieniIstanza();
	private ControllerAdapter adattatorePubblicaRicettaController= PubblicaRicettaControllerAdapter.ottieniIstanza(controller2);
	private ArrayList<AlimentoBean> alimentiBeanTrovati;
	private ArrayList<AlimentoBean> alimentiBeanRicetta;
	AreaPersonaleViewController controllerAreaPersonale = AreaPersonaleViewController.ottieniIstanza();
	private boolean bottoneModifica = true;
	@FXML
	private Label labelIngredienti;
	private Stage primaryStage;
	@FXML
	private VBox contenitoreIngredienti;
	@FXML
	private VBox contenitoreAlimentiTrovati;
	@FXML
	private TextField barraDiRicerca;
	@FXML
	private TextField quantita;
	private String nome;
	private String descrizione;
	private int difficolta;
	
	private InserisciIngredienteViewController() {
	}
	
	public static InserisciIngredienteViewController ottieniIstanza() { //METODO PER OTTENERE L'ISTANZA
		if(istanza == null) {
			istanza = new InserisciIngredienteViewController();
		}
		return istanza;
	}
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage= primaryStage;
	}
	@FXML
	private void gestioneRisultati(KeyEvent event) {
		if(event.getCode() == KeyCode.ENTER) {//GETCODE() TI RESTITUISCE IL TASTO PREMUTO
			trovaAlimenti();
		}
		else if(event.getCode() == KeyCode.BACK_SPACE) {
			eliminaAlimenti();
		}
	}
	private void eliminaAlimenti() {
		if(!contenitoreAlimentiTrovati.getChildren().isEmpty()) {
			this.quantita.setDisable(true);
			contenitoreAlimentiTrovati.getChildren().clear();
		}
	}
	private void salvaAlimento(String nomeAlimento, String quantita) {
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
	@FXML
	public void caricaViewRicetta(ActionEvent event) {
		try {
			if(bottoneModifica==false) { //resettare il bottone modifica se attivo
				bottoneModifica=true;
				labelIngredienti.setFont(Font.font("Arial",30));
				labelIngredienti.setText("La mia Dispensa");
			}
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NuovaRicettaView.fxml"));
            NuovaRicettaViewController nuovaRicettaViewController= NuovaRicettaViewController.ottieniIstanza();
            loader.setController(nuovaRicettaViewController);
            Parent root = loader.load();
            nuovaRicettaViewController.setPrimaryStage(primaryStage);
            nuovaRicettaViewController.aggiornaView(nome, descrizione, difficolta);
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
	}
	@FXML
	public void caricaViewLogin(MouseEvent event) {
		try {
			if(bottoneModifica==false) { //resettare il bottone modifica se attivo
				bottoneModifica=true;
				labelIngredienti.setFont(Font.font("Arial",30));
				labelIngredienti.setText("La mia Dispensa");
			}
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
	private void trovaAlimenti() {
		alimentiBeanTrovati=adattatoreTrovaRicettaController.trovaGliAlimenti(barraDiRicerca.getText());
		if(alimentiBeanTrovati!=null) {
			quantita.setDisable(false);
			for(AlimentoBean a: alimentiBeanTrovati) {
				Label labelAlimento = new Label(a.getNome());
				labelAlimento.setStyle("-fx-background-color: white;");
				labelAlimento.setMaxWidth(Double.MAX_VALUE);
				labelAlimento.setMinHeight(30);
				labelAlimento.setWrapText(true);
				labelAlimento.setFont(Font.font("Arial"));
				labelAlimento.setAlignment(Pos.CENTER);
				labelAlimento.setOnMouseClicked(event2->{salvaAlimento(labelAlimento.getText(),quantita.getText());});
				contenitoreAlimentiTrovati.getChildren().add(labelAlimento);
			}
		}
		else {
			Label label = new Label("NESSUN RISULTATO");
			label.setStyle("-fx-background-color: white;");
			label.setMaxWidth(Double.MAX_VALUE);
			label.setMinHeight(30);
			label.setWrapText(true);
			label.setFont(Font.font("Arial"));
			label.setAlignment(Pos.CENTER);
			contenitoreAlimentiTrovati.getChildren().add(label);
		}
	}
	public void aggiornaView() {
		contenitoreIngredienti.getChildren().clear();
		alimentiBeanRicetta =adattatorePubblicaRicettaController.mostraIngredientiRicetta();
		if(alimentiBeanRicetta!=null) {
			for(AlimentoBean a: alimentiBeanRicetta) {
				Label labelAlimento = new Label(a.getNome());
				labelAlimento.setStyle("-fx-background-color: white;");
				labelAlimento.setMaxWidth(Double.MAX_VALUE);
				labelAlimento.setMinHeight(50);
				labelAlimento.setWrapText(true);
				labelAlimento.setFont(Font.font("Arial",20));
				labelAlimento.setAlignment(Pos.CENTER);
				contenitoreIngredienti.getChildren().add(labelAlimento);
			}
			impostaLabel();
		}
		if(contenitoreIngredienti.getChildren().isEmpty() && bottoneModifica==false) { //PER EVITARE CHE SE LA DISPENSA è VUOTA RIMANGA ATTIVO IL BOTTONE E IL TESTO DELLA LABEL
			bottoneModifica=true;
			labelIngredienti.setFont(Font.font("Arial",30));//ESEMPIO PREMI MODIFICA CANCELLI L'ULTIMO ELEMENTO DELLA DISPENSA ALLORA SI DEVE DISATTIVARE LA MODIFICA
			labelIngredienti.setText("Ingredienti");
		}
	}
	private void impostaLabel() {
		if(bottoneModifica==false) {
			if(!contenitoreIngredienti.getChildren().isEmpty()) {
				contenitoreIngredienti.getChildren().forEach(node->{
					Label labelAlimento= (Label)node;
					labelAlimento.setOnMouseClicked(event->{eliminaAlimento(labelAlimento.getText());});
				});
			}
		}
		else {
			if(!contenitoreIngredienti.getChildren().isEmpty()) {
				contenitoreIngredienti.getChildren().forEach(node->{
					Label labelAlimento= (Label)node;
					labelAlimento.setOnMouseClicked(null);
				});
			}
		}
	}
	private void eliminaAlimento(String nomeAlimento) {
		AlimentoBean alimentoBean = new AlimentoBean();
		alimentoBean.setNome(nomeAlimento);
		adattatorePubblicaRicettaController.aggiungiIngredienteRicetta(alimentoBean,null, 1);
	}
	@FXML
	private void modificaIngredienti(ActionEvent e) {
		if(bottoneModifica==true && !contenitoreIngredienti.getChildren().isEmpty()) {
			bottoneModifica=false;
			labelIngredienti.setFont(Font.font("Arial",20));
			labelIngredienti.setText("CLICCA L'ALIMENTO DA ELIMINARE");
			impostaLabel();
		}
		else if(bottoneModifica==false && !contenitoreIngredienti.getChildren().isEmpty()) {
			bottoneModifica=true;
			labelIngredienti.setFont(Font.font("Arial",30));
			labelIngredienti.setText("Ingredienti");
			impostaLabel();
		}
	}
	public void setNome(String text) {
		this.nome=text;
	}
	public void setDescrizione(String text) {
		this.descrizione=text;
	}
	public void setDifficolta(int i) {
		this.difficolta=i;
	}
	public VBox getContenitoreIngredienti() {
		return contenitoreIngredienti;
	}
	@FXML
	private void caricaViewAreaPersonale(ActionEvent event) {
		FXMLLoader loader= new FXMLLoader(getClass().getResource("AreaPersonaleView.fxml"));
		loader.setController(controllerAreaPersonale);
		Parent root;		
		try {
			root = loader.load();
			controllerAreaPersonale.setPrimaryStage(primaryStage);
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
            Parent root = loader.load();
            GestisciRicetteViewController gestisciRicetteViewController = loader.getController();
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
