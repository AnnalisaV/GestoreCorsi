package it.polito.tdp.corsi.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model model= new Model(); 
		
		System.out.println(model.getCorsiByPeriodo(1)); 
		
		System.out.println("\n-----"); 
		System.out.println(model.getIscrittiCorsoByPeriodo(2)); 

	}

}
