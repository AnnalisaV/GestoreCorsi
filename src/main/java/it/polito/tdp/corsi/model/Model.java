package it.polito.tdp.corsi.model;

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

}
