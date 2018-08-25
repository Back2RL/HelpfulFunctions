package neuralnet.neuron;

import neuralnet.layer.Connection;
import neuralnet.layer.Layer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Neuron implements Serializable {

	private static final long serialVersionUID = 1L;

	// overall net learning rate: 0 slow learner, 0.2 medium learner, 1.0 reckless learner [0.0..1.0]
	private static final double eta = 0.01;
	// multiplier of last weight change: 0 no momentum, 0.5 moderate momentum [0.0..n]
	private static final double alpha = 0.01;

	private static final int MAX_MEMORIES = 3;

	private int myIndex;
	private double outputVal;
	private double gradient;
	private List<Connection> outputWeights;

	public int getNumConnections() {
		return outputWeights.size();
	}

	public List<Double> getGenome() {
		List<Double> genome = new ArrayList<>();
		for (Connection connection : outputWeights) {
			genome.add(connection.getWeight());
		}
		return genome;
	}

	public void setGenome(List<Double> genome) {
		for (int i = 0; i < outputWeights.size(); ++i) {
			outputWeights.get(i).setWeight(genome.get(i));
		}
	}


	public Neuron(final int numOutputs, final int myIndex) {
		outputWeights = new ArrayList<>();
		this.myIndex = myIndex;
		for (int connection = 0; connection < numOutputs; ++connection) {
			outputWeights.add(new Connection(randomWeight()));
		}
	}

	public Neuron(final int numOutputs, final int myIndex, List<Double> genome) {
		outputWeights = new ArrayList<>();
		this.myIndex = myIndex;
		int i = 0;
		for (int connection = 0; connection < numOutputs; ++connection) {
			outputWeights.add(new Connection(genome.get(i)));
			++i;
		}
	}


	public double getOutputVal() {
		return outputVal;
	}

	public void setOutputVal(double outputVal) {
		this.outputVal = outputVal;
	}

	private double randomWeight() {
		return -1.0 + 2.0 * Math.random();
	}

	public void feedForward(final Layer prevLayer) {

		double sum = 0;

		// sum the previous layers outputs
		// include the bias
		for (int n = 0; n < prevLayer.size(); ++n) {
			sum += prevLayer.get(n).getOutputVal() * prevLayer.get(n).outputWeights.get(myIndex).getWeight();
		}

		outputVal = transferFunction(sum);
	}

	private static double transferFunction(final double x) {
		// tanh - outputrange [-1.0...1.0]
		return Math.tanh(x);
	}

	private static double transferFunctionDerivative(final double x) {
		// tanh derivative (approximation)
		return 1.0 - x * x;
	}

	public void calcOutputGradients(final double targetVal) {
		double delta = targetVal - outputVal;
		gradient = delta * transferFunctionDerivative(outputVal);
	}

	public void calcHiddenGradients(final Layer nextLayer) {
		double dow = sumDOW(nextLayer);
		gradient = dow * transferFunctionDerivative(outputVal);
	}

	private double sumDOW(final Layer nextLayer) {
		double sum = 0;
		// sum the contributions of the errors at the nodes that are feeded
		for (int n = 0; n < nextLayer.size() - 1; ++n) {
			sum += outputWeights.get(n).getWeight() * nextLayer.get(n).gradient;
		}
		return sum;
	}

	public void updateInputWeights(Layer prevLayer) {
		// the weights to be updated are in the connection container in the neurons in the preceeding layer
		for (int n = 0; n < prevLayer.size(); ++n) {
			Neuron neuron = prevLayer.get(n);
			double oldDeltaWeight = neuron.outputWeights.get(myIndex).getDeltaWeight();
			double newDeltaWeight =
					// individual input, magnified by the gradient and train rate
					eta
							* neuron.getOutputVal()
							* gradient
							// also add momentum = a fraction of the previous delta weight
							+ alpha
							* oldDeltaWeight;

			neuron.outputWeights.get(myIndex).setDeltaWeight(newDeltaWeight);
			neuron.outputWeights.get(myIndex).setWeight(neuron.outputWeights.get(myIndex).getWeight() + newDeltaWeight);
		}
	}
}
