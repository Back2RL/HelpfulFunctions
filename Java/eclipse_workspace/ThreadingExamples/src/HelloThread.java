
public class HelloThread extends Thread {

	public void run() {
		System.out.println("Hello from a thread!");
	}

	public static void main(String args[]) {
		(new HelloThread()).start();
	}

	// The join method allows one thread to wait for the completion of another.
	// If t is a Thread object whose thread is currently executing,
	// t.join();
}