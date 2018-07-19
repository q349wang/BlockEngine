package blockrpg;

public class Vector2D extends Coord2D {

	private double length;

	/**
	 * Default Vector2D Constructor. Creates a Vector2D of (0,0) of length 0
	 */
	public Vector2D() {
		this.length = 0;
	}

	/**
	 * Custom Vector2D Constructor. Creates a Vector2D of (x,y) using separate
	 * values with calculated length
	 * 
	 * @param x x-Coordinate
	 * @param y y-Coordinate
	 */
	public Vector2D(double x, double y) {
		super(x, y);
		length = Math.sqrt(x * x + y * y);
	}

	/**
	 * Custom Vector2D Constructor. Creates a Vector2D of (x,y) using array with
	 * calculated length
	 * 
	 * @param coords double array containing x, y information in that order
	 * 
	 */
	public Vector2D(double[] coords) {
		super(coords);
		length = Math.sqrt(x * x + y * y);
	}

	/**
	 * Copys another Vector3D
	 * 
	 * @param other Other Vector3D to copy
	 */
	public Vector2D(Vector2D other) {
		super(other);
		this.length = other.length;
	}

	/**
	 * Normalizes vector to length of 1
	 * 
	 * @return normalized vector as Vector2D
	 */
	public Vector2D normalize() {
		Vector2D normalized = new Vector2D(this.getCoord());
		if (Math.abs(normalized.length - 0.0) < ERROR) {
			return this;
		}
		// Don't normalize if length is already close to 1 or length is 0 tiny changes
		if (Math.abs(normalized.length - 1.0) > ERROR && Math.abs(normalized.length - 0.0) > ERROR) {
			normalized.x = this.x / normalized.length + 0.0;
			normalized.y = this.y / normalized.length + 0.0;
		}
		normalized.length = 1.0;

		return normalized;

	}

	@Override
	public void setCoord(double coords[]) {
		super.setCoord(coords);
		this.length = Double.parseDouble(df.format(Math.sqrt(x * x + y * y)));
	}

	/**
	 * 
	 * @return length of Vector2D as double
	 */
	public double getLength() {
		return length;
	}

	/**
	 * 
	 * @param scalar multiplies vector by double scalar
	 * 
	 * @return returns vector multiplied by scalar as Vector2D
	 */
	public Vector2D multiply(double scalar) {
		Vector2D product = new Vector2D();
		product.setX(this.x * scalar);
		product.setY(this.y * scalar);
		this.length = Double.parseDouble(df.format(this.length * scalar));
		return product;
	}

	/**
	 * 
	 * @param other other Vector2D to dot with
	 * @return returns dot product as double
	 */
	public double dot(Vector2D other) {
		return this.x * other.x + this.y * other.y;
	}

	/**
	 * 
	 * @param other other vector for projection
	 * @return returns projection of other vector on this vector as Vector2D
	 */
	public Vector2D proj(Vector2D other) {
		double dotProd = this.dot(other);
		double projVal;
		if (Math.abs(dotProd - 0.0) < ERROR) {
			projVal = 0.0;
		} else {
			projVal = dotProd / (x * x + y * y) + 0.0;
		}

		return this.multiply(projVal);

	}

	/**
	 * 
	 * @param other other vector for rejection
	 * @return returns rejection of other vector on this vector as Vector2D
	 */
	public Vector2D perp(Vector2D other) {
		Vector2D rejection = this.proj(other);
		double coords[] = { other.x - rejection.x, other.y - rejection.y };
		rejection.setCoord(coords);

		return rejection;
	}

	/**
	 * 
	 * @param other Other Vector2D to compare
	 * @return Returns smallest angle between two Vector2Ds (between 0 and pi
	 *         radians)
	 */
	public double getAng(Vector2D other) {
		double cosVal = this.dot(other) / this.length / other.length;
		return Math.acos(cosVal);

	}

	/**
	 * 
	 * @param ang Angle in radians to turn Vector2D counter clockwise
	 * @return Returns Vector2D rotated ang radians counter clockwise
	 */
	public Vector2D rotate(double ang) {
		Vector2D rotation = new Vector2D();
		rotation.setCoord(super.rotate(ang).getCoord());

		return rotation;
	}

	@Override
	public Vector2D add(Coord2D other) {
		Vector2D sum = new Vector2D(super.add(other).getCoord());
		return sum;
	}

	@Override
	public Vector2D subtract(Coord2D other) {
		Vector2D diff = new Vector2D(super.subtract(other).getCoord());
		return diff;
	}

	/**
	 * 
	 * @return Returns position with same coordinates
	 */
	public Position2D toPos() {
		return new Position2D(this.getCoord());
	}

	/**
	 * @return Returns copy of this object
	 */
	@Override
	public Vector2D clone() {
		Vector2D clone = new Vector2D(this.getCoord());
		return clone;
	}

	/**
	 * 
	 * @param other other vector to check
	 * @return Returns true if parallel to other vector
	 */
	public boolean isParallel(Vector2D other) {
		if (!this.isOrigin() && !other.isOrigin()) {
			if (this.x == 0 && other.x == 0) {
				return true;
			} else if (this.y == 0 && other.y == 0) {
				return true;
			} else if (this.x == 0 || other.x == 0 || this.y == 0 || other.y == 0) {
				return false;
			} else {
				return Math.abs(this.x / other.x - this.y / other.y) < ERROR;
			}

		}

		return false;

	}

	/**
	 * 
	 * @param other Other vector that is multiple of this vector
	 * @return Returns scalar multiple
	 */
	public double getMultiple(Vector2D other) {
		if (other.isOrigin()) {
			return 0;
		}
		if (this.isParallel(other)) {
			double thisCoords[] = this.getCoord();
			double otherCoords[] = other.getCoord();
			for (int i = 0; i < 2; i++) {
				if (thisCoords[i] != 0) {
					return otherCoords[i] / thisCoords[i];
				}
			}
		} else {
			throw new IllegalArgumentException();
		}
		return 0;
	}

}
