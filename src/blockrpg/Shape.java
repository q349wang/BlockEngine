package blockrpg;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.ArrayList;

public class Shape {
	protected ArrayList<Face> faces;
	protected Perspective pov;

	protected Position3D center;
	
	protected static final Stroke THIN = new BasicStroke(1);
	protected static final Stroke THICK = new BasicStroke((float) 1.5);
	
	public Shape(ArrayList<Face> faces, Perspective pov, Position3D center) {
		this.faces = faces;
		this.pov = pov;
		this.center = center.clone();
	}

	/**
	 * 
	 * @return Returns center of shape
	 */
	public Position3D getCenter() {
		return center;
	}

	/**
	 * Sets prism to have center at pos
	 * 
	 * @param pos Position3D to set center to
	 */
	public void set(Position3D pos) {

		double xDiff = this.center.xDistancefrom(pos);
		double yDiff = this.center.yDistancefrom(pos);
		double zDiff = this.center.zDistancefrom(pos);

		for (Face face : faces) {
			face.addX(xDiff);
			face.addY(yDiff);
			face.addZ(zDiff);
		}

		this.center = pos.clone();
	}

	/**
	 * Sets shape to have center at inputed x
	 * 
	 * @param x value to change x coordinate to
	 */
	public void setX(double x) {
		Position3D pos = this.center.clone();
		pos.setX(x);
		this.set(pos);
	}

	/**
	 * Sets shape to have center at inputted y
	 * 
	 * @param y value to change y coordinate to
	 */
	public void setY(double y) {
		Position3D pos = this.center.clone();
		pos.setY(y);
		this.set(pos);
	}

	/**
	 * Sets shape to have center at inputted z
	 * 
	 * @param z value to change z coordinate to
	 */
	public void setZ(double z) {
		Position3D pos = this.center.clone();
		pos.setZ(z);
		this.set(pos);
	}

	/**
	 * Adds to x shape center
	 * 
	 * @param x value to add to x coordinate
	 */
	public void addX(double x) {
		Position3D pos = this.center.clone();
		pos.addX(x);
		this.set(pos);
	}

	/**
	 * Adds to y shape center
	 * 
	 * @param y value to add to y coordinate
	 */
	public void addY(double y) {
		Position3D pos = this.center.clone();
		pos.addY(y);
		this.set(pos);
	}

	/**
	 * Adds to z shape center
	 * 
	 * @param z value to add to z coordinate
	 */
	public void addZ(double z) {
		Position3D pos = this.center.clone();
		pos.addZ(z);
		this.set(pos);
	}

	/**
	 * 
	 * @return Returns current perspective
	 */
	public Perspective getPov() {
		return pov;
	}

	/**
	 * Sets perspective
	 * 
	 * @param pov Perspective to set to
	 */
	public void setPov(Perspective pov) {
		this.pov = pov;

		for (Face face : faces) {
			face.setPOV(pov);
		}
	}

	/**
	 * Sets Colour of the whole shape
	 * 
	 * @param col Colour to set it to
	 */
	public void setCol(Color col) {
		for (Face face : faces) {
			face.setCol(col);
		}
	}

	/**
	 * Sets Colour of face at specific index
	 * 
	 * @param col   Colour to set it to
	 * @param index Index of face
	 */
	public void setCol(Color col, int index) {
		if (index >= faces.size()) {
			throw new IndexOutOfBoundsException();
		}

		faces.get(index).setCol(col);
	}

	/**
	 * Rotates Prism about inputed axis
	 * 
	 * @param ang  Angle in radians to rotate counter clockwise
	 * @param axis Axis of rotation
	 */
	public void rotate(double ang, Vector3D axis) {
		for (int i = 0; i < faces.size(); i++) {
			faces.get(i).orbit(ang, axis, this.center);
		}
	}

	/**
	 * 
	 * @return Returns array of faces
	 */
	public ArrayList<Face> getFaces() {
		return this.faces;
	}
	
	/**
	 * Draws shape on g2
	 * @param g2
	 */
	public void draw(Graphics2D g2) {
		for (int i = 0; i < faces.size(); i++) {
		if (faces.get(i).isVisible()) {
			g2.setColor(faces.get(i).getCol());
			if (MainWindow.WIRE) {
				g2.setStroke(THIN);
				g2.drawPolygon(faces.get(i).getPoly());
			} else {
				// g2.setStroke(THICK);
				// g2.drawPolygon(sortedFace.get(i).getPoly());
				g2.fillPolygon(faces.get(i).getPoly());

			}

			if (MainWindow.SHOWCENT) {
				Position2D center = faces.get(i).getPOV().getViewPoint(faces.get(i).getCenter3D());
				g2.setColor(Color.BLACK);
				g2.fillArc((int) (center.getX() + Face.xOffset - 5), (int) (-center.getY() + Face.yOffset - 5), 10,
						10, 0, 360);
			}

		}

		if (MainWindow.DEBUG) {
			g2.setColor(faces.get(i).getCol());
			g2.drawString(Boolean.toString(faces.get(i).isVisible()) + " " + Double.toString(faces.get(i).getPlane().getNorm().dot(faces.get(i).getPOV().getPos().getDirection(faces.get(i).getCenter3D()))), 100, 100 + 10 * i);

		}
	}
	}
}
