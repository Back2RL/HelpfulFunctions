import java.util.ArrayList;
import java.util.List;

public class Neuron {

    // overall net learning rate: 0 slow learner, 0.2 medium learner, 1.0 reckless learner [0.0..1.0]
    private static final double eta = 0.1f;
    // multiplier of last weight change: 0 no momentum, 0.5 moderate momentum [0.0..n]
    private static final double alpha = 0.1f;

    private int myIndex;
    private double outputVal;
    private double gradient;
    private List<Connection> outputWeights;

    public Neuron(final int numOutputs, final int myIndex) {
        outputWeights =  new ArrayList<>();
        this.myIndex = myIndex;
        for (int connection = 0; connection < numOutputs; ++connection) {
            outputWeights.add(new Connection());
            outputWeights.get(connection).setWeight(randomWeight());
        }
    }

    public double getOutputVal() {
        return outputVal;
    }

    public void setOutputVal(double outputVal) {
        this.outputVal = outputVal;
    }

    private double randomWeight() {
        return (double) Math.random();
    }

    void feedForward(final Layer prevLayer) {

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
        return (double) Math.tanh(x);
    }

    private static double transferFunctionDerivative(final double x) {
        // tanh derivative (approximation)
        return 1.0f - x * x;
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

    public void updateInputWeights(Layer prevLayer){
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
