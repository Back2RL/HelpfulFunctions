package SwingExamples;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SwingTest extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SwingTest() {

		setTitle("Title");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		final JLabel yellowLabel = new JLabel("Hello World");
		final JLabel anotherLabel = new JLabel("another");
		yellowLabel.setOpaque(true);
		yellowLabel.setBackground(Color.white);
		yellowLabel.setPreferredSize(new Dimension(200, 80));
		final Container test = new JPanel();
		test.setLayout(new BoxLayout(test, BoxLayout.Y_AXIS));
		getContentPane().add(test, BorderLayout.WEST);

		test.add(yellowLabel, BoxLayout.X_AXIS);
		test.add(anotherLabel, BoxLayout.X_AXIS);
		final JButton button = new JButton("paff");
		getContentPane().add(button, BorderLayout.NORTH);
		final JButton button2 = new JButton("poff");
		getContentPane().add(button2, BorderLayout.EAST);
		final JButton exitButton = new JButton("Close");
		getContentPane().add(exitButton, BorderLayout.SOUTH);

		class MyActionListener implements ActionListener {
			@Override
			public void actionPerformed(final ActionEvent e) {
				yellowLabel.setText(yellowLabel.getText() + " X");
			}
		}
		final MyActionListener listener = new MyActionListener();
		button.addActionListener(listener);
		button2.addActionListener(new ActionListener() {
			// count how many times the button has been presse
			private int counter;
			// stores the progress of all activities
			private final Map<Long, Integer> progress = new TreeMap<>();
			// the button text
			private String text;

			private int waitingTasks = 0;

			private synchronized void incrementWaitingTasks() {
				waitingTasks++;
			}

			private synchronized void decrementWaitingTasks() {
				waitingTasks--;
			}

			// adds a new entry to the map to keep track of the progress
			synchronized private long createProgressTask(final Thread thread) {
				progress.put(thread.getId(), 0);
				return thread.getId();
			}

			// returns the overall progress percentage
			synchronized private int getOverallProgress() {
				int sum = 0;
				final int dividend = progress.size();
				for (final Integer value : progress.values()) {
					sum += value;
				}
				System.out.println(dividend + " sum = " + sum);
				return sum / dividend;
			}

			// how many actions are performed
			synchronized private int getProgressTasks() {
				return progress.size();
			}

			// update progress
			synchronized private void setProgress(final long threadID, final int value) {
				progress.replace(threadID, value);
			}

			// remove progress tracking entry when task finished
			synchronized private void removeProgressTask(final long threadID) {
				progress.remove(threadID);
			}

			@Override
			public void actionPerformed(final ActionEvent e) {
				// button pressed
				// show user that processing occurs
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						setCursor(new Cursor(Cursor.WAIT_CURSOR));
					}
				});

				// background thread for the heavy duty
				final Thread ht = new Thread() {
					@Override
					synchronized public void run() {

						Thread.yield();
						// prevent threads from starting when already all cores
						// are utilized
						incrementWaitingTasks();
						while (true) {
							if (getProgressTasks() >= Runtime.getRuntime().availableProcessors()) {
								try {
									sleep(100);
								} catch (final InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {
								break;
							}
						}
						decrementWaitingTasks();

						final long myID = createProgressTask(Thread.currentThread());
						if (text == null) {
							text = button2.getText();
						}

						// the heavy duty
						final long max = 10_000_000_000L;
						final long showProgress = max / 10;
						for (long i = 0; i < max; ++i) {
							if (i % showProgress == 0L) {
								setProgress(myID, (int) ((i * 100) / max));
								// final int currProgress = (int)
								// (getOverallProgress() * (double)
								// getProgressTasks()
								// / (getProgressTasks() + waitingTasks));
								final int currProgress = getOverallProgress();
								EventQueue.invokeLater(new Runnable() {
									@Override
									public void run() {
										button2.setText(text + " (" + currProgress + " %)");
									}
								});
							}

						}

						counter++;
						// end heavy duty
						// new thread to change swing elements, needed for
						// thread-safety
						removeProgressTask(myID);
						EventQueue.invokeLater(new Runnable() {
							@Override
							public void run() {
								yellowLabel.setText(yellowLabel.getText() + " -");
								if (getProgressTasks() < 1) {
									button2.setText(text + " " + counter);
									setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
								}
							}
						});
					}
				};
				ht.start();
			}
		});

		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				System.exit(1);
			}
		});

		pack();
	}

	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				final JFrame f = new SwingTest();
				f.setVisible(true);
				f.setResizable(true);
			}
		});
	}
}
