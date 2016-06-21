import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NetTrainigExample {
    public static void main(String[] args) {

        // layers:
        // 1st: number of inputs,
        // 1st to last: layers with number of neurons
        // last: number of outputs
        final int[] topology = {4, 4, 4, 4, 2};

        Net myNet = new Net(topology);


        for (int i = 0; i < 100000; i++) {

            List<Float> inputVals = new ArrayList<>();
            // dot forward
            inputVals.add(1.0f);
            // dot up
            inputVals.add(0.0f);
            // dot right
            inputVals.add(0.0f);
            // distance
            inputVals.add(0.0f);

            myNet.feedForward(inputVals);

            List<Float> targetVals = new ArrayList<>();
            // pitch
            targetVals.add(1.0f);
            // yaw
            targetVals.add(0.0f);

            myNet.backProp(targetVals);

            List<Float> resultVals = myNet.getResults();

            System.out.println("Target : Generated");
            System.out.println(targetVals.toString() + "      : " + resultVals.toString());

            // .................................................

            inputVals = new ArrayList<>();
            // dot forward
            inputVals.add(1.0f);
            // dot up
            inputVals.add(0.0f);
            // dot right
            inputVals.add(1.0f);
            // distance
            inputVals.add(1.0f);

            myNet.feedForward(inputVals);
            targetVals = new ArrayList<>();
            // pitch
            targetVals.add(0.0f);
            // yaw
            targetVals.add(1.0f);

            myNet.backProp(targetVals);

            resultVals = myNet.getResults();

            System.out.println("Target : Generated");
            System.out.println(targetVals.toString() + "      : " + resultVals.toString());

            // .................................................

            inputVals = new ArrayList<>();
            // dot forward
            inputVals.add(1.0f);
            // dot up
            inputVals.add(0.0f);
            // dot right
            inputVals.add(-1.0f);
            // distance
            inputVals.add(1.0f);

            myNet.feedForward(inputVals);
            targetVals = new ArrayList<>();
            // pitch
            targetVals.add(0.0f);
            // yaw
            targetVals.add(-1.0f);

            myNet.backProp(targetVals);

            resultVals = myNet.getResults();

            System.out.println("Target : Generated");
            System.out.println(targetVals.toString() + "      : " + resultVals.toString());

            // .................................................

            inputVals = new ArrayList<>();
            // dot forward
            inputVals.add(-1.0f);
            // dot up
            inputVals.add(0.0f);
            // dot right
            inputVals.add(1.0f);
            // distance
            inputVals.add(0.0f);

            myNet.feedForward(inputVals);
            targetVals = new ArrayList<>();
            // pitch
            targetVals.add(1.0f);
            // yaw
            targetVals.add(1.0f);

            myNet.backProp(targetVals);

            resultVals = myNet.getResults();

            System.out.println("Target : Generated");
            System.out.println(targetVals.toString() + "      : " + resultVals.toString());

            // .................................................

            inputVals = new ArrayList<>();
            // dot forward
            inputVals.add(-1.0f);
            // dot up
            inputVals.add(0.0f);
            // dot right
            inputVals.add(-1.0f);
            // distance
            inputVals.add(0.0f);

            myNet.feedForward(inputVals);
            targetVals = new ArrayList<>();
            // pitch
            targetVals.add(1.0f);
            // yaw
            targetVals.add(-1.0f);

            myNet.backProp(targetVals);

            resultVals = myNet.getResults();

            System.out.println("Target : Generated");
            System.out.println(targetVals.toString() + "      : " + resultVals.toString());

            // :::::::::::::::::::::::::::::::::::::::::::::::::::::::

            System.out.println("recent average Error = " + myNet.getRecentAverageError());
            System.out.println("recent  Error =                  " + myNet.getError());
            System.out.println("------------------------------------------");
        }

        List<Float> inputVals = new ArrayList<>();
        inputVals.add(1.0f);
        inputVals.add(0.0f);
        inputVals.add(1.0f);
        inputVals.add(0.5f);

        myNet.feedForward(inputVals);


        List<Float> resultVals = myNet.getResults();

        System.out.println("Generated");
        System.out.println(resultVals.toString());
    }
}
