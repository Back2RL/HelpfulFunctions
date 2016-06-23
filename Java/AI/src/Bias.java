public class Bias extends Neuron {
    public Bias(int numOutputs, final int myIndex) {
        super(numOutputs, myIndex);
    }

    @Override
    public double getOutputVal() {
        return 1.0f;
    }
}
