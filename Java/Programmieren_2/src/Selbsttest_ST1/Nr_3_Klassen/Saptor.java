package Selbsttest_ST1.Nr_3_Klassen;

public class Saptor {
    private int factorhood;
    private Pretor pretor;

    public Saptor(Pretor pretor, int factorhood) {
        this.factorhood = factorhood;
        this.pretor = pretor;
    }

    public void bewegen() {
        pretor.bewegen();
    }

    public int getLoneliness() {
        return pretor.getWeightance() * factorhood;
    }
}
