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
	private int numPoints;
	private Position3D[] absPoints;
	private Position3D center;

	/**
	 * Default constructor for face
	 */
	public VisualFace() {

		seenFace = new Polygon();
		viewPoints = null;
		numPoints = 0;
		relPoints =null;
		center = new Position3D();
	}
	
	public VisualFace(Position2D[] relPoints, Position2D anchor, int numPoints) {

		
		this.relPoints = relPoints;
		this.anchor = anchor;
		this.numPoints = numPoints;
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
