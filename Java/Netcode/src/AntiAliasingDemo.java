
/**
 * File: AntiAliasingDemo.java
 * 
 * This file is free to use and modify as it is for educational use.
 * brought to you by Game Programming Snippets (http://gpsnippets.blogspot.com/)
 * 
 * Revisions:
 * 1.1 Initial Revision 
 * 
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This file is an example of one method for creating a double buffering
 * solution in order to reduce flicker and splicing effects in graphics.
 * 
 * Added to this file is logic for displaying rendering effects using a timed
 * update thread. And demonstrating the use of RenderingHints in the Graphics2D
 * object for increasing the quality of rendering geometric primitives.
 * 
 * This class also sets up a timed rendering loop.
 */
public class AntiAliasingDemo extends JFrame implements MouseListener, MouseMotionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// A string to display in the window.
	@SuppressWarnings("unused")
	private static final String DISPLAY_STRING = "Hello World!";

	// backbuffer for page flipping, not initialized because we do not know
	// what the size of the window is yet. And it may change in the future.
	private BufferedImage backbuffer = null;

	// The main panel used for drawing so that no offset is applied for the
	// JFrames titlebar.
	private JPanel display = new JPanel();

	// A java.util.Timer object that will be used to schedule TimerTask's for
	// timed rendering and other types of timed tasks such as Animation and
	// timed updates.
	private Timer timer = new Timer();

	// indicates to the rendering loop that the windows size has changed.
	private boolean wndResized = true;

	// our circular decorator object responsible for drawing the spinning
	// circle.
	private Spinner decorator = new Spinner(Color.blue, 25, 25);

	// hotspot points
	private LinkedList<Point> hotspots = new LinkedList<Point>();

	// current hotspot
	private Point hotspot = null;

	// capture mouse location
	private double mouseX = 0;
	private double mouseY = 0;

	// is set to indicate if the left mouse button is being pressed.
	private boolean mouseClicked = false;

	// anti-aliasing on/off toggle
	public static boolean antiAlias = false;

	// stroke hint pure
	public static boolean strokeHint = false;

	/**
	 * This is the constructor which holds setup code for the JFrame and code
	 * for creating the TimerTask anonymous class as well as initializing
	 * resources.
	 */
	public AntiAliasingDemo() {
		super("gpSnippets - Rendering Hints: Anti Aliasing Demo");

		// to make sure that the display panel is resized properly.
		setLayout(new BorderLayout());

		// the display panel used for rendering
		add(display);

		// add handler for adding hotspots
		display.addMouseListener(this);

		// add handler for capturing mouse position.
		display.addMouseMotionListener(this);

		// make sure the display component has the input focus.
		display.requestFocus();

		// add a listener to detect when the window has been resized by the
		// user.
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				wndResized = true; // resize the backbuffer.
			}
		});

		// add a listener to handle closing events.
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {

				// end all timer tasks before visibility is lost.
				timer.cancel();

				setVisible(false); // hide all disposing operations.

				dispose(); // cleanup and destroy the window threads.

				// now exit to OS with no errors.
				System.exit(0);

			}
		});

		// set window size to 640 width by 480 height in pixels.
		setSize(640, 480);

		// show the frame.
		setVisible(true);

		// after all settings are set for window and it is shown initialize
		// resources.
		init();
	}

	/**
	 * Where we will place all our resource initialization and loading, this
	 * will also include starting the rendering task by scheduling it with the
	 * timer.
	 */
	public void init() {

		// create the TimerTask and schedule it with the timer.
		TimerTask renderTask = new TimerTask() {
			public void run() {

				// check to see if the backbuffer needs to be initialized.
				// this will help when window is resized.
				if (backbuffer == null || wndResized) {
					// create a BufferedImage the size of the JFrame.
					backbuffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
					wndResized = false;
				}

				// get the graphics object from the backbuffer.
				Graphics g = backbuffer.getGraphics();

				// TODO: draw things to the screen.
				// example

				// draw all hotspots
				drawHotspots((Graphics2D) g);

				// draw the control buttons
				drawButtons(g);

				// draw directions
				g.setColor(Color.white);
				g.drawString("Click anywhere to place a hotspot", 10, getHeight() - 40);

				// at the end of the frame reset mouse state
				mouseClicked = false;

				// now we do our page flipping to display the image on the
				// window. So we need the windows Graphics object.
				display.getGraphics().drawImage(backbuffer, 0, 0, null);

				// after we have drawn what we need on the screen we can clear
				// the backbuffer.
				g.clearRect(0, 0, backbuffer.getWidth(), backbuffer.getHeight());
			}
		};

		// start rendering at 60 fps.
		timer.schedule(renderTask, 100, 200);

		// timed thread for updating game entities and objects.
		TimerTask update = new TimerTask() {
			private long lastTime = -1;

			public void run() {

				// do once
				if (lastTime == -1) {
					lastTime = System.currentTimeMillis();
				}

				// time difference in seconds.
				double dt = (System.currentTimeMillis() - lastTime) / 1000d;

				// record the time before actually updating objects.
				lastTime = System.currentTimeMillis();

				decorator.update(dt);
			}
		};

		timer.schedule(update, 100, 10);
	}

	/**
	 * Method draws an oval around all the hotspots in the hotspots list.
	 * 
	 * @param g
	 *            The graphics object to render with
	 */
	protected void drawHotspots(Graphics2D g) {
		g.setColor(Color.white);
		if (antiAlias) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}

		Rectangle r = new Rectangle(0, 0, 0, 0);
		hotspot = null; // clear any previously highlight hotspot.

		// helps avoid concurrent modification exceptions.
		LinkedList<Point> hsList = new LinkedList<Point>(hotspots);

		// draw the hotspots and spinner if the mouse is over and hotspot.
		for (Point p : hotspots) {
			g.fillOval((int) (p.getX() - 5), (int) (p.getY() - 5), 10, 10);
			// check to see if mouse is over hotspot
			r.setBounds((int) (p.getX() - 4), (int) (p.getY() - 4), 8, 8);
			if (r.contains(mouseX, mouseY)) {
				hotspot = p;
			}
		}

		// render the spinner if the mouse if over any hotspot
		if (hotspot != null) {
			// draw decorator
			Graphics2D g2d = (Graphics2D) g.create((int) (hotspot.getX() - 13), (int) (hotspot.getY() - 13), 40, 40);
			decorator.render(g2d);
		}
	}

	/**
	 * Draws control buttons and fills them depending on whether they are
	 * highlighted or not.
	 * 
	 * @param g
	 *            The graphics object to render with
	 */
	protected void drawButtons(Graphics g) {
		Color highlight = new Color(110, 125, 255);
		Color enabled = new Color(100, 125, 200);

		// draw Anti-Aliasing button.
		Rectangle r = new Rectangle(10, 10, 100, 20);
		if (r.contains(mouseX, mouseY) && mouseClicked) {
			antiAlias = !antiAlias;
		}

		// display if hint is enabled
		if (AntiAliasingDemo.antiAlias) {
			g.setColor(enabled);
			g.fillRoundRect(12, 12, 96, 16, 4, 4);
		}

		// highlight button
		if (r.contains(mouseX, mouseY)) {
			g.setColor(highlight);
			g.fillRoundRect(12, 12, 96, 16, 4, 4);
		}

		// draw the button border and text.
		g.setColor(Color.white);
		g.drawRoundRect(10, 10, 100, 20, 5, 5);
		g.drawString("Anti-Aliasing", 25, 25);

		// draw stroke control button.
		r = new Rectangle(125, 10, 100, 20);
		if (r.contains(mouseX, mouseY) && mouseClicked) {
			strokeHint = !strokeHint;
		}

		// display if hint is enabled
		if (AntiAliasingDemo.strokeHint) {
			g.setColor(enabled);
			g.fillRoundRect(12 + 115, 12, 96, 16, 4, 4);
		}

		// highlight button
		if (r.contains(mouseX, mouseY)) {
			g.setColor(highlight);
			g.fillRoundRect(12 + 115, 12, 96, 16, 4, 4);
		}

		// draw the button border and text.
		g.setColor(Color.white);
		g.drawRoundRect(10 + 115, 10, 100, 20, 5, 5);
		g.drawString("Stroke Control", 135, 25);
	}

	/**
	 * The main entry point for the application which simply creates a new
	 * instance of this class.
	 * 
	 * @param args
	 *            Command line arguments. None needed in this example.
	 */
	public static void main(String[] args) {
		new AntiAliasingDemo();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getY() > 50) {
			hotspots.add(e.getPoint());
		}
		mouseClicked = true;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

} // end class DoubleBuffering
