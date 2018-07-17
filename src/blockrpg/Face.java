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

	public static double xOffset;
	public static double yOffset;

	protected double bound2D;
	protected double bound3D;

	private Polygon seenFace;

	private Position2D[] viewPoints;
	private Position2D[] relPoints;
	private Position2D[] polyPoints;
	private Position3D[] truePoints;

	private Line2D[] edges2D;
	private Line3D[] edges3D;

	private Plane facePlane;
	private int numPoints;

	private Position3D center3D;
	private Position2D center2D;

	private Perspective pov;

	/**
	 * Default constructor for Face
	 */
	public Face() {

		setOffset();
		this.seenFace = new Polygon();

		this.viewPoints = null;
		this.relPoints = null;
		this.polyPoints = null;
		this.truePoints = null;

		this.edges2D = null;
		this.edges3D = null;

		this.facePlane = new Plane();
		this.numPoints = 0;

		this.center3D = new Position3D();
		this.center2D = new Position2D();

		this.pov = new Perspective();
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
		this.seenFace = new Polygon();

		this.viewPoints = null;
		this.truePoints = null;
		this.relPoints = new Position2D[numPoints];

		for (int i = 0; i < numPoints; i++) {
			this.relPoints[i] = relPoints[i].clone();
		}
		this.polyPoints = null;

		this.facePlane = facePlane.clone();
		this.numPoints = numPoints;

		this.edges2D = null;
		this.edges3D = null;

		this.center3D = new Position3D();
		this.center2D = new Position2D();

		this.pov = pov; // You don't clone perspective

		setPoints();

	}

	/**
	 * Copies another Face
	 * 
	 * @param other Face to copy
	 */
	public Face(Face other) {

		setOffset();
		this.seenFace = new Polygon();

		this.viewPoints = null;
		this.truePoints = null;
		this.relPoints = new Position2D[other.numPoints];
		for (int i = 0; i < other.numPoints; i++) {
			this.relPoints[i] = other.relPoints[i].clone();
		}
		this.polyPoints = null;

		this.facePlane = other.facePlane.clone();
		this.numPoints = other.numPoints;

		this.edges2D = null;
		this.edges3D = null;

		this.center3D = new Position3D();
		this.center2D = new Position2D();

		this.pov = other.pov; // You don't clone perspective

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
		this.center3D.addX(x);
		this.facePlane.addX(x);
		setPoints();
	}

	/**
	 * 
	 * @param y Adds inputed value to the Y coordinate
	 */
	public void addY(double y) {
		this.center3D.addY(y);
		this.facePlane.addY(y);
		setPoints();
	}

	/**
	 * 
	 * @param z Adds inputed value to the Z coordinate
	 */
	public void addZ(double z) {
		this.center3D.addZ(z);
		this.facePlane.addZ(z);
		setPoints();
	}

	/**
	 * 
	 * @param x Sets X coordinate to the inputed value
	 */
	public void setX(double x) {
		this.center3D.setX(x);
		this.facePlane.setX(x);
		setPoints();
	}

	/**
	 * 
	 * @param y Sets Y coordinate to the inputed value
	 */
	public void setY(double y) {
		this.center3D.setY(y);
		this.facePlane.setY(y);
		setPoints();
	}

	/**
	 * 
	 * @param z Sets Z coordinate to the inputed value
	 */
	public void setZ(double z) {
		this.center3D.setZ(z);
		this.facePlane.setZ(z);
		setPoints();
	}

	/**
	 * 
	 * @return Returns center3D of Face
	 */
	public Position3D getCenter3D() {
		return this.center3D;
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
	 * 
	 * @return Returns POV
	 */
	public Perspective getPOV() {
		return this.pov;
	}

	/**
	 * Sets polygon's points with consideration of x and y offset and 2D center and edges
	 * 
	 * @param points Point position array
	 * @param num    Number of points
	 */
	private void setPoly(Position2D[] points, int num) {
		if (this.edges2D == null) {
			this.edges2D = new Line2D[this.numPoints - 1];
			for (int i = 0; i < this.edges2D.length; i++) {
				this.edges2D[i] = new Line2D();
			}
		}

		if (this.polyPoints == null) {
			this.polyPoints = new Position2D[this.numPoints];
			for (int i = 0; i < this.polyPoints.length; i++) {
				this.polyPoints[i] = new Position2D();
			}
		}

		double xCent = 0;
		double yCent = 0;
		int[] x = new int[num];
		int[] y = new int[num];

		for (int i = 0; i < num; i++) {
			this.polyPoints[i].setCoord(new double[] { points[i].getX(), points[i].getY() });

			xCent += points[i].getX();
			yCent += points[i].getY();

			x[i] = (int) (points[i].getX() + xOffset);
			y[i] = (int) (-points[i].getY() + yOffset);
		}

		for (int i = 1; i < num; i++) {
			this.edges2D[i - 1].setLineToPoints(this.polyPoints[i - 1], this.polyPoints[i]);
		}

		xCent /= num;
		yCent /= num;

		this.center2D.setCoord(new double[] { xCent, yCent });

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

		// Sets arrays if they are null
		if (this.viewPoints == null) {
			this.viewPoints = new Position2D[numPoints];
		}
		if (this.edges3D == null) {
			this.edges3D = new Line3D[numPoints - 1];
			for (int i = 0; i < this.edges3D.length; i++) {
				this.edges3D[i] = new Line3D();
			}
		}

		if (this.truePoints == null) {
			this.truePoints = new Position3D[numPoints];
		}

		double coords[] = new double[3];
		for (int i = 0; i < numPoints; i++) {
			this.truePoints[i] = this.facePlane.placeOnPlane(this.relPoints[i]);
			this.viewPoints[i] = this.pov.getViewPoint(this.truePoints[i]);
			for (int j = 0; j < 3; j++) {
				coords[j] += this.truePoints[i].getCoord()[j];
			}
		}

		// Create edges3D
		for (int i = 1; i < numPoints; i++) {
			this.edges3D[i - 1].setLineToPoints(this.truePoints[i - 1], this.truePoints[i]);
		}

		setPoly(this.viewPoints, this.numPoints);

		for (int i = 0; i < 3; i++) {
			coords[i] /= this.numPoints;
		}

		this.center3D.setCoord(coords);
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
