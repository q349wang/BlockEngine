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
					g2.drawPolygon(sortedFace.get(i).getPoly());
				} else {
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
				for (int j = 0; j < sortedFace.size(); j++) {
					g2.drawString(Integer.toString(sortedFace.get(i).compareTo(sortedFace.get(j))), 100 + 10 * j,
							100 + 10 * i);
				}

			}
		}

		if (MainWindow.DEBUG) {
			for (int a = 0; a < sortedFace.size(); a++) {
				Face face1 = sortedFace.get(a);
				for (int b = 0; b < sortedFace.size(); b++) {
					Face face2 = sortedFace.get(b);
					if (a == b) {
						continue;
					}
					for (Position2D vertex : face1.getViewPoints()) {
						if (face2.inShape(vertex)) {
							g2.setColor(face1.getCol());
							g2.fillArc((int) (vertex.getX() + Face.xOffset - 5),
									(int) (-vertex.getY() + Face.yOffset - 5), 10, 10, 0, 360);
						}
					}

					for (Position2D vertex : face2.getViewPoints()) {
						if (face1.inShape(vertex)) {
							g2.setColor(face1.getCol());
							g2.fillArc((int) (vertex.getX() + Face.xOffset - 5),
									(int) (-vertex.getY() + Face.yOffset - 5), 10, 10, 0, 360);
						}
					}

					for (int i = 0; i < face1.getEdges2D().length - 1; i++) {
						for (int j = 0; j < face2.getEdges2D().length - 1; j++) {
							Position2D poi = face1.getEdges2D()[i].intersects(face2.getEdges2D()[j]);
							if (poi != null) {
								if (face1.inBounds(poi, face1.getViewPoints()[i], face1.getViewPoints()[i + 1])) {
									if (face2.inBounds(poi, face2.getViewPoints()[j], face2.getViewPoints()[j + 1])) {
										g2.setColor(face1.getCol());
										g2.fillArc((int) (poi.getX() + Face.xOffset - 5),
												(int) (-poi.getY() + Face.yOffset - 5), 10, 10, 0, 360);
									}
								}

							}

						}
						Position2D poi = face1.getEdges2D()[i].intersects(face2.getEdges2D()[face2.getNumPoints() - 1]);
						if (poi != null) {
							if (face1.inBounds(poi, face1.getViewPoints()[i], face1.getViewPoints()[i + 1])) {
								if (face2.inBounds(poi, face2.getViewPoints()[face2.getNumPoints() - 1],
										face2.getViewPoints()[0])) {
									g2.setColor(face1.getCol());
									g2.fillArc((int) (poi.getX() + Face.xOffset - 5),
											(int) (-poi.getY() + Face.yOffset - 5), 10, 10, 0, 360);
								}
							}
						}
					}

					for (int j = 0; j < face2.getEdges2D().length - 1; j++) {
						Position2D poi = face1.getEdges2D()[face1.getNumPoints() - 1].intersects(face2.getEdges2D()[j]);
						if (poi != null) {
							if (face1.inBounds(poi, face1.getViewPoints()[face1.getNumPoints() - 1],
									face1.getViewPoints()[0])) {
								if (face2.inBounds(poi, face2.getViewPoints()[j], face2.getViewPoints()[j + 1])) {
									g2.setColor(face1.getCol());
									g2.fillArc((int) (poi.getX() + Face.xOffset - 5),
											(int) (-poi.getY() + Face.yOffset - 5), 10, 10, 0, 360);
								}
							}
						}

					}
					Position2D poi = face1.getEdges2D()[face1.getNumPoints() - 1]
							.intersects(face2.getEdges2D()[face2.getNumPoints() - 1]);
					if (poi != null) {
						if (face1.inBounds(poi, face1.getViewPoints()[face1.getNumPoints() - 1],
								face1.getViewPoints()[0])) {
							if (face2.inBounds(poi, face2.getViewPoints()[face2.getNumPoints() - 1],
									face2.getViewPoints()[0])) {
								g2.setColor(face1.getCol());
								g2.fillArc((int) (poi.getX() + Face.xOffset - 5),
										(int) (-poi.getY() + Face.yOffset - 5), 10, 10, 0, 360);
							}
						}
					}
				}
			}

		}

	}
}
