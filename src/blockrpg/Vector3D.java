package blockrpg;

public class Vector3D extends Coord3D {

	private double length;

	/**
	 * Default Vector3D Constructor. Creates a Vector3D of (0,0,0) of length 0
	 */
	public Vector3D() {
		super();
		this.length = 0;
	}

	/**
	 * Custom Vector3D Constructor. Creates a Vector3D of (x,y,z) using separate
	 * values with calculated length
	 * 
	 * @param x x-Coordinate
	 * @param y y-Coordinate
	 * @param z z-Coordinate
	 */
	public Vector3D(double x, double y, double z) {
		super(x, y, z);
		length = x * x + y * y + z * z;
	}

	/**
	 * Custom Vector3D Constructor. Creates a Vector3D of (x,y,z) using array with
	 * calculated length
	 * 
	 * @param coords double array containing x, y, z information in that order
	 * 
	 */
	public Vector3D(double[] coords) {
		super(coords);
		length = x * x + y * y + z * z;
	}

	/**
	 * Copys another Vector3D
	 * 
	 * @param other Other Vector3D to copy
	 */
	public Vector3D(Vector3D other) {
		super(other);
		this.length = other.length;
	}

	/**
	 * Normalizes vector to length of 1
	 * 
	 * @return normalized vector as Vector3D
	 */
	public Vector3D normalize() {
		Vector3D normalized = new Vector3D(this.getCoord());
		double oldLen = this.getLength();
		if (Math.abs(normalized.length) < ERROR) {
			return this;
		}
		// Don't normalize if length is already close to 1 or length is 0
		if (Math.abs(normalized.length - 1.0) > ERROR && Math.abs(normalized.length) > ERROR) {
			normalized.setX(this.x / oldLen);
			normalized.setY(this.y / oldLen);
			normalized.setZ(this.z / oldLen);
		}
		normalized.length = 1.0;

		return normalized;

	}

	@Override
	public void setCoord(double coords[]) {
		super.setCoord(coords);
		this.length = x * x + y * y + z * z;
	}

	/**
	 * 
	 * @return length of Vector3D as double
	 */
	public double getLength() {
		return Double.parseDouble(df.format(Math.sqrt(length)));
	}

	@Override
	public Vector3D add(Coord3D other) {
		Vector3D sum = new Vector3D(super.add(other).getCoord());
		return sum;
	}

	@Override
	public Vector3D subtract(Coord3D other) {
		Vector3D diff = new Vector3D(super.subtract(other).getCoord());
		return diff;
	}

	/**
	 * 
	 * @param scalar multiplies vector by double scalar
	 * 
	 * @return returns vector multiplied by scalar as Vector3D
	 */
	public Vector3D multiply(double scalar) {
		Vector3D product = new Vector3D();
		product.setX(this.x * scalar);
		product.setY(this.y * scalar);
		product.setZ(this.z * scalar);
		this.length = this.length * scalar * scalar;

		return product;
	}

	/**
	 * 
	 * @param other other Vector3D to dot with
	 * @return returns dot product as double
	 */
	public double dot(Vector3D other) {
		double product = this.x * other.x + this.y * other.y + this.z * other.z;
		if (Math.abs(product - 0.0) < ERROR) {
			product = 0.0;
		}

		return product;
	}

	/**
	 * 
	 * @param other other Vector3D to cross with
	 * @return returns cross product as Vector3D
	 */
	public Vector3D cross(Vector3D other) {
		Vector3D product = new Vector3D(this.y * other.z - this.z * other.y + 0.0,
				this.z * other.x - this.x * other.z + 0.0, this.x * other.y - this.y * other.x + 0.0);
		return product;
	}

	/**
	 * 
	 * @param other other vector for projection
	 * @return returns projection of other vector on this vector as Vector3D
	 */
	public Vector3D proj(Vector3D other) {
		double dotProd = this.dot(other);
		double projVal;
		if (Math.abs(dotProd - 0.0) < ERROR) {
			projVal = 0.0;
		} else {
			projVal = dotProd / (x * x + y * y + z * z) + 0.0;
		}

		return this.multiply(projVal);

	}

	/**
	 * 
	 * @param other other vector for rejection
	 * @return returns rejection of other vector on this vector as Vector3D
	 */
	public Vector3D perp(Vector3D other) {
		Vector3D rejection = this.proj(other);
		double coords[] = { other.x - rejection.x, other.y - rejection.y, other.z - rejection.z };
		rejection.setCoord(coords);

		return rejection;
	}

	/**
	 * 
	 * @param other Other Vector3D to compare
	 * @return Returns smallest angle between two Vector2Ds (between 0 and pi
	 *         radians)
	 */
	public double getAng(Vector3D other) {
		double cosVal = this.dot(other) / this.getLength() / other.getLength();
		return Math.acos(cosVal);

	}

	/**
	 * 
	 * @return Returns position with same coordinates
	 */
	public Position3D toPos() {
		return new Position3D(this.getCoord());
	}

	/**
	 * @return Returns copy of this object
	 */
	@Override
	public Vector3D clone() {
		Vector3D clone = new Vector3D(super.clone().getCoord());
		return clone;
	}

	/**
	 * 
	 * @param other other vector to check
	 * @return Returns true if parallel to other vector
	 */
	public boolean isParallel(Vector3D other) {
		if (!this.isOrigin() && !other.isOrigin()) {
			Vector3D origin = new Vector3D();
			return this.cross(other).equals(origin);
		}

		return false;
	}

	/**
	 * 
	 * @param other Other vector that is multiple of this vector
	 * @return
	 */
	public double getMultiple(Vector3D other) {
		if (other.isOrigin()) {
			return 0;
		}
		if (this.isParallel(other)) {
			double thisCoords[] = this.getCoord();
			double otherCoords[] = other.getCoord();
			for (int i = 0; i < 3; i++) {
				if (thisCoords[i] != 0) {
					return otherCoords[i] / thisCoords[i];
				}
			}
		} else {
			throw new IllegalArgumentException();
		}
		return 0;
	}

	@Override
	public String toString() {
		return "[ " + this.x + ", " + this.y + ", " + this.z + " ]\n" + this.getLength();
	}
}
