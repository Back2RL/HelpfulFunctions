package Threads;

import java.util.ArrayList;

public class Sub extends Thread {
	private boolean bPaused;
	private ArrayList<Integer> aList;

	public ArrayList<Integer> getaList() {
		return aList;
	}

	public void setaList(ArrayList<Integer> aList) {
		this.aList = aList;
	}

	public Sub(ArrayList<Integer> list) {
		if (list != null) {
			aList = list;
		} else {
			aList = new ArrayList<>();
		}
	}

	public boolean isbPaused() {
		return bPaused;
	}

	public void setbPaused(boolean bPaused) {
		this.bPaused = bPaused;
	}

	/**
	 * called on a thread to tell it to suspend its current work
	 */
	public void pause() {
		System.out.println(Thread.currentThread().getName() + ": sleep!");
		bPaused = true;
	}

	/**
	 * called on a thread to resume work
	 */
	public void proceed() {
		bPaused = false;
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
		System.out.println(aList);
		int num = (int) (Math.random() * 100);
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
				while (bPaused) {
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

}
