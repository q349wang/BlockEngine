package blockrpg;

public class Line {
	private Position3D pos;
	private Vector3D dir;
	
	public Line() {
		pos = new Position3D();
		dir = new Vector3D();
	}
	
	public Line(Vector3D dir, Position3D pos) {
		this.dir = new Vector3D(dir.getCoord());
		this.pos = new Position3D(pos.getCoord());
	}
}
