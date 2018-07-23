package blockrpg;

import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Drawer extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2810583553938140766L;

	public ArrayList<Face> faces;

	public Drawer() {
		faces = new ArrayList<Face>();
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
			ArrayList<Face> sortedFace = new ArrayList<Face>(faces);
			Collections.sort(sortedFace);
			for (Face face : sortedFace) {
				g2.setColor(face.getCol());
				g2.fillPolygon(face.getPoly());
			}
		}

	}
}
