package it.polito.tdp.food.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private Graph<String, DefaultWeightedEdge> grafo;
	private FoodDao dao;	
	
	// variabili utili per la ricerca del cammino
	private List<String> best;
	private int pesoMassimo;
	
	public Model() {
		dao = new FoodDao();
	}

	public void creaGrafo(int calorie) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		// Aggiunta dei vertici
		Graphs.addAllVertices(this.grafo, this.dao.getAllVertici(calorie));
		
		// Aggiunta degli archi
		for (Adiacenza a : this.dao.getAllAdiacenze()) {
			if (this.grafo.containsVertex(a.getV1()) && this.grafo.containsVertex(a.getV2())) {
				Graphs.addEdge(this.grafo, a.getV1(), a.getV2(), a.getPeso());
			}
		}
		
	}
	
	// Metodo che prepara la ricorsione
	public List<String> calcolaCamminoPesoMassimo(int N, String verticePartenza){		
		this.best = null;
		this.pesoMassimo = 0;
		
		List<String> parziale = new LinkedList<>();
		parziale.add(verticePartenza);
		
		cerca(parziale, N);
		
		return best;
	}
	
	// Metodo ricorsivo vero e proprio
	public void cerca(List<String> parziale, int N) {
		// condizione di terminazione
		if (parziale.size() == N) {
			int pesoCammino = calcolaPesoCammino(parziale);
			if (pesoCammino > this.pesoMassimo) {
				this.pesoMassimo = pesoCammino;
				this.best = new LinkedList<>(parziale);
				return;
			} else {
				// non faccio niente
				return;
			}
		}
		
		for (String s : this.grafo.vertexSet()) {
			if (!parziale.contains(s)) {
				parziale.add(s);
				cerca(parziale, N);
				parziale.remove(s);
			}
		}
	}
	
	public int calcolaPesoCammino(List<String> cammino) {
		int peso = 0;
		
		String partenza = cammino.get(0);
		
		for (int i=1; i<cammino.size(); i++) {
			String temp = cammino.get(i);
			if (this.grafo.getEdge(partenza, temp) != null) {
				peso += this.grafo.getEdgeWeight(this.grafo.getEdge(partenza, temp));
			}
			partenza = cammino.get(i);
		}
		
		return peso;
	}
	
	public List<String> getVerticiConnessi(String tipo){
		List<String> connessi = Graphs.neighborListOf(this.grafo, tipo);
		return connessi;
	}
	
	public int getPesoArco(String vertice1, String vertice2) {
		return (int)this.grafo.getEdgeWeight(this.grafo.getEdge(vertice1, vertice2));
	}
	
	public Set<String> getAllVertici(){
		return this.grafo.vertexSet();
	}
	
	public int getNumVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNumArchi() {
		return this.grafo.edgeSet().size();
	}
}
