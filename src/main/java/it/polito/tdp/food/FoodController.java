/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassi"
    private TextField txtPassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorrelate"
    private Button btnCorrelate; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="boxPorzioni"
    private ComboBox<String> boxPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCammino(ActionEvent event) {
    	txtResult.clear();
    
    	try {
    		int passi = Integer.parseInt(txtPassi.getText());
    		
    		String verticePartenza = boxPorzioni.getValue();
        	
        	if (verticePartenza == null) {
        		txtResult.appendText("Per favore selezionare un tipo di porzione!\n");
        		return;
        	}
    		
    		// Qui possiamo chiamare il metodo ricorsivo per la ricerca del cammino
    		List<String> cammino = this.model.calcolaCamminoPesoMassimo(passi, verticePartenza);
    		
    		txtResult.appendText("Il cammino di peso massimo vale : \n");
    		for (String s : cammino) {
    			txtResult.appendText(s + "\n");
    		}
    		
    	} catch (NumberFormatException e) {
    		txtResult.appendText("Inserire un numero di passi valido!\n");
    		return;
    	}
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	txtResult.clear();
    	
    	String tipo = boxPorzioni.getValue();
    	
    	if (tipo == null) {
    		txtResult.appendText("Per favore selezionare un tipo di porzione!\n");
    		return;
    	}
    	
    	List<String> connessi = this.model.getVerticiConnessi(tipo);
    	txtResult.appendText("Tipi di porzione connessi a: " + tipo + "\n");
    	for (String s : connessi) {
    		txtResult.appendText(s + " --> " + this.model.getPesoArco(tipo, s) + "\n");
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	boxPorzioni.getItems().clear();
    	
    	try {
	    	int calorie = Integer.parseInt(txtCalorie.getText());
	    	
	    	this.model.creaGrafo(calorie);
	    	txtResult.setText("Grafo creato!\n");
	    	txtResult.appendText("# Vertici " + this.model.getNumVertici() + "\n");
	    	txtResult.appendText("# Archi " + this.model.getNumArchi() + "\n");
	    	
	    	// Dopo aver creato il grafo, possiamo popolare il menu a tendina dei tipi di porzione 
	    	boxPorzioni.getItems().addAll(this.model.getAllVertici());
	    	
    	} catch (NumberFormatException e) {
    		txtResult.setText("Per favore inserire un numero valido di calorie!\n");
    		return;
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
