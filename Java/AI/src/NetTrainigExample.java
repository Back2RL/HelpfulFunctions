import neuralnet.Training;
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
		final int[] topology = {4, 2};

		NeuronNet myNeuronNet = new NeuronNet(topology);

		long startTime = System.currentTimeMillis();
		int iterations = 0;


		do {
			List<Double> inputVals = new ArrayList<>();
			// dot forward
			inputVals.add(1.0);
			// dot up
			inputVals.add(0.0);
			// dot right
			inputVals.add(0.0);
			// distance
			inputVals.add(0.0);

			List<Double> targetVals = new ArrayList<>();
			// pitch
			targetVals.add(1.0);
			// yaw
			targetVals.add(0.0);

			List<Double> resultVals = Training.singleIteration(myNeuronNet, inputVals, targetVals);

			//System.out.println("Target : Generated");
			//System.out.println(targetVals.toString() + "      : " + resultVals.toString());

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

			targetVals = new ArrayList<>();
			// pitch
			targetVals.add(0.0);
			// yaw
			targetVals.add(1.0);


			Training.singleIteration(myNeuronNet, inputVals, targetVals);

			//System.out.println("Target : Generated");
			//System.out.println(targetVals.toString() + "      : " + resultVals.toString());

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

			targetVals = new ArrayList<>();
			// pitch
			targetVals.add(0.0);
			// yaw
			targetVals.add(-1.0);


			Training.singleIteration(myNeuronNet, inputVals, targetVals);

			//System.out.println("Target : Generated");
			//System.out.println(targetVals.toString() + "      : " + resultVals.toString());

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

			targetVals = new ArrayList<>();
			// pitch
			targetVals.add(1.0);
			// yaw
			targetVals.add(1.0);


			Training.singleIteration(myNeuronNet, inputVals, targetVals);

			//System.out.println("Target : Generated");
			//System.out.println(targetVals.toString() + "      : " + resultVals.toString());

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

			targetVals = new ArrayList<>();
			// pitch
			targetVals.add(1.0);
			// yaw
			targetVals.add(-1.0);

			Training.singleIteration(myNeuronNet, inputVals, targetVals);


			//System.out.println("Target : Generated");
			//System.out.println(targetVals.toString() + "      : " + resultVals.toString());

			// :::::::::::::::::::::::::::::::::::::::::::::::::::::::

			//System.out.println("recent average Error = " + myNeuronNet.getRecentAverageError());
			//System.out.println("recent  Error =                  " + myNeuronNet.getError());
			//System.out.println("------------------------------------------");

			iterations += 4;
		} while (myNeuronNet.getError() > 5e-3);

		long end = System.currentTimeMillis();
		System.out.println((end - startTime) + "ms; " + iterations + " iterations; " + (double) iterations / ((end - startTime) / 1000.0) + " iterations/second");

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
