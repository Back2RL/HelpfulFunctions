
/**
 * File: AngularMovementSnippet.java
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
import java.util.Timer;
import java.util.TimerTask;

/**
 * This is an example of angular character movement, it is based off of the
 * http://gpsnippets.blogspot.com/2010/10/how-to-use-bufferstrategy-in-java.html
 * <br/>
 * So for details on how the rendering code works see the above post.
 *
 * @author Nick
 */
public class AngularMovementSnippet extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// java 2d control flags
	static {
		// System.setProperty("sun.java2d.transaccel", "True");
		// System.setProperty("sun.java2d.opengl", "true");
		// System.setProperty("sun.java2d.d3d", "True");
		// System.setProperty("sun.java2d.ddforcevram", "True");
	}

	// app title.
	private static final String TITLE = "GPSnippets: Angular Character Movement Snippet";

	// the strategy used for double buffering, or any number of buffered frames.
	private BufferStrategy strategy;

	// our time keeper
	private final Timer timer;

	// the main render and update task.
	private TimerTask renderTask;

	private Actor player = new Actor();

	// just a gradient background for our little demo.
	@SuppressWarnings("unused")
	private Paint backgroundGradient;

	// our little basic blocks speed, which will be multipled by the number
	// of seconds passed, each render frame.
	private final double speed = 400.0d /* px/sec */;

	// true if the up arrow key is being pressed, false otherwise
	private boolean up = false;

	// true if the down arrow key is being pressed, false otherwise
	// private boolean down = false;

	// true if the right arrow key is being pressed, false otherwise
	private boolean right = false;

	// true if the left arrow key is being pressed, false otherwise
	private boolean left = false;

	private double turnspeed = turnSpeed(720.0d);

	private int renderedFrames = 0;
	private double avgFramerate = 0.0d;
	public double maxFramerate = 100000.0d;
	private static boolean bIsRunning = true;

	private long timeout = (long) (1E9 / maxFramerate);

	/**
	 * This configures the canvas component for rendering. Creates any objects
	 * we need and sets up some default listeners for component events.
	 */
	public AngularMovementSnippet() {
		// we will be doing our own rendering, using the strategy.
		this.setIgnoreRepaint(true);

		timer = new Timer(); // used for the render thread

		// add quick and dirty 4 directional movement for our block
		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					up = true;
				}

				// if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				// down = true;
				// }

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

				// if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				// down = false;
				// }

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
	 * do offscreen rendering without having to wait for swing to repaint, the
	 * component. It also eliminates flickering and splicing issues.
	 */
	public void render() {
		// the back buffer graphics object
		Graphics2D bkG = (Graphics2D) strategy.getDrawGraphics();

		// clear the backbuffer, this could be substituted for a background
		// image or a tiled background.
		bkG.clearRect(0, 0, getWidth(), getHeight());

		// bkG.setPaint(backgroundGradient);
		// bkG.fillRect(0, 0, getWidth(), getHeight());

		// TODO: Draw your game world, or scene or anything else here.

		// Rectangle2D is a shape subclass, and the graphics object can render
		// it, makes things a little easier.
		int cx = (int) player.basicBlock.getCenterX();
		int cy = (int) player.basicBlock.getCenterY();

		bkG.setColor(Color.green.darker());
		Graphics2D g = (Graphics2D) bkG.create();
		g.rotate(-player.rotation, cx, cy);
		g.fill(player.basicBlock);
		g.dispose();

		// show direction
		bkG.drawLine(cx, cy, (int) (cx + Math.sin(player.rotation) * 20), (int) (cy + Math.cos(player.rotation) * 20));

		bkG.setColor(Color.BLACK);
		bkG.drawString(String.format("%s %5d", "fps = ", (int) (avgFramerate)), 0, 12);

		// properly dispose of the backbuffer graphics object. Release resources
		// and cleanup.
		bkG.dispose();

		// flip the backbuffer to the canvas component.
		strategy.show();

		++renderedFrames;

		// synchronize drawing with the display refresh rate.
		Toolkit.getDefaultToolkit().sync();
	}

	/**
	 * It is necessary to wait until after the component has been displayed in
	 * order to create and retreive the buffer strategy. This is the part that
	 * took me the longest to figure out. But it makes sense since the component
	 * requires native resources in order to perform hardware accleration and
	 * those resources are handled by the component itself, and are only
	 * available once the component is created and displayed.
	 */
	public void setup() {
		player.basicBlock = new Rectangle2D.Double(0, 0, 16, 16);

		// center the block in the canvas.
		player.setPosition(new Loc((int) (this.getWidth() / 2 - player.basicBlock.width / 2),
				(int) (this.getHeight() / 2 - player.basicBlock.height / 2)));

		// create the background gradient paint object.
		backgroundGradient = new GradientPaint(0, 0, Color.gray, getWidth(), getHeight(), Color.lightGray.brighter());

		// create a strategy that uses two buffers, or is double buffered.
		this.createBufferStrategy(2);

		// get a reference to the strategy object, for use in our render method
		// this isn't necessary but it eliminates a call during rendering.
		strategy = this.getBufferStrategy();

		start();
	}

	/**
	 * Initialize the render and update tasks, to call the render method, do
	 * timing and fps counting, handling input and cancelling existing tasks.
	 */
	public void start() {

		// if the render task is already running stop it, this may cause an
		// exception to be thrown if the task is already canceled.
		if (renderTask != null) {
			renderTask.cancel();
		}
		// our main task for handling the rendering and for updating and
		// handling input and movement events. The timer class isn't the most
		// reliable for game updating and rendering but it will suffice for the
		// purpose of this snippet.
		renderTask = new TimerTask() {

			@Override
			public void run() {

				avgFramerate = renderedFrames;
				renderedFrames = 0;
			}
		};

		// These will cap our frame rate but give us unexpected results if our
		// rendering or updates take longer than the 'period' time. It
		// is likely that we could have overlapping calls.
		timer.schedule(renderTask, 0, 1000);

		long renderTimeout = 0;
		long lasttime = System.nanoTime();
		while (bIsRunning) {
			// get the current system time
			long time = System.nanoTime();

			// calculate the time passed in milliseconds
			long deltaNanoSeconds = time - lasttime;
			double dt = deltaNanoSeconds * 0.000000001d;

			// save the current time
			lasttime = time;

			// for now just move the basic block
			update(dt);

			render();

			renderTimeout = timeout - (deltaNanoSeconds - renderTimeout);

			if (renderTimeout > 0) {
				try {
					Thread.sleep((long) (renderTimeout * 1E-6), (int) ((int) renderTimeout % 1E6));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	double turnSpeed(double degPerSecond) {
		return Math.PI * degPerSecond / 360.0d;
	}

	protected void update(double dt) {
		// angular movement is based on a radian angle for direction

		if (right) {
			player.rotation -= turnspeed * dt; // angular turning
												// speed
		}

		if (left) {
			player.rotation += turnspeed * dt; // angular turning
												// speed
		}

		if (up) {
			// the movement calculations, sin and cos are used to get the
			// distance in x and y directions respectively. We multiply by the
			// timed passed so that we get more accurate movement and then the
			// speed variable controls how fast the block moves. And it has
			// specific units which are pixels per second.
			player.basicBlock.x += Math.sin(player.rotation) * dt * speed;
			player.basicBlock.y += Math.cos(player.rotation) * dt * speed;

		}
		// System.out.println("Forward = " + player.getForwardVector());
		// System.out.println("Right = " + player.getRightVector());
	}

	/**
	 * Stops the rendering cycle so that the application can close gracefully.
	 */
	protected void stop() {
		timer.cancel();
	}

	/**
	 * Creates a Frame and adds a new canvas to the Frame, displays it and
	 * initializes the rendering method.
	 */
	protected static void createAndDisplay() {
		// Never mix swing and awt, since we use a canvas to utilize the
		// buffered strategy we will put the canvas in a Frame instead of a
		// JFrame.
		final Frame frame = new Frame(TITLE);
		frame.setLayout(new BorderLayout());
		final AngularMovementSnippet canvas = new AngularMovementSnippet();
		frame.add(canvas);

		// convenience exiting from the demo using the ESCAPE key.
		canvas.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					bIsRunning = false;
					canvas.stop(); // first stop the drawing and updating
					frame.setVisible(false); // hide the window quickly
					frame.dispose(); // release all system resources
					System.exit(0); // finally exit.
				}
			}
		});

		// need this to trap when the user attempts to close the window using
		// the close icon for the window, or the close option from the window
		// menu or alt+f4 or by other means.
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				canvas.stop(); // first stop the drawing and updating
				frame.setVisible(false); // hide the window quickly
				frame.dispose(); // release all system resources
				System.exit(0); // finally exit.
			}
		});
		frame.setSize(800, 600); // should use configurable properties here
		frame.setLocationRelativeTo(null); // centers window on screen
		frame.setVisible(true); // creates and displays the actual window

		// this is our scene setup to initialize all necessary configurable
		// objects and properties. Using a setup method helps control the way
		// things look from a single location, it can be extended to include
		// how things act as well.
		canvas.setup();
	}

	/**
	 * Calls the swing event thread to create and display a new application
	 * frame. This is done so that the setVisible method is not part of the main
	 * application thread but is done within the swing event thread.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// create and display the window.
		createAndDisplay();
	}

}
