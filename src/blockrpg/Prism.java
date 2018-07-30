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
			points[i].setCoord(sideFace.getRelPoints()[i].getCoord());
		}
		Plane plane = new Plane(new Vector3D(1,0,0), new Vector3D(0,1,0), new Position3D());
		plane.setPos(center.add(plane.getNorm().multiply(length/2)).getCoord());
		
		faces[0] = new Face(points, points.length, plane, pov);
		faces[1] = new Face(points, points.length, plane.rotatePos(Math.PI, new Vector3D(0,0,1)), pov);
		for (int i = 2; i< faces.length-1;i++) {
			points = new Position2D[4];
			

		}
		
		this.pov = pov;
		this.center = center.clone();
	}
}
