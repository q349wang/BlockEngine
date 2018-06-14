package blockrpg;

class Vector extends Coord {
	
	private double length;
	
	public Vector() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	public Vector(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void normalize() {
		this.x /=  length;
		this.y /=  length;
		this.z /= length;
	}
	
	@Override
	public void setCoord(double arr[]) {
		this.x = arr[0];
		this.y = arr[1];
		this.y = arr[2];
		length = Math.sqrt(x * x + y * y + z * z);
	}
	
	public double getLength() {
		return length;
	}
}
