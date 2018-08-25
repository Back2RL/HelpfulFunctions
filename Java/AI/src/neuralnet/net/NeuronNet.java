package neuralnet.net;

import neuralnet.layer.Layer;
import neuralnet.neuron.Bias;
import neuralnet.neuron.Neuron;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class NeuronNet implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final double recentAverageSmoothingFactor = 10.0;
	private double fitness = 0.0;
	private List<Layer> layers;
	private double error;
	private double recentAverageError;

	public NeuronNet(final int[] netTopology) {
		int numLayers = netTopology.length;
		layers = new ArrayList<>();

		final int lastLayerIndex = netTopology.length - 1;


		for (int layerNum = 0; layerNum < numLayers; ++layerNum) {
			// create the layers
			layers.add(new Layer());

			// get how many outputs the Neurons on this layer shall have
			final boolean bIsLastLayer = layerNum == lastLayerIndex;
			final int numOutputs = bIsLastLayer ? 0 : netTopology[layerNum + 1];

			// fill the new layer with neurons
			int neuronIndex;
			for (neuronIndex = 0; neuronIndex < netTopology[layerNum]; ++neuronIndex) {
				layers.get(layers.size() - 1).add(new Neuron(numOutputs, neuronIndex));
			}
			layers.get(layers.size() - 1).add(new Bias(numOutputs, neuronIndex));
		}
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public double getError() {
		return error;
	}

	public double getRecentAverageError() {
		return recentAverageError;
	}

	public void feedForward(final double[] inputVals) {
		if (inputVals.length != layers.get(0).size() - 1) {
			try {
				throw new Exception("Number of Inputvalues does not equal number of Neurons in Inputlayer.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// put the input values into the input neurons
		for (int i = 0; i < inputVals.length; ++i) {
			layers.get(0).get(i).setOutputVal(inputVals[i]);
		}

		// forward propagate
		for (int layernum = 1; layernum < layers.size(); ++layernum) {
			Layer prevLayer = layers.get(layernum - 1);
			for (int n = 0; n < layers.get(layernum).size() - 1; ++n) {
				layers.get(layernum).get(n).feedForward(prevLayer);
			}
		}
	}

	public void backProp(final double[] targetVals) {
		// calculate overall net error (Root Mean Square errors)
		Layer outputLayer = layers.get(layers.size() -1);
		error = 0.0;
		for (int n = 0; n < outputLayer.size() - 1; ++n) {
			double delta = targetVals[n] - outputLayer.get(n).getOutputVal();
			error += delta * delta;
		}
		// get average error squared
		error /= outputLayer.size() - 1;
		error = Math.sqrt(error);

		// implement a recent average measurement
		// TODO: implement functionality to turn this off
		recentAverageError = (recentAverageError * recentAverageSmoothingFactor + error) / (recentAverageSmoothingFactor + 1.0);

		// calculate output layer gradients
		for (int n = 0; n < outputLayer.size() - 1; ++n) {
			outputLayer.get(n).calcOutputGradients(targetVals[n]);
		}

		// calculate gradients on hidden layers
		for (int layerNum = layers.size() - 2; layerNum > 0; --layerNum) {
			Layer hiddenLayer = layers.get(layerNum);
			Layer nextLayer = layers.get(layerNum + 1);
			for (int n = 0; n < hiddenLayer.size(); ++n) {
				hiddenLayer.get(n).calcHiddenGradients(nextLayer);
			}
		}

		// form all layers from outputs to first hidden layer update all connection weights
		for (int layerNum = layers.size() - 1; layerNum > 0; --layerNum) {
			Layer layer = layers.get(layerNum);
			Layer prevLayer = layers.get(layerNum - 1);

			for (int n = 0; n < layer.size() - 1; ++n) {
				layer.get(n).updateInputWeights(prevLayer);
			}
		}

	}

	public double[] getResults() {
		int num = layers.get(layers.size() - 1).size() - 1;

		double[] resultVals = new double[num];
		for (int n = 0; n <num; ++n) {
			resultVals[n] = layers.get(layers.size() - 1).get(n).getOutputVal();
		}

		return resultVals;
	}

	public List<Double> getGenome() {
		List<Double> genome = new ArrayList<>();
		for (Layer layer : layers) {
			for (Neuron node : layer) {
				genome.addAll(node.getGenome());
			}
		}
		return genome;
	}

	public void setGenome(List<Double> genome) {
		for (Layer layer : layers) {
			for (Neuron node : layer) {
				List<Double> subGenome = genome.subList(0, node.getNumConnections());
				genome = genome.subList(subGenome.size(), genome.size());

				node.setGenome(subGenome);
			}
		}
	}

	public static void saveNeuralNet(NeuronNet net) throws IOException {
		try (FileOutputStream fos = new FileOutputStream("net.serialized");
		     ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(net);
		}
	}

	public static NeuronNet loadNeuralNet(int[] topology) {
		NeuronNet net;
		try (FileInputStream fis = new FileInputStream("net.serialized");
		     ObjectInputStream ois = new ObjectInputStream(fis)) {
			net = (NeuronNet) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			net = new NeuronNet(topology);
		}
		return net;
	}
}


