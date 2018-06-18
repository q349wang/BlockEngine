package blockrpg;

public class Position3D extends Coord3D {

	/**
	 * Default Position3D Constructor. Creates a Position3D of (0,0,0)
	 */
	public Position3D() {
		super();
	}

	/**
	 * Custom Position3D Constructor. Creates a Position3D at (x,y,z)
	 * 
	 * @param x
	 *            x-Coordinate
	 * @param y
	 *            y-Coordinate
	 * @param z
	 *            z-Coordinate
	 */
	public Position3D(double x, double y, double z) {
		super(x, y, z);
	}

	/**
	 * Custom Position3D Constructor. Creates a Position3D at (x,y,z)
	 * 
	 * @param coords
	 *            double array containing x, y, z information in that order
	 * 
	 */
	public Position3D(double[] coords) {
		super(coords);
	}

	/**
	 * 
	 * @param other
	 *            Another position
	 * @return the x distance from this to the other position as a double
	 */
	public double xDistancefrom(Position3D other) {
		return other.x - this.x;
	}

	/**
	 * 
	 * @param other
	 *            Another position
	 * @return the y distance from this to the other position as a double
	 */
	public double yDistancefrom(Position3D other) {
		return other.y - this.y;
	}

	/**
	 * 
	 * @param other
	 *            Another position
	 * @return the z distance from this to the other position as a double
	 */
	public double zDistancefrom(Position3D other) {
		return other.z - this.z;
	}

	/**
	 * 
	 * @param other
	 *            Another position
	 * @return the total distance from this to the other position as a double
	 */
	public double totDistanceFrom(Position3D other) {

		double deltaX = xDistancefrom(other);
		double deltaY = yDistancefrom(other);
		double deltaZ = zDistancefrom(other);
		return Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
	}

	/**
	 * 
	 * @param other
	 *            Another position
	 * @return the direction from this to the other position as a Vector3D
	 */
	public Vector3D getDirection(Position3D other) {
		Vector3D direction = new Vector3D(xDistancefrom(other), yDistancefrom(other), zDistancefrom(other));
		return direction;
	}
}
