package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.model.Corso;

public class CorsoDAO {
	
	
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

}
