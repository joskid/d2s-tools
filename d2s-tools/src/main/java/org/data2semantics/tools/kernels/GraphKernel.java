package org.data2semantics.tools.kernels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.data2semantics.tools.graphs.Edge;
import org.data2semantics.tools.graphs.GraphFactory;
import org.data2semantics.tools.graphs.Vertex;

import edu.uci.ics.jung.graph.DirectedGraph;

public abstract class GraphKernel {
	protected double[][] kernel;
	protected List<DirectedGraph<Vertex<String>, Edge<String>>> graphs;
	protected String label;
	
	public GraphKernel() {
		this.label = "Graph Kernel";
	}
	
	public GraphKernel(List<DirectedGraph<Vertex<String>, Edge<String>>> graphs) {
		this();
		this.graphs = graphs;
		initMatrix();
	}
	
	
	public abstract void compute();
	
	public void normalize() {
		double[] ss = new double[kernel.length];
		
		for (int i = 0; i < ss.length; i++) {
			ss[i] = kernel[i][i];
		}
			
		for (int i = 0; i < kernel.length; i++) {
			for (int j = i; j < kernel[i].length; j++) {
				kernel[i][j] /= Math.sqrt(ss[i] * ss[j]);
				kernel[j][i] = kernel[i][j];
			}
		}
	}
	

	public double[][] getKernel() {
		return kernel;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void shuffle(long seed) {		
		Double[][] kernelDouble = convert2DoubleObjects(kernel);		
		for (int i = 0; i < kernel.length; i++) {
			Collections.shuffle(Arrays.asList(kernelDouble[i]), new Random(seed));
		}
		Collections.shuffle(Arrays.asList(kernelDouble), new Random(seed));
		kernel = convert2DoublePrimitives(kernelDouble);
	}
	
	private Double[][] convert2DoubleObjects(double[][] kernel) {
		Double[][] kernelDouble = new Double[kernel.length][kernel[0].length];
		
		for (int i = 0; i < kernel.length; i++) {
			for (int j = 0; j < kernel[i].length; j++) {
				kernelDouble[i][j] = new Double(kernel[i][j]);
			}
		}
		return kernelDouble;
	}
	
	private double[][] convert2DoublePrimitives(Double[][] kernelDouble) {
		double[][] kernel = new double[kernelDouble.length][kernelDouble[0].length];
		
		for (int i = 0; i < kernelDouble.length; i++) {
			for (int j = 0; j < kernelDouble[i].length; j++) {
				kernel[i][j] = kernelDouble[i][j].doubleValue();
			}
		}
		return kernel;
	}
	
	public void setGraphs(List<DirectedGraph<Vertex<String>, Edge<String>>> graphs) {
		this.graphs = graphs;
		initMatrix();
	}
	
	protected void initMatrix() {
		kernel = new double[graphs.size()][graphs.size()];
		for (int i = 0; i < graphs.size(); i++) {
			Arrays.fill(kernel[i], 0.0);
		}
	}
	
	
}
