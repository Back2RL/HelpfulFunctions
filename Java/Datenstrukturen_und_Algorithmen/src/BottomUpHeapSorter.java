public class BottomUpHeapSorter {
	private int[] a;

	private int n;

	public void sort(int[] a) {
		this.a = a;
		n = a.length;
		bottomupheapsort();
	}

	private void bottomupheapsort() {
		int x, u;
		buildheap();
		while (n > 1) {
			n--;
			x = a[n]; // Markierung des letzten Blatts
			a[n] = a[0];
			u = holedownheap();
			upheap(u, x);
		}
	}

	private void buildheap() {
		for (int v = n / 2 - 1; v >= 0; v--)
			downheap(v);
	}

	private void downheap(int v) {
		int w = 2 * v + 1; // erster Nachfolger von v
		while (w < n) {
			if (w + 1 < n) // gibt es einen zweiten Nachfolger?
				if (a[w + 1] > a[w])
					w++;
			// w ist der Nachfolger von v mit maximaler Markierung

			if (a[v] >= a[w])
				return; // v hat die Heap-Eigenschaft
			// sonst
			exchange(v, w); // vertausche Markierungen von v und w
			v = w; // fahre mit v=w fort
			w = 2 * v + 1;
		}
	}

	private int holedownheap() {
		int v = 0, w = 2 * v + 1;

		while (w + 1 < n) // zweiter Nachfolger existiert
		{
			if (a[w + 1] > a[w])
				w++;
			// w ist der Nachfolger von v mit maximaler Markierung
			a[v] = a[w];
			v = w;
			w = 2 * v + 1;
		}

		if (w < n) // einzelnes Blatt
		{
			a[v] = a[w];
			v = w;
		}
		// freigewordene Position ist an einem Blatt angekommen
		return v;
	}

	private void upheap(int v, int x) {
		int u;
		a[v] = x;
		while (v > 0) {
			u = (v - 1) / 2; // Vorgänger
			if (a[u] >= a[v])
				return;
			// sonst
			exchange(u, v);
			v = u;
		}
	}

	private void exchange(int i, int j) {
		int t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

} // end class BottomupHeapSorter
