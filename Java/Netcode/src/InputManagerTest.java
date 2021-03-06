
/**
 * File: InputManagerTest.java
 * 
 * This file is free to use and modify as it is for educational use.
 * brought to you by Game Programming Snippets (http://gpsnippets.blogspot.com/)
 * 
 * Revisions:
 * 1.1 Initial Revision 
 * 
 */

import java.awt.Frame;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * This is just a simple test example for the input manager snippet. It is just
 * a simple frame and a threaded loop that runs about every 16 milliseconds or
 * so which is 62.5 times per second.
 */
public class InputManagerTest extends Frame implements Runnable {

	/**
	 * TODO: find out what this is
	 */
	private static final long serialVersionUID = 1L;

	private final int MAXFRAMERATE = 10;
	private final boolean LOCKFRAMERATE = true;
	private final long TIMEOUT = (long) (1000 / MAXFRAMERATE);

	// The test input manager
	private final InputManager input;

	// A lock object used by the thread for pausing, there are other ways to do
	// this.
	private final Object lock = new Object();

	// Used to only start the looping thread once.
	private boolean isTestLoopRunning = false;

	/**
	 * Add the InputManager as the KeyListener to the Frame.
	 */
	public InputManagerTest() {
		input = new InputManager();
		this.addKeyListener(input);

		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				startTestLoop();
			}
		});
	}

	/**
	 * Starts a new thread using this class as the Runnable instance for that
	 * thread.
	 */
	public void startTestLoop() {
		// only want to run once.
		if (!isTestLoopRunning) {
			(new Thread(this)).start();
			isTestLoopRunning = true;
		}
	}

	/**
	 * The meat of the test to check and make sure the input manager is working
	 * correctly.
	 */
	@Override
	public void run() {
		// This is the makeshift polling loop to test the input manager
		synchronized (lock) {
			System.out.println("Start Test Loop");

			// use this to test how many times the loop detects a key is down
			int count = 0;
			long lastTick = System.nanoTime();
			double deltaSeconds;
			// run while the frame is showing
			while (this.isShowing()) {

				// calculate the time since last tick
				deltaSeconds = (System.nanoTime() - lastTick) * 0.000000001f;
				lastTick = System.nanoTime();

				tick(deltaSeconds);

				// run test cases
				// if (input.isAnyKeyDown()) {
				// System.out.println("Some key is down");
				// }

				// if (input.isAnyKeyUp()) {
				// System.out.println("Some key is up");
				// }
				//
				// if (input.isKeyDown(KeyEvent.VK_SPACE)) {
				// ++count;
				// System.out.println("Spacebar is down");
				// }
				//
				// if (input.isKeyDown(KeyEvent.VK_UP)) {
				// System.out.println("UP is down");
				// }
				//
				// if (input.isKeyUp(KeyEvent.VK_SPACE)) {
				// System.out.println("Spacebar is up");
				// System.out.format("Spacebar detected down %d times\n",
				// count);
				// count = 0; // reset the counter
				// }
				// System.out.println(
				// input.getNumKeysDown() + " Keys are pressed; Any key is
				// pressed: " + input.isKeyPressed());
				// input.update();

				if (LOCKFRAMERATE) {
					try {
						lock.wait(TIMEOUT);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} // just do a constant 16 milliseconds
				}
			}
			System.out.println("End Test Loop");
		}
	}

	/**
	 * Tick function
	 * 
	 * @param deltaSeconds
	 *            in seconds (float)
	 */
	public void tick(double deltaSeconds) {
		// System.out.println(1.0f / deltaSeconds);
	}

	/**
	 * Main method to use to run the test from, it creates a basic awt frame and
	 * displays the frame.
	 * 
	 * @param args
	 *            Command line arguments.
	 */
	public static void main(String[] args) {
		final Frame frame = new InputManagerTest(); // create our test class.

		// set a default size for the window, basically just give it some area.
		frame.setSize(640, 480);

		frame.setLocationRelativeTo(null); // centers window on screen

		// Need to add this to handle window closing events with the awt Frame.
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent arg0) {
				frame.setVisible(false);
				frame.dispose();
				System.exit(0);
			}

		});

		// show the frame
		frame.setVisible(true);
	}

}
