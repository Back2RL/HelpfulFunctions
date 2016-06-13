package Selbsttest_ST1.Nr_1_Klassen;

public class Telefon {
    private int tastenAnzahl;

    public Telefon(int tastenAnzahl) {
        this.tastenAnzahl = tastenAnzahl;
    }

    public int getTastenAnzahl() {
        return tastenAnzahl;
    }

    public void benutzen() {
        System.out.println("Hörer abnehmen und sprechen");
    }

    public double getSchluessel() {
        return Math.log(2.0 + Math.sin(30.0 * tastenAnzahl));
    }

}
