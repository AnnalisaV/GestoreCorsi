package it.polito.tdp.corsi.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.db.CorsoDAO;

public class Model {
	
	CorsoDAO dao= new CorsoDAO(); //per non crearlo mille volte in ogni metodo 
	
	
	public List<Corso> getCorsiByPeriodo(Integer pd){
	return dao.getCorsiByperiodo(pd); 
	}
	
	public Map<Corso, Integer> getIscrittiCorsoByPeriodo(Integer pd){
		return dao.getIscrittiCorsoByPeriodo(pd); 
		
		
	}
	
	public List<Studente> getStudentiByCorso(Corso c){
		return dao.getStudentiByCorso(c); 
	}
	
	public boolean esisteCorso(String codins) {
		return dao.esisteCorso(codins); 
	}
	
	
	public Map<String, Integer> getDivisioneCDS(Corso c){
		// tutta la logica qui senza toccare il DAO ( a meno che non lo implemento tramite query che fa gia' tutto)
		/*List<Studente> studenti= dao.getStudentiByCorso(c); 
		
		Map<String, Integer> statistiche= new HashMap<String, Integer>(); // dove le chiavi sono univoche e rappresentano i cds
		// la scorro per estrarne i numeri
		for (Studente s: studenti) {
			
			if (s.getCds() != null && !s.getCds().equals("")) {
			if(statistiche.containsKey(s.getCds())) {
				//sovrascrivo aggiugendo una persona
			
			statistiche.put(s.getCds(), statistiche.get(s.getCds()+1)); // aumento il numero di iscritti
			}
			else {
				//prima volta che lo incontro come cds, lo 'creo'
				statistiche.put(s.getCds(), 1); 
			}
			}
			
		}
		return statistiche; 
		*/
		
		// fatto solo dalla query che delega tutto al DAO e soprattutto migliora le prestazioni
		return this.dao.getDivisioneCDS(c); 
	}

}
