/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockrpg;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.swing.JComponent;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author L
 */
public class VisualFace {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3860601078926040180L;

	private double xOffset;
	private double yOffset;

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
	 * Default constructor for VisualFace
	 */
	public VisualFace() {

		setOffset();
		seenFace = new Polygon();
		viewPoints = null;
		numPoints = 0;
		viewPoints = null;
		relPoints = null;
		center = new Position3D();
		pov = new Perspective();
	}

	/**
	 * Custom constructor for VisualFace
	 * @param relPoints Relative points of face on plane
	 * @param numPoints Number of points
	 * @param facePlane Plane that face lies on
	 * @param pov POV used
	 */
	public VisualFace(Position2D[] relPoints, int numPoints, Plane facePlane, Perspective pov) {

		setOffset();
		seenFace = new Polygon();
		this.facePlane = facePlane;
		this.relPoints = relPoints;
		this.pov = pov;
		// this.anchor = anchor;
		this.numPoints = numPoints;
		truePoints = new Position3D[numPoints];
		viewPoints = new Position2D[numPoints];

		setPoints();

	}

	public VisualFace(VisualFace other) {

		setOffset();
		seenFace = new Polygon();
		this.facePlane = other.facePlane;
		this.relPoints = other.relPoints;
		this.pov = other.pov;
		// this.anchor = anchor;
		this.numPoints = other.numPoints;
		this.truePoints = new Position3D[numPoints];
		this.viewPoints = new Position2D[numPoints];
		setPoints();
	}
	
	public void setPOV(Perspective pov) {
		this.pov = pov;
		setPoints();
	}

	private void setPoly(Position2D[] points, int num) {
		int[] x = new int[num];
		int[] y = new int[num];

		for (int i = 0; i < num; i++) {
			x[i] = (int) (points[i].getX() + xOffset);
			y[i] = (int) (-points[i].getY() + yOffset);
		}

		seenFace.npoints = num;
		seenFace.xpoints = x;
		seenFace.ypoints = y;
	}

	private void setPoints() {

		double coords[] = new double[3];
		for (int i = 0; i < numPoints; i++) {
			this.truePoints[i] = this.facePlane.placeOnPlane(this.relPoints[i]);
			this.viewPoints[i] = this.pov.getViewPoint(this.truePoints[i]);
			for (int j = 0; j < 3; j++) {
				coords[j] += this.truePoints[i].getCoord()[j];
			}
		}

		setPoly(this.viewPoints, this.numPoints);

		for (int i = 0; i < 3; i++) {
			coords[i] /= this.numPoints;
		}

		this.center = new Position3D(coords);
	}

	private void setOffset() {
		File file = new File("src\\blockrpg\\MainWindow.form");
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);

			xOffset = Integer.parseInt(document.getElementsByTagName("width").item(0).getTextContent()) / 2;
			yOffset = Integer.parseInt(document.getElementsByTagName("height").item(0).getTextContent()) / 2;

		} catch (SAXException | IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	public VisualFace rotate(double ang, Vector3D axis) {
		VisualFace face = new VisualFace(this);
		face.facePlane = face.facePlane.rotatePlane(ang, axis);
		face.setPoints();
		return face;
	}

	public Polygon getPoly() {
		return seenFace;
	}

}
