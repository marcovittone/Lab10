package it.polito.tdp.rivers.model;

import java.util.List;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {
	
	
	private RiversDAO dao = new RiversDAO();
	
	
	
	public List<River> getAllRivers() {
		return this.dao.getAllRivers();
	}
	
	
}
