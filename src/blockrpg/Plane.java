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
		this.vecX = vecX.normalize();
		this.vecY = this.vecX.perp(vecY).normalize();
		norm = this.vecX.cross(this.vecY);
		d = norm.dot(this.vecX);
		this.pos = pos;
	}

	public Position3D placeOnPlane(Position2D point2D) {
		Position3D point3D = new Position3D();
		Vector2D dirOfPoint = new Vector2D(point2D.getCoord());
		Vector2D xAxis = new Vector2D(1,0);
		Vector2D yAxis = new Vector2D(0,1);
		
		double xScale = xAxis.proj(dirOfPoint).getLength();
		double yScale = yAxis.proj(dirOfPoint).getLength();
		
		point3D.setCoord(this.vecX.multiply(xScale).add(this.vecY.multiply(yScale)).getCoord());
		point3D = point3D.add(this.pos);
		return point3D;
	}
}
