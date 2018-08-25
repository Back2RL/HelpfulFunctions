package neuralnet.neuron;

public class Bias extends Neuron {

    private static final long serialVersionUID = 1L;

    public Bias(int numOutputs, final int myIndex) {
        super(numOutputs, myIndex);
    }

    @Override
    public double getOutputVal() {
        return 1.0;
    }
}
