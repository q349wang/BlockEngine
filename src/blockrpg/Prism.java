package blockrpg;

public class Prism extends Shape {

	@SuppressWarnings("unused")
	/**
	 * Default Prism of empty faces
	 */
	public Prism() {
		center = new Position3D();
		faces = new Face[4];
		for (Face face : faces) {
			face = new Face();
		}

		setPov(new Perspective());
		set(new Position3D());
	}

	/**
	 * Creates a custom prism with an inputed face as the sides
	 * 
	 * @param length   Length from face to face
	 * @param center   Center of prism
	 * @param pov      Perspective prism is seen from
	 * @param sideFace Face inputed
	 */
	public Prism(double length, Position3D center, Perspective pov, Face sideFace) {
		this.center = center.clone();
		faces = new Face[2 + sideFace.getNumPoints()];
		Position2D[] points = new Position2D[sideFace.getNumPoints()];
		for (int i = 0; i < sideFace.getNumPoints(); i++) {
			points[i] = new Position2D(sideFace.getRelPoints()[i].getCoord());
		}
		Plane plane = new Plane(new Vector3D(1, 0, 0), new Vector3D(0, 1, 0), new Position3D());

		plane.setPos(center.add(plane.getNorm().multiply(length / 2).toPos()).getCoord());

		faces[1] = new Face(points, points.length, plane, pov);

		plane.setPos(center.subtract(plane.getNorm().multiply(length / 2).toPos()).getCoord());
		for (Position2D point : points) {
			point.setCoord(point.toVec().multiply(-1).getCoord());
		}
		plane.rotatePlane(Math.PI, new Vector3D(0, 0, 1));
		faces[0] = new Face(points, points.length, plane, pov);
		for (int i = 2; i < faces.length - 1; i++) {
			points = new Position2D[4];
			Position3D[] truePoints = new Position3D[4];

			truePoints[0] = faces[0].getTruePoints()[i - 2];
			truePoints[1] = faces[0].getTruePoints()[i - 1];
			truePoints[2] = faces[1].getTruePoints()[i - 1];
			truePoints[3] = faces[1].getTruePoints()[i - 2];

			Position3D centerPos = new Position3D();
			for (Position3D point : truePoints) {
				centerPos = centerPos.add(point);
			}
			centerPos = centerPos.toVec().multiply(0.25).toPos();
			plane = new Plane(truePoints[0].getDirection(truePoints[3]), truePoints[0].getDirection(truePoints[1]),
					centerPos);

			for (int j = 0; j < 4; j++) {
				points[j] = plane.get2DPoint(truePoints[j]);
			}

			faces[i] = new Face(points, points.length, plane, pov);

		}

		points = new Position2D[4];
		Position3D[] truePoints = new Position3D[4];

		truePoints[0] = faces[0].getTruePoints()[faces.length - 3];
		truePoints[1] = faces[0].getTruePoints()[0];
		truePoints[3] = faces[1].getTruePoints()[faces.length - 3];
		truePoints[2] = faces[1].getTruePoints()[0];

		Position3D centerPos = new Position3D();
		for (Position3D point : truePoints) {
			centerPos = centerPos.add(point);
		}
		centerPos = centerPos.toVec().multiply(0.25).toPos();
		plane = new Plane(truePoints[0].getDirection(truePoints[3]), truePoints[0].getDirection(truePoints[1]),
				centerPos);

		for (int j = 0; j < 4; j++) {
			points[j] = plane.get2DPoint(truePoints[j]);
		}

		faces[faces.length - 1] = new Face(points, points.length, plane, pov);

		this.setPov(pov);
		this.set(center.clone());
	}

	/**
	 * Copies another prism
	 * 
	 * @param other Other prism to copy
	 */
	public Prism(Prism other) {
		this.pov = other.pov;
		this.center = other.center.clone();

		this.faces = new Face[other.faces.length];
		for (int i = 0; i < other.faces.length; i++) {
			this.faces[i] = other.faces[i].clone();
		}
	}

}
