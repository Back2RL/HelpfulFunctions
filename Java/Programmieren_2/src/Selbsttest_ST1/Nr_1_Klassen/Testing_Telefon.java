package Selbsttest_ST1.Nr_1_Klassen;

/**
 * Created by oertelt on 13.06.16.
 */
public class Testing_Telefon {
    public static void main(String[] args) {
        Telefon tel = new Telefon(12);
        tel.benutzen();
        System.out.println(tel.getSchluessel());
        System.out.println(tel.getTastenAnzahl());

        Telefon mob = new Mobiltelefon(12, "Telekom");
        mob.benutzen();
        System.out.println(mob.getSchluessel());
        System.out.println(mob.getTastenAnzahl());
        System.out.println(((Mobiltelefon) mob).istSeniorenModel());
    }
}
