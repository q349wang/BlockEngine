package blockrpg;

public class Coord2D {

	protected final double ERROR = 0.001;

	protected double x;
	protected double y;

	/**
	 * Default Coord2D Constructor. Creates a Coord3D at (0,0)
	 */
	public Coord2D() {
		this.x = 0;
		this.y = 0;
	}

	/**
	 * Custom Coord2D Constructor. Creates a Coord3D at (x,y)
	 * 
	 * @param x
	 *            x-Coordinate
	 * @param y
	 *            y-Coordinate
	 */
	public Coord2D(double x, double y) {
		this.x = x;
	}

	/**
	 * Custom Coord2D Constructor. Creates a Coord2D at (x,y)
	 * 
	 * @param coords
	 *            double array containing x, y information in that order
	 * 
	 */
	public Coord2D(double[] coords) {
		this.x = coords[0];
		this.y = coords[1];
	}

	/**
	 * 
	 * @param coords
	 *            Sets coordinate to given array (in x, y form)
	 */
	public void setCoord(double[] coords) {
		this.x = coords[0];
		this.y = coords[1];
	}

	/**
	 * 
	 * @param x
	 *            Adds inputed value to the X coordinate
	 */
	public void addX(double x) {
		double[] coords = { this.x + x, this.y };
		setCoord(coords);
	}

	/**
	 * 
	 * @param y
	 *            Adds inputed value to the Y coordinate
	 */
	public void addY(double y) {
		double[] coords = { this.x, this.y + y };
		setCoord(coords);
	}

	/**
	 * 
	 * @return double[2] Array of the x, and y values in that order
	 */
	public double[] getCoord() {
		double[] coords = new double[2];
		coords[0] = this.x;
		coords[1] = this.y;

		return coords;
	}
}
