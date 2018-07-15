package blockrpg;

public class Line2D {
	private Position2D pos;
	private Vector2D dir;

	/**
	 * Default constructor for line
	 */
	public Line2D() {
		pos = new Position2D();
		dir = new Vector2D();
	}

	/**
	 * Custom constructor for line with coords for direction and position
	 * 
	 * @param dir Direction Vector2D
	 * @param pos Position Position2D
	 */
	public Line2D(Vector2D dir, Position2D pos) {
		this.dir = new Vector2D(dir.getCoord());
		dir = dir.normalize();
		this.pos = new Position2D(pos.getCoord());
	}

	/**
	 * Copies Line2DTests
	 * 
	 * @param other Other line to copy
	 */
	public Line2D(Line2D other) {
		this.pos = other.pos.clone();
		this.dir = other.dir.clone();
	}

	/**
	 * Creates a new line using two points
	 * 
	 * @param pos1 First point (set as pos)
	 * @param pos2 Second point (dir is found using pos1.getDirection(pos2))
	 */
	public Line2D(Position2D pos1, Position2D pos2) {
		this.pos = pos1.clone();
		this.dir = pos1.getDirection(pos2).normalize();
	}

	@Override
	public Line2D clone() {
		return new Line2D(this);
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
	public Vector2D getDir() {
		return dir;
	}

	/**
	 * 
	 * @return Returns position
	 */
	public Position2D getPos() {
		return pos;
	}

	/**
	 * 
	 * @param t Scalar multiple
	 * @return Returns point on line at t
	 */
	public Position2D getLinePoint(double t) {
		return pos.add(dir.multiply(t));
	}

	/**
	 * 
	 * @param ang  Angle to rotate line direction
	 * @param axis Axis to rotate line direction
	 * @return Returns rotated line
	 */
	public Line2D rotateDir(double ang) {
		Line2D rotatedDir = new Line2D(this.dir, this.pos);
		rotatedDir.setDir(dir.rotate(ang).getCoord());
		return rotatedDir;
	}

	/**
	 * 
	 * @param ang  Angle to rotate line position
	 * @param axis Axis to rotate line position
	 * @return Returns rotated line
	 */
	public Line2D rotatePos(double ang) {
		Line2D rotatedDir = new Line2D(this.dir, this.pos);
		rotatedDir.setDir(dir.rotate(ang).getCoord());
		rotatedDir.pos = pos.rotate(ang);
		return rotatedDir;
	}

	/**
	 * 
	 * @param other Other line to check
	 * @return Returns true if lines are parallel
	 */
	public boolean isParallel(Line2D other) {
		return this.dir.isParallel(other.dir);

	}

	/**
	 * 
	 * @param other Other line to check
	 * @return Returns null if no intersection and Position3D if there is one
	 */
	public Position2D intersects(Line2D other) {

		if (this.isParallel(other)) {
			return null;
		}
		Vector2D origin = new Vector2D(0, 0);
		if (this.getDir().equals(origin) || other.getDir().equals(origin)) {
			return null;
		}
		double a[] = this.getPos().getCoord();
		double b[] = other.getPos().getCoord();
		double v[] = this.getDir().getCoord();
		double u[] = other.getDir().getCoord();
		double multiple = 0;
		if (Math.abs(v[0]) < Coord2D.ERROR) {
			multiple = (a[0] - b[0] + v[0] / v[1] * (b[1] - a[1])) / (u[0] - v[0] / v[1] * u[1]);
		} else {
			multiple = (a[1] - b[1] + v[1] / v[0] * (b[0] - a[0])) / (u[1] - v[1] / v[0] * u[0]);
		}
		
		return other.getLinePoint(multiple);

	}

	// Overriding equals() to compare two Line2DTests objects
	@Override
	public boolean equals(Object other) {

		if (other == this) {
			return true;
		}

		if (!(other instanceof Line2D)) {
			return false;
		}

		Line2D line2D = (Line2D) other;

		return this.dir.equals(line2D.dir) && this.pos.equals(line2D.pos);

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

		if (!(other instanceof Line2D)) {
			return false;
		}

		Line2D line2D = (Line2D) other;

		return this.dir.isParallel(line2D.dir) && this.onLine(line2D.pos);
	}

	/**
	 * Checks if Position3D is on the Line2D
	 * 
	 * @param pos Position3D to check
	 * @return Returns true if on Line2D
	 */
	public boolean onLine(Position2D pos) {
		return pos.subtract(this.pos).toVec().isParallel(this.dir);
	}

}
