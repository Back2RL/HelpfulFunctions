import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.ArrayList;
import java.util.List;

public class Net {

    private List<Layer> layers;

    public float getError() {
        return error;
    }

    private float error;

    public float getRecentAverageError() {
        return recentAverageError;
    }

    private float recentAverageError;
    private static final float recentAverageSmoothingFactor = 10.0f;


    public Net(final int[] topology) {
        int numLayers = topology.length;

        layers = new ArrayList<>();

        for (int layerNum = 0; layerNum < numLayers; ++layerNum) {
            // create the layers
            layers.add(new Layer());

            final int numOutputs = layerNum == (topology.length - 1) ? 0 : topology[layerNum + 1];

            // fill the new layer with neurons
            for (int neuronNum = 0; neuronNum <= topology[layerNum]; ++neuronNum) {
                // the last element in a layer is a Bias-Neuron
                if (neuronNum == topology[layerNum]) {
                    layers.get(layers.size() - 1).add(new Bias(numOutputs, neuronNum));
                } else {
                    layers.get(layers.size() - 1).add(new Neuron(numOutputs, neuronNum));
                }

                System.out.println("Made a Neuron of type " + layers.get(layers.size() - 1).get(neuronNum).getClass().getName());
            }
        }
    }

    public void feedForward(final List<Float> inputVals) {

        if (inputVals.size() != layers.get(0).size() - 1) {
            try {
                throw new Exception("Number of Inputvalues does not equal number of Neurons in Inputlayer.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // put the input values into the input neurons
        for (int i = 0; i < inputVals.size(); ++i) {
            layers.get(0).get(i).setOutputVal(inputVals.get(i));
        }

        // forward propagate
        for (int layernum = 1; layernum < layers.size(); ++layernum) {

            Layer prevLayer = layers.get(layernum - 1);
            for (int n = 0; n < layers.get(layernum).size() - 1; ++n) {
                layers.get(layernum).get(n).feedForward(prevLayer);

            }
        }

    }

    public void backProp(final List<Float> targetVals) {
        // calculate overall net error (Root Mean Square errors)
        Layer outputLayer = layers.get(layers.size() - 1);
        error = 0.0f;
        for (int n = 0; n < outputLayer.size() - 1; ++n) {
            float delta = targetVals.get(n) - outputLayer.get(n).getOutputVal();
            error += delta * delta;
        }
        // get average error squared
        error /= outputLayer.size() - 1;
        error = (float) Math.sqrt(error);

        // implement a recent average measurement
        // TODO: implement functionality to turn this off
        recentAverageError = (recentAverageError * recentAverageSmoothingFactor + error) / (recentAverageSmoothingFactor + 1.0f);

        // calculate output layer gradients
        for (int n = 0; n < outputLayer.size() -1; ++n) {
            outputLayer.get(n).calcOutputGradients(targetVals.get(n));
        }

        // calculate gradients on hidden layers
        for(int layerNum = layers.size() - 2; layerNum > 0; -- layerNum){
            Layer hiddenLayer = layers.get(layerNum);
            Layer nextLayer = layers.get(layerNum + 1);
            for(int n = 0; n < hiddenLayer.size(); ++n){
                hiddenLayer.get(n).calcHiddenGradients(nextLayer);
            }
        }

        // form all layers from outputs to first hidden layer update all connection weights
        for(int layerNum = layers.size() -1; layerNum > 0; --layerNum){
            Layer layer =  layers.get(layerNum);
            Layer prevLayer =  layers.get(layerNum -1);

            for(int n = 0; n< layer.size()-1;++n){
                layer.get(n).updateInputWeights(prevLayer);
            }
        }

    }

    public List<Float> getResults() {
    List<Float> resultVals =  new ArrayList<>();

        for(int n = 0; n< layers.get(layers.size()-1).size() - 1; ++n){
            resultVals.add(layers.get(layers.size() - 1).get(n).getOutputVal());
        }

       return resultVals;
    }


}
