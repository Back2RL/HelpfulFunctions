package Leseaufgaben;
import java.awt.Color;
import java.awt.Graphics;

import com.bjp.DrawingPanel;

public class Test {

	/**
	 * @param args
	 *            none
	 */
	public static void main(String[] args) {
		drawAbb7();
		uebL_2_2_1();
		lernzielfrage();
	}

	/**
	 * Zeichne Abbildung 7 Seite 6 PR2_02_L.drvd.pdf
	 */
	public static void drawAbb7() {
		DrawingPanel panel = new DrawingPanel(200, 100);
		Graphics g = panel.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(10, 30, 60, 35);
		g.setColor(Color.RED);
		g.fillOval(80, 40, 50, 70);
		g.setColor(Color.BLUE);
		g.drawLine(0, 0, 199, 99);
	}

	/**
	 * Übungsaufgabe L.2.2.1 Seite 7 PR2_02_L.drvd.pdf
	 */
	public static void uebL_2_2_1() {
		DrawingPanel panel = new DrawingPanel(200, 100);
		Graphics g = panel.getGraphics();
		// Ampel außen
		g.drawRect(5, 5, 30, 90);
		// Ampelleuchten
		{
			g.setColor(Color.RED);
			g.fillOval(10, 10, 20, 20);
			g.setColor(Color.YELLOW);
			g.fillOval(10, 40, 20, 20);
			g.setColor(Color.GREEN);
			g.fillOval(10, 70, 20, 20);
		}

		// Schild
		int[] xP = { 60, 60, 40, 60, 80, 60 };
		int[] yP = { 95, 60, 40, 20, 40, 60 };
		g.setColor(Color.BLACK);
		g.drawPolygon(xP, yP, 6);
		// Schildoval
		g.setColor(Color.YELLOW);
		g.fillOval(52, 32, 16, 16);
	}

	/**
	 * Welche Ausgabe?
	 */
	public static void lernzielfrage() {
		DrawingPanel panel = new DrawingPanel(200, 120);
		Graphics g = panel.getGraphics();
		g.setColor(Color.ORANGE);
		g.fillRect(10, 5, 90, 110);

		g.setColor(Color.WHITE);
		g.fillRect(35, 15, 10, 60);
		g.fillRect(65, 15, 10, 60);
		g.fillRect(45, 40, 20, 10);
		g.fillRect(35, 90, 40, 10);

	}
}
