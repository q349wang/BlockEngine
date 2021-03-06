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
	 * Copys another Position3D
	 * @param other Other Position3D to copy
	 */
	public Position3D(Position3D other) {
		super(other);
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
	 * @return the total distance squared from this to the other position as a double
	 */
	public double totDistanceFromSQ(Position3D other) {

		double deltaX = xDistancefrom(other);
		double deltaY = yDistancefrom(other);
		double deltaZ = zDistancefrom(other);
		return deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
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
	
	@Override
	public Position3D add(Coord3D other) {
		Position3D sum = new Position3D(super.add(other).getCoord());
		return sum;
	}

	@Override
	public Position3D subtract(Coord3D other) {
		Position3D diff = new Position3D(super.subtract(other).getCoord());
		return diff;
	}
	
	/**
	 * 
	 * @return Returns vector with same coordinates
	 */
	public Vector3D toVec() {
		return new Vector3D(this.getCoord());
	}
	
	
	/**
	 * @return Returns copy of this object
	 */
	@Override
	public Position3D clone() {
		Position3D clone = new Position3D(super.clone().getCoord());
		return clone;
	}
	
}
