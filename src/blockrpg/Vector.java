package blockrpg;

class Vector extends Coord {

	private double length;

	/**
	 * Default Vector Constructor. Creates a Vector of (0,0,0) of length 0
	 */
	public Vector() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.length = 0;
	}

	/**
	 * Custom Vector Constructor. Creates a Vector of (x,y,z) using separate values
	 * with calculated length
	 * 
	 * @param x
	 *            x-Coordinate
	 * @param y
	 *            y-Coordinate
	 * @param z
	 *            z-Coordinate
	 */
	public Vector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		length = Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Custom Vector Constructor. Creates a Vector of (x,y,z) using array with
	 * calculated length
	 * 
	 * @param coords
	 *            double array containing x, y, z information in that order
	 * 
	 */
	public Vector(double[] coords) {
		this.x = coords[0];
		this.y = coords[1];
		this.z = coords[2];
		length = Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Normalizes vector to length of 1
	 * 
	 * @return normalized vector as Vector
	 */
	public Vector normalize() {
		Vector normalized = new Vector();
		normalized.length = Math.sqrt(x * x + y * y + z * z);

		// Don't normalize if length is already close to 1 or length is 0 tiny changes
		if (Math.abs(normalized.length - 1.0) > ERROR && Math.abs(normalized.length - 0.0) > ERROR) {
			normalized.x = this.x / normalized.length;
			normalized.y = this.x / normalized.length;
			normalized.z = this.x / normalized.length;
		}
		normalized.length = 1.0;

		return normalized;

	}

	@Override
	public void setCoord(double arr[]) {
		this.x = arr[0];
		this.y = arr[1];
		this.y = arr[2];
		length = Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * 
	 * @return length of Vector as double
	 */
	public double getLength() {
		return length;
	}

	/**
	 * 
	 * @param scalar
	 *            multiplies vector by double scalar
	 * 
	 * @return returns vector multiplied by scalar as Vector
	 */
	public Vector multiply(double scalar) {
		Vector product = new Vector();
		product.x = this.x * scalar;
		product.y = this.y * scalar;
		product.z = this.z * scalar;
		product.length = this.length * scalar;

		return product;
	}

	/**
	 * 
	 * @param other
	 *            other Vector to dot with
	 * @return returns dot product as double
	 */
	public double dot(Vector other) {
		return this.x * other.x + this.y * other.y + this.z * other.z;
	}

	/**
	 * 
	 * @param other
	 *            other Vector to cross with
	 * @return returns cross product as Vector
	 */
	public Vector cross(Vector other) {
		Vector product = new Vector(this.y * other.z - this.z * other.y, this.z * other.x - this.x * other.z,
				this.x * other.y - this.y * other.x);
		return product;
	}

	/**
	 * 
	 * @param other other vector for projection
	 * @return returns projection of other vector on this vector as Vector
	 */
	public Vector proj(Vector other) {
		double projVal = this.normalize().dot(other);
		return this.multiply(projVal);

	}
	
	/**
	 * 
	 * @param other other vector for rejection
	 * @return returns rejection of other vector on this vector as Vector
	 */
	public Vector perp(Vector other) {
		Vector rejection = this.proj(other);
		double coords[] = {this.x-rejection.x, this.y-rejection.y, this.y-rejection.y};
		rejection.setCoord(coords);
		
		return rejection;
	}
}
