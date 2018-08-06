package blockrpg;

import java.awt.Color;

public class Prism {
	private Face[] faces;
	private Perspective pov;

	private Position3D center;

	@SuppressWarnings("unused")
	/**
	 * Default Prism of empty faces
	 */
	public Prism() {
		center = new Position3D();
		faces = new Face[4];
		for (Face face : faces) {
			face = new Face();
		}

		setPov(new Perspective());
		set(new Position3D());
	}
	
	/**
	 * Creates a custom prism with an inputted face as the sides
	 * @param length Length from face to face
	 * @param center Center of prism
	 * @param pov Perspective prism is seen from
	 * @param sideFace Face inputted
	 */
	public Prism(double length, Position3D center, Perspective pov, Face sideFace) {
		this.center = center.clone();
		faces = new Face[2 + sideFace.getNumPoints()];
		Position2D[] points = new Position2D[sideFace.getNumPoints()];
		for (int i = 0; i < sideFace.getNumPoints(); i++) {
			points[i] = new Position2D(sideFace.getRelPoints()[i].getCoord());
		}
		Plane plane = new Plane(new Vector3D(1, 0, 0), new Vector3D(0, 1, 0), new Position3D());

		plane.setPos(center.add(plane.getNorm().multiply(length / 2).toPos()).getCoord());

		faces[0] = new Face(points, points.length, plane, pov);
		
		plane.setPos(center.subtract(plane.getNorm().multiply(length / 2).toPos()).getCoord());

		faces[1] = new Face(points, points.length, plane, pov);
		for (int i = 2; i < faces.length - 1; i++) {
			points = new Position2D[4];
			Position3D[] truePoints = new Position3D[4];

			truePoints[0] = faces[0].getTruePoints()[i-2];
			truePoints[1] = faces[0].getTruePoints()[i -1];
			truePoints[2] = faces[1].getTruePoints()[i-1];
			truePoints[3] = faces[1].getTruePoints()[i -2];

			Position3D centerPos = new Position3D();
			for (Position3D point : truePoints) {
				centerPos = centerPos.add(point);
			}
			centerPos = centerPos.toVec().multiply(0.25).toPos();
			plane = new Plane(truePoints[0].getDirection(truePoints[3]), truePoints[0].getDirection(truePoints[1]),
					centerPos);

			for (int j = 0; j < 4; j++) {
				points[j] = plane.get2DPoint(truePoints[j]);
			}

			faces[i] = new Face(points, points.length, plane, pov);

		}

		points = new Position2D[4];
		Position3D[] truePoints = new Position3D[4];

		truePoints[0] = faces[0].getTruePoints()[faces.length - 3];
		truePoints[1] = faces[0].getTruePoints()[0];
		truePoints[3] = faces[1].getTruePoints()[faces.length - 3];
		truePoints[2] = faces[1].getTruePoints()[0];

		Position3D centerPos = new Position3D();
		for (Position3D point : truePoints) {
			centerPos = centerPos.add(point);
		}
		centerPos = centerPos.toVec().multiply(0.25).toPos();
		plane = new Plane(truePoints[0].getDirection(truePoints[3]), truePoints[0].getDirection(truePoints[1]),
				centerPos);

		for (int j = 0; j < 4; j++) {
			points[j] = plane.get2DPoint(truePoints[j]);
		}

		faces[faces.length - 1] = new Face(points, points.length, plane, pov);

		this.setPov(pov);
		this.set(center.clone());
	}

	/**
	 * 
	 * @return Returns center of prism
	 */
	public Position3D getCenter() {
		return center;
	}

	/**
	 * Sets prism to have center at pos
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
	 * Sets prism to have center at inputted x
	 * @param x value to change x coordinate to
	 */
	public void setX(double x) {
		Position3D pos = this.center.clone();
		pos.setX(x);
		this.set(pos);
	}
	
	/**
	 * Sets prism to have center at inputted y
	 * @param y value to change y coordinate to
	 */
	public void setY(double y) {
		Position3D pos = this.center.clone();
		pos.setY(y);
		this.set(pos);
	}
	
	/**
	 * Sets prism to have center at inputted z
	 * @param z value to change z coordinate to
	 */
	public void setZ(double z) {
		Position3D pos = this.center.clone();
		pos.setZ(z);
		this.set(pos);
	}
	
	/**
	 * Adds to x prism center
	 * @param x value to add to x coordinate
	 */
	public void addX(double x) {
		Position3D pos = this.center.clone();
		pos.addX(x);
		this.set(pos);
	}
	
	/**
	 * Adds to y prism center
	 * @param y value to add to y coordinate
	 */
	public void addY(double y) {
		Position3D pos = this.center.clone();
		pos.addY(y);
		this.set(pos);
	}
	
	/**
	 * Adds to z prism center
	 * @param z value to add to z coordinate
	 */
	public void addZ(double z) {
		Position3D pos = this.center.clone();
		pos.setZ(z);
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
	 * @param pov Perspective to set to
	 */
	public void setPov(Perspective pov) {
		this.pov = pov;
		
		for (Face face :faces) {
			face.setPOV(pov);
		}
	}
	
	/**
	 * Sets Colour of the whole prism
	 * @param col Colour to set it to
	 */
	public void setCol(Color col) {
		for (Face face : faces) {
			face.setCol(col);
		}
	}

	/**
	 * Sets Colour of face at specific index
	 * @param col Colour to set it to
	 * @param index Index of face
	 */
	public void setCol(Color col, int index) {
		if(index >= faces.length) {
			throw new IndexOutOfBoundsException();
		}
		
		faces[index].setCol(col);
	}
	
	/**
	 * 
	 * @return Returns array of faces
	 */
	public Face[] getFaces() {
		return this.faces;
	}
}
