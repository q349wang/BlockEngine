/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockrpg;

/**
 *
 * @author L
 */
class Coord {
	protected double x;
	protected double y;
	protected double z;

	/**
	 * Default Coord Constructor. Creates a Coord at (0,0,0)
	 */
	public Coord() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	/**
	 * Custom Coord Constructor. Creates a Coord at (x,y,z)
	 * 
	 * @param x
	 *            x-Coordinate
	 * @param y
	 *            y-Coordinate
	 * @param z
	 *            z-Coordinate
	 */
	public Coord(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * 
	 * @param arr
	 *            Sets coordinate to given array (in x, y, z form)
	 */
	public void setCoord(double[] arr) {
		this.x = arr[0];
		this.y = arr[1];
		this.y = arr[2];
	}

	/**
	 * 
	 * @return double[3] Array of the x, y, and z values in that order
	 */
	public double[] getCoord() {
		double[] coords = new double[3];
		coords[0] = this.x;
		coords[1] = this.y;
		coords[2] = this.z;

		return coords;
	}
}
