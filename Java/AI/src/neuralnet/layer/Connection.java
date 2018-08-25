package neuralnet.layer;

import java.io.Serializable;

public class Connection implements Serializable {

    private static final long serialVersionUID = 1L;

    private double weight;
    private double deltaWeight;

    public Connection(){
        weight = 0.0;
        deltaWeight = 0.0;
    }

    public Connection(double weight){
        this.weight = weight;
        deltaWeight = 0.0;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getDeltaWeight() {
        return deltaWeight;
    }

    public void setDeltaWeight(double deltaWeight) {
        this.deltaWeight = deltaWeight;
    }
}
