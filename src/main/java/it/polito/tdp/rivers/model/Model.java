package it.polito.tdp.rivers.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import it.polito.tdp.rivers.db.RiversDAO;

public class Model {
	
	
	private RiversDAO dao = new RiversDAO();
	private Map<Integer,River> fiumiMap;
	private List<River> fiumi;
	
	public Model() {
		
		this.fiumiMap = new HashMap<Integer,River>();
		this.fiumi = dao.getAllRivers(fiumiMap);
		
		for(River r: this.fiumi)
		{
			List<Flow> misurazioni = this.dao.getMisurazioniDatoFiume(r);
			r.getFlows().addAll(misurazioni);
			this.dao.getMediaFlow(r);
			
		}
		
	}
	
	
	
	public List<River> getAllRivers() {
		return this.fiumi;
	}



	public LocalDate getDataPrimaMiusurazione(River r) {
		return this.dao.getDataPrimaMisurazione(r);
	}
	
	
	public LocalDate getDataUltimaMiusurazione(River r) {
		return this.dao.getDataUltimaMisurazione(r);
	}



	public int numMisurazioni(River r) {
		return this.fiumiMap.get(r.getId()).getFlows().size();
	}



	public double getMediaMisurazioni(River r) {
		return this.fiumiMap.get(r.getId()).getFlowAvg();
	}
	
	
}
