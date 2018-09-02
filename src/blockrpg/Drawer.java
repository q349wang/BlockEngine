package blockrpg;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
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
	private BufferedImage screen;
	private double[][] zBuf;

	public Drawer() {
		sortedFace = new ArrayList<Face>();
		screen = new BufferedImage(MainWindow._width, MainWindow._height, BufferedImage.TYPE_INT_RGB);
		zBuf = new double[MainWindow._width][MainWindow._height];
	}

	public void setList(ArrayList<Face> faces) {
		sortedFace = faces;
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g; // Casts the Graphics to Graphics2D

		// Turns on Anti-Aliasing
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		 g2.setRenderingHints(rh);
		Position2D pos2D = new Position2D();
		Position3D pos3D = new Position3D();
//		for (int i = 0; i < MainWindow._width; i++) {
//			for (int j = 0; j < MainWindow._height; j++) {
//				int col = Color.WHITE.getRGB();
//				zBuf[i][j] = -1;
//				
//				for (int k = 0; k < sortedFace.size(); k++) {
//					if (pos2D.totDistanceFromSQ(sortedFace.get(k).getCenter2D()) > sortedFace.get(k).getBound2DSQ()) {
//						continue;
//					}
//
//					double dist = sortedFace.get(k).getPOV().getPos().totDistanceFrom(pos3D);
//					if(zBuf[i][j] == -1 && sortedFace.get(k).inShape(pos2D)) {
//						zBuf[i][j] = dist;
//						col = sortedFace.get(k).getCol().getRGB();
//					}else if (dist < zBuf[i][j] && sortedFace.get(k).inShape(pos2D)) {
//						zBuf[i][j] = dist;
//						col = sortedFace.get(k).getCol().getRGB();
//					}
//				}
//				
//			}
//		}
//
//		 g2.drawImage(screen, 0, 0, MainWindow._width, MainWindow._height, null);
		
		

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
				g2.setColor(sortedFace.get(i).getCol());
				g2.drawString(Boolean.toString(sortedFace.get(i).isVisible()) + " " + Double.toString(sortedFace.get(i).getBound2DSQ()), 100, 100 + 10 * i);

			}
		}

	}
}
