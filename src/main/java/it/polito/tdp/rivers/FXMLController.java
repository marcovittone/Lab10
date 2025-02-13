package it.polito.tdp.rivers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import it.polito.tdp.rivers.model.Model;
import it.polito.tdp.rivers.model.River;
import it.polito.tdp.rivers.model.Simulator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model m;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<River> boxRiver;

    @FXML
    private TextField txtStartDate;

    @FXML
    private TextField txtEndDate;

    @FXML
    private TextField txtNumMeasurements;

    @FXML
    private TextField txtFMed;

    @FXML
    private TextField txtK;

    @FXML
    private Button btnSimula;

    @FXML
    private TextArea txtResult;

    @FXML
    void handleMagicBox(ActionEvent event) {
    	
    	this.txtStartDate.clear();
    	this.txtEndDate.clear();
    	this.txtNumMeasurements.clear();
    	this.txtFMed.clear();
    	
    	River r = this.boxRiver.getValue();
    	
    	LocalDate ldp = this.m.getDataPrimaMiusurazione(r);
    	LocalDate ldu = this.m.getDataUltimaMiusurazione(r);
    	
    	int numMisurazioni = this.m.numMisurazioni(r);
    	double media = this.m.getMediaMisurazioni(r);
    	
    	this.txtStartDate.setText(ldp.toString());
    	this.txtEndDate.setText(ldu.toString());
    	this.txtNumMeasurements.setText(String.valueOf(numMisurazioni));
    	this.txtFMed.setText(String.valueOf(media));
    	
    }

    @FXML
    void handleSimula(ActionEvent event) {
    	
    	this.txtResult.clear();
    	
    	River r = this.boxRiver.getValue();
    	
    	if(r== null)
    	{
    		this.txtK.clear();
    		this.txtResult.setText("errore devi prima selezionare il fiume dal menu a tendina");
			return;
    	}
    	
    	String s = this.txtK.getText();
    	double k;
    	
    	try {
    		k=Double.parseDouble(s);
			
		} catch (NumberFormatException e) {
			
			this.txtResult.setText("errore devi inserire un numero");
			this.txtK.clear();
			return;
		}
    	
    	if(k<=0)
    		{
    			this.txtResult.setText("errore devi inserire un numero maggiore di zero");
    			this.txtK.clear();
    			return;
    		}
    	
    	double mediaGiornaliera = this.m.getMediaMisurazioni(r)*60*60*24;
    	double Q = k*mediaGiornaliera*(30);
    	
    	Simulator simulator = new Simulator();
    	simulator.run(Q, r, mediaGiornaliera);
    	
    	this.txtResult.appendText("LIVELLO DI ACQUA MEDIO :"+simulator.getCMedio()+" m^3/giorno");
    	this.txtResult.appendText("\nCON INSUFFICIENZE REGISTRATE IN "+simulator.getGiorniInsufficienti()+" GIORNI");

    }

    @FXML
    void initialize() {
        assert boxRiver != null : "fx:id=\"boxRiver\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtStartDate != null : "fx:id=\"txtStartDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtEndDate != null : "fx:id=\"txtEndDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNumMeasurements != null : "fx:id=\"txtNumMeasurements\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtFMed != null : "fx:id=\"txtFMed\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model m) {
    	this.m=m;
    	this.boxRiver.getItems().addAll(this.m.getAllRivers());
    	
    }
}
