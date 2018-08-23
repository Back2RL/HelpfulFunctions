package neuralnet;

import neuralnet.net.NeuronNet;

import java.util.List;

public class Training {
	public static List<Double> singleIteration(NeuronNet net, List<Double> inputVals, List<Double> targetVals) {
		net.feedForward(inputVals);
		net.backProp(targetVals);
		return net.getResults();
	}
}
