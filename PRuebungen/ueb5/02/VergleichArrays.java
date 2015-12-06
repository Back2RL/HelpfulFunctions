public class VergleichArrays {

	public boolean kleinerGleich(int[] a, int[] b) {

		return isEmpty(a) || firstShorterOrEqualLengthAndEveryElementSmallerOrEquals(a, b)
				|| firstLongerButEveryIndexSmallerOrEqualsAndOneSmaller(a, b);
	}

	private boolean firstLongerButEveryIndexSmallerOrEqualsAndOneSmaller(int[] a, int[] b) {
		if (a.length <= b.length || b.length < 1)
			return false;
		boolean ret = false;
		for (int i = 0; i < b.length; i++) {
			if (b[i] < a[i])
				return false;
			if (a[i] < b[i])
				ret = true;
		}
		return ret;
	}

	private boolean firstShorterOrEqualLengthAndEveryElementSmallerOrEquals(int[] a, int[] b) {
		if (a.length > b.length)
			return false;
		for (int i = 0; i < a.length; i++) {
			if (a[i] > b[i])
				return false;
		}
		return true;
	}

	private boolean isEmpty(int[] a) {
		return a.length == 0;
	}
}
