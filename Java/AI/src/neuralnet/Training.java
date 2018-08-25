package neuralnet;

import neuralnet.net.NeuronNet;

import java.util.List;

public class Training {
	public static double[] singleIteration(NeuronNet net, double[] inputVals, double[] targetVals) {
		net.feedForward(inputVals);
		net.backProp(targetVals);
		return net.getResults();
	}

	public static double[] getResultFromInput(NeuronNet net, double[] inputVals) {
		net.feedForward(inputVals);
		return net.getResults();
	}
}
