package u1a04;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Format {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner file = null;
		try {
			File datei = new File(args[0]);
			file = new Scanner(datei);

			if (file.hasNextLine())
				// erste Zeile mit Spaltenbeschreibung überspringen
				file.nextLine();
			while (file.hasNextLine()) {
				String line = file.nextLine();
				StringBuilder sentence = new StringBuilder();

				String[] tokens = line.split(";");
				sentence.append(tokens[0]);
				appendChars(sentence, tokens[0], 13, ' ');
				sentence.append(" ist geboren in ");
				sentence.append(tokens[1]);
				appendChars(sentence, tokens[1], 3, ' ');
				sentence.append(" und liebt ");
				sentence.append(tokens[2]);
				appendChars(sentence, tokens[2], 9, ' ');
				sentence.append(" und ");
				// Komma ersetzen da z.B. 3,33 nicht als Float/Double gelesen
				// werden kann
				double number = Double.valueOf(tokens[3].replace(',', '.'));
				DecimalFormat formatter = new DecimalFormat("0000.00");
				sentence.append(formatter.format(number));
				// Ausgabe
				System.out.println(sentence);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (file != null)
				file.close();
		}
	}

	/**
	 * Füllt Character auf
	 * 
	 * @param sentence
	 *            Der Stringbuilder an den die Character angehängt werden
	 * @param s
	 *            Der String der einen Teil des aufzufüllenden Bereichs ausmacht
	 * @param n
	 *            Geforderte Länge des Bereichs
	 * @param c
	 *            Der Character der zum Auffüllen benutzt wird
	 */
	public static void appendChars(StringBuilder sentence, String s, int n, char c) {
		for (int i = 0; i < n - s.length(); i++) {
			sentence.append(c);
		}
	}
}
