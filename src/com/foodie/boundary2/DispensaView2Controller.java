package com.foodie.boundary2;



import java.util.List;
import com.foodie.controller.AdattatoreFactory;
import com.foodie.controller.ControllerAdapter;
import com.foodie.controller.LoginController;
import com.foodie.controller.TrovaRicettaController;
import com.foodie.model.AlimentoBean;
import com.foodie.model.Observer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class DispensaView2Controller implements Observer{
	
	private static DispensaView2Controller istanza;
	private AdattatoreFactory factory= AdattatoreFactory.ottieniIstanza();
	private TrovaRicettaController controller = TrovaRicettaController.ottieniIstanza();
	private LoginController loginController= LoginController.ottieniIstanza();
	private ControllerAdapter adattatoreTrovaRicettaController= factory.creaTrovaRicettaAdapter();
	private CaricaView2 caricaView2= CaricaView2.ottieniIstanza();
	private Stage primaryStage;
	@FXML
	private VBox contenitoreDispensa;
	
	private DispensaView2Controller() {	
	}
	
	public static synchronized DispensaView2Controller ottieniIstanza() { //SINGLETON  METODO PER OTTENERE L'ISTANZA
		if(istanza == null) {
			istanza = new DispensaView2Controller();
		}
		return istanza;
	}
	
	public void setPrimaryStage(Stage primaryStage) {  //PASSO LO STAGE
		this.primaryStage= primaryStage;
	}
	
	@FXML
    private void tornaAlLogin(MouseEvent event) { //CARICA VIEW LOGIN
        caricaView2.tornaAlLogin(primaryStage);
    }
	
	@FXML
    private void caricaViewAlimenti(ActionEvent event) {  //CARICA VIEW TROV ALIMENTI
        caricaView2.caricaViewAlimenti(primaryStage);
    }
	
	@FXML
    private void caricaViewTrovaRicetta(ActionEvent event) {  //CARICA VIEW TROVA RICETTA
        caricaView2.caricaViewTrovaRicetta(primaryStage);
    }
	
	@FXML
	private void svuotaDispensa(ActionEvent event) {  //SVUOTA DISPENSA
		controller.svuotaDispensa();
		loginController.salvaDispensa(); //SALVO DISPENSA SU FILE IN AUTOMATICO
	}
	
	public void aggiornaView() {  //AGGIORNA LA VIEW IN FUNZIONE DELLA DISPENSA
		contenitoreDispensa.getChildren().clear();
		List<AlimentoBean> alimentiBeanDispensa =adattatoreTrovaRicettaController.mostraLaDispensa();
		if(!alimentiBeanDispensa.isEmpty()) {
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
	}
	
	private void impostaLabel() {  //IMPOSTA LABEL DISPENSA CLICCABILI
			if(!contenitoreDispensa.getChildren().isEmpty()) {
				contenitoreDispensa.getChildren().forEach(node->{
					Label labelAlimento= (Label)node;
					labelAlimento.setOnMouseClicked(event->eliminaAlimento(labelAlimento.getText()));
				});
			}
		}
	
	private void eliminaAlimento(String nomeAlimento) {  //ELIMINA ALIMENTO DALLA DISPENSA
		AlimentoBean alimentoBean = new AlimentoBean();
		alimentoBean.setNome(nomeAlimento);
		adattatoreTrovaRicettaController.modificaDispensa(alimentoBean, 1);
		loginController.salvaDispensa(); //SALVO DISPENSA SU FILE IN AUTOMATICO
	}
	
}