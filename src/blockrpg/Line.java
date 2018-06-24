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
	
	/**
	 * 
	 * @param dirCoords Sets direction vector to inputed coordinates
	 */
	public void setDir(double[] dirCoords) {
		this.dir.setCoord(dirCoords);
	}
	
	/**
	 * 
	 * @param posCoords Sets position vector to inputed coordinates
	 */
	public void setPos(double[] posCoords) {
		this.pos.setCoord(posCoords);
	}
	
	/**
	 * 
	 * @return Returns direction
	 */
	public Vector3D getDir() {
		return dir;
	}
	
	/**
	 * 
	 * @return Returns position
	 */
	public Vector3D getPos() {
		return dir;
	}
	
	public Line rotateDir(double ang, Vector3D axis) {
		Line rotatedDir = new Line(this.dir, this.pos);
		rotatedDir.dir = dir.rotate(ang, axis);
		return rotatedDir;
	}
	
	public Line rotatePos(double ang, Vector3D axis) {
		Line rotatedDir = new Line(this.dir, this.pos);
		rotatedDir.dir = dir.rotate(ang, axis);
		rotatedDir.pos = pos.rotate(ang, axis);
		return rotatedDir;
	}
}
