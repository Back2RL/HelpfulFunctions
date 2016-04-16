import java.util.ArrayList;

public class HelloThread extends Thread {
	public boolean fPause = false;

	// Irgendwelcher Code...

	public void pause() {
		System.out.println(Thread.currentThread().getName() + ": calling pause!");
		fPause = true;
	}

	public void proceed() {
		fPause = false;
		System.out.println(Thread.currentThread().getName() + ": awakened!");
		notify();
	}

	public void run() {
		System.out.println(Thread.currentThread().getName() + ": Hello from a thread!");

		int num = 100;
		for (int i = 0; i < num; ++i) {
			// Irgendwelcher Code...
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Überprüfen, ob pausieren angesagt ist:
			synchronized (this) {
				while (fPause) {
					try {
						System.out.println(Thread.currentThread().getName() + ": supended!");
						wait();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		System.out.println(Thread.currentThread().getName() + ": Thread work finished");
	}

	public static void main(String args[]) {

		// HelloThread aThread = new HelloThread();

		System.out.println("Wait for Threads to finish");
		// aThread.start();

		ArrayList<HelloThread> multipleThreads = new ArrayList<>();
		for (int i = 0; i < 4; ++i) {
			multipleThreads.add(new HelloThread());
			multipleThreads.get(i).start();
		}

		int index = (int) (Math.random() * 4);

		// pause the thread
		synchronized (multipleThreads.get(index)) {
			multipleThreads.get(index).pause();
		}

		for (int i = 0; i < 4; ++i) {
			if (i == index)
				continue;
			try {
				if (multipleThreads.get(i).isAlive())
					multipleThreads.get(i).join(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Back in main Thread");

		System.out.println("3 Threads finished");

		// resume the thread
		synchronized (multipleThreads.get(index)) {
			multipleThreads.get(index).proceed();
		}

	}

	// The join method allows one thread to wait for the completion of another.
	// If t is a Thread object whose thread is currently executing,
	// t.join();
}