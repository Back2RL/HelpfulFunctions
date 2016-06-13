import javax.swing.*;

public class ColorChooserFrame {
    // main-Methode
    public static void main(String[] args) {
        // Erzeugung eines neuen Frames 
        // mit dem Titel "Mein JFrameBeispiel"         
        JFrame meinJFrame = new JFrame();
        meinJFrame.setTitle("Mein JFrame Beispiel");
        // Wir setzen die Breite auf 450 
        // und die Höhe auf 300 Pixel, 
        // damit unser JColorChooser hineinpasst
        meinJFrame.setSize(450, 300);
        // Erzeugung eines Objektes der Klasse JColorChooser
        JColorChooser colorChooser = new JColorChooser();
        // Holt ContentPane von unserem Frame 
        // und fügt diesem unseren JColorChooser hinzu      
        meinJFrame.getContentPane().add(colorChooser);
        // Wir lassen unseren Frame anzeigen
        meinJFrame.setVisible(true);
    }
}