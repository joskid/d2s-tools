package org.data2semantics.tools.kernels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.data2semantics.tools.graphs.Edge;
import org.data2semantics.tools.graphs.Vertex;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Tree;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

public class IntersectionGraphKernel extends GraphKernel {
	private int maxLength;
	private double discountFactor;
	
	public IntersectionGraphKernel(List<DirectedGraph<Vertex<String>, Edge<String>>> graphs, int maxLength, double discountFactor) {
		super(graphs);
		this.label = "Intersection Graph Kernel, maxLength=" + maxLength + ", lambda=" + discountFactor;
		this.maxLength = maxLength;
		this.discountFactor = discountFactor;
	}

	@Override
	public void compute() {
		DirectedGraph<Vertex<String>, Edge<String>> graph;

		for (int i = 0; i < graphs.size(); i++) {
			for (int j = i; j < graphs.size(); j++) {				
				graph = computeIntersectionGraph(graphs.get(i), graphs.get(j));
				kernel[i][j] = subGraphCount(graph, maxLength, discountFactor);
				kernel[j][i] = kernel[i][j];
			}
		}
	}

	public DirectedGraph<Vertex<String>, Edge<String>> computeIntersectionGraph(DirectedGraph<Vertex<String>, Edge<String>> graphA, DirectedGraph<Vertex<String>, Edge<String>> graphB) {	
		DirectedGraph<Vertex<String>, Edge<String>> iGraph = new DirectedSparseMultigraph<Vertex<String>, Edge<String>>();
		List<String> evA = new ArrayList<String>();
		List<String> evB = new ArrayList<String>();		
		Map<String, Edge<String>> eMap = new HashMap<String, Edge<String>>();
		Map<Vertex<String>, Vertex<String>> vMap = new HashMap<Vertex<String>, Vertex<String>>();


		for (Edge<String> edgeA : graphA.getEdges()) {
			evA.add(graphA.getSource(edgeA).getLabel() + edgeA.getLabel() + graphA.getDest(edgeA).getLabel());
			eMap.put(graphA.getSource(edgeA).getLabel() + edgeA.getLabel() + graphA.getDest(edgeA).getLabel(), edgeA);
		}

		for (Edge<String> edgeB : graphB.getEdges()) {
			evB.add(graphB.getSource(edgeB).getLabel() + edgeB.getLabel() + graphB.getDest(edgeB).getLabel());
		}

		Collections.sort(evA);
		Collections.sort(evB);	

		Iterator<String> itA = evA.iterator();
		Iterator<String> itB = evB.iterator();

		int comparison = 0;
		String edgeA = null;
		String edgeB = null;
		boolean stop = false;
		while (!stop) {
			if (comparison == 0 && itA.hasNext() && itB.hasNext()) {
				edgeA = itA.next();
				edgeB = itB.next();
			} else if (comparison < 0 && itA.hasNext()) {
				edgeA = itA.next();
			} else if (comparison > 0 && itB.hasNext()){
				edgeB = itB.next();
			} else {
				stop = true;
			}

			if (!stop) {
				comparison = edgeA.compareTo(edgeB);
				if (comparison == 0) {
					Edge<String> edge = eMap.get(edgeA);
					Vertex<String> v1 = graphA.getSource(edge);
					Vertex<String> v2 = graphA.getDest(edge);
					Vertex<String> v1n = vMap.get(v1);
					Vertex<String> v2n = vMap.get(v2);

					if (v1n == null) {
						v1n = new Vertex<String>(v1);
						vMap.put(v1, v1n);
					}
					if (v2n == null) {
						v2n = new Vertex<String>(v2);
						vMap.put(v2, v2n);
					}
					iGraph.addEdge(new Edge<String>(edge), v1n, v2n, EdgeType.DIRECTED);
				} 
			}
		}
		return iGraph;
	}

	public double subGraphCount(DirectedGraph<Vertex<String>, Edge<String>> graph, int maxLength, double discountFactor) {
		double n = graph.getEdgeCount();
		double score = 0;
		double prevScore = 1;
		
		for (int i = 0; i < n && i < maxLength; i++) {
			prevScore = prevScore * (n - i) / (i + 1);
			score += Math.pow(discountFactor, i+1) * prevScore;
		}
		return score;
	}
}