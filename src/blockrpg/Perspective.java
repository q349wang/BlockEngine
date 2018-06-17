package blockrpg;

class Perspective {
	private Position pos;
	private Vector dir; // Direction of perspective (into screen is positive)
	private Vector tilt; // Tilt of perspective (left is positive)
	private Vector norm; // Cross product of dir X tilt (up is positive)
	private double[][] viewBasis;
	private double[][] viewBasisInverse;

	/**
	 * Default constructor for a perspective (Calls default constructors for
	 * position, direciton and tilt
	 */
	public Perspective() {
		pos = new Position();
		dir = new Vector();
		tilt = new Vector();

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
		pos = new Position(posCoords);
		dir = new Vector(dirCoords);
		dir=dir.normalize();

		tilt = new Vector(tiltCoords);
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
	 * @return returns position of perspective as Position
	 */
	public Position getPos() {
		return pos;
	}

	/**
	 * 
	 * @return returns direction of perspective as Vector
	 */
	public Vector getDir() {
		return dir;
	}

	/**
	 * 
	 * @return returns tilt of perspective as Vector
	 */
	public Vector getTilt() {
		return tilt;
	}

	/**
	 * 
	 * @return returns norm of perspective as Vector
	 */
	public Vector getNorm() {
		return norm;
	}

	/**
	 * 
	 * @return returns view basis matrix
	 */
	public double[][] getBasis() {
		return viewBasis;
	}

	/**
	 * 
	 * @return returns inverse of view basis matrix
	 */
	public double[][] getInverse() {
		return viewBasisInverse;
	}

	/**
	 * 
	 * @param stdCoord
	 *            Vector in standard basis
	 * @return returns Vector in view basis
	 */
	public Vector toViewBasis(Vector stdCoord) {
		double[] newCoords = new double[3];
		double[] oldCoords = stdCoord.getCoord();
		double[] posCoords = pos.getCoord();
		for (int i = 0; i < 3; i++) {
			oldCoords[i] -= posCoords[i];
			for (int j = 0; j < 3; j++) {
				newCoords[j] += viewBasisInverse[i][j] * oldCoords[i] + 0.0;
			}
		}
		Vector viewCoord = new Vector();
		viewCoord.setCoord(newCoords);
		return viewCoord;
	}

	/**
	 * 
	 * @param viewCoord
	 *            Vector in view basis
	 * @return returns coordinate in standard basis
	 */
	public Vector toStdBasis(Vector viewCoord) {
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
		Vector stdCoord = new Vector();
		stdCoord.setCoord(newCoords);
		return stdCoord;
	}
	
	/**
	 * 
	 * @param stdCoord
	 *            Vector in standard basis
	 * @return returns Vector in view basis
	 */
	public Position toViewBasis(Position stdCoord) {
		double[] newCoords = new double[3];
		double[] oldCoords = stdCoord.getCoord();
		double[] posCoords = pos.getCoord();
		for (int i = 0; i < 3; i++) {
			oldCoords[i] -= posCoords[i];
			for (int j = 0; j < 3; j++) {
				newCoords[j] += viewBasisInverse[i][j] * oldCoords[i] + 0.0;
			}
		}
		Position viewCoord = new Position();
		viewCoord.setCoord(newCoords);
		return viewCoord;
	}

	/**
	 * 
	 * @param viewCoord
	 *            Position in view basis
	 * @return returns coordinate in standard basis
	 */
	public Position toStdBasis(Position viewCoord) {
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
		Position stdCoord = new Position();
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
		norm = dir.cross(tilt);
		setBasis();
	}

}
