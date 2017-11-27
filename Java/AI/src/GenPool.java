import neuralnet.net.NeuronNet;

import java.util.*;

/**
 * Created by Leo2400 on 24.06.2016.
 */
public class GenPool {


    public List<NeuronNet> getBrains() {
        return brains;
    }

    public void setBrains(List<NeuronNet> brains) {
        this.brains = brains;
    }

    private List<NeuronNet> brains;

    public GenPool(int size, final int[] topology){

        brains =  new ArrayList<>();

        for (int i = 0; i < size; i++) {
            NeuronNet newBrain = new NeuronNet(topology);
            brains.add(newBrain);
        }
    }

    public void sortBrains(){
        brains.sort(new Comparator<NeuronNet>() {
            @Override
            public int compare(NeuronNet o1, NeuronNet o2) {
                return Double.compare(o1.getFitness(),o2.getFitness());
            }
        });
    }
}
