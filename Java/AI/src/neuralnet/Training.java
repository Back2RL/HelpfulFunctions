package neuralnet;

import neuralnet.net.NeuronNet;

import java.util.List;

public class Training {
	public static List<Double> singleIteration(NeuronNet net, List<Double> inputVals, List<Double> targetVals) {
		net.feedForward(inputVals);
		net.backProp(targetVals);
		return net.getResults();
	}

	public static List<Double> getResultFromInput(NeuronNet net, List<Double> inputVals) {
		net.feedForward(inputVals);
		return net.getResults();
	}
}
