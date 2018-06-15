package blockrpg;

class Perspective {
	private Position pos;
	private Vector dir; // Direction of perspective (into screen is positive)
	private Vector tilt; // Tilt of perspective (left is positive)
	private Vector norm; // Cross product of dir X tilt (up is positive)
	private double[][] viewBasis;
	private double[][] viewBasisInverse;

	public Perspective(double[] posCoords, double[] dirCoords, double[] tiltCoords) {
		pos = new Position(posCoords);
		dir = new Vector(dirCoords);

		tilt = new Vector(tiltCoords);
		tilt = dir.perp(tilt);
		norm = dir.cross(tilt);

		viewBasis = new double[3][3];
		viewBasis[0] = dir.getCoord();
		viewBasis[1] = dir.getCoord();
		viewBasis[2] = dir.getCoord();

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
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				viewBasisInverse[i][i] = temp[i+1][j+1]/det*temp[i+2][j+2] - temp[i+2][j+1]/det*temp[i+1][j+2];
			}
		}
	}
}
