package blockrpg;

public class Plane {
	private Vector3D vecX;
	private Vector3D vecY;
	private Vector3D norm;
	private double d;
	private Position3D pos;

	public Plane() {
		vecX = new Vector3D();
		vecY = new Vector3D();
		norm = new Vector3D();
		d = 0;
		pos = new Position3D();
	}

	public Plane(Vector3D vecX, Vector3D vecY, Position3D pos) {
		Vector3D xAxis = new Vector3D(1, 0 ,0);
		Vector3D yAxis = new Vector3D(0, 1 ,0);
		this.vecX = xAxis.proj(vecX);
		this.vecY = yAxis.proj(vecY);
		norm = this.vecX.cross(this.vecY);
		d = norm.dot(this.vecX);
		this.pos = pos;
	}

	public Position3D placeOnPlane(Position2D point2D) {
		Position3D point3D = new Position3D();
		double coords[] = new double[3];
		double XCoords[] = vecX.getCoord();
		double YCoords[] = vecY.getCoord();
		double normCoords[] = norm.getCoord();
		coords[0] = vecX.getLength() / point2D.x * XCoords[0];
		coords[1] = vecY.getLength() / point2D.y * YCoords[1];
		coords[2] = (d/normCoords[2] - normCoords[0]/normCoords[2]*coords[0] - normCoords[1]/normCoords[2]*coords[1]);
		return point3D;
	}
}
