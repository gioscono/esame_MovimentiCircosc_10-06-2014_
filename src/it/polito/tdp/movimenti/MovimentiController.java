package it.polito.tdp.movimenti;


import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.movimenti.bean.CircoscrizioneEMovimenti;
import it.polito.tdp.movimenti.bean.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MovimentiController {
	
	private Model model;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ChoiceBox<Integer> boxCircPartenza;

	@FXML
	private Button btnElenca;

	@FXML
	private Button btnRicerca;

	@FXML
	private Label lblStatus;

	@FXML
	private ProgressBar pbProgress;

	@FXML
	private TextField txtLunghezza;

	@FXML
	private TextArea txtResult;

    @FXML
    void doElenca(ActionEvent event) {
    	txtResult.clear();
    	List<CircoscrizioneEMovimenti> ris = model.getAllMovimentiFrom(boxCircPartenza.getValue());
    	Collections.sort(ris);
    	txtResult.appendText("Movimenti dalla circ. "+boxCircPartenza.getValue()+" a:\n");
    	for(CircoscrizioneEMovimenti c : ris){
    		txtResult.appendText(c.toString()+"\n");
    	}
    }

    @FXML
    void doRicerca(ActionEvent event) {
    	txtResult.clear();
    	model.creaGrafo();
    	int partenza = boxCircPartenza.getValue();
    	String lunghezza = txtLunghezza.getText();
    	try{
    		int passi = Integer.parseInt(lunghezza);
    		List<Integer> best = model.creaRicorsione(passi, partenza);
    		txtResult.appendText("Best: "+best+"-numero di cambi: "+model.getNumeroCambiBest(best));
    	}catch(NumberFormatException e){
    		txtResult.appendText("Errore: inserisci un numero.\n");
    		return;
    	}
    }

	@FXML
	void initialize() {
		assert boxCircPartenza != null : "fx:id=\"boxCircPartenza\" was not injected: check your FXML file 'Movimenti.fxml'.";
		assert btnElenca != null : "fx:id=\"btnElenca\" was not injected: check your FXML file 'Movimenti.fxml'.";
		assert btnRicerca != null : "fx:id=\"btnRicerca\" was not injected: check your FXML file 'Movimenti.fxml'.";
		assert lblStatus != null : "fx:id=\"lblStatus\" was not injected: check your FXML file 'Movimenti.fxml'.";
		assert pbProgress != null : "fx:id=\"pbProgress\" was not injected: check your FXML file 'Movimenti.fxml'.";
		assert txtLunghezza != null : "fx:id=\"txtLunghezza\" was not injected: check your FXML file 'Movimenti.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Movimenti.fxml'.";

	}

	public void setModel(Model model) {
		this.model = model;
		boxCircPartenza.getItems().clear();
		boxCircPartenza.getItems().addAll(model.getTutteCircoscrizioni());
		boxCircPartenza.setValue(boxCircPartenza.getItems().get(0));
	}

}
