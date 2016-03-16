/**
 *	Mo 30. Nov 17:34:44 CET 2015
 */



public class Test {
	public static void main(String[] args ) {


		switch (seasons(21,12)) {
			case 0:
				System.out.println("Winter");
				break;
			case 1:
				System.out.println("Frühling");
				break;
			case 2:
				System.out.println("Sommer");
				break;
			case 3:
				System.out.println("Herbst");
		}
	}
public static int seasons(int day, int month) {
	int[] begins = {20,21,22,21};


	int jahreszeit = month / 3 -1;
	int rest   = month % 3;

	System.out.println(jahreszeit+", "+rest );

	if (!(rest == 0 && day >= begins[jahreszeit])) {
		jahreszeit++;
	}
	if (jahreszeit > 3 || jahreszeit < 0) {
		jahreszeit = 0;
	}

	System.out.println(jahreszeit+", "+rest );
	return jahreszeit;
}





}
