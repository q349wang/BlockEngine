package blockrpg;

public class Line3D {
	private Position3D pos;
	private Vector3D dir;

	/**
	 * Default constructor for line
	 */
	public Line3D() {
		pos = new Position3D();
		dir = new Vector3D();
	}

	/**
	 * Custom constructor for line with coords for direction and position
	 * 
	 * @param dir Direction Vector3D
	 * @param pos Position Position3D
	 */
	public Line3D(Vector3D dir, Position3D pos) {
		this.dir = new Vector3D(dir.getCoord());
		dir = dir.normalize();
		this.pos = new Position3D(pos.getCoord());
	}

	/**
	 * Copies Line3D
	 * 
	 * @param other Other line to copy
	 */
	public Line3D(Line3D other) {
		this.pos = other.pos.clone();
		this.dir = other.dir.clone();
	}

	/**
	 * Creates a new line using two points
	 * 
	 * @param pos1 First point (set as pos)
	 * @param pos2 Second point (dir is found using pos1.getDirection(pos2))
	 */
	public Line3D(Position3D pos1, Position3D pos2) {
		this.pos = pos1.clone();
		this.dir = pos1.getDirection(pos2).normalize();
	}

	@Override
	public Line3D clone() {
		return new Line3D(this);
	}

	/**
	 * Sets line using two points
	 * 
	 * @param pos1 First point (set as pos)
	 * @param pos2 Second point (dir is found using pos1.getDirection(pos2))
	 */
	public void setLineToPoints(Position3D pos1, Position3D pos2) {
		this.pos = pos1.clone();
		this.dir = pos1.getDirection(pos2).normalize();
	}

	/**
	 * 
	 * @param dirCoords Sets direction vector to inputed coordinates
	 */
	public void setDir(double[] dirCoords) {
		this.dir.setCoord(dirCoords);
		dir = dir.normalize();
	}

	/**
	 * 
	 * @param posCoords Sets position vector to inputed coordinates
	 */
	public void setPos(double[] posCoords) {
		this.pos.setCoord(posCoords);
	}

	/**
	 * 
	 * @return Returns direction
	 */
	public Vector3D getDir() {
		return dir;
	}

	/**
	 * 
	 * @return Returns position
	 */
	public Position3D getPos() {
		return pos;
	}

	/**
	 * 
	 * @param t Scalar multiple
	 * @return Returns point on line at t
	 */
	public Position3D getLinePoint(double t) {
		return pos.add(dir.multiply(t));
	}

	/**
	 * Rotates line ang radians about axis
	 * 
	 * @param ang  Angle to rotate line direction
	 * @param axis Axis to rotate line direction
	 */
	public void rotateDir(double ang, Vector3D axis) {
		this.dir.rotate(ang, axis);
	}

	/**
	 * Rotates line ang radians about axis at the origin
	 * 
	 * @param ang  Angle to rotate line position
	 * @param axis Axis to rotate line position
	 */
	public void rotatePos(double ang, Vector3D axis) {
		this.dir.rotate(ang, axis);
		this.pos.rotate(ang, axis);
	}

	/**
	 * 
	 * @param other Other line to check
	 * @return Returns true if lines are parallel
	 */
	public boolean isParallel(Line3D other) {
		Vector3D origin = new Vector3D();
		return this.dir.cross(other.dir).equals(origin);

	}

	/**
	 * 
	 * @param other Other Plane to compare
	 * @return Returns whether a line and a plane are parallel
	 */
	public boolean isParallel(Plane other) {
		return other.isParallel(this);
	}

	/**
	 * 
	 * @param other Other line to check
	 * @return Returns null if no intersection and Position3D if there is one
	 */
	public Position3D intersects(Line3D other) {

		if (this.isParallel(other)) {
			return null;
		}
		Position3D poi = new Position3D();
		try {
			double multiple = other.dir.cross(this.dir)
					.getMultiple(this.pos.subtract(other.pos).toVec().cross(this.dir));
			poi = other.getLinePoint(multiple);
		} catch (IllegalArgumentException e) {
			return null;
		}

		if (poi.equals(this.getLinePoint(
				this.dir.cross(other.dir).getMultiple(other.pos.subtract(this.pos).toVec().cross(other.dir))))) {
			return poi;
		} else {
			return null;
		}

	}

	// Overriding equals() to compare two Line3D objects
	@Override
	public boolean equals(Object other) {

		if (other == this) {
			return true;
		}

		if (!(other instanceof Line3D)) {
			return false;
		}

		Line3D line3D = (Line3D) other;

		return this.dir.equals(line3D.dir) && this.pos.equals(line3D.pos);

	}

	/**
	 * 
	 * @param other Other line to compare
	 * @return Returns whether two lines objects represent the same line in space
	 */
	public boolean similar(Object other) {
		if (other == this) {
			return true;
		}

		if (!(other instanceof Line3D)) {
			return false;
		}

		Line3D line3D = (Line3D) other;

		return this.dir.isParallel(line3D.dir) && this.onLine(line3D.pos);
	}

	/**
	 * Checks if Position3D is on the Line3D
	 * 
	 * @param pos Position3D to check
	 * @return Returns true if on Line3D
	 */
	public boolean onLine(Position3D pos) {
		return pos.subtract(this.pos).toVec().isParallel(this.dir);
	}

	/**
	 * Finds Position3D that a Line3D intersects a Plane on (returns null if Line3D
	 * is parallel to Plane)
	 * 
	 * @param other
	 * @return Returns Position3D that is on line and plane
	 */
	public Position3D getIntersect(Plane other) {
		return other.getIntersect(this);
	}

	@Override
	public String toString() {
		return "Direction: " + this.dir.toString() + "\nPosition: " + this.pos.toString();
	}
}
