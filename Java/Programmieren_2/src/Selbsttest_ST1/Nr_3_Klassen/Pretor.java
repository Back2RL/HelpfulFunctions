package Selbsttest_ST1.Nr_3_Klassen;

public class Pretor {
    private int weightance;

    public Pretor(int weightance) {
        this.weightance = weightance;
    }

    public int getWeightance() {
        return weightance;
    }

    public void bewegen() {
        System.out.println("Pretor bewegt sich");
    }
}
