package blockrpg;

import javax.swing.JPanel;
import java.awt.*;
import java.util.Vector;

public class Drawer extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2810583553938140766L;

	private Vector<VisualFace> faces;

	public Drawer() {
		faces = new Vector<VisualFace>();
	}

	public void addShape(VisualFace face) {
		faces.add(face);
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g; // Casts the Graphics to Graphics2D

		// Turns on Anti-Aliasing
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(rh);
		g2.setColor(new Color(45, 84, 38));
		for (VisualFace face : faces) {
			g2.fillPolygon(face.getPoly());
		}

	}
}
