package it.polito.tdp.rivers.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Simulator {
	
	//parametri
	private double Q;
	private double C;
	private int giorniInsufficienti;
	private double mediaRiverConsiderato;
	private double flowMinimo;
	private double flowEccezionale;
	private List<Double> CMedio;
	private River fiume;
	//variabili per i flussi 
	
	//stato del mondo
	PriorityQueue<Flow> eventi = new PriorityQueue<>();
	
	public void run(double Q, River r, double mediaGiornaliera) {
		
		this.Q=Q;
		this.giorniInsufficienti=0;
		this.C= Q/2;
		this.CMedio= new ArrayList<>();
		this.fiume=r;
		this.mediaRiverConsiderato= mediaGiornaliera;
		this.flowMinimo= 0.8*mediaGiornaliera;
		this.flowEccezionale= 10*this.flowMinimo;
		this.eventi.addAll(r.getFlows());
		
		
		while(!this.eventi.isEmpty())
			this.processEvent(this.eventi.poll());
		
	}
	
	private void processEvent(Flow f) {
		
		System.out.println(C);
		
		double tempC = this.C+f.getFlow()*86400;
		
		if((int)(Math.random()*100)+1<5)
			tempC-=this.flowEccezionale;
		else
			tempC-=this.flowMinimo;
		
		if(tempC>this.Q) { //tracimazione
			this.C=Q;
		}
		else if (tempC<0) {
			this.C=0;
			this.giorniInsufficienti++;
		}
		else {
			this.C=tempC;
		}
		
		this.CMedio.add(this.C);
	}

	public int getGiorniInsufficienti() {
		return giorniInsufficienti;
	}

	public Double getCMedio() {
		
		if(fiume.getFlows().size()==0)
			return null;
		
		return this.mean2(CMedio);
	}
	
	private double mean(List<Double> lista) {
		
		  double avg = 0;
		  int t = 1;
		  for (double x : lista) {
		    avg += (x - avg) / t;
		    ++t;
		  }
		  return avg;
		}
	
	private double mean2(List<Double> list) {
		
		double tot=0;
		for(Double d:list)
			tot+=d/list.size();
		return tot;
			
	}
	

}
