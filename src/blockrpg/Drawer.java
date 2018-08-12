package blockrpg;

import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Drawer extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2810583553938140766L;

	private static final Stroke THIN = new BasicStroke(1);
	private static final Stroke THICK = new BasicStroke((float) 1.5);

	private List<Face> sortedFace;

	public Drawer() {
		sortedFace = new ArrayList<Face>();
	}

	public void setList(ArrayList<Face> faces) {
		sortedFace = faces;
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g; // Casts the Graphics to Graphics2D

		g.setColor(this.getBackground());
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		// Turns on Anti-Aliasing
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(rh);

		for (int i = 0; i < sortedFace.size(); i++) {
			if (sortedFace.get(i).isVisible()) {
				g2.setColor(sortedFace.get(i).getCol());
				if (MainWindow.WIRE) {
					g2.setStroke(THIN);
					g2.drawPolygon(sortedFace.get(i).getPoly());
				} else {
					// g2.setStroke(THICK);
					// g2.drawPolygon(sortedFace.get(i).getPoly());
					g2.fillPolygon(sortedFace.get(i).getPoly());

				}

				if (MainWindow.SHOWCENT) {
					Position2D center = sortedFace.get(i).getPOV().getViewPoint(sortedFace.get(i).getCenter3D());
					g2.setColor(Color.BLACK);
					g2.fillArc((int) (center.getX() + Face.xOffset - 5), (int) (-center.getY() + Face.yOffset - 5), 10,
							10, 0, 360);
				}

			}

			if (MainWindow.DEBUG) {
				g2.drawString(Boolean.toString(sortedFace.get(i).isVisible()), 100, 100 + 10 * i);

			}
		}

	}
}
