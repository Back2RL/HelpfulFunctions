package Threads;

public class MyThread extends Thread {

	@Override
	public void run() {
		yield();
		for (int i = 0; i < 1000; i++) {
			System.out.print('0');
		}
	}

}
