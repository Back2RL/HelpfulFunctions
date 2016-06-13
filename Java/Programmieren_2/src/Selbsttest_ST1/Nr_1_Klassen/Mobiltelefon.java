package Selbsttest_ST1.Nr_1_Klassen;

public class Mobiltelefon extends Telefon {

    private String netzKennung;

    public Mobiltelefon(final int tastenAnzahl, final String netzkennung) {
        super(tastenAnzahl);
        this.netzKennung = netzkennung;
    }

    public boolean istSeniorenModel() {
        return getTastenAnzahl() == 12 && netzKennung.equals("Telekom");
    }

    @Override
    public double getSchluessel() {
        return super.getSchluessel() + 1.0;
    }

    @Override
    public void benutzen() {
        System.out.println("Laufen und sprechen");
    }
}
