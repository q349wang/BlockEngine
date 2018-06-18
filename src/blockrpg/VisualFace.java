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
public class VisualFace extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3860601078926040180L;

	private Polygon seenFace;
	private int[] viewX;
	private int[] viewY;
	private int numPoints;
	private Position3D[] points;
	private Position3D center;

	/**
	 * Default constructor for face
	 */
	public VisualFace() {

		seenFace = new Polygon();
		viewX = null;
		viewY = null;
		numPoints = 0;
		points =null;
		center = new Position3D();
	}

	
	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g; // Casts the Graphics to Graphics2D

		// Turns on Anti-Aliasing
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setRenderingHints(rh);

		// Draws VisualFace
		g2.fillPolygon(seenFace);

	}

}
