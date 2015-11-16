/**
 *	Mo 16. Nov 23:58:40 CET 2015
 *
 *	PR1, WS2015/16
 *
 *	Leonard Oertelt
 *	Matrikelnummer 1276156
 *	leonard.oertelt@stud.hs-hannover.de
 *
 *	-----------------------------------------
 *	collection of examples with files
 */
import java.io.*;
import java.util.*;

public class ReadFile {
	public static void main(String[] args) throws IOException {

		File f = new File("example.txt");

		System.out.println("canRead " + f.canRead());
		//System.out.println("delete " + f.delete());
		System.out.println("exists " + f.exists());
		System.out.println("getAbsolutePath " + f.getAbsolutePath());
		System.out.println("getPath " + f.getPath());
		System.out.println("getName " + f.getName());
		System.out.println("isDirectory	" + f.isDirectory());
		System.out.println("isFile " + f.isFile());
		System.out.println("length " + f.length());
		System.out.println("mkdirs " + new File("Test").mkdir());
		System.out.println("renameTo " + f.renameTo(new File("example2.txt")));

		f = new File("DataExample.txt");
		if(!f.exists()) {
			System.out.println("createNewFile " + f.createNewFile());
		}
		//Scanner input = new Scanner(new File("DataExample.txt"));
		//or short
		Scanner input = new Scanner(f); // this needs IOException-ignoration (see main method)

		printlnAllTokens(input);

		input = new Scanner(f);

		printlnAllLines(input);

		input = new Scanner(f);

		printlnAllIntegers(input);

		input = new Scanner(f);

		printlnAllDoubles(input);
		input.close();
	}

	public static void printlnAllTokens(Scanner input) {
		while (input.hasNext()) {
			System.out.println(input.next());
		}
	}

	public static void printlnAllLines(Scanner input) {
		while (input.hasNextLine()) {
			System.out.println(input.nextLine());
		}
	}

	public static void printlnAllIntegers(Scanner input) {
		while (input.hasNext()) {
			if (input.hasNextInt()) {
				System.out.println(input.nextInt());
				continue;
			}
			input.next();
		}
	}
	
public static void printlnAllDoubles(Scanner input) {

// IMPORTANT for use with decimals
input.useLocale(new Locale("en","US"));

		while (input.hasNext()) {
			if (input.hasNextDouble()) {
				System.out.println(input.nextDouble());
				continue;
			}
			input.next(); // skip what is not a double
		}
	}
}
