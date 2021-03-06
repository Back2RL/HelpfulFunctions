
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;

public class Spinner {

	private double startAngle = 0;
	private Stroke theStroke = null;
	private double rate = 150;
	private int angle = 300;
	private Color color = new Color(0, 200, 0, 200);
	private int width = 30;
	private int height = 30;

	public Spinner(Color r, int width, int height) {
		theStroke = new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);
		color = r;
		this.width = width;
		this.height = height;
	}

	public void render(Graphics2D g) {
		g.setColor(color);
		g.setStroke(theStroke);

		// check whether anti-aliasing is enabled.
		if (AntiAliasingDemo.antiAlias) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}

		// check whether stroke hint is enabled.
		if (AntiAliasingDemo.strokeHint) {
			g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		}

		g.drawArc(2, 2, (int) (width - 4), (int) (height - 4), (int) startAngle, angle);
	}

	public void update(double dt) {
		double move = dt * rate;
		startAngle += move;
		if (startAngle >= 360) {
			startAngle = 0;
		}
	}
}
