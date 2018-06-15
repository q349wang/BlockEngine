/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockrpg;

import java.awt.*;

import javax.swing.JComponent;

/**
 *
 * @author L
 */
class Face extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3860601078926040180L;

	private Polygon seenFace;
	private int[] xVal;
	private int[] yVal;
	private int numPoints;
	private Position[] points;
	private Position center;

	// Default Face - Nothing
	public Face() {

		seenFace = new Polygon();
		xVal = null;
		yVal = null;
		numPoints = 0;
		center = new Position();
	}
	
	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g; // Casts the Graphics to Graphics2D

		// Turns on Anti-Aliasing
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setRenderingHints(rh);

		// Draws Face
		g2.fillPolygon(seenFace);

	}

}
