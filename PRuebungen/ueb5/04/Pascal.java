import java.util.Scanner;

public class Pascal {
	private static final String PROMPT = "Wieviele Zeilen? ";

	public static void main(String[] args) {
		int rows = getRowsFromUser();
		int[][] pascal = getPascalTriangle(rows);
		printArray(pascal);
	}

	private static void printArray(int[][] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}

	}

	private static int getRowsFromUser() {
		while (true)
			try {
				//nice to know: do not close System.in
				@SuppressWarnings("resource")
				Scanner c = new Scanner(System.in);
				System.out.print(PROMPT);
				return c.nextInt();
			} catch (Exception e) {
				System.out.println("Ungültige Eingabe");
				continue;
			}
	}

	private static int[][] getPascalTriangle(int rows) {
		if (rows <= 0)
			return new int[0][0];
		int[][] ret = new int[rows][];
		ret[0] = new int[] { 1 };
		for (int i = 1; i < rows; i++) {
			ret[i] = new int[i + 1];
			for (int j = 0; j <= i; j++) {
				int num = 0;
				num += getValueAboveAndLeft(ret, i, j);
				ret[i][j] = num;
			}
		}
		return ret;
	}

	private static int getValueAboveAndLeft(int[][] array, int i, int j) {
		int ret = 0;
		int xIndex = i - 1;
		int yIndex1 = j;
		int yIndex2 = j - 1;
		if (xIndex >= 0)
			if (yIndex1 >= 0 && yIndex1 < i)
				ret += array[xIndex][yIndex1];
		if (yIndex2 >= 0 && yIndex2 < i)
			ret += array[xIndex][yIndex2];
		return ret;
	}
}
