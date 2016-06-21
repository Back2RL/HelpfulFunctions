public class Bias extends Neuron {
    public Bias(int numOutputs, final int myIndex) {
        super(numOutputs, myIndex);
    }

    @Override
    public float getOutputVal() {
        return 1.0f;
    }
}
