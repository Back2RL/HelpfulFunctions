package u1a03;

import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner console = new Scanner(System.in);
		System.out.print("Wie lautet Ihr Name? ");
		String name = console.nextLine();
		System.out.print("Wie lautet Ihre Matrikelnummer? ");
		String matnr = console.nextLine();
		console.close();
		System.out.println(name + "(" + matnr + ")");

	}

}
