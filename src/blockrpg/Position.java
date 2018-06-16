package blockrpg;

public class Position extends Coord {

	/**
	 * Default Position Constructor. Creates a Position of (0,0,0)
	 */
	public Position() {
		super();
	}

	/**
	 * Custom Position Constructor. Creates a Position at (x,y,z)
	 * 
	 * @param x
	 *            x-Coordinate
	 * @param y
	 *            y-Coordinate
	 * @param z
	 *            z-Coordinate
	 */
	public Position(double x, double y, double z) {
		super(x, y, z);
	}

	/**
	 * Custom Position Constructor. Creates a Position at (x,y,z)
	 * 
	 * @param coords
	 *            double array containing x, y, z information in that order
	 * 
	 */
	public Position(double[] coords) {
		super(coords);
	}

	/**
	 * 
	 * @param other
	 *            Another position
	 * @return the x distance from this to the other position as a double
	 */
	public double xDistancefrom(Position other) {
		return other.x - this.x;
	}

	/**
	 * 
	 * @param other
	 *            Another position
	 * @return the y distance from this to the other position as a double
	 */
	public double yDistancefrom(Position other) {
		return other.y - this.y;
	}

	/**
	 * 
	 * @param other
	 *            Another position
	 * @return the z distance from this to the other position as a double
	 */
	public double zDistancefrom(Position other) {
		return other.z - this.z;
	}

	/**
	 * 
	 * @param other
	 *            Another position
	 * @return the total distance from this to the other position as a double
	 */
	public double totDistanceFrom(Position other) {

		double deltaX = xDistancefrom(other);
		double deltaY = yDistancefrom(other);
		double deltaZ = zDistancefrom(other);
		return Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
	}

	/**
	 * 
	 * @param other
	 *            Another position
	 * @return the direction from this to the other position as a Vector
	 */
	public Vector getDirection(Position other) {
		Vector direction = new Vector(xDistancefrom(other), yDistancefrom(other), zDistancefrom(other));
		return direction;
	}
}
