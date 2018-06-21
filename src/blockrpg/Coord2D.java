package blockrpg;

import java.util.Arrays;

public class Coord2D {

	protected final static double ERROR = 0.0000000000001;

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
		// Rounds numbers that are very close to integers
		for (int i = 0; i < 2; i++) {
			if (Math.abs(coords[i] - Math.round(coords[i])) < ERROR) {
				coords[i] = Math.round(coords[i]);
			}
		}
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
	 * @param x
	 *            Sets X coordinate to the inputed value
	 */
	public void setX(double x) {
		double[] coords = { x, this.y };
		setCoord(coords);
	}

	/**
	 * 
	 * @param y
	 *            Sets Y coordinate to the inputed value
	 */
	public void setY(double y) {
		double[] coords = { this.x, y };
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
	
    // Overriding equals() to compare two Coord2D objects
    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof Coord2D)) {
            return false;
        }

        Coord2D c = (Coord2D) o;
         
        // Compare the data members and return accordingly 
        return Arrays.equals(this.getCoord(), c.getCoord());
    }
}
