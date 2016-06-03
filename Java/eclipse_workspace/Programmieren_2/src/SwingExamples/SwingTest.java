package SwingExamples;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class SwingTest extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8978290962753919447L;

	public SwingTest() {

		setTitle("Title");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		final JLabel yellowLabel = new JLabel("Hello World");
		yellowLabel.setOpaque(true);
		yellowLabel.setBackground(Color.yellow);
		yellowLabel.setPreferredSize(new Dimension(200, 80));

		getContentPane().add(yellowLabel, BorderLayout.CENTER);
		final JButton button = new JButton("paff");
		getContentPane().add(button, BorderLayout.NORTH);
		final JButton button2 = new JButton("poff");
		getContentPane().add(button2, BorderLayout.SOUTH);

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
			private String text;

			@Override
			public void actionPerformed(final ActionEvent e) {
				if (text == null) {
					text = button2.getText();
				}
				counter++;
				button2.setText(text + " " + counter);
				yellowLabel.setText(yellowLabel.getText() + " -");
			}
		});

		pack();
	}

	public static void main(final String[] args) {
		final JFrame f = new SwingTest();
		f.setVisible(true);
	}

}