package it.polito.tdp.corsi;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.corsi.model.Corso;
import it.polito.tdp.corsi.model.Model;
import it.polito.tdp.corsi.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model; 
	
	public void setModel(Model model) {
		this.model=model; 
	}

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtPeriodo;

    @FXML
    private TextField txtCorso;

    @FXML
    private Button btnCorsiPerperiodo;

    @FXML
    private Button btnStudenti;

    @FXML
    private Button btnNumeroStudenti;

    @FXML
    private Button btnDivisioneStudenti;

    @FXML
    private TextArea txtRisultato;

    @FXML
    void corsiPerPeriodo(ActionEvent event) {
    	txtRisultato.clear();

    	String pdString= txtPeriodo.getText(); 
    	// verifico la correttezza dell'input 
    	Integer pd; 
    	
    	try {
    		
    	pd= Integer.parseInt(pdString);
    	
    	}catch(NumberFormatException nfe) {
    		
    		txtRisultato.appendText("Devi inserire un numero : 1 o 2 !! ");
    		return; 
    	}
    	
    	if (!pd.equals(1) && !pd.equals(2)) {
    		txtRisultato.appendText("Devi inserire un numero : 1 o 2 !! ");
    		return; 
    	}
    	
    	//input corretto
    	List<Corso> corsi= this.model.getCorsiByPeriodo(pd);
    	
    	for (Corso c : corsi) {
    		txtRisultato.appendText(c.toString()+"\n");
    	}
    }

    @FXML
    void numeroStudenti(ActionEvent event) {
    	txtRisultato.clear();
    	String pdString= txtPeriodo.getText(); 
    	// verifico la correttezza dell'input 
    	Integer pd; 
    	
    	try {
    		
    	pd= Integer.parseInt(pdString);
    	
    	}catch(NumberFormatException nfe) {
    		
    		txtRisultato.appendText("Devi inserire un numero : 1 o 2 !! ");
    		return; 
    	}
    	
    	if (!pd.equals(1) && !pd.equals(2)) {
    		txtRisultato.appendText("Devi inserire un numero : 1 o 2 !! ");
    		return; 
    	}
    	
    	Map<Corso, Integer> statistiche= this.model.getIscrittiCorsoByPeriodo(pd); 
    	
    	for (Corso c : statistiche.keySet()) {
    		txtRisultato.appendText(c.getNome() +" "+statistiche.get(c)+"\n");
    	}
    	

    }

    @FXML
    void stampaDivisione(ActionEvent event) {
    	// OUTPUT : Elenco di CDS con a fianco il numero di studenti appartenenti
    	// Tdp : Informatica 12
    	//       Gestionali 89
    	
            txtRisultato.clear(); 
    	
    	String codins= txtCorso.getText(); 
    	
    	// controllare che sia ok ( se lo faccio qui e' meglio perche' se passo e non esiste non so distinguere se la lista mi ritorna vuota perche' il corso non esiste o se ho sbagliato a scrivere l'input)
    	if (!this.model.esisteCorso(codins)) {
    		txtRisultato.appendText("Il corso non esiste!! \n");
    		return; 
    	}
    	
    	
    	Map<String, Integer> stat= this.model.getDivisioneCDS(new Corso(codins, null, null, null)); 

    	for (String cds : stat.keySet()) {
    		txtRisultato.appendText(cds+" "+stat.get(cds)+"\n");
    		
    	}
    }

    @FXML
    void stampaStudenti(ActionEvent event) {
    	
    	txtRisultato.clear(); 
    	
    	String codins= txtCorso.getText(); 
    	
    	// controllare che sia ok ( se lo faccio qui e' meglio perche' se passo e non esiste non so distinguere se la lista mi ritorna vuota perche' il corso non esiste o se ho sbagliato a scrivere l'input)
    	if (!this.model.esisteCorso(codins)) {
    		txtRisultato.appendText("Il corso non esiste!! \n");
    		return; 
    	}
    	List<Studente> studenti= this.model.getStudentiByCorso(new Corso(codins, null, null, null)); 
    	if (studenti.size()==0) {
    		txtRisultato.appendText("Il corso non ha studenti iscritti! \n");
    		return; 
    	}
    	// esistono studenti
    	for (Studente s : studenti) {
    		txtRisultato.appendText(s.toString()+"\n");
    	}
    	

    }

    @FXML
    void initialize() {
        assert txtPeriodo != null : "fx:id=\"txtPeriodo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCorso != null : "fx:id=\"txtCorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCorsiPerperiodo != null : "fx:id=\"btnCorsiPerperiodo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnStudenti != null : "fx:id=\"btnStudenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnNumeroStudenti != null : "fx:id=\"btnNumeroStudenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDivisioneStudenti != null : "fx:id=\"btnDivisioneStudenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";

    }
}
