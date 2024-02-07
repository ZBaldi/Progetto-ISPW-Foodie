package com.foodie.boundary;

import java.io.IOException;
import java.util.List;

import com.foodie.applicazione.LoginViewController;
import com.foodie.controller.AdattatoreFactory;
import com.foodie.controller.ControllerAdapter;
import com.foodie.controller.PubblicaRicettaController;
import com.foodie.controller.PubblicaRicettaControllerAdapter;
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
	private AdattatoreFactory factory = AdattatoreFactory.ottieniIstanza();
	private PubblicaRicettaController controller = PubblicaRicettaController.ottieniIstanza();
	private ControllerAdapter adattatoreTrovaRicettaController= factory.creaTrovaRicettaAdapter();
	private ControllerAdapter adattatorePubblicaRicettaController = PubblicaRicettaControllerAdapter.ottieniIstanza(controller);
	private AreaPersonaleViewController controllerAreaPersonale = AreaPersonaleViewController.ottieniIstanza();
	private boolean bottoneModifica = true;
	private Stage primaryStage;
	private static final String FORMATO = "Arial";
	private static final String SFONDOBIANCO = "-fx-background-color: white;";
	@FXML
	private Label labelIngredienti;
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
	
	public static synchronized InserisciIngredienteViewController ottieniIstanza() { //METODO PER OTTENERE L'ISTANZA
		if(istanza == null) {
			istanza = new InserisciIngredienteViewController();
		}
		return istanza;
	}
	
	public void setPrimaryStage(Stage primaryStage) {  //PASSO LO STAGE
		this.primaryStage= primaryStage;
	}
	
	@FXML
	private void gestioneRisultati(KeyEvent event) {  //GESTISCE LA BARRA DI RICERCA DEGLI ALIMENTI
		if(event.getCode() == KeyCode.ENTER) {//GETCODE() TI RESTITUISCE IL TASTO PREMUTO
			trovaAlimenti();
		}
		else if(event.getCode() == KeyCode.BACK_SPACE) {
			eliminaAlimenti();
		}
	}
	
	private void eliminaAlimenti() {  //PULISCE GLI ALIMENTI TROVATI
		if(!contenitoreAlimentiTrovati.getChildren().isEmpty()) {
			this.quantita.setDisable(true);
			contenitoreAlimentiTrovati.getChildren().clear();
		}
	}
	
	private void salvaAlimento(String nomeAlimento, String quantita) { //SALVA ALIMENTO NELLA RICETTA
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
	public void caricaViewRicetta(ActionEvent event) {  //CARICA VIEW RICETTA
		try {
			if(!bottoneModifica) { //resettare il bottone modifica se attivo
				bottoneModifica=true;
				labelIngredienti.setFont(Font.font(FORMATO,30));
				labelIngredienti.setText("La mia Dispensa");
			}
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NuovaRicettaView.fxml"));
            NuovaRicettaViewController nuovaRicettaViewController= NuovaRicettaViewController.ottieniIstanza();
            loader.setController(nuovaRicettaViewController);
            Parent root = loader.load();
            nuovaRicettaViewController.setPrimaryStage(primaryStage);
            nuovaRicettaViewController.aggiornaView(nome, descrizione, difficolta);  //RIPOPOLA LO STATO
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
	}
	
	@FXML
	public void tornaAlLogin(MouseEvent event) {  //CARICA VIEW LOGIN
		try {
			if(!bottoneModifica) { //resettare il bottone modifica se attivo
				bottoneModifica=true;
				labelIngredienti.setFont(Font.font(FORMATO,30));
				labelIngredienti.setText("La mia Dispensa");
			}
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
	
	private void trovaAlimenti() { //GESTISCE IL TROVA ALIMENTI
		List<AlimentoBean> alimentiBeanTrovati=adattatoreTrovaRicettaController.trovaGliAlimenti(barraDiRicerca.getText());
		if(!alimentiBeanTrovati.isEmpty()) {
			quantita.setDisable(false);
			for(AlimentoBean a: alimentiBeanTrovati) {
				Label labelAlimento = new Label(a.getNome());
				labelAlimento.setStyle(SFONDOBIANCO);
				labelAlimento.setMaxWidth(Double.MAX_VALUE);
				labelAlimento.setMinHeight(30);
				labelAlimento.setWrapText(true);
				labelAlimento.setFont(Font.font(FORMATO));
				labelAlimento.setAlignment(Pos.CENTER);
				labelAlimento.setOnMouseClicked(event2->salvaAlimento(labelAlimento.getText(),quantita.getText()));
				contenitoreAlimentiTrovati.getChildren().add(labelAlimento);
			}
		}
		else {//NESSUN RISULTATO
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
	
	public void aggiornaView() {  //AGGIORNA GLI INGREDIENTI DELLA RICETTA
		contenitoreIngredienti.getChildren().clear();
		List<AlimentoBean> alimentiBeanRicetta=adattatorePubblicaRicettaController.mostraIngredientiRicetta();
		if(!alimentiBeanRicetta.isEmpty()) {
			for(AlimentoBean a: alimentiBeanRicetta) {
				Label labelAlimento = new Label(a.getNome());
				labelAlimento.setStyle(SFONDOBIANCO);
				labelAlimento.setMaxWidth(Double.MAX_VALUE);
				labelAlimento.setMinHeight(50);
				labelAlimento.setWrapText(true);
				labelAlimento.setFont(Font.font(FORMATO,20));
				labelAlimento.setAlignment(Pos.CENTER);
				contenitoreIngredienti.getChildren().add(labelAlimento);
			}
			impostaLabel();
		}
		if(contenitoreIngredienti.getChildren().isEmpty() && !bottoneModifica) { //PER EVITARE CHE SE LA DISPENSA è VUOTA RIMANGA ATTIVO IL BOTTONE E IL TESTO DELLA LABEL
			bottoneModifica=true;
			labelIngredienti.setFont(Font.font(FORMATO,30));//ESEMPIO PREMI MODIFICA CANCELLI L'ULTIMO ELEMENTO DELLA DISPENSA ALLORA SI DEVE DISATTIVARE LA MODIFICA
			labelIngredienti.setText("Ingredienti");
		}
	}
	
	private void impostaLabel() {  //IMPOSTA LE LABEL DELLA RICETTA CLICCABILI
		if(!bottoneModifica) {
			if(!contenitoreIngredienti.getChildren().isEmpty()) {
				contenitoreIngredienti.getChildren().forEach(node->{
					Label labelAlimento= (Label)node;
					labelAlimento.setOnMouseClicked(event->eliminaAlimento(labelAlimento.getText()));
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
	
	private void eliminaAlimento(String nomeAlimento) {  //ELIMINA ALIMENTO RICETTA
		AlimentoBean alimentoBean = new AlimentoBean();
		alimentoBean.setNome(nomeAlimento);
		adattatorePubblicaRicettaController.aggiungiIngredienteRicetta(alimentoBean,null, 1);
	}
	
	@FXML
	private void modificaIngredienti(ActionEvent e) {  //GESTISCE PULSANTE MODIFICA
		if(bottoneModifica && !contenitoreIngredienti.getChildren().isEmpty()) {
			bottoneModifica=false;
			labelIngredienti.setFont(Font.font(FORMATO,20));
			labelIngredienti.setText("CLICCA L'ALIMENTO DA ELIMINARE");
			impostaLabel();
		}
		else if(!bottoneModifica && !contenitoreIngredienti.getChildren().isEmpty()) {
			bottoneModifica=true;
			labelIngredienti.setFont(Font.font(FORMATO,30));
			labelIngredienti.setText("Ingredienti");
			impostaLabel();
		}
	}
	
	public void setNome(String text) {  //SETTERS PER SALVARE LO STATO DEL NUOVA RICETTA VIEW
		this.nome=text;
	}
	
	public void setDescrizione(String text) {
		this.descrizione=text;
	}
	
	public void setDifficolta(int i) {
		this.difficolta=i;
	}
	
	public VBox getContenitoreIngredienti() {  //RESTITUISCE CONTENITORE INGREDIENTI
		return contenitoreIngredienti;
	}
	
	@FXML
	private void caricaViewAreaPersonale(ActionEvent event) {  //CARICA VIEW AREA PERSONALE
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
    private void caricaViewGestisciRicette(ActionEvent event) {  //CARICA VIEW GESTISCI RICETTE
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