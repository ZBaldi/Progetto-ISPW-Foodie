package com.foodie.boundary;

import java.util.ArrayList;

import com.foodie.controller.LoginController;
import com.foodie.controller.TrovaRicettaController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import com.foodie.model.Alimento;
import com.foodie.model.AlimentoBean;
import com.foodie.model.Dispensa;
import com.foodie.model.Observer;
import com.foodie.model.RicettaBean;

public class DispensaUtenteViewController implements Observer {
	private static DispensaUtenteViewController istanza;  //SINGLETON
	private TrovaRicettaController controller = new TrovaRicettaController();
	private ArrayList<AlimentoBean> alimentiBeanTrovati;
	private ArrayList<AlimentoBean> alimentiBeanDispensa;
	private LoginController controllerLogin= new LoginController();
	private String username;
	private boolean bottoneModifica = true;
	private Stage primaryStage;
	@FXML
	private TextField barraDiRicerca;
	@FXML
	private VBox contenitoreAlimentiTrovati;
	@FXML
	private VBox contenitoreDispensa;
	@FXML
	private Label labelDispensa;
	private DispensaUtenteViewController() {
	}
	public static DispensaUtenteViewController ottieniIstanza() { //METODO PER OTTENERE L'ISTANZA
		if(istanza==null) {
			istanza=new DispensaUtenteViewController();
		}
		return istanza;
	}	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage= primaryStage;
	}
	@FXML
	private void svuotaDispensa(ActionEvent event) {
		controller.svuotaDispensa();
	}
	@FXML
	private void caricaViewTrovaRicetta(ActionEvent event) {
		try {
			if(bottoneModifica==false) { //resettare il bottone modifica se attivo
				bottoneModifica=true;
				labelDispensa.setFont(Font.font("Arial",30));
				labelDispensa.setText("La mia Dispensa");
			}
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TrovaRicetteView.fxml"));
            Parent root = loader.load();
            TrovaRicetteViewController trovaRicetteViewController=loader.getController();
            trovaRicette(trovaRicetteViewController);
            trovaRicetteViewController.setPrimaryStage(primaryStage);
            Scene nuovaScena = new Scene(root);
            primaryStage.setScene(nuovaScena);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
	}
	private void trovaRicette(TrovaRicetteViewController trovaRicetteViewController) {
		ArrayList<RicettaBean> ricetteTrovate= null;
		int count=0;
		VBox contenitoreRicette=trovaRicetteViewController.getContenitoreRicette();
		for(int i=1;i<4;i++) {
			ricetteTrovate=controller.trovaRicette(i);
			if(ricetteTrovate!=null) {
				for(RicettaBean r: ricetteTrovate) {
					HBox contenitoreRicettaSingola = new HBox();
					contenitoreRicettaSingola.setAlignment(Pos.CENTER_LEFT);
					contenitoreRicettaSingola.setStyle("-fx-background-color: rgba(217, 217, 217, 0.75);-fx-border-color: black;");
					contenitoreRicettaSingola.setMinHeight(110);
					contenitoreRicettaSingola.setMaxWidth(Double.MAX_VALUE);
					Label labelNome = new Label(r.getNome());
					labelNome.setMinWidth(313);
					labelNome.setMinHeight(65);
					labelNome.setFont(Font.font("Arial",20));
					labelNome.setAlignment(Pos.CENTER);
					Label labelAutore = new Label(r.getAutore());
					labelAutore.setMinWidth(313);
					labelAutore.setMinHeight(65);
					labelAutore.setFont(Font.font("Arial",20));
					labelAutore.setAlignment(Pos.CENTER);
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
					}
					Label labelDifficolta = new Label(difficolta);
					labelDifficolta.setMinWidth(313);
					labelDifficolta.setMinHeight(65);
					labelDifficolta.setFont(Font.font("Arial",20));
					labelDifficolta.setAlignment(Pos.CENTER);
					contenitoreRicettaSingola.getChildren().addAll(labelNome,labelAutore,labelDifficolta);
					contenitoreRicette.getChildren().add(contenitoreRicettaSingola);
				}
				contenitoreRicette.getChildren().forEach(node -> {
			        HBox contenitoreRicetta = (HBox) node;
			        contenitoreRicetta.setOnMouseClicked(event -> {
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
			            trovaRicetteViewController.caricaViewRicetta(nomeRicetta, autoreRicetta, difficoltaRicetta);
			        });
			    });
			}
			else{
				count++;
				if(count==3) {
					Label label=new Label("NESSUN RISULTATO");
					label.setStyle("-fx-background-color: rgba(217, 217, 217, 0.75);-fx-border-color: black;");
					label.setMaxWidth(Double.MAX_VALUE);
					label.setMinHeight(110);
					label.setWrapText(true);
					label.setFont(Font.font("Arial",50));
					label.setAlignment(Pos.CENTER);
					contenitoreRicette.getChildren().add(label);
				}
			}
		}
	}
	@FXML
	private void caricaViewLogin(MouseEvent event) {
		controller.svuotaDispensa();
		try {
			if(bottoneModifica==false) { //resettare il bottone modifica se attivo
				bottoneModifica=true;
				labelDispensa.setFont(Font.font("Arial",30));
				labelDispensa.setText("La mia Dispensa");
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
	@FXML
	private void gestioneRisultati(KeyEvent event) {
		if(event.getCode() == KeyCode.ENTER) {//GETCODE() TI RESTITUISCE IL TASTO PREMUTO
			trovaAlimenti();
		}
		else if(event.getCode() == KeyCode.BACK_SPACE) {
			eliminaAlimenti();
		}
	}
	private void trovaAlimenti() {
			alimentiBeanTrovati=controller.trovaAlimenti(barraDiRicerca.getText());
			if(alimentiBeanTrovati!=null) {
				for(AlimentoBean a: alimentiBeanTrovati) {
					Label labelAlimento = new Label(a.getNome());
					labelAlimento.setStyle("-fx-background-color: white;");
					labelAlimento.setMaxWidth(Double.MAX_VALUE);
					labelAlimento.setMinHeight(30);
					labelAlimento.setWrapText(true);
					labelAlimento.setFont(Font.font("Arial"));
					labelAlimento.setAlignment(Pos.CENTER);
					labelAlimento.setOnMouseClicked(event2->{salvaAlimento(labelAlimento.getText());eliminaAlimenti();});
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
	private void salvaAlimento(String nomeAlimento) {
		AlimentoBean alimentoBean = new AlimentoBean();
		alimentoBean.setNome(nomeAlimento);
		controller.aggiornaDispensa(alimentoBean, 0);
	}
	private void eliminaAlimenti() {
		if(!contenitoreAlimentiTrovati.getChildren().isEmpty()) {
			contenitoreAlimentiTrovati.getChildren().clear();
		}
	}
	public void aggiornaView() {
		contenitoreDispensa.getChildren().clear();
		alimentiBeanDispensa =controller.mostraDispensa();
		if(alimentiBeanDispensa!=null) {
			for(AlimentoBean a: alimentiBeanDispensa) {
				Label labelAlimento = new Label(a.getNome());
				labelAlimento.setStyle("-fx-background-color: white;");
				labelAlimento.setMaxWidth(Double.MAX_VALUE);
				labelAlimento.setMinHeight(50);
				labelAlimento.setWrapText(true);
				labelAlimento.setFont(Font.font("Arial",20));
				labelAlimento.setAlignment(Pos.CENTER);
				contenitoreDispensa.getChildren().add(labelAlimento);
			}
			impostaLabel();
		}
		if(contenitoreDispensa.getChildren().isEmpty() && bottoneModifica==false) { //PER EVITARE CHE SE LA DISPENSA è VUOTA RIMANGA ATTIVO IL BOTTONE E IL TESTO DELLA LABEL
			bottoneModifica=true;
			labelDispensa.setFont(Font.font("Arial",30));//ESEMPIO PREMI MODIFICA CANCELLI L'ULTIMO ELEMENTO DELLA DISPENSA ALLORA SI DEVE DISATTIVARE LA MODIFICA
			labelDispensa.setText("La mia Dispensa");
		}
	}
	@FXML
	private void modificaDispensa(ActionEvent e) {
		if(bottoneModifica==true && !contenitoreDispensa.getChildren().isEmpty()) {
			bottoneModifica=false;
			labelDispensa.setFont(Font.font("Arial",20));
			labelDispensa.setText("CLICCA L'ALIMENTO DA ELIMINARE");
			impostaLabel();
		}
		else if(bottoneModifica==false && !contenitoreDispensa.getChildren().isEmpty()) {
			bottoneModifica=true;
			labelDispensa.setFont(Font.font("Arial",30));
			labelDispensa.setText("La mia Dispensa");
			impostaLabel();
		}
	}
	private void eliminaAlimento(String nomeAlimento) {
		AlimentoBean alimentoBean = new AlimentoBean();
		alimentoBean.setNome(nomeAlimento);
		controller.aggiornaDispensa(alimentoBean, 1);
	}
	private void impostaLabel() {
		if(bottoneModifica==false) {
			if(!contenitoreDispensa.getChildren().isEmpty()) {
				contenitoreDispensa.getChildren().forEach(node->{
					Label labelAlimento= (Label)node;
					labelAlimento.setOnMouseClicked(event->{eliminaAlimento(labelAlimento.getText());});
				});
			}
		}
		else {
			if(!contenitoreDispensa.getChildren().isEmpty()) {
				contenitoreDispensa.getChildren().forEach(node->{
					Label labelAlimento= (Label)node;
					labelAlimento.setOnMouseClicked(null);
				});
			}
		}
	}
	public void setUsername(String username) {
		this.username=username;
	}
	@FXML
	private void salvaDispensa(ActionEvent event) {
		controllerLogin.salvaDispensa(username);
	}
}