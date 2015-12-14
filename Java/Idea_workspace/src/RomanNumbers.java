import java.util.Random;

/**
 * Mi 21. Okt 13:17:04 CEST 2015
 * <p>
 * PR1, WS2015/16
 * <p>
 * Leonard Oertelt
 * Matrikelnummer 1276156
 * leonard.oertelt@stud.hs-hannover.de
 * <p>
 * -----------------------------------------
 * prints random numbers as roman numbers (non-subtracting)
 */
public class RomanNumbers {
    public static void main(String[] args) {
        Random rand = new Random();

        System.out.println(ArabToRoman(rand.nextInt(2000) + 1));


    }

    public static String ArabToRoman(int number) {
        StringBuilder roman = new StringBuilder();
        for (int i = 0; i < number / 1000; i++) {
            roman.append('M');
        }
        int remainder = number % 1000;
        for (int i = 0; i < remainder / 500; i++) {
            roman.append('D');
        }
        remainder = remainder % 500;
        for (int i = 0; i < remainder / 100; i++) {
            roman.append('C');
        }
        remainder = remainder % 100;
        for (int i = 0; i < remainder / 50; i++) {
            roman.append('L');
        }
        remainder = remainder % 50;
        for (int i = 0; i < remainder / 10; i++) {
            roman.append('X');
        }
        remainder = remainder % 10;
        for (int i = 0; i < remainder / 5; i++) {
            roman.append('V');
        }
        remainder = remainder % 5;
        for (int i = 0; i < remainder; i++) {
            roman.append('I');
        }
        return roman.toString();
    }
}

/* EBNF für Römische Zahlen

Cs      ::= [C][C]
100-900 ::= (CCs|CD)|(DCs|DCCC|CM)
Xs      ::= [X][X]
10-90   ::= (XXs|XL)|(LXs|LXXX|XC)
Is      ::= [I][I]
1-9     ::= (IIs|IV)|(VIs|VIII|IX)

ZahlGleich2000          ::= MM
ZahlGrößerGleich1000    ::= M[<100-900>][<10-90>][<1-9>]
ZahlGrößerGleich100     ::= <100-900>[<10-90>][<1-9>]
ZahlGrößerGleich10      ::= <10-90>[<1-9>]
ZahlGrößerGleich1       ::= <1-9>


roman ::= ZahlGleich2000 | ZahlGrößerGleich1000 | ZahlGrößerGleich100 |  | ZahlGrößerGleich10 | ZahlGrößerGleich1

463
CDLXIII

*/