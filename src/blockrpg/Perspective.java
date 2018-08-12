package blockrpg;

public class Perspective {
	private Position3D pos;
	private Vector3D dir; // Direction of perspective (into screen is positive)
	private Vector3D tilt; // Tilt of perspective (left is positive)
	private Vector3D norm; // Cross product of dir X tilt (up is positive)
	private double[][] viewBasis;
	private double[][] viewBasisInverse;
	private double zoom;

	/**
	 * Default constructor for a perspective (Calls constructors for position,
	 * direciton and tilt for std basis)
	 */
	public Perspective() {
		pos = new Position3D();
		dir = new Vector3D(1, 0, 0);
		tilt = new Vector3D(0, 1, 0);
		setZoom(1);
		viewBasis = new double[3][3];
		viewBasisInverse = new double[3][3];
	}

	/**
	 * Custom constructor for a Perspective
	 * 
	 * @param posCoords  coords for position
	 * @param dirCoords  coords for perspective direction
	 * @param tiltCoords coords for perspective tilt
	 */
	public Perspective(double[] posCoords, double[] dirCoords, double[] tiltCoords) {
		pos = new Position3D(posCoords);
		dir = new Vector3D(dirCoords);
		dir = dir.normalize();
		setZoom(1);
		tilt = new Vector3D(tiltCoords);
		tilt = dir.perp(tilt).normalize();
		norm = dir.cross(tilt).normalize();

		viewBasis = new double[3][3];
		viewBasisInverse = new double[3][3];

		setBasis();
	}

	/**
	 * Copies other Perspective
	 * 
	 * @param other Other Perspective to copy
	 */
	public Perspective(Perspective other) {
		this.pos = other.pos.clone();
		this.dir = other.dir.clone();
		this.tilt = other.tilt.clone();
		this.norm = other.norm.clone();
		this.viewBasis = other.viewBasis.clone();
		this.viewBasisInverse = other.viewBasisInverse.clone();
	}

	@Override
	public Perspective clone() {
		return new Perspective(this);
	}

	/**
	 * Procedure to set the view basis matrix and the inverse of said matrix
	 */
	private void setBasis() {
		viewBasis[0] = dir.getCoord();
		viewBasis[1] = tilt.getCoord();
		viewBasis[2] = norm.getCoord();

		double[][] temp = new double[5][5];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				temp[i][j] = viewBasis[i][j];
			}
		}
		for (int i = 3; i < 5; i++) {
			for (int j = 0; j < 3; j++) {
				temp[i][j] = temp[i - 3][j];
			}
		}

		for (int i = 0; i < 5; i++) {
			for (int j = 3; j < 5; j++) {
				temp[i][j] = temp[i][j - 3];
			}
		}

		double det = temp[0][0] * (temp[1][1] * temp[2][2] - temp[1][2] * temp[2][1])
				- temp[0][1] * (temp[1][0] * temp[2][2] - temp[2][0] * temp[1][2])
				+ temp[0][2] * (temp[1][0] * temp[2][1] - temp[1][1] * temp[2][0]);

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				viewBasisInverse[j][i] = temp[i + 1][j + 1] / det * temp[i + 2][j + 2]
						- temp[i + 2][j + 1] / det * temp[i + 1][j + 2] + 0.0;
			}
		}
	}

	/**
	 * 
	 * @return returns position of perspective as Position3D
	 */
	public Position3D getPos() {
		return pos;
	}

	/**
	 * 
	 * @return returns direction of perspective as Vector3D
	 */
	public Vector3D getDir() {
		return dir;
	}

	/**
	 * 
	 * @return returns tilt of perspective as Vector3D
	 */
	public Vector3D getTilt() {
		return tilt;
	}

	/**
	 * 
	 * @return returns norm of perspective as Vector3D
	 */
	public Vector3D getNorm() {
		return norm;
	}

	/**
	 * 
	 * @return returns view basis matrix
	 */
	public double[][] getBasis() {
		return this.viewBasis;
	}

	/**
	 * 
	 * @return returns inverse of view basis matrix
	 */
	public double[][] getInverse() {
		return this.viewBasisInverse;
	}

	/**
	 * 
	 * @param stdCoord Vector3D in standard basis
	 * @return returns Vector3D in view basis
	 */
	public Vector3D toViewBasis(Vector3D stdCoord) {
		double[] newCoords = new double[3];
		double[] oldCoords = stdCoord.getCoord();
		double[] posCoords = pos.getCoord();
		for (int i = 0; i < 3; i++) {
			oldCoords[i] -= posCoords[i];
			for (int j = 0; j < 3; j++) {
				newCoords[j] += viewBasisInverse[i][j] * oldCoords[i] + 0.0;
			}
		}
		Vector3D viewCoord = new Vector3D();
		viewCoord.setCoord(newCoords);
		return viewCoord;
	}

	/**
	 * 
	 * @param viewCoord Vector3D in view basis
	 * @return returns coordinate in standard basis
	 */
	public Vector3D toStdBasis(Vector3D viewCoord) {
		double[] newCoords = new double[3];
		double[] oldCoords = viewCoord.getCoord();
		double[] posCoords = pos.getCoord();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				newCoords[j] += viewBasis[i][j] * oldCoords[i] + 0.0;
			}
		}

		for (int i = 0; i < 3; i++) {
			newCoords[i] += posCoords[i];
		}
		Vector3D stdCoord = new Vector3D();
		stdCoord.setCoord(newCoords);
		return stdCoord;
	}

	/**
	 * 
	 * @param stdCoord Vector3D in standard basis
	 * @return returns Vector3D in view basis
	 */
	public Position3D toViewBasis(Position3D stdCoord) {
		double[] newCoords = new double[3];
		double[] oldCoords = stdCoord.getCoord();
		double[] posCoords = pos.getCoord();
		for (int i = 0; i < 3; i++) {
			oldCoords[i] -= posCoords[i];
			for (int j = 0; j < 3; j++) {
				newCoords[j] += viewBasisInverse[i][j] * oldCoords[i] + 0.0;
			}
		}
		Position3D viewCoord = new Position3D();
		viewCoord.setCoord(newCoords);
		return viewCoord;
	}

	/**
	 * 
	 * @param viewCoord Position3D in view basis
	 * @return returns coordinate in standard basis
	 */
	public Position3D toStdBasis(Position3D viewCoord) {
		double[] newCoords = new double[3];
		double[] oldCoords = viewCoord.getCoord();
		double[] posCoords = pos.getCoord();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				newCoords[j] += viewBasis[i][j] * oldCoords[i] + 0.0;
			}
		}

		for (int i = 0; i < 3; i++) {
			newCoords[i] += posCoords[i];
		}
		Position3D stdCoord = new Position3D();
		stdCoord.setCoord(newCoords);
		return stdCoord;
	}

	/**
	 * 
	 * @param posCoords sets pos to posCoords
	 */
	public void setPos(double[] posCoords) {
		pos.setCoord(posCoords);
	}

	/**
	 * 
	 * @param dirCoords sets dir to dirCoords
	 */
	public void setDir(double[] dirCoords) {
		dir.setCoord(dirCoords);
		dir = dir.normalize();
		tilt = dir.perp(tilt).normalize();
		norm = dir.cross(tilt);
		setBasis();
	}

	/**
	 * 
	 * @param tiltCoords sets tilt to tiltCoords
	 */
	public void setTilt(double[] tiltCoords) {
		tilt.setCoord(tiltCoords);
		tilt = dir.perp(tilt).normalize();
		norm = dir.cross(tilt);
		setBasis();
	}

	/**
	 * 
	 * @param point Position3D to convert (in standard basis)
	 * @return Returns Position2D for what a perspective sees
	 */
	public Position2D getViewPoint(Position3D point) {
		Position3D viewPoint3D = this.toViewBasis(point);
		Position3D posPoint = this.toViewBasis(this.pos);
		Position2D viewPoint2D = new Position2D();
		double viewX = 0;
		double viewY = 0;
		double dis = posPoint.totDistanceFrom(viewPoint3D);
		if (dis == 0) {
			// TODO on point
		} else {
			// Needs to translate like this
			viewX = -posPoint.yDistancefrom(viewPoint3D) / dis * this.zoom;
			viewY = posPoint.zDistancefrom(viewPoint3D) / dis * this.zoom;
		}
		viewPoint2D.setX(viewX);
		viewPoint2D.setY(viewY);
		return viewPoint2D;
	}

	/**
	 * Finds a point corresponding to a 2D view point on a plane. Assumes that all
	 * view points are in front of pov
	 * 
	 * @param point 2D view point
	 * @param plane Plane to look at
	 * @return
	 */
	public Position3D getRealPoint(Position2D point, Plane plane) {

		double xVal = Math.sqrt(this.zoom * this.zoom - point.getY() * point.getY() - point.getX() * point.getX());

		Position3D viewPoint3D = this.toViewBasis(this.pos).add(new Position3D(xVal, -point.getX(), point.getY()));
		Line3D ray = new Line3D(this.pos, this.toStdBasis(viewPoint3D));
		Position3D stdPoint = plane.getIntersect(ray);
		if (stdPoint == null) {
			return null;
		} else {
			return stdPoint;
		}
	}

	// Overriding equals() to compare two Perspective objects
	@Override
	public boolean equals(Object other) {

		if (other == this) {
			return true;
		}

		if (!(other instanceof Perspective)) {
			return false;
		}

		Perspective p = (Perspective) other;

		return this.dir.equals(p.dir) && this.pos.equals(p.pos) && this.tilt.equals(p.tilt);

	}

	/**
	 * 
	 * @return Returns zoom
	 */
	public double getZoom() {
		return zoom;
	}

	/**
	 * Sets zoom
	 * 
	 * @param zoom double value to set zoom to
	 */
	public void setZoom(double zoom) {
		this.zoom = zoom;
	}

	@Override
	public String toString() {
		return "Direction: " + this.dir.toString() + "\nTilt" + this.tilt.toString() + "\nPosition: "
				+ this.pos.toString();
	}

	/**
	 * Rotates direction of perspective ang radians ccw about axis
	 * @param ang angle in radians
	 * @param axis axis to rotate about
	 */
	public void rotateDir(double ang, Vector3D axis) {
		dir.rotate(ang, axis);
		dir = dir.normalize();
		tilt = dir.perp(tilt).normalize();
		norm = dir.cross(tilt);
		setBasis();
		
	}
	
	/**
	 * Orbits perspective ang radians ccw about axis around given point
	 * @param ang angle in radians
	 * @param axis axis to rotate about
	 * @param pivot Point to orbit around
	 */
	public void orbit(double ang, Vector3D axis, Position3D pivot) {
		Vector3D pivotDir = pivot.getDirection(this.pos);
		dir.rotate(ang, axis);
		dir = dir.normalize();
		tilt = dir.perp(tilt).normalize();
		norm = dir.cross(tilt);
		
		pivotDir.rotate(ang, axis);
		this.pos = pivot.add(pivotDir);
		setBasis();
		
	}

}
