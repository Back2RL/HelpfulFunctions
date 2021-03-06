
/**
 * File: SmoothMovement.java
 *
 * This file is free to use and modify as it is for educational use.
 *
 * Version:
 * 1.1 Initial Version
 *
 */

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;

/**
 * This snippet uses the java.awt.Canvas and the java.awt.image.BufferStrategy
 * class in order to draw. The smooth movement is a result of precise and smooth
 * update interval amounts such that the user will see movement on a regular and
 * continued basis without too big of a jump between frames.
 *
 * @author Nick
 */
public class SmoothMovement extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// performance settings
	static {
		System.setProperty("sun.java2d.transaccel", "True");
		System.setProperty("sun.java2d.opengl", "True");
		// System.setProperty("sun.java2d.d3d", "True"); //on by default
		// System.setProperty("sun.java2d.ddforcevram", "True");
	}

	// app title.
	private static final String TITLE = "GPSnippets: SmoothMovement Snippet";

	// the strategy used for double buffering, or any number of buffered frames.
	private BufferStrategy strategy;

	// the main render and update task.
	private Thread renderTask;

	private final Rectangle2D.Double basicBlock = new Rectangle2D.Double(0, 0, 16, 16);

	// just a gradient background for our little demo.
	private Paint backgroundGradient;

	// our little basic blocks speed, which will be multiplied by the number
	// of seconds passed, each render frame.
	private final double speed = 350.0d /* px/sec */;

	// true if the up arrow key is being pressed, false otherwise
	private boolean up = false;

	// true if the down arrow key is being pressed, false otherwise
	private boolean down = false;

	// true if the right arrow key is being pressed, false otherwise
	private boolean right = false;

	// true if the left arrow key is being pressed, false otherwise
	private boolean left = false;

	/**
	 * This configures the canvas component for rendering. Creates any objects
	 * we need and sets up some default listeners for component events.
	 */
	public SmoothMovement() {
		// used for the pack method to keep the rendered area an exact size.
		setPreferredSize(new Dimension(800, 600));

		// we will be doing our own rendering, using the strategy.
		this.setIgnoreRepaint(true);

		// add quick and dirty 4 directional movement for our block
		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					up = true;
				}

				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					down = true;
				}

				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					right = true;
				}

				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					left = true;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					up = false;
				}

				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					down = false;
				}

				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					right = false;
				}

				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					left = false;
				}
			}
		});
	}

	/**
	 * Our drawing function which utilizes the BufferStrategy that enables us to
	 * do off-screen rendering without having to wait for swing to repaint, the
	 * component. It also eliminates flickering and splicing issues.
	 */
	public void render() {
		// the back buffer graphics object
		Graphics2D bkG = (Graphics2D) strategy.getDrawGraphics();

		// clear the backbuffer, this could be substituted for a background
		// image or a tiled background.
		bkG.setPaint(backgroundGradient);
		bkG.fillRect(0, 0, getWidth(), getHeight());

		// TODO: Draw your game world, or scene or anything else here.

		// Rectangle2D is a shape subclass, and the graphics object can render
		// it, makes things a little easier.
		bkG.setColor(Color.green.darker());
		bkG.fill(basicBlock);

		// properly dispose of the backbuffer graphics object. Release resources
		// and cleanup.
		bkG.dispose();

		// flip the backbuffer to the canvas component.
		strategy.show();

		// synchronize drawing with the display refresh rate.
		Toolkit.getDefaultToolkit().sync();
	}

	/**
	 * It is necessary to wait until after the component has been displayed in
	 * order to create and retrieve the buffer strategy. This is the part that
	 * took me the longest to figure out. But it makes sense since the component
	 * requires native resources in order to perform hardware acceleration and
	 * those resources are handled by the component itself, and are only
	 * available once the component is created and displayed.
	 */
	public void setup() {
		// center the block in the canvas.
		basicBlock.x = this.getWidth() / 2 - basicBlock.width / 2;
		basicBlock.y = this.getHeight() / 2 - basicBlock.height / 2;

		// create the background gradient paint object.
		backgroundGradient = new GradientPaint(0, 0, Color.magenta, getWidth(), getHeight(), Color.black.brighter());

		// create a strategy that uses two buffers, or is double buffered.
		this.createBufferStrategy(2);

		// get a reference to the strategy object, for use in our render method
		// this isn't necessary but it eliminates a call during rendering.
		strategy = this.getBufferStrategy();

		start();
	}

	/**
	 * Initialize the render and update task, to call the render method, do
	 * timing and fps counting, handling input.
	 */
	public void start() {

		// our main task for handling the rendering and for updating and
		// handling input and movement events.
		renderTask = new Thread() {
			// no local variables in a constant loop.
			long lasttime = -1;
			long msDelta = 0;
			double dt = 0.01;
			long time;
			double deltaTime = 0;
			double accumulator = 0;

			@Override
			public void run() {
				final Object sync = new Object();
				while (true) {
					synchronized (sync) {
						// use nano time, for more accuracy
						time = System.nanoTime();

						// record the initial last time
						if (lasttime == -1) {
							lasttime = time;
						}

						// delta in milliseconds
						msDelta = (long) ((time - lasttime) * 0.000001d);

						// delta in seconds
						deltaTime = (time - lasttime) * 0.000000001d;

						// cap the number of update loops needed to keep up with
						// high frame-rates.
						if (deltaTime > 0.25) {
							deltaTime = 0.25;
						}

						lasttime = time;

						accumulator += deltaTime;

						// accurate update rates, loop for each fixed
						while (accumulator > deltaTime) {
							update(dt);
							accumulator -= dt;
						}

						// check to make sure that the renderstrategy is valid
						if (SmoothMovement.this.isShowing()) {
							render();
						}

						// if there is any time left due to quick updating and
						// rendering then the thread can sleep
						long timeLeft = (10 - msDelta);
						if (timeLeft > 0) {
							try {
								sync.wait(timeLeft);
							} catch (InterruptedException e) {
							}
						}
					}
				}

			}
		};

		// this will increase the thread priority, not as deterministic as using
		// a realtime vm, but it does have some benefit.
		renderTask.setPriority(renderTask.getPriority() + 1);

		// start our rendering and update thread.
		renderTask.start();
	}

	protected void update(double dt) {
		// for now just move the basic block
		if (up) {
			basicBlock.y -= dt * speed;
		}

		if (down) {
			basicBlock.y += dt * speed;
		}

		if (right) {
			basicBlock.x += dt * speed;
		}

		if (left) {
			basicBlock.x -= dt * speed;
		}
	}

	/**
	 * Stops the rendering cycle so that the application can close gracefully.
	 */
	protected void stop() {
		// must interrupt the thread.
		renderTask.interrupt();
	}

	/**
	 * Creates a Frame and adds a new canvas to the Frame, displays it and
	 * initializes the rendering method. Sets up listeners for key component
	 * events, such as Key events and Window events.
	 */
	protected static void createAndDisplay() {
		// create the frame and set the default layout
		final Frame frame = new Frame(TITLE);
		frame.setLayout(new BorderLayout());

		// create and add our custom component to the frame
		final SmoothMovement canvas = new SmoothMovement();
		frame.add(canvas);

		// setup our event listeners
		canvas.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					canvas.stop();
					frame.setVisible(false);
					frame.dispose();
					System.exit(0);
				}
			}
		});
		// need a handler for when the window x button is pressed
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				canvas.stop();
				frame.setVisible(false);
				frame.dispose();
				System.exit(0);
			}
		});

		// use pack to resize the frame
		frame.pack();

		// center the window on the screen after resizing using pack
		frame.setLocationRelativeTo(null);

		// show the frame now
		frame.setVisible(true);

		// initiate the render thread
		canvas.setup();

		// make sure key events get routed properly.
		canvas.requestFocus();
	}

	/**
	 * Creates and displays a new application frame.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// create and display the window
		createAndDisplay();
	}

}
