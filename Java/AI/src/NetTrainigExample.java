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
			double[] inputVals = new double[4];
			// dot forward
			inputVals[0] = 1.0;
			// dot up
			inputVals[0] = 0.0;
			// dot right
			inputVals[0] = 0.0;
			// distance
			inputVals[0] = 0.0;

			double[] targetVals =  new double[2];
			// pitch
			targetVals[0] = 1.0;
			// yaw
			targetVals[0] = 0.0;

			double[] resultVals = Training.singleIteration(myNeuronNet, inputVals, targetVals);

			//System.out.println("Target : Generated");
			//System.out.println(targetVals.toString() + "      : " + resultVals.toString());

			// .................................................

			inputVals = new double[4];
			// dot forward
			inputVals[0] = 1.0;
			// dot up
			inputVals[0] = 0.0;
			// dot right
			inputVals[0] = 1.0;
			// distance
			inputVals[0] = 1.0;

			targetVals = new double[2];
			// pitch
			targetVals[0] = 0.0;
			// yaw
			targetVals[0] = 1.0;


			Training.singleIteration(myNeuronNet, inputVals, targetVals);

			//System.out.println("Target : Generated");
			//System.out.println(targetVals.toString() + "      : " + resultVals.toString());

			// .................................................

			inputVals = new double[4];
			// dot forward
			inputVals[0] = 1.0;
			// dot up
			inputVals[0] = 0.0;
			// dot right
			inputVals[0] = -1.0;
			// distance
			inputVals[0] = 1.0;

			targetVals = new double[2];
			// pitch
			targetVals[0] = 0.0;
			// yaw
			targetVals[0] = -1.0;


			Training.singleIteration(myNeuronNet, inputVals, targetVals);

			//System.out.println("Target : Generated");
			//System.out.println(targetVals.toString() + "      : " + resultVals.toString());

			// .................................................

			inputVals = new double[4];
			// dot forward
			inputVals[0] = -1.0;
			// dot up
			inputVals[0] = 0.0;
			// dot right
			inputVals[0] = 1.0;
			// distance
			inputVals[0] = 0.0;

			targetVals = new double[2];
			// pitch
			targetVals[0] = 1.0;
			// yaw
			targetVals[0] = 1.0;


			Training.singleIteration(myNeuronNet, inputVals, targetVals);

			//System.out.println("Target : Generated");
			//System.out.println(targetVals.toString() + "      : " + resultVals.toString());

			// .................................................

			inputVals = new double[4];
			// dot forward
			inputVals[0] = -1.0;
			// dot up
			inputVals[0] = 0.0;
			// dot right
			inputVals[0] = -1.0;
			// distance
			inputVals[0] = 0.0;

			targetVals = new double[2];
			// pitch
			targetVals[0] = 1.0;
			// yaw
			targetVals[0] = -1.0;

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

		double[] inputVals = new double[4];
		inputVals[0] = 1.0;
		inputVals[0] = 0.0;
		inputVals[0] = 1.0;
		inputVals[0] = 1.0;

		myNeuronNet.feedForward(inputVals);


		double[] resultVals = myNeuronNet.getResults();
		System.out.println(myNeuronNet.getGenome().toString());
		List<Double> genome = myNeuronNet.getGenome();
		genome.set(new Random().nextInt(genome.size()), 0.0);
		myNeuronNet.setGenome(genome);
		System.out.println(myNeuronNet.getGenome().toString());


		System.out.println("Generated");
		System.out.println(resultVals.toString());
	}
}
