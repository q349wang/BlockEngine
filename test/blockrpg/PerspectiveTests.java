package blockrpg;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PerspectiveTests {

	@Test
	void testSetAndGetPos() {
		Perspective test = new Perspective();
		Position3D pos = new Position3D(1, 2, 0);
		test.setPos(pos.getCoord());
		assertEquals(pos, test.getPos());
	}

	@Test
	void testSetAndGetDirAndTilt() {
		Perspective test = new Perspective();
		Vector3D dir = new Vector3D(0, 2, 0);
		Vector3D tilt = new Vector3D(0, 0, 2);
		test.setDir(dir.getCoord());
		test.setTilt(tilt.getCoord());
		assertEquals(dir.normalize(), test.getDir());
		assertEquals(dir.perp(tilt).normalize(), test.getTilt());
	}

	@Test
	void testGetNorm() {
		Perspective test = new Perspective();
		Vector3D dir = new Vector3D(0, 2, 0);
		Vector3D tilt = new Vector3D(0, 0, 2);
		test.setDir(dir.getCoord());
		test.setTilt(tilt.getCoord());
		assertEquals(dir.normalize().cross(dir.perp(tilt).normalize()), test.getNorm());
	}

	@Test
	void testGetBasis() {
		Perspective test = new Perspective();
		Vector3D dir = new Vector3D(0, 2, 0);
		Vector3D tilt = new Vector3D(0, 0, 2);
		test.setDir(dir.getCoord());
		test.setTilt(tilt.getCoord());
		double expected[][] = { { 0.0, 1.0, 0.0 }, { 0.0, 0.0, 1.0 }, { 1.0, 0.0, 0.0 } };

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				assertEquals(expected[i][j], test.getBasis()[i][j]);
			}
		}

	}

	@Test
	void testGetInverse() {
		Perspective test = new Perspective();
		Vector3D dir = new Vector3D(0, 2, 0);
		Vector3D tilt = new Vector3D(0, 0, 2);
		test.setDir(dir.getCoord());
		test.setTilt(tilt.getCoord());
		double expected[][] = { { 0.0, 0.0, 1.0 }, { 1.0, 0.0, 0.0 }, { 0.0, 1.0, 0.0 } };

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				assertEquals(expected[i][j], test.getInverse()[i][j]);
			}
		}
	}

	@Test
	void testToViewBasisVector3DOrigin() {
		Perspective test = new Perspective();
		Vector3D dir = new Vector3D(0, 2, 0);
		Vector3D tilt = new Vector3D(0, 0, 2);
		Position3D pos = new Position3D(1, 0, 0);
		test.setPos(pos.getCoord());
		test.setDir(dir.getCoord());
		test.setTilt(tilt.getCoord());

		Vector3D stdVec = new Vector3D(1, 0, 0);
		Vector3D expected = new Vector3D(0,0,0);

		assertEquals(expected, test.toViewBasis(stdVec));

	}
	
	@Test
	void testToViewBasisVector3DTypical() {
		Perspective test = new Perspective();
		Vector3D dir = new Vector3D(0, 2, 0);
		Vector3D tilt = new Vector3D(0, 0, 2);
		Position3D pos = new Position3D(1, 0, 0);
		test.setPos(pos.getCoord());
		test.setDir(dir.getCoord());
		test.setTilt(tilt.getCoord());

		Vector3D stdVec = new Vector3D(1, 0, 1);
		Vector3D expected = new Vector3D(0,1,0);

		assertEquals(expected, test.toViewBasis(stdVec));

	}

	@Test
	void testToStdBasisVector3D() {
		Perspective test = new Perspective();
		Vector3D dir = new Vector3D(0, 2, 0);
		Vector3D tilt = new Vector3D(0, 0, 2);
		Position3D pos = new Position3D(1, 0, 0);
		test.setPos(pos.getCoord());
		test.setDir(dir.getCoord());
		test.setTilt(tilt.getCoord());

		Vector3D viewVec = new Vector3D(0, 1, 0);
		Vector3D expected = new Vector3D(1,0,1);

		assertEquals(expected, test.toStdBasis(viewVec));
	}

	@Test
	void testToViewBasisPosition3DOrigin() {
		Perspective test = new Perspective();
		Vector3D dir = new Vector3D(0, 2, 0);
		Vector3D tilt = new Vector3D(0, 0, 2);
		Position3D pos = new Position3D(1, 0, 0);
		test.setPos(pos.getCoord());
		test.setDir(dir.getCoord());
		test.setTilt(tilt.getCoord());

		Position3D stdPos = new Position3D(1, 0, 0);
		Position3D expected = new Position3D(0,0,0);

		assertEquals(expected, test.toViewBasis(stdPos));

	}
	
	@Test
	void testToViewBasisPosition3DTypical() {
		Perspective test = new Perspective();
		Vector3D dir = new Vector3D(0, 2, 0);
		Vector3D tilt = new Vector3D(0, 0, 2);
		Position3D pos = new Position3D(1, 0, 0);
		test.setPos(pos.getCoord());
		test.setDir(dir.getCoord());
		test.setTilt(tilt.getCoord());

		Position3D stdPos = new Position3D(1, 0, 1);
		Position3D expected = new Position3D(0,1,0);

		assertEquals(expected, test.toViewBasis(stdPos));
	}

	@Test
	void testToStdBasisPosition3D() {
		Perspective test = new Perspective();
		Vector3D dir = new Vector3D(0, 2, 0);
		Vector3D tilt = new Vector3D(0, 0, 2);
		Position3D pos = new Position3D(1, 0, 0);
		test.setPos(pos.getCoord());
		test.setDir(dir.getCoord());
		test.setTilt(tilt.getCoord());

		Position3D viewPos = new Position3D(0, 1, 0);
		Position3D expected = new Position3D(1,0,1);

		assertEquals(expected, test.toStdBasis(viewPos));
	}

}
