/**
 *	PR1, WS2015/16
 *
 *	Leonard Oertelt
 *	Matrikelnummer 1276156
 *	leonard.oertelt@stud.hs-hannover.de
 * 
 *	Fr 2. Okt 14:32:05 CEST 2015
 *	-----------------------------------------
 *	Dieses Programm gibt 1000-mal das Zitat von Imanuel Kant aus:
 *	"Habe Mut dich deines eigenen Verstandes zu bedienen!"
 */
public class Kant {
	/* Methode, die beim Programmstart zuerst ausgeführt wird. */
	public static void main(String[] args) {
		//Aufruf der Methode, die widerum 10-mal die eigentliche Ausgabe aufruft: 10³ = 1000 Ausgaben.		
		rufeAusgabeAuf();
		rufeAusgabeAuf();
		rufeAusgabeAuf();
		rufeAusgabeAuf();
		rufeAusgabeAuf();
		rufeAusgabeAuf();
		rufeAusgabeAuf();
		rufeAusgabeAuf();
		rufeAusgabeAuf();
		rufeAusgabeAuf();
	}
	//ruft 10-mal die Methode ausgabe auf	
	public static void rufeAusgabeAuf() {
		ausgabe();
		ausgabe();
		ausgabe();
		ausgabe();
		ausgabe();
		ausgabe();
		ausgabe();
		ausgabe();
		ausgabe();
		ausgabe();
	}
	//gibt mithilfe eines String Kant's Zitat 10-mal aus
	public static void ausgabe() {
		String text = "Habe Mut dich deines eigenen Verstandes zu bedienen!\n";
		System.out.print(text + text + text + text + text + text + text + text + text + text);
	}
}

