package it.polito.tdp.movimenti.bean;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.movimenti.dao.MovimentiDAO;

public class Model {

	private MovimentiDAO dao;
	private SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> grafo;
	private List<Integer> circoscrizioni;
	
	public Model(){
		dao = new MovimentiDAO();
	}
	
	public List<Integer> getTutteCircoscrizioni() {
		if(circoscrizioni == null){
			this.circoscrizioni = dao.getAllCircoscrizioni();
		}
		return circoscrizioni;
	}

	public List<CircoscrizioneEMovimenti> getAllMovimentiFrom(int partenza){
		return dao.getAllMovimentiFrom(partenza);
	}
	
	
	public void creaGrafo(){
		
		grafo = new SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		List<Integer> vertici = this.getTutteCircoscrizioni();
		
		//aggiungo le circoscrizioni come vertici
		Graphs.addAllVertices(grafo, vertici);
		System.out.println(grafo.vertexSet().size()+"-"+grafo.edgeSet().size());
		for(Integer partenza : grafo.vertexSet()){
			List<CircoscrizioneEMovimenti> movimenti = dao.getAllMovimentiFrom(partenza);
			for(CircoscrizioneEMovimenti  destinazione: movimenti){
				if(partenza!=destinazione.getCircDestinazione())
					Graphs.addEdgeWithVertices(grafo, partenza, destinazione.getCircDestinazione(), destinazione.getNumeroMovimenti());
			}
		}
		
		System.out.println(grafo.vertexSet().size()+"-"+grafo.edgeSet().size());
		System.out.println(grafo);
		
	}

	public List<Integer> creaRicorsione(int lunghezza, int partenza) {
		
		List<Integer> parziale = new ArrayList<>();
		List<Integer> finale = new ArrayList<>();
		parziale.add(partenza);
		
		ricorsione(parziale, finale, lunghezza);
		
		return finale;
		
	}

	private void ricorsione(List<Integer> parziale, List<Integer> finale, int lunghezza) {
		
		if(parziale.size()==lunghezza){
			//System.out.println(parziale+"-"+calcolaPeso(parziale));
			if(finale.isEmpty()){
				finale.addAll(parziale);
			}else{
				if(calcolaPeso(parziale)>calcolaPeso(finale)){
					finale.clear();
					finale.addAll(parziale);
				}
			}
			return;
		}
		
		for(Integer i : this.getTutteCircoscrizioni()){
			if(!parziale.contains(i)){
				
				parziale.add(i);
				
				ricorsione(parziale, finale, lunghezza);
				
				parziale.remove(i);
			}
		}
		
		
	}

	private int calcolaPeso(List<Integer> lista) {
		int peso = 0;
		int lung = lista.size();
		for(int i =0 ; i<lung-1; i++){
			peso += grafo.getEdgeWeight(grafo.getEdge(lista.get(i), lista.get(i+1)));
		}
		return peso;
	}

	public int getNumeroCambiBest(List<Integer> best) {
		
		return calcolaPeso(best);
	}
}
