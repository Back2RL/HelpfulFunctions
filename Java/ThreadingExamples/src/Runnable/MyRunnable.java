package Runnable;

public class MyRunnable implements Runnable {

	volatile boolean paused;

	@Override
	public void run() {
		while (true) {
			while (!paused) {
				System.out.println("Hallo");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {

		MyRunnable run = new MyRunnable();
		Thread thread = new Thread(run);
		thread.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		run.paused = true;
		System.out.println("paused");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out.println("restart");
		run.paused = false;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("All threads finished work.");
		System.exit(0);
	}

}
