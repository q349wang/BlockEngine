package blockrpg;

import javax.swing.JPanel;
import java.awt.*;
import java.util.Vector;

public class Drawer extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2810583553938140766L;

	public Vector<Face> faces;

	public Drawer() {
		faces = new Vector<Face>();
	}

	public void addShape(Face face) {
		faces.add(face);
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g; // Casts the Graphics to Graphics2D

		// Turns on Anti-Aliasing
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(rh);

		if (faces.size() != 0) {
			g2.setColor(new Color(45, 84, 38));
			g2.fillPolygon(faces.get(0).getPoly());

			g2.setColor(new Color(72, 41, 124));
			g2.fillPolygon(faces.get(1).getPoly());

			g2.setColor(Color.YELLOW);
			g2.fillArc(
					(int) (faces.get(0).getPOV().getViewPoint(faces.get(0).getCenter3D()).getCoord()[0]
							+ Face.xOffset),
					(int) (-faces.get(0).getPOV().getViewPoint(faces.get(0).getCenter3D()).getCoord()[1]
							+ Face.yOffset),
					20, 20, 0, 360);
			g2.fillArc(
					(int) (faces.get(1).getPOV().getViewPoint(faces.get(1).getCenter3D()).getCoord()[0]
							+ Face.xOffset),
					(int) (-faces.get(1).getPOV().getViewPoint(faces.get(1).getCenter3D()).getCoord()[1]
							+ Face.yOffset),
					20, 20, 0, 360);
		}

	}
}
