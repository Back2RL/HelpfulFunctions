package Selbsttest_ST2.Nr_1_Klassenhierarchie;


public abstract class Halbierer implements  Calculator{
    // halbiert eine Zahl (Ganzzahldivision
    public abstract int halb(final int zahl);

    @Override
    public String type() {
        return "point calculation";
    }
}
