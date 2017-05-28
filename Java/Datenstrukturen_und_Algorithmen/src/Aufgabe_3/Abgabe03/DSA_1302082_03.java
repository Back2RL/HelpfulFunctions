package Aufgabe_3.Abgabe03;

public class DSA_1302082_03 {

	private final static long LIMIT = 62;

	public static void main(String[] args) {
		for (long i = 40; i <= LIMIT; i++) {
			long start = System.currentTimeMillis();
			System.out.printf("n = %d, k = %d\rrecursive binomial: %d\rtime taken: %.2f seconds\r\r", i, i / 2,
					binomialStupidRecursive(i, i / 2), (System.currentTimeMillis() - start) / 1000.0);
		}
	}

	private static void binomialPrecondition(final long n, final long k) {
		if (n < 0 || k < 0 || n < k)
			throw new IllegalArgumentException();
	}

	/**
	 * correct result up to binom(61,30) WolframAlpha result for binom(62,31):
	 * 465428353255261088 this method: -129627907186982512
	 */
	public static long binomialLittleBetter(final long n, final long k) {
		binomialPrecondition(n, k);
		long start = (k >= n - k) ? k : n - k;
		long divisor = (k < n - k) ? k : n - k;
		long dividend = 1;
		long j = 1;
		for (long i = start + 1; i <= n; i++) {
			dividend *= i;
			if (j <= divisor) {
				dividend /= j;
				j++;
			}
		}
		return dividend;
	}

	/**
	 * correct result up to binom(20,10) WolframAlpha result for binom(21,10):
	 * 352716 this method: -29335
	 */
	public static long binomialStupid(final long n, final long k) {
		binomialPrecondition(n, k);
		return factorial(n) / (factorial(n - k) * factorial(k));
	}

	/**
	 * Infeasibly slow for values over ~34 Largest calculated: binom(40,20) =
	 * 137846528820 time taken: 190,51 seconds
	 */
	public static long binomialStupidRecursive(final long n, final long k) {
		// binomialPrecondition(n, k);
		if (k == 1)
			return n;
		if (k == n)
			return 1;
		return binomialStupidRecursive(n - 1, k - 1) + binomialStupidRecursive(n - 1, k);
	}

	private static long factorial(final long num) {
		long ret = 1;
		for (int i = 1; i <= num; i++) {
			ret *= i;
		}
		return ret;
	}

}
