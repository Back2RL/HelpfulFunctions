
/**
 * File: InputManager.java
 * 
 * This file is free to use and modify as it is for educational use.
 * brought to you by Game Programming Snippets (http://gpsnippets.blogspot.com/)
 * 
 * Revisions:
 * 1.1 Initial Revision 
 * 
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class was created to show how implement a keyboard polling system using
 * java.awt.event.KeyListener this allows the ability to capture multiple keys
 * being pressed simultaneously. To use this class just simply add it as a key
 * listener to your JFrame and it will be populated with input.
 */
public final class InputManager implements KeyListener {

	// array of key states made as integers for more possible states.
	private long[] keyPressTime = new long[256];

	// store the number of pressed keys
	private int numKeysDown = 0;

	public int getNumKeysDown() {
		return numKeysDown;
	}

	// one for each ascii character.
	private boolean[] key_state_up = new boolean[256]; // true if pressed
	private boolean[] key_state_down = new boolean[256]; // true if not pressed

	// variable that indicates when any key(s) are being pressed.
	private boolean keyPressed = false;

	public boolean isKeyPressed() {
		return keyPressed;
	}

	// variable that indicates that some key was released this frame.
	private boolean keyReleased = false; // cleared every frame.

	// a StringBuilder used as a buffer by widgets or other text input controls
	private StringBuilder kCache = new StringBuilder();

	// the only instantiated object
	private static InputManager instance = new InputManager();

	/**
	 * Empty Constructor: nothing really needed here.
	 */
	protected InputManager() {
	}

	/**
	 * Singleton accessor the only means of getting the instantiated object.
	 * 
	 * @return One and only InputManager object.
	 */
	public static InputManager getInstance() {
		return instance;
	}

	/**
	 * This function is specified in the KeyListener interface. It sets the
	 * state elements for whatever key was pressed.
	 * 
	 * @param e
	 *            The KeyEvent fired by the awt Toolkit
	 */
	public void keyPressed(KeyEvent e) {
		// System.out.println("InputManager: A key has been pressed code=" +
		// e.getKeyCode());
		if (e.getKeyCode() >= 0 && e.getKeyCode() < 256) {
			if (key_state_down[e.getKeyCode()] == false) {
				System.out.println(KeyEvent.getKeyText(e.getKeyCode()));
				keyPressTime[e.getKeyCode()] = System.currentTimeMillis();
				key_state_down[e.getKeyCode()] = true;
				key_state_up[e.getKeyCode()] = false;

				// check if with this key press any keys is pressed
				if (numKeysDown < 1) {
					numKeysDown = 0;
					keyPressed = true;
					keyReleased = false;
				}
				++numKeysDown;
			}
		}
	}

	/**
	 * This function is specified in the KeyListener interface. It sets the
	 * state elements for whatever key was released.
	 * 
	 * @param e
	 *            The KeyEvent fired by the awt Toolkit.
	 */
	public void keyReleased(KeyEvent e) {
		// System.out.println("InputManager: A key has been released code=" +
		// e.getKeyCode());
		if (e.getKeyCode() >= 0 && e.getKeyCode() < 256) {

			// keyPressTime[e.getKeyCode()] = 0;
			System.out.println(KeyEvent.getKeyText(e.getKeyCode()) + " Key was pressed for "
					+ (System.currentTimeMillis() - keyPressTime[e.getKeyCode()]) + " ms");
			key_state_up[e.getKeyCode()] = true;
			key_state_down[e.getKeyCode()] = false;

			// check if with this key release all keys are released
			if (numKeysDown < 2) {
				numKeysDown = 1;
				keyPressed = false;
				keyReleased = true;
			}
			--numKeysDown;
		}
	}

	/**
	 * This function is called if certain keys are pressed namely those used for
	 * text input.
	 * 
	 * @param e
	 *            The KeyEvent fired by the awt Toolkit.
	 */
	public void keyTyped(KeyEvent e) {
		kCache.append(e.getKeyChar());
	}

	/**
	 * Returns true if the key (0-256) is being pressed use the KeyEvent.VK_ key
	 * variables to check specific keys.
	 * 
	 * @param key
	 *            The ascii value of the keyboard key being checked
	 * @return true is that key is currently being pressed.
	 */
	public boolean isKeyDown(int key) {
		return key_state_down[key];
	}

	/**
	 * Returns true if the key (0-256) is being pressed use the KeyEvent.VK_ key
	 * variables to check specific keys.
	 * 
	 * @param key
	 *            The ascii value of the keyboard key being checked
	 * @return true is that key is currently being pressed.
	 */
	public boolean isKeyUp(int key) {
		return key_state_up[key];
	}

	/**
	 * In case you want to know if a user is pressing a key but don't care which
	 * one.
	 * 
	 * @return true if one or more keys are currently being pressed.
	 */
	public boolean isAnyKeyDown() {
		return keyPressed;
	}

	/**
	 * In case you want to know if a user released a key but don't care which
	 * one.
	 * 
	 * @return true if one or more keys have been released this frame.
	 */
	public boolean isAnyKeyUp() {
		return keyReleased;
	}

	/**
	 * Only resets the key state up because you don't want keys to be showing as
	 * up forever which is what will happen unless the array is cleared.
	 */
	public void update() {
		// clear out the key up states
		key_state_up = new boolean[256];
		keyReleased = false;
		if (kCache.length() > 1024) {
			kCache = new StringBuilder();
		}
	}

} // InputManager
