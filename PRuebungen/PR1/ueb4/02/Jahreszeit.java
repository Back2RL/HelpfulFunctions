import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

/**
 *	30.11.2015 15:45:08
 *
 *	PR1, WS2015/16
 *
 *	Leonard Oertelt
 *	Matrikelnummer 1276156
 *	leonard.oertelt@stud.hs-hannover.de
 * 
 *	Julian Opitz
 * 	Matrikelnummer 1302082
 * 	julian.opitz@stud.hs-hannover.de
 *
 */
public class Jahreszeit {

	private static final String PROMPT_DAY = "Tag eingeben (1-31): ";
	private static final String NOT_A_NUMBER = "Keine ganze Zahl. Bitte nochmal.";
	private static final String ILLEGAL_DAY = "Bitte im Bereich [1,31]! Nochmal.";
	private static final String PROMPT_MONTH = "Monat eingeben (1-12): ";
	private static final String ILLEGAL_MONTH = "Bitte im Bereich [1,12]! Nochmal.";
	private static final String ILLEGAL_DATE = "Ungültiges Datum. Nochmal.";
	private static final Map<String, String> ERROR_MAP = new HashMap<>();
	private static final int[] DAYS_PER_MONTH = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	private static final String[] SEASONS = { "Winter", "Frühling", "Sommer", "Herbst", };
	private static final int[] SEASON_START_DAY = { 20, 21, 22, 21 };

	static {
		ERROR_MAP.put(PROMPT_DAY, ILLEGAL_DAY);
		ERROR_MAP.put(PROMPT_MONTH, ILLEGAL_MONTH);
	}

	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		int day;
		int month;
		while (true) {
			day = promptForNumber(console, PROMPT_DAY, 1, 31);
			month = promptForNumber(console, PROMPT_MONTH, 1, 12);
			if (isLegalDate(day, month)) {
				break;
			} else {
				System.out.println(ILLEGAL_DATE);
			}
		}
		printSeason(day, month);
		console.close();
	}

	private static void printSeason(final int day, final int month) {
		int season = getSeasonNumber(day, month);
		System.out.println(SEASONS[season]);
	}

	private static int getSeasonNumber(final int day, final int month) {
		int i;
		for (i = 0; i < SEASON_START_DAY.length; i++) {
			if (month <= ((i + 1) * 3) - 1 || (month <= ((i + 1) * 3) && day < SEASON_START_DAY[i])) {
				return i;
			}
		}
		return i % 4;
	}

	private static boolean isLegalDate(final int day, final int month) {
		return isInInclusiveBounds(day, 1, 31) && isInInclusiveBounds(month, 1, 12) && day <= DAYS_PER_MONTH[month - 1];
	}

	private static int promptForNumber(final Scanner console, final String promptMessage, final int lowerBound,
			final int upperBound) {
		int ret;
		while (true) {
			ret = -1;
			try {
				System.out.print(promptMessage);
				ret = console.nextInt();
			} catch (InputMismatchException e) {
				console.nextLine();
				System.out.println(NOT_A_NUMBER);
				continue;
			}
			if (!isInInclusiveBounds(ret, lowerBound, upperBound)) {
				System.out.println(ERROR_MAP.get(promptMessage));
				continue;
			}
			return ret;
		}
	}

	private static boolean isInInclusiveBounds(final int number, final int lowerBound, final int upperBound) {
		return number >= lowerBound && number <= upperBound;
	}

}
