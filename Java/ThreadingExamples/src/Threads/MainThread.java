package Threads;

public class MainThread {
	public static void main(String[] args) {
		MyThread t = new MyThread();
		t.start();

		// if (t.isAlive()) {
		t.interrupt();
		// }
		for (int i = 0; i < 1000; i++) {
			System.out.print('#');
		}
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
