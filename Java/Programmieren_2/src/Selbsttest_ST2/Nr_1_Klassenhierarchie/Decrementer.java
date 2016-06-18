package Selbsttest_ST2.Nr_1_Klassenhierarchie;


public class Decrementer implements Calculator {

    public int decr(final int zahl) {
        return zahl - 1;
    }

    @Override
    public String type() {
        return "dash calculation";
    }
}
