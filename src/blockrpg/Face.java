/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockrpg;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author L
 */
public class Face {

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
	 * Default constructor for Face
	 */
	public Face() {

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
	 * Custom constructor for Face
	 * 
	 * @param relPoints Relative points of face on plane
	 * @param numPoints Number of points
	 * @param facePlane Plane that face lies on
	 * @param pov       POV used
	 */
	public Face(Position2D[] relPoints, int numPoints, Plane facePlane, Perspective pov) {

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

	/**
	 * Copies another Face
	 * 
	 * @param other Face to copy
	 */
	public Face(Face other) {

		setOffset();
		seenFace = new Polygon();
		this.facePlane = other.facePlane.clone();
		this.relPoints = new Position2D[other.numPoints];
		for(int i = 0; i < other.numPoints;i++) {
			this.relPoints[i] = other.relPoints[i].clone();
		}
		this.pov = other.pov; // You don't clone perspective
		// this.anchor = anchor;
		this.numPoints = other.numPoints;
		this.truePoints = new Position3D[numPoints];
		this.viewPoints = new Position2D[numPoints];
		setPoints();
	}
	
	@Override
	public Face clone() {
		return new Face(this);
	}

	/**
	 * 
	 * @param x Adds inputed value to the X coordinate
	 */
	public void addX(double x) {
		this.center.addX(x);
		this.facePlane.addX(x);
		setPoints();
	}

	/**
	 * 
	 * @param y Adds inputed value to the Y coordinate
	 */
	public void addY(double y) {
		this.center.addY(y);
		this.facePlane.addY(y);
		setPoints();
	}

	/**
	 * 
	 * @param z Adds inputed value to the Z coordinate
	 */
	public void addZ(double z) {
		this.center.addZ(z);
		this.facePlane.addZ(z);
		setPoints();
	}

	/**
	 * 
	 * @param x Sets X coordinate to the inputed value
	 */
	public void setX(double x) {
		this.center.setX(x);
		this.facePlane.setX(x);
		setPoints();
	}

	/**
	 * 
	 * @param y Sets Y coordinate to the inputed value
	 */
	public void setY(double y) {
		this.center.setY(y);
		this.facePlane.setY(y);
		setPoints();
	}

	/**
	 * 
	 * @param z Sets Z coordinate to the inputed value
	 */
	public void setZ(double z) {
		this.center.setZ(z);
		this.facePlane.setZ(z);
		setPoints();
	}
	
	/**
	 * 
	 * @return Returns center of Face
	 */
	public Position3D getCenter() {
		return this.center;
	}

	/**
	 * 
	 * @return Returns plane of Face
	 */
	public Plane getPlane() {
		return this.facePlane;
	}
	/**
	 * Sets pov to set
	 * 
	 * @param pov Perspective to set
	 */
	public void setPOV(Perspective pov) {
		this.pov = pov;
		setPoints();
	}

	/**
	 * Sets polygon's points with consideration of x and y offset
	 * 
	 * @param points Point position array
	 * @param num    Number of points
	 */
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

	/**
	 * 
	 * @return Returns polygon
	 */
	public Polygon getPoly() {
		return seenFace;
	}

	/**
	 * Sets true, and view point arrays for face
	 */
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

	/**
	 * Sets offset of origin for the coordinate system (set to standard
	 */
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

	/**
	 * Rotates Face by given angle and axis
	 * 
	 * @param ang  Angle to rotate
	 * @param axis Axis to rotate about
	 * @return Returns Face
	 */
	public Face rotate(double ang, Vector3D axis) {
		Face face = new Face(this);
		face.facePlane = face.facePlane.rotatePlane(ang, axis);
		face.setPoints();
		return face;
	}

}
