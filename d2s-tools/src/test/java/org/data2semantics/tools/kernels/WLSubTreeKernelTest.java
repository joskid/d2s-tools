package org.data2semantics.tools.kernels;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.data2semantics.tools.graphs.Edge;
import org.data2semantics.tools.graphs.GraphFactory;
import org.data2semantics.tools.graphs.Vertex;
import org.data2semantics.tools.rdf.RDFDataSet;
import org.data2semantics.tools.rdf.RDFFileDataSet;
import org.junit.Test;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.rio.RDFFormat;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;

public class WLSubTreeKernelTest {

	@Test
	public void test() {
		List<DirectedGraph<Vertex<String>, Edge<String>>> graphs = new ArrayList<DirectedGraph<Vertex<String>, Edge<String>>>();
		
		
		RDFDataSet testSet = new RDFFileDataSet("D:\\workspaces\\eclipse_workspace\\rdfgraphlearning\\src\\main\\resources\\aifb-fixed_complete.rdf", RDFFormat.RDFXML);
		List<Statement> triples = testSet.getStatementsFromStrings(null, "http://swrc.ontoware.org/ontology#affiliation", null, true);	
		for (Statement triple : triples) {
			if (triple.getSubject() instanceof URI) {
				graphs.add(GraphFactory.createJUNGGraph(testSet.getSubGraph((URI) triple.getSubject(), 2, true)));
			}
		}
		
//				
//		DirectedGraph<Vertex<String>, Edge<String>> graphA, graphB, graphC;
//		graphA = new DirectedSparseMultigraph<Vertex<String>, Edge<String>>();
//		graphB = new DirectedSparseMultigraph<Vertex<String>, Edge<String>>();
//		graphC = new DirectedSparseMultigraph<Vertex<String>, Edge<String>>();
//
//		Vertex<String> v1, v2, v3, v4;
//		v1 = new Vertex<String>("a");
//		v2 = new Vertex<String>("b");
//		v3 = new Vertex<String>("c");
//		v4 = new Vertex<String>("d");
//
//		Edge<String> e1, e2, e3, e4;
//
//		e1 = new Edge<String>("A");
//		e2 = new Edge<String>("B");
//		e3 = new Edge<String>("C");
//		e4 = new Edge<String>("D");
//
//		graphA.addVertex(v1);
//		graphA.addVertex(v2);
//		graphA.addVertex(v3);
//
//		graphA.addEdge(e1, v1, v2, EdgeType.DIRECTED);
//		graphA.addEdge(e2, v2, v3, EdgeType.DIRECTED);
//		graphA.addEdge(e3, v3, v1, EdgeType.DIRECTED);
//
//		v1 = new Vertex<String>("a");
//		v2 = new Vertex<String>("b");
//		v3 = new Vertex<String>("c");
//		v4 = new Vertex<String>("a");
//
//		e1 = new Edge<String>("A");
//		e2 = new Edge<String>("B");
//		e3 = new Edge<String>("C");
//		e4 = new Edge<String>("A");
//
//		graphB.addVertex(v1);
//		graphB.addVertex(v2);
//		graphB.addVertex(v3);
//		graphB.addVertex(v4);
//
//		graphB.addEdge(e1, v1, v2, EdgeType.DIRECTED);
//		graphB.addEdge(e2, v2, v3, EdgeType.DIRECTED);
//		graphB.addEdge(e3, v3, v1, EdgeType.DIRECTED);
//		graphB.addEdge(e4, v1, v4, EdgeType.DIRECTED);
//
//		v1 = new Vertex<String>("b");
//		v2 = new Vertex<String>("b");
//		v3 = new Vertex<String>("c");
//		v4 = new Vertex<String>("b");
//
//		e1 = new Edge<String>("E");
//		e2 = new Edge<String>("E");
//		e3 = new Edge<String>("C");
//		e4 = new Edge<String>("A");
//
//		graphC.addVertex(v1);
//		graphC.addVertex(v2);
//		graphC.addVertex(v3);
//		graphC.addVertex(v4);
//
//		graphC.addEdge(e1, v1, v2, EdgeType.DIRECTED);
//		graphC.addEdge(e2, v2, v3, EdgeType.DIRECTED);
//		graphC.addEdge(e3, v3, v1, EdgeType.DIRECTED);
//		graphC.addEdge(e4, v1, v4, EdgeType.DIRECTED);
//
//		graphs.add(graphA);
//		graphs.add(graphB);		
//		graphs.add(graphC);	
//		
		
		WLSubTreeKernel kernel = new WLSubTreeKernel(graphs);
		kernel.compute();
		kernel.normalize();
		
//		for (List<Double> fv : kernel.getFeatureVectors()) {
//			System.out.println(fv);
//		}
		
		double[][] matrix = kernel.getKernel();
		
		for (int i = 0; i < matrix.length; i++) {
			System.out.println(Arrays.toString(matrix[i]));
		}
		
	}

}