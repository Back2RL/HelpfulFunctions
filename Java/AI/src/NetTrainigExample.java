import neuralnet.net.NeuronNet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NetTrainigExample {
	public static void main(String[] args) {

		// layers:
		// 1st: number of inputs,
		// 1st to last: layers with number of neurons
		// last: number of outputs
		final int[] topology = {4, 5, 4, 3, 2};

		NeuronNet myNeuronNet = new NeuronNet(topology);


		for (int i = 0; i < 10000; i++) {

			List<Double> inputVals = new ArrayList<>();
			// dot forward
			inputVals.add(1.0);
			// dot up
			inputVals.add(0.0);
			// dot right
			inputVals.add(0.0);
			// distance
			inputVals.add(0.0);

			myNeuronNet.feedForward(inputVals);

			List<Double> targetVals = new ArrayList<>();
			// pitch
			targetVals.add(1.0);
			// yaw
			targetVals.add(0.0);

			myNeuronNet.backProp(targetVals);

			List<Double> resultVals = myNeuronNet.getResults();

			System.out.println("Target : Generated");
			System.out.println(targetVals.toString() + "      : " + resultVals.toString());

			// .................................................

			inputVals = new ArrayList<>();
			// dot forward
			inputVals.add(1.0);
			// dot up
			inputVals.add(0.0);
			// dot right
			inputVals.add(1.0);
			// distance
			inputVals.add(1.0);

			myNeuronNet.feedForward(inputVals);
			targetVals = new ArrayList<>();
			// pitch
			targetVals.add(0.0);
			// yaw
			targetVals.add(1.0);

			myNeuronNet.backProp(targetVals);

			resultVals = myNeuronNet.getResults();

			System.out.println("Target : Generated");
			System.out.println(targetVals.toString() + "      : " + resultVals.toString());

			// .................................................

			inputVals = new ArrayList<>();
			// dot forward
			inputVals.add(1.0);
			// dot up
			inputVals.add(0.0);
			// dot right
			inputVals.add(-1.0);
			// distance
			inputVals.add(1.0);

			myNeuronNet.feedForward(inputVals);
			targetVals = new ArrayList<>();
			// pitch
			targetVals.add(0.0);
			// yaw
			targetVals.add(-1.0);

			myNeuronNet.backProp(targetVals);

			resultVals = myNeuronNet.getResults();

			System.out.println("Target : Generated");
			System.out.println(targetVals.toString() + "      : " + resultVals.toString());

			// .................................................

			inputVals = new ArrayList<>();
			// dot forward
			inputVals.add(-1.0);
			// dot up
			inputVals.add(0.0);
			// dot right
			inputVals.add(1.0);
			// distance
			inputVals.add(0.0);

			myNeuronNet.feedForward(inputVals);
			targetVals = new ArrayList<>();
			// pitch
			targetVals.add(1.0);
			// yaw
			targetVals.add(1.0);

			myNeuronNet.backProp(targetVals);

			resultVals = myNeuronNet.getResults();

			System.out.println("Target : Generated");
			System.out.println(targetVals.toString() + "      : " + resultVals.toString());

			// .................................................

			inputVals = new ArrayList<>();
			// dot forward
			inputVals.add(-1.0);
			// dot up
			inputVals.add(0.0);
			// dot right
			inputVals.add(-1.0);
			// distance
			inputVals.add(0.0);

			myNeuronNet.feedForward(inputVals);
			targetVals = new ArrayList<>();
			// pitch
			targetVals.add(1.0);
			// yaw
			targetVals.add(-1.0);

			myNeuronNet.backProp(targetVals);

			resultVals = myNeuronNet.getResults();

			System.out.println("Target : Generated");
			System.out.println(targetVals.toString() + "      : " + resultVals.toString());

			// :::::::::::::::::::::::::::::::::::::::::::::::::::::::

			System.out.println("recent average Error = " + myNeuronNet.getRecentAverageError());
			System.out.println("recent  Error =                  " + myNeuronNet.getError());
			System.out.println("------------------------------------------");
		}

		List<Double> inputVals = new ArrayList<>();
		inputVals.add(1.0);
		inputVals.add(0.0);
		inputVals.add(1.0);
		inputVals.add(1.0);

		myNeuronNet.feedForward(inputVals);


		List<Double> resultVals = myNeuronNet.getResults();
		System.out.println(myNeuronNet.getGenome().toString());
		List<Double> genome = myNeuronNet.getGenome();
		genome.set(new Random().nextInt(genome.size()), 0.0);
		myNeuronNet.setGenome(genome);
		System.out.println(myNeuronNet.getGenome().toString());


		System.out.println("Generated");
		System.out.println(resultVals.toString());
	}
}
