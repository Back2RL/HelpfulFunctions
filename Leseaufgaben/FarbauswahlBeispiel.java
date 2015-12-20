import java.awt.Color;
import javax.swing.JColorChooser;

public class FarbauswahlBeispiel
{
    // main-Methode
    public static void main(String[] args)
    {
        // Erstellung eines JColorChooser Dialoges, 
        // der eine Farbe zurück gibt
        Color ausgewaehlteFarbe = JColorChooser.showDialog(null,
                "Farbauswahl", null);
        // Ausgabe der ausgewählten Farbe
        System.out.println(ausgewaehlteFarbe);
    }
}