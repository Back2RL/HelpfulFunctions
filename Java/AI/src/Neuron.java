import java.util.ArrayList;
import java.util.List;

public class Neuron {

    // overall net learning rate: 0 slow learner, 0.2 medium learner, 1.0 reckless learner [0.0..1.0]
    private static final float eta = 0.1f;
    // multiplier of last weight change: 0 no momentum, 0.5 moderate momentum [0.0..n]
    private static final float alpha = 0.5f;

    private int myIndex;
    private float outputVal;
    private float gradient;
    private List<Connection> outputWeights;

    public Neuron(final int numOutputs, final int myIndex) {
        outputWeights =  new ArrayList<>();
        this.myIndex = myIndex;
        for (int connection = 0; connection < numOutputs; ++connection) {
            outputWeights.add(new Connection());
            outputWeights.get(connection).setWeight(randomWeight());
        }
    }

    public float getOutputVal() {
        return outputVal;
    }

    public void setOutputVal(float outputVal) {
        this.outputVal = outputVal;
    }

    private float randomWeight() {
        return (float) Math.random();
    }

    void feedForward(final Layer prevLayer) {

        float sum = 0;

        // sum the previous layers outputs
        // include the bias
        for (int n = 0; n < prevLayer.size(); ++n) {
            sum += prevLayer.get(n).getOutputVal() * prevLayer.get(n).outputWeights.get(myIndex).getWeight();
        }

        outputVal = transferFunction(sum);
    }

    private static float transferFunction(final float x) {
        // tanh - outputrange [-1.0...1.0]
        return (float) Math.tanh(x);
    }

    private static float transferFunctionDerivative(final float x) {
        // tanh derivative (approximation)
        return 1.0f - x * x;
    }

    public void calcOutputGradients(final float targetVal) {
        float delta = targetVal - outputVal;
        gradient = delta * transferFunctionDerivative(outputVal);
    }

    public void calcHiddenGradients(final Layer nextLayer) {
        float dow = sumDOW(nextLayer);
        gradient = dow * transferFunctionDerivative(outputVal);
    }

    private float sumDOW(final Layer nextLayer) {
        float sum = 0;
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
            float oldDeltaWeight = neuron.outputWeights.get(myIndex).getDeltaWeight();
float newDeltaWeight =
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
