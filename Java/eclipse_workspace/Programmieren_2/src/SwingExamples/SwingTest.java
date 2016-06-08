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
			private int counter;
			private final Map<Integer, Integer> progess = new TreeMap<>();
			private String text;

			synchronized private int createProgressTask() {
				progess.put(progess.size(), 0);
				return progess.size() - 1;
			}

			synchronized private int getAvaerageProgress() {
				int sum = 0;
				for (final Integer value : progess.values()) {
					sum += value;
				}
				return sum / getProgressTasks();
			}

			synchronized private int getProgressTasks() {
				return progess.size();
			}

			synchronized private void setProgress(final int index, final int value) {
				progess.replace(index, value);
			}

			synchronized private void removeProgressTask(final int index) {
				progess.remove(index);
			}

			@Override
			public void actionPerformed(final ActionEvent e) {
				final int myIndex = createProgressTask();
				// button pressed
				// show user that processing occurs
				if (getProgressTasks() == 0) {
					setCursor(new Cursor(Cursor.WAIT_CURSOR));
				}
				// background thread for the heavy duty
				final Thread ht = new Thread() {
					@Override
					synchronized public void run() {
						if (text == null) {
							text = button2.getText();
						}
						// the heavy duty
						final long max = 1_000_000_000L;
						final long showProgress = max / 100;
						for (long i = 0; i < max; ++i) {
							if (i % showProgress == 0L) {
								setProgress(myIndex, (int) ((i * 100) / max));

								EventQueue.invokeLater(new Runnable() {
									@Override
									public void run() {
										button2.setText(text + " (" + getAvaerageProgress() + " %)");
									}
								});
							}

						}

						counter++;
						// end heavy duty
						// new thread to change swing elements, needed for
						// thread-safety
						removeProgressTask(myIndex);
						EventQueue.invokeLater(new Runnable() {
							@Override
							public void run() {
								button2.setText(text + " " + counter);
								yellowLabel.setText(yellowLabel.getText() + " -");
								if (getProgressTasks() < 1) {
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
