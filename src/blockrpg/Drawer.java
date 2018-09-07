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
	private int[][] colBuf;

	public Drawer() {
		sortedFace = new ArrayList<Face>();
		screen = new BufferedImage(MainWindow._width, MainWindow._height, BufferedImage.TYPE_INT_RGB);
		colBuf = new double[MainWindow._width][MainWindow._height];
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
		for (int i = 0; i < MainWindow._width; i++) {
			for (int j = 0; j < MainWindow._height; j++) {
				int col = Color.WHITE.getRGB();
				colBuf[i][j] = col;
				zBuf[i][j] = -1;
			}
		}

		for(Face face : sortedFace) {
			double[][] faceBuf = face.getzBuf();
			
			int dim = (int) Math.ceil(2 * face.getBound2D());
			for(int i = 0; i < dim;i++) {
				for(int j = 0; j < dim;j++) {
					int x = (int) (Math.floor(i-face.getBound2D()+face.getCenter2D().getX()) - Face.xOffset);
					int y = (int) (-Math.floor(j-face.getBound2D()+face.getCenter2D().getY()) - Face.yOffset);
					if(x >= 0 && x < MainWindow._width && y >= 0 && y < MainWindow._height) {
						if(zBuf[x][y] == -1) {
							zBuf[x][y] = faceBuf[i][j];
							colBuf[x][y] = face.getCol().getRGB();
						} else if (faceBuf[i][j] < zBuf[x][y]) {
							zBuf[x][y] = faceBuf[i][j];
							colBuf[x][y] = face.getCol().getRGB();
						}
					}
				}
			}
		}

		for (int i = 0; i < MainWindow._width; i++) {
			for (int j = 0; j < MainWindow._height; j++) {
				screen.setRGB(i,j, colBuf[i][j]);
			}
		}
	
		 g2.drawImage(screen, 0, 0, MainWindow._width, MainWindow._height, null);
		
//		
//
//		for (int i = 0; i < sortedFace.size(); i++) {
//			if (sortedFace.get(i).isVisible()) {
//				g2.setColor(sortedFace.get(i).getCol());
//				if (MainWindow.WIRE) {
//					g2.setStroke(THIN);
//					g2.drawPolygon(sortedFace.get(i).getPoly());
//				} else {
//					// g2.setStroke(THICK);
//					// g2.drawPolygon(sortedFace.get(i).getPoly());
//					g2.fillPolygon(sortedFace.get(i).getPoly());
//
//				}
//
//				if (MainWindow.SHOWCENT) {
//					Position2D center = sortedFace.get(i).getPOV().getViewPoint(sortedFace.get(i).getCenter3D());
//					g2.setColor(Color.BLACK);
//					g2.fillArc((int) (center.getX() + Face.xOffset - 5), (int) (-center.getY() + Face.yOffset - 5), 10,
//							10, 0, 360);
//				}
//
//			}
//
//			if (MainWindow.DEBUG) {
//				g2.setColor(sortedFace.get(i).getCol());
//				g2.drawString(Boolean.toString(sortedFace.get(i).isVisible()) + " " + Double.toString(sortedFace.get(i).getBound2DSQ()), 100, 100 + 10 * i);
//
//			}
//		}

	}
}
