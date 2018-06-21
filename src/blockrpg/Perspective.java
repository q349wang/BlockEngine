package blockrpg;

public class Perspective {
	private Position3D pos;
	private Vector3D dir; // Direction of perspective (into screen is positive)
	private Vector3D tilt; // Tilt of perspective (left is positive)
	private Vector3D norm; // Cross product of dir X tilt (up is positive)
	private double[][] viewBasis;
	private double[][] viewBasisInverse;

	/**
	 * Default constructor for a perspective (Calls default constructors for
	 * position, direciton and tilt
	 */
	public Perspective() {
		pos = new Position3D();
		dir = new Vector3D();
		tilt = new Vector3D();

		viewBasis = new double[3][3];
		viewBasisInverse = new double[3][3];
	}

	/**
	 * Custom constructor for a Perspective
	 * 
	 * @param posCoords
	 *            coords for position
	 * @param dirCoords
	 *            coords for perspective direction
	 * @param tiltCoords
	 *            coords for perspective tilt
	 */
	public Perspective(double[] posCoords, double[] dirCoords, double[] tiltCoords) {
		pos = new Position3D(posCoords);
		dir = new Vector3D(dirCoords);
		dir=dir.normalize();

		tilt = new Vector3D(tiltCoords);
		tilt = dir.perp(tilt).normalize();
		norm = dir.cross(tilt).normalize();

		viewBasis = new double[3][3];
		viewBasisInverse = new double[3][3];

		setBasis();
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
	 * @param stdCoord
	 *            Vector3D in standard basis
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
	 * @param viewCoord
	 *            Vector3D in view basis
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
		
		for(int i = 0; i < 3; i++) {
			newCoords[i] += posCoords[i];
		}
		Vector3D stdCoord = new Vector3D();
		stdCoord.setCoord(newCoords);
		return stdCoord;
	}
	
	/**
	 * 
	 * @param stdCoord
	 *            Vector3D in standard basis
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
	 * @param viewCoord
	 *            Position3D in view basis
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
		

		for(int i = 0; i < 3; i++) {
			newCoords[i] += posCoords[i];
		}
		Position3D stdCoord = new Position3D();
		stdCoord.setCoord(newCoords);
		return stdCoord;
	}

	/**
	 * 
	 * @param posCoords
	 *            sets pos to posCoords
	 */
	public void setPos(double[] posCoords) {
		pos.setCoord(posCoords);
	}

	/**
	 * 
	 * @param dirCoords
	 *            sets dir to dirCoords
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
	 * @param tiltCoords
	 *            sets tilt to tiltCoords
	 */
	public void setTilt(double[] tiltCoords) {
		tilt.setCoord(tiltCoords);
		tilt = dir.perp(tilt).normalize();
		norm = dir.cross(tilt);
		setBasis();
	}

}
