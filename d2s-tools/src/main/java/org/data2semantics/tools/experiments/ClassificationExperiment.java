package org.data2semantics.tools.experiments;

import java.io.OutputStream;
import java.io.PrintWriter;

import org.data2semantics.tools.kernels.GraphKernel;
import org.data2semantics.tools.libsvm.LibSVMWrapper;

import cern.colt.Arrays;


public class ClassificationExperiment implements Runnable {
	private GraphClassificationDataSet dataSet;
	private GraphKernel kernel;
	private long[] seeds;
	private double[] cs;
	private double accuracy;
	private double f1;
	private PrintWriter output;
	private ExperimentResults results;
	
	public ClassificationExperiment(GraphClassificationDataSet dataSet, GraphKernel kernel, long[] seeds, double[] cs) {
		this(dataSet, kernel, seeds,  cs, System.out);
	}
	
	
	public ClassificationExperiment(GraphClassificationDataSet dataSet, GraphKernel kernel, long[] seeds, double[] cs, OutputStream outputStream) {
		this.dataSet = new GraphClassificationDataSet(dataSet);
		this.kernel = kernel;
		this.seeds = seeds;
		this.cs = cs;
		output = new PrintWriter(outputStream);
		results = new ExperimentResults();
	}


	public void run() {			
		double acc = 0, meanAcc = 0, f = 0;
		
		kernel.compute();
		kernel.normalize();	

		for (int i = 0; i < seeds.length; i++) {
			kernel.shuffle(seeds[i]);
			dataSet.shuffle(seeds[i]);
			
			double[][] matrix = kernel.getKernel();
			double[] target = LibSVMWrapper.createTargets(dataSet.getLabels());	
			double[] prediction = LibSVMWrapper.crossValidate(matrix, target, 10, cs);
			
			acc += LibSVMWrapper.computeAccuracy(target, prediction);
			f +=  LibSVMWrapper.computeF1(target, prediction);
		}
		
		accuracy = acc / seeds.length;
		f1 = f / seeds.length;
		
		output.println(dataSet.getLabel());
		output.println(kernel.getLabel() + ", Seeds=" + Arrays.toString(seeds) + ", C=" + Arrays.toString(cs));
		output.print("Overall Accuracy: " + accuracy);
		output.print(", Average F1: " + f1);
		output.println("");
		output.flush();
		
		results.setLabel(dataSet.getLabel() + " " + kernel.getLabel() + ", Seeds=" + Arrays.toString(seeds) + ", C=" + Arrays.toString(cs));
		results.setAccuracy(accuracy);
		results.setF1(f1);

	}

	public double getAccuracy() {
		return accuracy;
	}

	public double getF1() {
		return f1;
	}

	public ExperimentResults getResults() {
		return results;
	}	
}