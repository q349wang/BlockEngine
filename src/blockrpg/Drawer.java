package blockrpg;

import javax.swing.JPanel;

import javafx.util.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Vector;
import java.util.List;

public class Drawer extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2810583553938140766L;

	private static final Stroke THIN = new BasicStroke(1);
	private static final Stroke THICK = new BasicStroke((float) 1.5);

	private List<Shape> sortedFace;
	private List<Position2D> drawStack;
	private BufferedImage screen;
	private double[][] zBuf;
	private int[][] colBuf;

	public Drawer() {
		sortedFace = new Vector<Shape>();
		screen = new BufferedImage(MainWindow._width, MainWindow._height, BufferedImage.TYPE_INT_RGB);
		colBuf = new int[MainWindow._width][MainWindow._height];
		zBuf = new double[MainWindow._width][MainWindow._height];
	}

	public void setList(Vector<Shape>faces) {
		sortedFace = faces;
	}
	
	public void drawScreen() {
		drawStack.clear();
		for (int i = 0; i < MainWindow._height; i++) {
			for (int j = 0; j <MainWindow._width; j++) {
				
			}
		}
	}
	
	private List<Position2D> drawLine(int yOffset) {
		List<Position2D> toUpdate = new Vector<Position2D>();
		List<Position2D> intersects = new Vector<Position2D>();
		Line2D line = new Line2D(new Vector2D(0,1), new Position2D(0, yOffset));
		for(Shape shape : sortedFace)
		{
			for(Face face : shape.getFaces())
			{
				List<Position2D> faceIntersects = face.intersectsView(line);
				if (faceIntersects.size() > 0)
				{
					for (Position2D pos : faceIntersects)
					{
						if (pos.getX() < Face.xOffset)
						{
							intersects.add(pos);
						}
					}
				}
			}
		}
		for(int i = 0; i < MainWindow._width; i++)
		{
			
		}
		return toUpdate;
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g; // Casts the Graphics to Graphics2D

		// Turns on Anti-Aliasing
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		 g2.setRenderingHints(rh);
		 

//		Position2D pos2D = new Position2D();
//		Position3D pos3D = new Position3D();
//		for (int i = 0; i < MainWindow._width; i++) {
//			for (int j = 0; j < MainWindow._height; j++) {
//				int col = Color.WHITE.getRGB();
//				colBuf[i][j] = col;
//				zBuf[i][j] = 0;
//			}
//		}
//
//		for(int k = 0; k < sortedFace.size();k++) {
//			Face face = sortedFace.get(k);
//			double[][] faceBuf = face.getzBuf();
//			
//			int dim = (int) Math.ceil(2 * face.getBound2D());
//			for(int i = 0; i < dim;i++) {
//				for(int j = 0; j < dim;j++) {
//					if (i < faceBuf.length && faceBuf.length != 0 && j < faceBuf[0].length) {
//						int x = (int) (Math.floor(i - face.getBound2D() + face.getCenter2D().getX()) + Face.xOffset);
//						int y = (int) (-Math.floor(j - face.getBound2D() + face.getCenter2D().getY()) + Face.yOffset);
//						if (x >= 0 && x < MainWindow._width && y >= 0 && y < MainWindow._height) {
//							if (zBuf[x][y] == 0) {
//								zBuf[x][y] = faceBuf[i][j];
//								if (zBuf[x][y] != 0) {
//									colBuf[x][y] = face.getCol().getRGB();
//								}
//
//							} else if (faceBuf[i][j] < zBuf[x][y]) {
//								zBuf[x][y] = faceBuf[i][j];
//								colBuf[x][y] = face.getCol().getRGB();
//							}
//						} 
//					}
//				}
//			}
//		}
//
//		for (int i = 0; i < MainWindow._width; i++) {
//			for (int j = 0; j < MainWindow._height; j++) {
//				screen.setRGB(i,j, colBuf[i][j]);
//			}
//		}
//	
//		 g2.drawImage(screen, 0, 0, MainWindow._width, MainWindow._height, null);
//		
		
		 

		for (int i = 0; i < sortedFace.size(); i++) {
			for(Face face : sortedFace.get(i).getFaces())
			if (MainWindow.DEBUG) {
				g2.setColor(face.getCol());
				g2.drawString(Boolean.toString(face.isVisible()) + " " + Double.toString(face.getPlane().getNorm().dot(face.getPOV().getPos().getDirection(face.getCenter3D()))), 100, 100 + 10 * i);

			}
		}
	}
}
