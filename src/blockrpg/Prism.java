package blockrpg;

public class Prism {
	private Face[] faces;
	private Perspective pov;
	
	private Position3D center;
	
	public Prism() {
		faces = new Face[4];
		for (Face face: faces) {
			face = new Face();
		}
		
		pov = new Perspective();
		center = new Position3D();
	}
	
	public Prism(double length, Position3D center, Perspective pov, Face sideFace) {
		faces = new Face[2+ sideFace.getNumPoints()];
		Position2D[] points = new Position2D[sideFace.getNumPoints()];
		for(int i = 0; i < sideFace.getNumPoints(); i++) {
			points[i] = new Position2D(sideFace.getRelPoints()[i].getCoord());
		}
		Plane plane = new Plane(new Vector3D(1,0,0), new Vector3D(0,1,0), new Position3D());
		
		plane.setPos(center.add(plane.getNorm().multiply(length/2)).getCoord());
		
		faces[0] = new Face(points, points.length, plane, pov);
		faces[1] = new Face(points, points.length, plane.rotatePos(Math.PI, new Vector3D(0,0,1)), pov);
		for (int i = 2; i< faces.length-1;i++) {
			points = new Position2D[4];
			Position3D[] truePoints = new Position3D[4];
			
			truePoints[0] = faces[0].getTruePoints()[i];
			truePoints[1] = faces[0].getTruePoints()[i+1];
			truePoints[2] = faces[1].getTruePoints()[i];
			truePoints[3] = faces[1].getTruePoints()[i+1];
			
			Position3D centerPos = new Position3D();
			for (Position3D point : truePoints) {
				centerPos.add(point);
			}
			centerPos = centerPos.toVec().multiply(0.25).toPos();
			plane = new Plane(truePoints[0].getDirection(truePoints[2]), truePoints[0].getDirection(truePoints[1]), centerPos);
			
			for (int j =0; j < 4; j++) {
				points[j] = plane.get2DPoint(truePoints[j]);
			}
			
			faces[i] = new Face(points, points.length, plane, pov);

		}
		
		points = new Position2D[4];
		Position3D[] truePoints = new Position3D[4];
		
		truePoints[0] = faces[0].getTruePoints()[faces.length - 1];
		truePoints[1] = faces[0].getTruePoints()[0];
		truePoints[2] = faces[1].getTruePoints()[faces.length - 1];
		truePoints[3] = faces[1].getTruePoints()[0];
		
		Position3D centerPos = new Position3D();
		for (Position3D point : truePoints) {
			centerPos.add(point);
		}
		centerPos = centerPos.toVec().multiply(0.25).toPos();
		plane = new Plane(truePoints[0].getDirection(truePoints[2]), truePoints[0].getDirection(truePoints[1]), centerPos);
		
		for (int j =0; j < 4; j++) {
			points[j] = plane.get2DPoint(truePoints[j]);
		}
		
		faces[faces.length - 1] = new Face(points, points.length, plane, pov);
		
		this.pov = pov;
		this.center = center.clone();
	}
}
