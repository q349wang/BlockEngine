package blockrpg;

public class Position2D extends Coord2D {

	/**
	 * Default Position2D Constructor. Creates a Position2D of (0,0)
	 */
	public Position2D() {
		super();
	}

	/**
	 * Custom Position2D Constructor. Creates a Position2D at (x,y)
	 * 
	 * @param x
	 *            x-Coordinate
	 * @param y
	 *            y-Coordinate
	 */
	public Position2D(double x, double y) {
		super(x, y);
	}

	/**
	 * Custom Position2D Constructor. Creates a Position2D at (x,y)
	 * 
	 * @param coords
	 *            double array containing x, y information in that order
	 * 
	 */
	public Position2D(double[] coords) {
		super(coords);
	}

	/**
	 * 
	 * @param other
	 *            Another position
	 * @return the x distance from this to the other position as a double
	 */
	public double xDistancefrom(Position2D other) {
		return other.x - this.x;
	}

	/**
	 * 
	 * @param other
	 *            Another position
	 * @return the y distance from this to the other position as a double
	 */
	public double yDistancefrom(Position2D other) {
		return other.y - this.y;
	}

	/**
	 * 
	 * @param other
	 *            Another position
	 * @return the total distance from this to the other position as a double
	 */
	public double totDistanceFrom(Position2D other) {

		double deltaX = xDistancefrom(other);
		double deltaY = yDistancefrom(other);
		return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
	}

	/**
	 * 
	 * @param other
	 *            Another position
	 * @return the direction from this to the other position as a Vector2D
	 */
	public Vector2D getDirection(Position2D other) {
		Vector2D direction = new Vector2D(xDistancefrom(other), yDistancefrom(other));
		return direction;
	}

}