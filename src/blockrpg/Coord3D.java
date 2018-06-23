/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockrpg;

import java.text.DecimalFormat;
import java.util.Arrays;

/**
 *
 * @author L
 */
public class Coord3D {

	protected final static double ERROR = 0.000000001;

	protected double x;
	protected double y;
	protected double z;

	/**
	 * Default Coord3D Constructor. Creates a Coord3D at (0,0,0)
	 */
	public Coord3D() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	/**
	 * Custom Coord3D Constructor. Creates a Coord3D at (x,y,z)
	 * 
	 * @param x
	 *            x-Coordinate
	 * @param y
	 *            y-Coordinate
	 * @param z
	 *            z-Coordinate
	 */
	public Coord3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Custom Coord3D Constructor. Creates a Coord3D at (x,y,z)
	 * 
	 * @param coords
	 *            double array containing x, y, z information in that order
	 * 
	 */
	public Coord3D(double[] coords) {
		this.x = coords[0];
		this.y = coords[1];
		this.z = coords[2];
	}

	/**
	 * 
	 * @param coords
	 *            Sets coordinate to given array (in x, y, z form)
	 */
	public void setCoord(double[] coords) {
		DecimalFormat df = new DecimalFormat("#.##########");
		// Rounds numbers that are very close to nearest billionth
		for (int i = 0; i < 3; i++) {
			if (Math.abs(coords[i] - Double.parseDouble(df.format(coords[i]))) < ERROR) {
				coords[i] = Double.parseDouble(df.format(coords[i]));
			}
		}
		this.x = coords[0];
		this.y = coords[1];
		this.z = coords[2];
	}

	/**
	 * 
	 * @param x
	 *            Adds inputed value to the X coordinate
	 */
	public void addX(double x) {
		double[] coords = { this.x + x, this.y, this.z };
		setCoord(coords);
	}

	/**
	 * 
	 * @param y
	 *            Adds inputed value to the Y coordinate
	 */
	public void addY(double y) {
		double[] coords = { this.x, this.y + y, this.z };
		setCoord(coords);
	}

	/**
	 * 
	 * @param z
	 *            Adds inputed value to the Z coordinate
	 */
	public void addZ(double z) {
		double[] coords = { this.x, this.y, this.z + z };
		setCoord(coords);
	}

	/**
	 * 
	 * @param x
	 *            Sets X coordinate to the inputed value
	 */
	public void setX(double x) {
		double[] coords = { x, this.y, this.z };
		setCoord(coords);
	}

	/**
	 * 
	 * @param y
	 *            Sets Y coordinate to the inputed value
	 */
	public void setY(double y) {
		double[] coords = { this.x, y, this.z };
		setCoord(coords);
	}

	/**
	 * 
	 * @param z
	 *            Sets Z coordinate to the inputed value
	 */
	public void setZ(double z) {
		double[] coords = { this.x, this.y, z };
		setCoord(coords);
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

	/**
	 * 
	 * @param other
	 *            Other Coord3D to add
	 * @return returns Coord3D with other added to it
	 */
	public Coord3D add(Coord3D other) {
		Coord3D sum = new Coord3D();
		double coords[] = { this.x + other.x, this.y + other.y, this.z + other.z };
		sum.setCoord(coords);
		return sum;
	}

	/**
	 * 
	 * @param other
	 *            Other Coord3D to subtract
	 * @return returns Coord3D with other subtracted from it
	 */
	public Coord3D subtract(Coord3D other) {
		Coord3D diff = new Coord3D();
		double coords[] = { this.x - other.x, this.y - other.y, this.z - other.z };
		diff.setCoord(coords);
		return diff;
	}
	

	/**
	 * 
	 * @param ang
	 *            angle in radians
	 * @return Returns Coord3D rotated ang radians counter clockwise about the X
	 *         axis
	 */
	public Coord3D rotateX(double ang) {
		Coord3D rotation = new Vector3D();
		double coords[] = { this.x, this.y * Math.cos(ang) - this.z * Math.sin(ang),
				this.y * Math.sin(ang) + this.z * Math.cos(ang) };
		rotation.setCoord(coords);

		return rotation;
	}

	/**
	 * 
	 * @param ang
	 *            angle in radians
	 * @return Returns Coord3D rotated ang radians counter clockwise about the Y
	 *         axis
	 */
	public Coord3D rotateY(double ang) {
		Coord3D rotation = new Coord3D();
		double coords[] = { this.x * Math.cos(ang) + this.z * Math.sin(ang), this.y,
				-this.x * Math.sin(ang) + this.z * Math.cos(ang) };
		rotation.setCoord(coords);

		return rotation;
	}

	/**
	 * 
	 * @param ang
	 *            angle in radians
	 * @return Returns Coord3D rotated ang radians counter clockwise about the Y
	 *         axis
	 */
	public Coord3D rotateZ(double ang) {
		Coord3D rotation = new Coord3D();
		double coords[] = { this.x * Math.cos(ang) - this.y * Math.sin(ang),
				this.x * Math.sin(ang) + this.y * Math.cos(ang), this.z };
		rotation.setCoord(coords);

		return rotation;
	}

	/**
	 * 
	 * @param ang
	 *            angle in radians
	 * @param axis
	 *            axis to rotate about
	 * @return Returns Coord3D rotated ang radians counter clockwise about the
	 *         specified axis
	 */
	public Coord3D rotate(double ang, Vector3D axis) {
		Coord3D rotation = new Coord3D();
		double[] oldCoords = this.getCoord();
		double[] axisCoords = axis.getCoord();
		double[] newCoords = new double[3];
		double[][] rotMatrix = new double[3][3];

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (i == j) {
					rotMatrix[i][j] = Math.cos(ang);
				} else {
					int index = i + j;
					switch (index) {
					case 1:
						rotMatrix[i][j] = Math.sin(ang) * axisCoords[2] * (j - i);
						break;
					case 2:
						rotMatrix[i][j] = Math.sin(ang) * axisCoords[1] * (i - j) / 2;
						break;
					case 3:
						rotMatrix[i][j] = Math.sin(ang) * axisCoords[0] * (j - i);
						break;
					default:
						break;
					}
				}

				rotMatrix[i][j] += axisCoords[i] * axisCoords[j] * (1 - Math.cos(ang));
			}

		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				newCoords[j] += rotMatrix[i][j] * oldCoords[i] + 0.0;
			}
		}
		rotation.setCoord(newCoords);

		return rotation;
	}

	// Overriding equals() to compare two Coord3D objects
	@Override
	public boolean equals(Object o) {

		if (o == this) {
			return true;
		}

		if (!(o instanceof Coord3D)) {
			return false;
		}

		Coord3D c = (Coord3D) o;

		// Compare the data members and return accordingly
		return Arrays.equals(this.getCoord(), c.getCoord());
	}
}
