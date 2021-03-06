package org.data2semantics.tools.kernels;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.data2semantics.tools.graphs.Edge;
import org.data2semantics.tools.graphs.Vertex;
import org.junit.Test;

import cern.colt.Arrays;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;

public class IntersectionGraphKernelTest {

	@Test
	public void test() {
		 
		List<DirectedGraph<Vertex<String>, Edge<String>>> graphs = new ArrayList<DirectedGraph<Vertex<String>, Edge<String>>>();
		List<Vertex<String>> rootV = new ArrayList<Vertex<String>>();
		
		DirectedGraph<Vertex<String>, Edge<String>> graphA, graphB;
		graphA = new DirectedSparseMultigraph<Vertex<String>, Edge<String>>();
		graphB = new DirectedSparseMultigraph<Vertex<String>, Edge<String>>();

		Vertex<String> v1, v2, v3, v4;

		v1 = new Vertex<String>("a");
		v2 = new Vertex<String>("b");
		v3 = new Vertex<String>("c");
		v4 = new Vertex<String>("d");

		Edge<String> e1, e2, e3, e4;

		e1 = new Edge<String>("A");
		e2 = new Edge<String>("B");
		e3 = new Edge<String>("C");
		e4 = new Edge<String>("E");

		graphA.addVertex(v1);
		graphA.addVertex(v2);
		graphA.addVertex(v3);
		graphA.addVertex(v4);
		
		graphA.addEdge(e1, v1, v2, EdgeType.DIRECTED);
		graphA.addEdge(e2, v1, v3, EdgeType.DIRECTED);
		graphA.addEdge(e3, v3, v4, EdgeType.DIRECTED);
		graphA.addEdge(e4, v3, v4, EdgeType.DIRECTED);
		
		Vertex<String> v1B, v2B, v3B, v4B;

		v1B = new Vertex<String>("a");
		v2B = new Vertex<String>("b");
		v3B = new Vertex<String>("c");
		v4B = new Vertex<String>("d");

		Edge<String> e1B, e2B, e3B, e4B;

		e1B = new Edge<String>("A");
		e2B = new Edge<String>("B");
		e3B = new Edge<String>("C");
		e4B = new Edge<String>("D");

		graphB.addVertex(v1B);
		graphB.addVertex(v2B);
		graphB.addVertex(v3B);
		graphB.addVertex(v4B);

		graphB.addEdge(e1B, v1B, v2B, EdgeType.DIRECTED);
		graphB.addEdge(e2B, v1B, v3B, EdgeType.DIRECTED);
		graphB.addEdge(e3B, v3B, v4B, EdgeType.DIRECTED);
		graphB.addEdge(e4B, v2B, v4B, EdgeType.DIRECTED);
		
		graphs.add(graphA);
		graphs.add(graphB);
		
		rootV.add(v1);
		rootV.add(v1B);

		IntersectionGraphKernel kernel = new IntersectionGraphPathKernel(2, 1);
		
		//double[][] matrix = kernel.compute(graphs);
		
		/*
		System.out.println(Arrays.toString(matrix[0]));
		System.out.println(Arrays.toString(matrix[1]));
		
		DirectedGraph<Vertex<String>, Edge<String>> iTree = kernel.computeIntersectionGraph(graphA, graphB);
		System.out.println(graphA);
		System.out.println(graphB);
		System.out.println(iTree);
		System.out.println(kernel.subGraphCount(iTree, 2, 1));
		*/
		
	}

}
