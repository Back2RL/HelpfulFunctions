package Leseaufgabe_05;

public class Quersumme {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Summe = " + ziffernSumme(29107));
	}

	public static int ziffernSumme(int zahl) {
		int sum = 0;
		while (zahl != 0) {
			sum += zahl % 10;
			zahl = zahl / 10;
		}
		return sum;
	}
}
