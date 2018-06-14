package blockrpg;

public class Position extends Coord {
	
	public Position() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	public Position(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
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

	public double totDistanceFrom(Position other) {

		double deltaX = xDistancefrom(other);
		double deltaY = yDistancefrom(other);
		double deltaZ = zDistancefrom(other);
		return Math.sqrt(deltaX*deltaX+ deltaY*deltaY + deltaZ *deltaZ);
	}
}
