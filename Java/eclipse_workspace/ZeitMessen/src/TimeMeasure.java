
public class TimeMeasure {

	private long start = 0;
	private double laufzeit;

	public double getLaufzeit() {
		return laufzeit;
	}

	public void startTimer() {
		// ----- Zeit messen ------
		System.out.println("Zeit Messung start...");
		start = System.currentTimeMillis();
	}

	public void stopTimer() {
		laufzeit = laufzeitSekunden(start);
		System.out.println(" Laufzeit = " + laufzeit + " Sekunden");
	}

	public static double laufzeitSekunden(long startTime) {
		return (System.currentTimeMillis() - startTime) * 0.001f;
	}
}
