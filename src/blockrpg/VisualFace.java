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
	private Position2D[] viewPoints;
	private Position2D[] relPoints;
	private Position2D anchor;
	private Plane facePlane;
	private int numPoints;
	private Position3D[] truePoints;
	private Position3D center;
	private Perspective pov;

	/**
	 * Default constructor for face
	 */
	public VisualFace() {

		seenFace = new Polygon();
		viewPoints = null;
		numPoints = 0;
		viewPoints = null;
		relPoints =null;
		center = new Position3D();
		pov = new Perspective();
	}
	
	public VisualFace(Position2D[] relPoints, Position2D anchor, int numPoints, Plane facePlane) {

		this.facePlane = facePlane;
		this.relPoints = relPoints;
		this.anchor = anchor;
		this.numPoints = numPoints;
		double coords[] = new double[3];
		
		for (int i = 0; i < numPoints;i++) {
			truePoints[i] = facePlane.placeOnPlane(relPoints[i]);
			for (int j = 0; j < 3;j++) {
				coords[j] += truePoints[i].getCoord()[j];
			}
		}

		for(int i = 0; i < 3;i++) {
			coords[i] /= numPoints;
		}
		
		this.center = new Position3D(coords);
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
