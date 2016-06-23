import java.util.ArrayList;
import java.util.List;

public class NetTrainigExample {
    public static void main(String[] args) {

        // layers:
        // 1st: number of inputs,
        // 1st to last: layers with number of neurons
        // last: number of outputs
        final int[] topology = {4, 4, 4, 4, 2};

        NeuronNet myNeuronNet = new NeuronNet(topology);


        for (int i = 0; i < 100000; i++) {

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
        inputVals.add(0.5);

        myNeuronNet.feedForward(inputVals);


        List<Double> resultVals = myNeuronNet.getResults();

        System.out.println("Generated");
        System.out.println(resultVals.toString());
    }
}
