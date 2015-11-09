/**
 *	Do 22. Okt 12:01:22 CEST 2015
 *
 *	WS2015/16
 *
 *	Leonard Oertelt
 *	Matrikelnummer 1276156
 *	leonard.oertelt@stud.hs-hannover.de
 * 
 *	Fr 3. Okt 14:24:49 CEST 2015
 *	-----------------------------------------
 *	Programmbeschreibung
 */
public class Leseaufgabe02 {
    public static void main(String[] args) {
        // Letzte Ziffer einer Zahl isolieren:
        System.out.println(123%10);
        // Vorletzte Ziffer einer Zahl isolieren:
        System.out.println(123%100/10);
        
        // System.out.println("C:\Users\norbert\Documents\teafortwo");
        // Richtig wäre:
        System.out.println("C:\\Users\\norbert\\Documents\\teafortwo");
        
        // Aufruf der Methode
        doBreak();
        
        // Augabe einer Rechnung:
        System.out.println(1.3*5-5/2);
        
        // Ausgabe einer Rechnung bei der sich der Rechenfehler zeigt:
        System.out.println(0.1+0.1+0.1);
        
        FragenZurVorlesung2fi();
        FragenZurVorlesung2fii();
        FragenZurVorlesung2ki();
        FragenZurVorlesung2kiii();
        FragenZurVorlesung2mi();
        FragenZurVorlesung2mii();
        FragenZurVorlesung2miii();
        FragenZurVorlesung2miv();
        FragenZurVorlesung2oi();
        
    }
    
    // public static void break(){ System.out.println("Spielabbruch"); }
    // Richtig wäre:
    public static void doBreak(){
        System.out.println("Spielabbruch");
    }
    
    // Was gibt der folgende Code aus?
    public static void FragenZurVorlesung2fi() {
        System.out.println("Was gibt der folgende Code aus?");
        int number;
        number = 2 + 3 * 4;
        System.out.println(number - 1);
        
        number = 16 % 6;
        System.out.println(2 * number);
    }
    public static void FragenZurVorlesung2fii() {
        System.out.println("Was gibt der folgende Code aus?");
        double average;
        average = (11 + 8) / 2;
        System.out.println(average);
        
        average = (5 + average * 2) /2;
        System.out.println(average);
    }
    // Schreiben Sie eine Schleife für die folgende Ausgabe:
    public static void FragenZurVorlesung2ki() {
        for (int i = 1; i <= 7; ++i) {
            System.out.println("Am "+i+"-ten Tag der Woche will ich eifrig programmieren.");   
        }
    }
    
    // Betrachte folgende Schleife: 
    // for (int count = 1; count <= 5; count++) { 
    // ... 
    // }  
    // Welche Anweisung schreiben Sie in den Rumpf, um folgende Ausgabe zu erhalten? 
    public static void FragenZurVorlesung2kiii() {
        for (int count = 1; count <= 5; count++) {
            System.out.println(21-(count*4));     
        }
    }
    
    // Was gibt die folgende geschachtelte Schleife aus? 
    public static void FragenZurVorlesung2mi() {
        for (int i = 1; i <= 6; i++) { 
            for (int j = 1; j <= 10; j++) { 
                System.out.print("*"); 
            } 
            System.out.println();
        }
    }
    
    public static void FragenZurVorlesung2mii() {
        for (int i = 1; i <= 6; i++) { 
            for (int j = 1; j <= i; j++) { 
                System.out.print("*"); 
            } 
            System.out.println(); 
        } 
    }
    public static void FragenZurVorlesung2miii() {
        for (int i = 1; i <= 6; i++) { 
            for (int j = 1; j <= i; j++) { 
                System.out.print(i); 
            } 
            System.out.println(); 
        } 
    }
    public static void FragenZurVorlesung2miv() {
        for (int i = 1; i<=2; i++) {
            for (int n = 1; n<=3; n++) {
                System.out.println(n+", "+i);
            }
        }
    }
    
    // Korrigieren Sie das Programm, so dass das letzte Komma hinter der letzten ausgegebenen 
    // Zahl verschwindet. Gewünschte Ausgabe: 
    // 2.0, 4.0, 8.0, 16.0
    // Powers
    public static void FragenZurVorlesung2oi() { 
        double FACTOR= 2.0; 
        int EXPONENT= 4; 
        double product= 1.0; 
        for (int i=1; i <= EXPONENT; i++) { 
            product *= FACTOR;
            if (i == EXPONENT) {
                System.out.print(product);
                return;
            }
            System.out.print(product + ", "); 
        } 
        System.out.println(); 
    } 
    
}


