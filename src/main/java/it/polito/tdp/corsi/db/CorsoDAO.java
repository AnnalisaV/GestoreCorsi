package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.model.Corso;
import it.polito.tdp.corsi.model.Studente;
//CHIUDERE TUTTE LE CONNESISONI PRIMA DI OGNI RETURN
public class CorsoDAO {
	
	public boolean esisteCorso(String codins) {
		String sql= "SELECT * FROM corso WHERE codins= ?"; 
		// no struttura dati perche' voglio solo sapere sì o no (esiste)
		try {
			Connection conn= ConnectDB.getConnection(); 
			PreparedStatement st= conn.prepareStatement(sql); 
			st.setString(1,  codins);
			ResultSet res= st.executeQuery(); 
			
			if(res.next()) {
				conn.close(); 
				//ok ha la riga quindi una soluzione l'ha trovata 
				return true; 
				
			}else {
				conn.close(); 
				return false; // non ha trovato manco una riga 
			}
			
			
			
			
		}catch(SQLException e) {
			throw new RuntimeException(); 
		}
	}
	
	/**
	 * Dato un periodo didattico, fornisce l'elenco dei corsi tenuti in quel periodo
	 * @param pd numero di riferimento per il periodo didattico
	 * @return elenco dei corsi
	 */
	public List<Corso> getCorsiByperiodo(Integer pd){
		
		//query opportuna
		String sql="SELECT * FROM corso WHERE pd= ?"; 
		
		List<Corso> risultato= new ArrayList<>(); 
		
		try {
			Connection conn= ConnectDB.getConnection(); 
			PreparedStatement st= conn.prepareStatement(sql); 
			st.setInt(1,  pd);
			ResultSet res= st.executeQuery(); 
			
			while(res.next()) {
				Corso c= new Corso(res.getString("codins"), res.getInt("crediti"),
						res.getString("nome"), res.getInt("pd"));
				
				risultato.add(c); 
			}
			
			conn.close(); 
			
		} catch(SQLException e) {
			throw new RuntimeException(); 
		}
		return risultato; 
		
		
		
	}
	
	public Map<Corso, Integer> getIscrittiCorsoByPeriodo(Integer pd){
		
		String sql= "SELECT c.codins, c.nome, c.crediti, c.pd, COUNT(*) AS tot " + 
				"FROM corso as c, iscrizione i " + 
				"WHERE c.codins=i.codins AND c.pd= ? " + 
				"GROUP BY c.codins, c.nome, c.crediti, c.pd "; // tolgo io tutti i \n ma metto gli spazi altrimenti in SQL non lo prende
	
		Map<Corso, Integer> risultato= new HashMap<Corso, Integer>(); 
		
		try {
			Connection conn= ConnectDB.getConnection(); 
			PreparedStatement st= conn.prepareStatement(sql); 
			st.setInt(1, pd);
			ResultSet res= st.executeQuery(); 
			
			while(res.next()) {
				Corso c= new Corso(res.getString("codins"), res.getInt("crediti"), 
						res.getString("nome"), res.getInt("pd")); 
				Integer tot= res.getInt("tot"); // relativo alal nuova colonna di conto
				
				risultato.put(c,tot); 
			}
			conn.close();
		}catch(SQLException e) {
			throw new RuntimeException(); 
			
			
		}
		return risultato; 
	
	}
	
	public List<Studente> getStudentiByCorso(Corso corso){
		
		String sql="SELECT studente.matricola, studente.nome, studente.cognome, studente.CDS " + 
				"FROM iscrizione, studente " + 
				"WHERE codins= ? AND studente.matricola=iscrizione.matricola "; 
		
		List<Studente> studenti= new LinkedList<Studente>(); 
		
		try {
			Connection conn= ConnectDB.getConnection(); 
			PreparedStatement st= conn.prepareStatement(sql); 
			st.setString(1, corso.getCodins());
			ResultSet res= st.executeQuery(); 
			
			while(res.next()) {
				Studente s= new Studente(res.getInt("matricola"), res.getString("nome"), res.getString("cognome"), res.getString("CDS")); 
				studenti.add(s); 
			}
			conn.close();
		}catch(SQLException e) {
			throw new RuntimeException(); 
			
			
		}
		return studenti; 
	}

	
	public Map<String, Integer> getDivisioneCDS(Corso c){
		String sql="SELECT studente.CDS , COUNT(*) AS tot " + 
				"			FROM iscrizione, studente " + 
				"			WHERE codins= ? AND studente.cds<>\"\" and studente.matricola=iscrizione.matricola " + 
				"			GROUP BY studente.CDS "; 
		
		Map<String, Integer> statistiche= new HashMap<String, Integer>(); 
		
		try {
			Connection conn= ConnectDB.getConnection(); 
			PreparedStatement st= conn.prepareStatement(sql); 
			st.setString(1, c.getCodins());
			ResultSet res= st.executeQuery(); 
			
			while(res.next()) {
				statistiche.put(res.getString("CDS"), res.getInt("tot"));
			}
			conn.close();
		}catch(SQLException e) {
			throw new RuntimeException(); 
			
			
		}
		return statistiche; 
		
	}
}
