package it.polito.tdp.food.model;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private Graph<String, DefaultWeightedEdge> grafo;
	private FoodDao dao;	
	
	public Model() {
		dao = new FoodDao();
	}

	public void creaGrafo(int calorie) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		// Aggiunta dei vertici
		Graphs.addAllVertices(this.grafo, this.dao.getAllVertici(calorie));
		
		// Aggiunta degli archi
		for (Adiacenza a : this.dao.getAllAdiacenze()) {
			Graphs.addEdge(this.grafo, a.getV1(), a.getV2(), a.getPeso());
		}
		
	}
	
	public int getNumVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNumArchi() {
		return this.grafo.edgeSet().size();
	}
}
