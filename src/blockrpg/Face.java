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
	private int[] numPoints;
	private Position center;

	// Default Face - Nothing
	public Face() {

		seenFace = new Polygon();
		xVal = null;
		yVal = null;
		numPoints = null;
		center = new Position();
	}

}
