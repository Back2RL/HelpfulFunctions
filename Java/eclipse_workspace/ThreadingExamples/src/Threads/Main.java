package Threads;

import java.util.ArrayList;

public class Main {
	public static final int THREADS = 12;

	public static void main(String[] args) {
		System.out.println("Start " + THREADS + " threats and wait for them to finish");
		ArrayList<Sub> multipleThreads = new ArrayList<>();
		for (int i = 0; i < THREADS; ++i) {
			ArrayList<Integer> list = new ArrayList<>();
			for (int j = 0; j < THREADS; ++j) {
				list.add((int) (Math.random() * THREADS));
			}
			multipleThreads.add(new Sub(list));
			multipleThreads.get(i).start();
		}

		int index = (int) (Math.random() * THREADS);

		// pause a random thread
		synchronized (multipleThreads.get(index)) {
			if (!multipleThreads.get(index).isbPaused()) {
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

		for (int i = 0; i < THREADS; ++i) {
			synchronized (multipleThreads.get(i)) {
				System.out.println(multipleThreads.get(i).getaList());
			}
			// multipleThreads.get(i).interrupt();
			System.out.println(multipleThreads.get(i).getState());
			System.out.println(multipleThreads.get(i).isAlive());
		}
		// resume the thread
		synchronized (multipleThreads.get(index)) {
			if (multipleThreads.get(index).isbPaused()) {
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
}