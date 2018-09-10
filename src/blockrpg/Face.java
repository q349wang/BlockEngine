/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockrpg;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author L
 */
public class Face implements Comparable<Face> {

	public static double xOffset;
	public static double yOffset;

	private double bound2D;
	private double bound2DSQ;
	private double bound3D;
	private double bound3DSQ;

	private Polygon seenFace;
	private double[][] zBuf;

	private Position2D[] viewPoints;
	private Position2D[] relPoints;
	private Position3D[] truePoints;

	private Line2D[] edges2D;
	private Line3D[] edges3D;

	private Plane facePlane;
	private int numPoints;

	private Position3D center3D;
	private Position2D center2D;

	private Perspective pov;

	private boolean visible;
	private boolean forceTransparent;
	private boolean moved;

	private Map<Face, Integer> comps;

	private Color col;

	public double test = 0;

	/**
	 * Default constructor for Face
	 */
	public Face() {

		setOffset();
		this.seenFace = new Polygon();

		this.viewPoints = null;
		this.relPoints = null;
		this.truePoints = null;

		this.edges2D = null;
		this.edges3D = null;

		this.facePlane = new Plane();
		this.numPoints = 0;

		this.center3D = new Position3D();
		this.center2D = new Position2D();

		this.pov = new Perspective();

		this.visible = true;
		this.setForceTransparent(false);
		this.moved = true;

		this.comps = new HashMap<Face, Integer>();

		this.col = new Color(0, 0, 0);
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

		Position2D planeCenter = new Position2D();
		for (int i = 0; i < numPoints; i++) {
			this.relPoints[i] = relPoints[i].clone();
			planeCenter = planeCenter.add(this.relPoints[i]);
		}

		planeCenter.setCoord(planeCenter.toVec().multiply(1.0 / numPoints).getCoord());

		for (int i = 0; i < numPoints; i++) {
			this.relPoints[i] = this.relPoints[i].subtract(planeCenter);
		}

		this.facePlane = facePlane.clone();
		this.facePlane.setPos(facePlane.placeOnPlane(planeCenter).getCoord());
		this.numPoints = numPoints;

		this.edges2D = null;
		this.edges3D = null;

		this.center3D = new Position3D();
		this.center2D = new Position2D();

		this.pov = pov; // You don't clone perspective

		this.visible = true;
		this.setForceTransparent(false);
		this.moved = true;

		this.comps = new HashMap<Face, Integer>();

		this.col = new Color(0, 0, 0);

		setPoints();

	}

	/**
	 * Custom constructor for Face
	 * 
	 * @param relPoints Relative points of face on plane
	 * @param numPoints Number of points
	 * @param facePlane Plane that face lies on
	 * @param pov       POV used
	 * @param col       Color
	 */
	public Face(Position2D[] relPoints, int numPoints, Plane facePlane, Perspective pov, Color col) {

		setOffset();
		this.seenFace = new Polygon();

		this.viewPoints = null;
		this.truePoints = null;
		this.relPoints = new Position2D[numPoints];

		Position2D planeCenter = new Position2D();
		for (int i = 0; i < numPoints; i++) {
			this.relPoints[i] = relPoints[i].clone();
			planeCenter = planeCenter.add(this.relPoints[i]);
		}

		planeCenter.setCoord(planeCenter.toVec().multiply(1.0 / numPoints).getCoord());

		for (int i = 0; i < numPoints; i++) {
			this.relPoints[i] = this.relPoints[i].subtract(planeCenter);
		}

		this.facePlane = facePlane.clone();
		this.facePlane.setPos(facePlane.placeOnPlane(planeCenter).getCoord());
		this.numPoints = numPoints;

		this.edges2D = null;
		this.edges3D = null;

		this.center3D = new Position3D();
		this.center2D = new Position2D();

		this.pov = pov; // You don't clone perspective

		this.visible = true;
		this.setForceTransparent(false);
		this.moved = true;

		this.comps = new HashMap<Face, Integer>();

		this.col = new Color(col.getRGB());

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

		this.viewPoints = new Position2D[other.numPoints];
		this.truePoints = new Position3D[other.numPoints];
		this.relPoints = new Position2D[other.numPoints];
		Position2D planeCenter = new Position2D();
		for (int i = 0; i < other.numPoints; i++) {
			this.viewPoints[i] = other.viewPoints[i].clone();
			this.truePoints[i] = other.truePoints[i].clone();
			this.relPoints[i] = other.relPoints[i].clone();
			planeCenter = planeCenter.add(this.relPoints[i]);
		}

		planeCenter.setCoord(planeCenter.toVec().multiply(1.0 / other.numPoints).getCoord());

		for (int i = 0; i < other.numPoints; i++) {
			this.relPoints[i] = this.relPoints[i].subtract(planeCenter);
		}

		this.facePlane = other.facePlane.clone();
		this.facePlane.setPos(other.facePlane.placeOnPlane(planeCenter).getCoord());
		this.numPoints = other.numPoints;

		this.edges2D = null;
		this.edges3D = null;

		this.center3D = new Position3D();
		this.center2D = new Position2D();

		this.pov = other.pov; // You don't clone perspective

		this.visible = true;
		this.setForceTransparent(false);
		this.moved = true;

		this.comps = new HashMap<Face, Integer>();

		this.col = new Color(other.col.getRGB());

		setPoints();
	}

	@Override
	public Face clone() {
		return new Face(this);
	}

	/**
	 * Sets this face to match other face
	 * 
	 * @param other Other face to match
	 */
	public void setTo(Face other) {
		setOffset();

		this.viewPoints = new Position2D[other.numPoints];
		this.truePoints = new Position3D[other.numPoints];
		this.relPoints = new Position2D[other.numPoints];
		for (int i = 0; i < other.numPoints; i++) {
			this.viewPoints[i] = other.viewPoints[i].clone();
			this.truePoints[i] = other.truePoints[i].clone();
			this.relPoints[i] = other.relPoints[i].clone();
		}

		this.facePlane = other.facePlane.clone();
		this.numPoints = other.numPoints;

		this.edges2D = new Line2D[other.numPoints];
		this.edges3D = new Line3D[other.numPoints];
		for (int i = 0; i < other.numPoints; i++) {
			this.edges2D[i] = other.edges2D[i].clone();
			this.edges3D[i] = other.edges3D[i].clone();
		}

		this.center3D = new Position3D();
		this.center2D = new Position2D();

		this.pov = other.pov; // You don't clone perspective

		this.visible = other.visible;
		this.setForceTransparent(other.forceTransparent);
		this.moved = other.moved;

		this.comps = new HashMap<Face, Integer>();

		this.col = new Color(other.col.getRGB());

		setPoints();
	}

	/**
	 * Sets 3D coordinates to inputted array
	 * 
	 * @param coords array of double coordinates
	 */
	public void setCoords(double[] coords) {
		this.center3D.setCoord(coords);
		this.facePlane.setPos(coords);
		setPoints();
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
	 * @return Returns comparision map
	 */
	public Map<Face, Integer> getComps() {
		return this.comps;
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
	 * @return Returns center2D of Face
	 */
	public Position2D getCenter2D() {
		return this.center2D;
	}

	/**
	 * 
	 * @return Returns 2D boundary
	 */
	public double getBound2D() {
		return this.bound2D;
	}

	/**
	 * 
	 * @return Returns 3D boundary
	 */
	public double getBound3D() {
		return this.bound3D;
	}

	/**
	 * 
	 * @return Returns number of points
	 */
	public int getNumPoints() {
		return this.numPoints;
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
	 * 
	 * @return Returns view points
	 */
	public Position2D[] getViewPoints() {
		return this.viewPoints;
	}

	/**
	 * 
	 * @return Returns relative points
	 */
	public Position2D[] getRelPoints() {
		return this.relPoints;
	}

	/**
	 * 
	 * @return Returns true points
	 */
	public Position3D[] getTruePoints() {
		return this.truePoints;
	}

	/**
	 * 
	 * @return Returns 2D edges
	 */
	public Line2D[] getEdges2D() {
		return this.edges2D;
	}

	/**
	 * Sets polygon's points with consideration of x and y offset and 2D center and
	 * edges
	 * 
	 * @param points Point position array
	 * @param num    Number of points
	 */
	private void setPoly(Position2D[] points, int num) {

		this.bound2D = 0;
		this.bound2DSQ = 0;

		if (this.edges2D == null) {
			this.edges2D = new Line2D[this.numPoints];
			for (int i = 0; i < this.edges2D.length; i++) {
				this.edges2D[i] = new Line2D();
			}
		}

		double xCent = 0;
		double yCent = 0;
		int[] x = new int[num];
		int[] y = new int[num];

		for (int i = 0; i < num; i++) {

			xCent += points[i].getX();
			yCent += points[i].getY();

			x[i] = (int) (points[i].getX() + xOffset);
			y[i] = (int) (-points[i].getY() + yOffset);
		}

		for (int i = 1; i < num; i++) {
			this.edges2D[i - 1].setLineToPoints(points[i - 1], points[i]);
		}

		this.edges2D[num - 1].setLineToPoints(points[num - 1], points[0]);
		xCent /= num;
		yCent /= num;

		this.center2D.setCoord(new double[] { xCent, yCent });

		for (int i = 0; i < num; i++) {
			this.bound2DSQ = Math.max(this.bound2DSQ, this.center2D.totDistanceFromSQ(points[i]));

		}

		this.bound2D = Math.sqrt(this.bound2DSQ);

//		int dim = (int) Math.ceil(2 * this.bound2D);
//
//		long time = System.nanoTime();
//		this.zBuf = new double[dim][dim];
//		Line2D scanLine = new Line2D(new Vector2D(1, 0), new Position2D());
//		ArrayList<Integer> pivots = new ArrayList<Integer>();
//		for (int i = 0; i < dim; i++) {
//
//			scanLine.setPos(new double[] { 0, i + this.center2D.getY() - this.bound2D });
//
//			pivots.clear();
//
//			for (int j = 0; j < this.edges2D.length - 1; j++) {
//				Position2D poi = this.edges2D[j].intersects(scanLine);
//				if (poi != null) { // Test for parallel
//					// Test for bounds
//					if (this.inBounds(poi, this.viewPoints[j], this.viewPoints[j + 1])) {
//						pivots.add((int) Math.round(poi.getCoord()[0]));
//					}
//				}
//			}
//
//			Position2D poi = this.edges2D[num - 1].intersects(scanLine);
//			if (poi != null) { // Test for parallel
//				// Test for bounds
//				if (this.inBounds(poi, this.viewPoints[0], this.viewPoints[num - 1])) {
//					pivots.add((int) Math.round(poi.getCoord()[0]));
//				}
//			}
//			Collections.sort(pivots);
//			int pivot = 0;
//			int in = -1;
//			if (pivots.size() == 0) {
//				for (int j = 0; j < dim; j++) {
//
//					zBuf[j][i] = 0;
//				}
//			} else {
//				for (int j = 0; j < dim; j++) {
//					if (pivot < pivots.size() && j + this.center2D.getX() - this.bound2D >= pivots.get(pivot)) {
//						in = -in;
//						pivot++;
//					}
//
//					if (in < 0) {
//						zBuf[j][i] = 0;
//					} else {
//						Position3D pos = this.pov.getRealPoint(new Position2D(j + this.center2D.getX() - this.bound2D,
//								i + this.center2D.getY() - this.bound2D), this.facePlane);
//						if (pos != null) {
//							zBuf[j][i] = this.pov.getPos().totDistanceFromSQ(pos);
//						}
//
//					}
//				}
//			}
//
//		}

//		System.out.println(System.nanoTime() - time);
//		System.out.println(this.bound2DSQ);
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
	public void setPoints() {

		this.moved = true;

		this.bound3D = 0;
		this.bound3DSQ = 0;

		// Sets arrays if they are null
		if (this.viewPoints == null) {
			this.viewPoints = new Position2D[numPoints];
		}
		if (this.edges3D == null) {
			this.edges3D = new Line3D[numPoints];
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
		for (int i = 1; i < this.numPoints; i++) {
			this.edges3D[i - 1].setLineToPoints(this.truePoints[i - 1], this.truePoints[i]);
		}

		this.edges3D[this.numPoints - 1].setLineToPoints(this.truePoints[this.numPoints - 1], this.truePoints[0]);

		setPoly(this.viewPoints, this.numPoints);

		for (int i = 0; i < 3; i++) {
			coords[i] /= this.numPoints;
		}

		this.center3D.setCoord(coords);

		for (int i = 0; i < this.numPoints; i++) {
			this.bound3DSQ = Math.max(this.bound3DSQ, this.center3D.totDistanceFromSQ(this.truePoints[i]));
		}

		this.bound3D = Math.sqrt(this.bound3DSQ);
		if (Math.abs(this.facePlane.getNorm().dot(this.pov.getPos().getDirection(this.center3D))) < Coord3D.ERROR) {
			this.visible = false;
		} else {
			this.visible = true;
		}
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
	 */
	public void rotate(double ang, Vector3D axis) {
		this.facePlane.rotatePlane(ang, axis);
		this.setPoints();
	}

	/**
	 * Checks if the 2D visual face may intersect with another
	 * 
	 * @param other Other face to check
	 * @return Returns true if faces may intersect
	 */
	public boolean mayIntersect2D(Face other) {
		return this.center2D.totDistanceFrom(other.center2D) - this.bound2D - other.bound2D <= 0;
	}

	/**
	 * Checks if the 3D face may intersect with another
	 * 
	 * @param other Other face to check
	 * @return Returns true if faces may intersect
	 */
	public boolean mayIntersect3D(Face other) {
		return this.center3D.totDistanceFrom(other.center3D) - this.bound3D - other.bound3D <= 0;
	}

	/**
	 * 
	 * @return Returns true if face is forced to be transparent
	 */
	public boolean isForceTransparent() {
		return forceTransparent;
	}

	/**
	 * 
	 * @return Returns true if face is visible
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * 
	 * @return Returns true if face needs to be checked for visibility
	 */
	public boolean checkMoved() {
		return moved;
	}

	/**
	 * 
	 * @param forceTransparent Sets forceTransparent
	 */
	public void setForceTransparent(boolean forceTransparent) {
		this.forceTransparent = forceTransparent;
	}

	/**
	 * 
	 * @param visible Sets visible
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * 
	 * @param moved Sets check for visible
	 */
	public void setMoved(boolean moved) {
		this.moved = moved;
	}

	@Override
	public int compareTo(Face other) {

//		int coverCheck = this.checkCover(other);
//		if (coverCheck != 2) {
//			if (coverCheck < 0) {
//				this.setVisible(false);
//			} else {
//				this.setVisible(true);
//			}
//			
//			this.comps.put(other, coverCheck);
//			other.comps.put(this, -coverCheck);
//			return coverCheck;
//		} else {
//			this.setVisible(true);
//		}

		if ((!this.moved && !other.moved) && this.comps.containsKey(other)) {
			return this.comps.get(other);
		}

		if ((this.center2D.totDistanceFrom(other.center2D) - this.getBound2D() - other.getBound2D() > 0)
				&& this.comps.containsKey(other)) {
			return this.comps.get(other);
		}

		ArrayList<Position2D> intersects = new ArrayList<Position2D>();
		intersects.clear();

		for (int i = 0; i < this.edges2D.length - 1; i++) {

			for (int j = 0; j < other.edges2D.length - 1; j++) {

				Position2D poi = this.edges2D[i].intersects(other.edges2D[j]);
				if (poi != null) { // Test for parallel
					// Test for bounds
					if (this.inBounds(poi, this.viewPoints[i], this.viewPoints[i + 1])) {
						if (other.inBounds(poi, other.viewPoints[j], other.viewPoints[j + 1])) {
							intersects.add(poi);
						}
					}
				} else if (this.edges2D[i].similar(other.edges2D[j])) {
					if (this.inBounds(this.viewPoints[i], this.viewPoints[i], this.viewPoints[i + 1])) {
						if (other.inBounds(this.viewPoints[i], other.viewPoints[j], other.viewPoints[j + 1])) {
							intersects.add(this.viewPoints[i]);
						}
					}

					if (this.inBounds(this.viewPoints[i + 1], this.viewPoints[i], this.viewPoints[i + 1])) {
						if (other.inBounds(this.viewPoints[i + 1], other.viewPoints[j], other.viewPoints[j + 1])) {
							intersects.add(this.viewPoints[i + 1]);
						}
					}

					if (this.inBounds(other.viewPoints[j], this.viewPoints[i], this.viewPoints[i + 1])) {
						if (other.inBounds(other.viewPoints[j], other.viewPoints[j], other.viewPoints[j + 1])) {
							intersects.add(other.viewPoints[j]);
						}
					}

					if (this.inBounds(other.viewPoints[j + 1], this.viewPoints[i], this.viewPoints[i + 1])) {
						if (other.inBounds(other.viewPoints[j + 1], other.viewPoints[j], other.viewPoints[j + 1])) {
							intersects.add(other.viewPoints[j + 1]);
						}
					}
				}
			}

			Position2D poi = this.edges2D[i].intersects(other.edges2D[other.numPoints - 1]);
			if (poi != null) { // Test for parallel
				// Test for bounds
				if (this.inBounds(poi, this.viewPoints[i], this.viewPoints[i + 1])) {
					if (other.inBounds(poi, other.viewPoints[other.numPoints - 1], other.viewPoints[0])) {
						intersects.add(poi);
					}
				}
			} else if (this.edges2D[i].similar(other.edges2D[other.numPoints - 1])) {
				if (this.inBounds(this.viewPoints[i], this.viewPoints[i], this.viewPoints[i + 1])) {
					if (other.inBounds(this.viewPoints[i], other.viewPoints[other.numPoints - 1],
							other.viewPoints[0])) {
						intersects.add(this.viewPoints[i]);
					}
				}

				if (this.inBounds(this.viewPoints[i + 1], this.viewPoints[i], this.viewPoints[i + 1])) {
					if (other.inBounds(this.viewPoints[i + 1], other.viewPoints[other.numPoints - 1],
							other.viewPoints[0])) {
						intersects.add(this.viewPoints[i + 1]);
					}
				}

				if (this.inBounds(other.viewPoints[other.numPoints - 1], this.viewPoints[i], this.viewPoints[i + 1])) {
					if (other.inBounds(other.viewPoints[other.numPoints - 1], other.viewPoints[other.numPoints - 1],
							other.viewPoints[0])) {
						intersects.add(other.viewPoints[other.numPoints - 1]);
					}
				}

				if (this.inBounds(other.viewPoints[0], this.viewPoints[i], this.viewPoints[i + 1])) {
					if (other.inBounds(other.viewPoints[0], other.viewPoints[other.numPoints - 1],
							other.viewPoints[0])) {
						intersects.add(other.viewPoints[0]);
					}
				}
			}
		}

		for (int j = 0; j < other.edges2D.length - 1; j++) {

			Position2D poi = this.edges2D[this.numPoints - 1].intersects(other.edges2D[j]);
			if (poi != null) { // Test for parallel
				// Test for bounds
				if (this.inBounds(poi, this.viewPoints[this.numPoints - 1], this.viewPoints[0])) {
					if (other.inBounds(poi, other.viewPoints[j], other.viewPoints[j + 1])) {

						intersects.add(poi);
					}
				}
			} else if (this.edges2D[this.numPoints - 1].similar(other.edges2D[j])) {
				if (this.inBounds(this.viewPoints[this.numPoints - 1], this.viewPoints[this.numPoints - 1],
						this.viewPoints[0])) {
					if (other.inBounds(this.viewPoints[this.numPoints - 1], other.viewPoints[j],
							other.viewPoints[j + 1])) {
						intersects.add(this.viewPoints[this.numPoints - 1]);
					}
				}

				if (this.inBounds(this.viewPoints[0], this.viewPoints[this.numPoints - 1], this.viewPoints[0])) {
					if (other.inBounds(this.viewPoints[0], other.viewPoints[j], other.viewPoints[j + 1])) {
						intersects.add(this.viewPoints[0]);
					}
				}

				if (this.inBounds(other.viewPoints[j], this.viewPoints[this.numPoints - 1], this.viewPoints[0])) {
					if (other.inBounds(other.viewPoints[j], other.viewPoints[j + 1], other.viewPoints[j + 1])) {
						intersects.add(other.viewPoints[j]);
					}
				}

				if (this.inBounds(other.viewPoints[j + 1], this.viewPoints[this.numPoints - 1], this.viewPoints[0])) {
					if (other.inBounds(other.viewPoints[j + 1], other.viewPoints[j], other.viewPoints[j + 1])) {
						intersects.add(other.viewPoints[j + 1]);
					}
				}
			}
		}

		Position2D poi = this.edges2D[this.numPoints - 1].intersects(other.edges2D[other.numPoints - 1]);
		if (poi != null) { // Test for parallel
			// Test for bounds
			if (this.inBounds(poi, this.viewPoints[this.numPoints - 1], this.viewPoints[0])) {
				if (other.inBounds(poi, other.viewPoints[other.numPoints - 1], other.viewPoints[0])) {

					intersects.add(poi);
				}
			}
		} else if (this.edges2D[this.numPoints - 1].similar(other.edges2D[other.numPoints - 1])) {
			if (this.inBounds(this.viewPoints[this.numPoints - 1], this.viewPoints[this.numPoints - 1],
					this.viewPoints[0])) {
				if (other.inBounds(this.viewPoints[this.numPoints - 1], other.viewPoints[other.numPoints - 1],
						other.viewPoints[0])) {
					intersects.add(this.viewPoints[this.numPoints - 1]);
				}
			}

			if (this.inBounds(this.viewPoints[0], this.viewPoints[this.numPoints - 1], this.viewPoints[0])) {
				if (other.inBounds(this.viewPoints[0], other.viewPoints[other.numPoints - 1], other.viewPoints[0])) {
					intersects.add(this.viewPoints[0]);
				}
			}

			if (this.inBounds(other.viewPoints[other.numPoints - 1], this.viewPoints[this.numPoints - 1],
					this.viewPoints[0])) {
				if (other.inBounds(other.viewPoints[other.numPoints - 1], other.viewPoints[other.numPoints - 1],
						other.viewPoints[0])) {
					intersects.add(other.viewPoints[other.numPoints - 1]);
				}
			}

			if (this.inBounds(other.viewPoints[0], this.viewPoints[this.numPoints - 1], this.viewPoints[0])) {
				if (other.inBounds(other.viewPoints[0], other.viewPoints[other.numPoints - 1], other.viewPoints[0])) {
					intersects.add(other.viewPoints[0]);
				}
			}

		}

		for (Position2D vertex : this.viewPoints) {
			if (other.inShape(vertex)) {
				intersects.add(vertex);
			}
		}

		for (Position2D vertex : other.viewPoints) {
			if (this.inShape(vertex)) {
				intersects.add(vertex);
			}
		}

		if (intersects.size() == 0) {
			if (this.comps.containsKey(other)) {
				return this.comps.get(other);
			}
			return 0;
		}

		Position2D center = new Position2D();

		for (int i = 0; i < intersects.size(); i++) {
			double[] coords = center.getCoord();
			for (int j = 0; j < 2; j++) {
				coords[j] += intersects.get(i).getCoord()[j];
			}
			center.setCoord(coords);
		}

		center.setCoord(center.toVec().multiply(1.0 / intersects.size()).toPos().getCoord());

		Position3D thisReal = this.pov.getRealPoint(center, this.facePlane);
		Position3D otherReal = other.pov.getRealPoint(center, other.facePlane);

		if (thisReal == null || otherReal == null) {
			if (this.comps.containsKey(other)) {
				return this.comps.get(other);
			}
			return 0;
		}

		Line3D ray = new Line3D(this.pov.getPos(), thisReal);
		double thisDis = this.pov.getPos().totDistanceFromSQ(this.facePlane.getIntersect(ray));
		double otherDis = other.pov.getPos().totDistanceFromSQ(other.facePlane.getIntersect(ray));

		test = thisDis;
		if (Math.abs(thisDis - otherDis) < Coord3D.ERROR) {

			this.comps.put(other, 0);
			other.comps.put(this, 0);
			return 0;
		} else if (thisDis > otherDis) {

			this.comps.put(other, -1);
			other.comps.put(this, 1);
			return -1;
		} else {

			this.comps.put(other, 1);
			other.comps.put(this, -1);
			return 1;
		}

	}

	/**
	 * 
	 * @return Returns colour of shape
	 */
	public Color getCol() {
		return col;
	}

	/**
	 * 
	 * @param col Sets colour of shape to col
	 */
	public void setCol(Color col) {
		this.col = new Color(col.getRGB());
	}

	/**
	 * Checks if a position is in between two other points
	 * 
	 * @param pos      Point to check
	 * @param lowBound Lower point
	 * @param upBound  Upper point
	 * @return Returns true if in between points
	 */
	public boolean inBounds(Position2D pos, Position2D lowBound, Position2D upBound) {
		boolean testX = (pos.getX() >= lowBound.getX() && pos.getX() <= upBound.getX())
				|| (pos.getX() <= lowBound.getX() && pos.getX() >= upBound.getX());

		boolean testY = (pos.getY() >= lowBound.getY() && pos.getY() <= upBound.getY())
				|| (pos.getY() <= lowBound.getY() && pos.getY() >= upBound.getY());

		return testX && testY;
	}

	/**
	 * Checks if this object covers other object (-1 if other covers this, 1 if
	 * other covers this, 0 if equal, 2 if they dont cover)
	 * 
	 * @param other
	 * @return
	 */
	public int checkCover(Face other) {
		ArrayList<Position2D> intersects = new ArrayList<Position2D>();
		intersects.clear();

		for (Position2D vertex : this.viewPoints) {
			if (other.inShape(vertex)) {
				intersects.add(vertex);
			}
		}

		if (intersects.size() == this.viewPoints.length) {
			Position2D center = new Position2D();

			for (int i = 0; i < intersects.size(); i++) {
				double[] coords = center.getCoord();
				for (int j = 0; j < 2; j++) {
					coords[j] += intersects.get(i).getCoord()[j];
				}
				center.setCoord(coords);
			}

			center.setCoord(center.toVec().multiply(1.0 / intersects.size()).toPos().getCoord());

			Position3D thisReal = this.pov.getRealPoint(center, this.facePlane);
			Position3D otherReal = other.pov.getRealPoint(center, other.facePlane);

			if (thisReal == null || otherReal == null) {
				if (this.comps.containsKey(other)) {
					return this.comps.get(other);
				}
			}

			Line3D ray = new Line3D(this.pov.getPos(), thisReal);
			double thisDis = this.pov.getPos().totDistanceFrom(this.facePlane.getIntersect(ray));
			double otherDis = other.pov.getPos().totDistanceFrom(other.facePlane.getIntersect(ray));

			if (Math.abs(thisDis - otherDis) < Coord3D.ERROR) {

				return 0;
			} else if (thisDis > otherDis) {

				return -1;
			} else {

				return 1;
			}
		}

		intersects.clear();
		for (Position2D vertex : other.viewPoints) {
			if (this.inShape(vertex)) {
				intersects.add(vertex);
			}
		}

		if (intersects.size() == other.viewPoints.length) {
			Position2D center = new Position2D();

			for (int i = 0; i < intersects.size(); i++) {
				double[] coords = center.getCoord();
				for (int j = 0; j < 2; j++) {
					coords[j] += intersects.get(i).getCoord()[j];
				}
				center.setCoord(coords);
			}

			center.setCoord(center.toVec().multiply(1.0 / intersects.size()).toPos().getCoord());

			Position3D thisReal = this.pov.getRealPoint(center, this.facePlane);
			Position3D otherReal = other.pov.getRealPoint(center, other.facePlane);

			if (thisReal == null || otherReal == null) {
				if (this.comps.containsKey(other)) {
					return this.comps.get(other);
				}
			}
			double thisDis = this.pov.getPos().totDistanceFrom(thisReal);
			double otherDis = other.pov.getPos().totDistanceFrom(otherReal);

			if (Math.abs(thisDis - otherDis) < Coord3D.ERROR) {
				return 0;
			} else if (thisDis > otherDis) {
				return -1;
			} else {
				return 1;
			}
		}

		return 2;
	}

	/**
	 * 
	 * @param point Point to consider
	 * @return Returns true if point is in 2D shape or on shape border
	 */
	public boolean inShape(Position2D point) {
		if (point.totDistanceFrom(this.center2D) > this.bound2D) {
			return false;
		}
		if (point.equals(this.center2D)) {
			return true;
		} else {

			Line2D edgeTest;
			for (int i = 0; i < this.edges2D.length - 1; i++) {
				edgeTest = new Line2D(this.viewPoints[i], point);
				if (edgeTest.isParallel(this.edges2D[i])) {
					if (this.inBounds(point, this.viewPoints[i], this.viewPoints[i + 1])) {
						return true;
					}
				}

			}
			edgeTest = new Line2D(this.viewPoints[this.numPoints - 1], point);
			if (edgeTest.isParallel(this.edges2D[this.numPoints - 1])) {
				if (this.inBounds(point, this.viewPoints[this.numPoints - 1], this.viewPoints[0])) {
					return true;
				}
			}

			Line2D ray = new Line2D(point, this.center2D);
			Position2D outBound = ray.getLinePoint(this.bound2D + point.totDistanceFrom(this.center2D));

			int cn = 0;
			for (int i = 0; i < this.edges2D.length - 2; i++) {
				Position2D poi = ray.intersects(this.edges2D[i]);
				if (poi != null && (this.inBounds(poi, point, outBound) || this.inBounds(poi, outBound, point))) {
					if (this.inBounds(poi, this.viewPoints[i], this.viewPoints[i + 1])) {
						if (poi.equals(this.viewPoints[i + 1])) {
							if (poi.yDistancefrom(this.viewPoints[i]) > 0
									&& poi.yDistancefrom(this.viewPoints[i + 2]) > 0) {
								cn++;
							} else if (poi.yDistancefrom(this.viewPoints[i]) < 0
									&& poi.yDistancefrom(this.viewPoints[i + 2]) < 0) {
								cn++;
							}

						} else {
							cn++;
						}
					}
				}

			}

			Position2D poi = ray.intersects(this.edges2D[this.numPoints - 2]);
			if (poi != null && (this.inBounds(poi, point, outBound) || this.inBounds(poi, outBound, point))) {
				if (this.inBounds(poi, this.viewPoints[this.numPoints - 2], this.viewPoints[this.numPoints - 1])) {
					if (poi.equals(this.viewPoints[this.numPoints - 1])) {
						if (poi.yDistancefrom(this.viewPoints[this.numPoints - 2]) > 0
								&& poi.yDistancefrom(this.viewPoints[0]) > 0) {
							cn++;
						} else if (poi.yDistancefrom(this.viewPoints[this.numPoints - 2]) < 0
								&& poi.yDistancefrom(this.viewPoints[0]) < 0) {
							cn++;
						}

					} else {
						cn++;
					}
				}
			}

			poi = ray.intersects(this.edges2D[this.numPoints - 1]);
			if (poi != null && (this.inBounds(poi, point, outBound) || this.inBounds(poi, outBound, point))) {
				if (this.inBounds(poi, this.viewPoints[this.numPoints - 1], this.viewPoints[0])) {
					if (poi.equals(this.viewPoints[0])) {
						if (poi.yDistancefrom(this.viewPoints[this.numPoints - 1]) > 0
								&& poi.yDistancefrom(this.viewPoints[1]) > 0) {
							cn++;
						} else if (poi.yDistancefrom(this.viewPoints[this.numPoints - 1]) < 0
								&& poi.yDistancefrom(this.viewPoints[1]) < 0) {
							cn++;
						}

					} else {
						cn++;
					}
				}
			}

			if (cn % 2 == 0) {
				return false;
			} else {
				return true;
			}
		}
	}

	/**
	 * Rotates a Face around an inputted point
	 * 
	 * @param ang   Angle in radians to rotate counter clockwise
	 * @param axis  Axis of rotation
	 * @param pivot Pivot point
	 */
	public void orbit(double ang, Vector3D axis, Position3D pivot) {
		if (ang == 0) {
			return;
		}
		Vector3D dir = pivot.getDirection(this.center3D);

		this.rotate(ang, axis);
		dir.rotate(ang, axis);

		this.setCoords(pivot.add(dir).getCoord());
	}

	public double getBound2DSQ() {
		return bound2DSQ;
	}

	public void setBound2DSQ(double bound2dsq) {
		bound2DSQ = bound2dsq;
	}

	public double getBound3DSQ() {
		return bound3DSQ;
	}

	public void setBound3DSQ(double bound3dsq) {
		bound3DSQ = bound3dsq;
	}

	public double[][] getzBuf() {
		return zBuf;
	}

	public void setzBuf(double[][] zBuf) {
		this.zBuf = zBuf;
	}
	
	public static ArrayList<Face> sort(ArrayList<Face> faces, int lo, int hi) {
		ArrayList<Face> ans = new ArrayList<Face>();
		if(faces.size() < 1 || lo > hi) {
			return ans;
		}
		if(faces.size() == 1 || lo == hi) {
			ans.add(faces.get(0));
			return ans;
		}
		
		if(faces.size() == 2 || lo-hi == 1) {
			
			if(faces.get(0).compareTo(faces.get(1)) > 0) {
				
				ans.add(faces.get(1));
				ans.add(faces.get(0));
			}else {
				ans.add(faces.get(0));
				ans.add(faces.get(1));
			}
			return ans;
		}
		int mid = (hi+lo)/2;
		ArrayList<Face> left = sort(faces, lo, mid);
		ArrayList<Face> right = sort(faces, mid+1, hi);
		
		int i = 0;
		int l = 0;
		int r = 0;
		while(i < faces.size()) {
			if(l < left.size() && r < right.size()) {
				if(left.get(l).compareTo(right.get(r)) <= 0) {
					ans.add(left.get(l));
					l++;
				} else {
					ans.add(right.get(r));
					r++;
				}
			} else if(l >= left.size()  && r < right.size()) {
				ans.add(right.get(r));
				r++;
			} else if(r >= right.size() && l < left.size()) {
				ans.add(left.get(l));
				l++;
			}
			
			i++;
		}
		
		return ans;
		
	}
	
}
