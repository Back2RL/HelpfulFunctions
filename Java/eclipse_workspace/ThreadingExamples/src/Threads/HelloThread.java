package Threads;
import java.util.ArrayList;

/**
 * @author Leo
 *
 */
public class HelloThread extends Thread {
	// to tell a thread to pause its current work
	public boolean fPause = false;

	public boolean isfPause() {
		return fPause;
	}

	public static final int THREADS = 4;

	/**
	 * called on a thread to tell it to suspend its current work
	 */
	public void pause() {
		System.out.println(Thread.currentThread().getName() + ": sleep!");
		fPause = true;
	}

	/**
	 * called on a thread to resume work
	 */
	public void proceed() {
		fPause = false;
		System.out.println(Thread.currentThread().getName() + ": awaken!");
		notify();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		System.out.println(Thread.currentThread().getName() + ": Hello from a thread!");

		// simulate work in thread {
		int num = 100;
		for (int i = 0; i < num; ++i) {

			// TODO: here the magic happens (work)
			try {
				// simulate time intensive work
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// check regularly whether parent thread called for suspension {
			synchronized (this) {
				while (fPause) {
					// suspend thread when pasue is true
					try {
						System.out.println(Thread.currentThread().getName() + ": supended!");
						wait();
						// thread will be awakened with proceed() call from
						// parent thread
						System.out.println(Thread.currentThread().getName() + ": resuming work!");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			// }
		}
		// }
		// simple message to tell user that thread has finished its work
		System.out.println(Thread.currentThread().getName() + ": Thread work finished");
	}

	public static void main(String args[]) {

		System.out.println("Start " + THREADS + " threats and wait for them to finish");
		ArrayList<HelloThread> multipleThreads = new ArrayList<>();
		for (int i = 0; i < THREADS; ++i) {
			multipleThreads.add(new HelloThread());
			multipleThreads.get(i).start();
		}

		int index = (int) (Math.random() * THREADS);

		// pause a random thread
		synchronized (multipleThreads.get(index)) {
			if (!multipleThreads.get(index).isfPause()) {
				System.out.println(multipleThreads.get(index).getName() + " is running.");
				multipleThreads.get(index).pause();
			}
		}

		for (int i = 0; i < THREADS; ++i) {
			if (i == index)
				continue;
			try {
				// if (multipleThreads.get(i).isAlive())
				multipleThreads.get(i).join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Back in main Thread");

		System.out.println((THREADS - 1) + " Threads finished");

		// resume the thread
		synchronized (multipleThreads.get(index)) {
			if (multipleThreads.get(index).isfPause()) {
				System.out.println(multipleThreads.get(index).getName() + " is sleeping.");
				multipleThreads.get(index).proceed();
			}
		}

		try {
			multipleThreads.get(index).join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("All threads finished work.");
	}

	// The join method allows one thread to wait for the completion of another.
	// If t is a Thread object whose thread is currently executing,
	// t.join();
}