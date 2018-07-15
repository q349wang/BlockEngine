package blockrpg;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Line3DTests {

	@Test
	void testCloneConstructor() {
		Line3D expected = new Line3D();
		double dirCoords[] = { 2.0/3, 1.0/3, 2.0/3 };
		double posCoords[] = { 1, 2, 3 };
		expected.setDir(dirCoords);
		expected.setPos(posCoords);
		Line3D test = new Line3D(expected);
		assertEquals(expected, test);
	}
	
	@Test
	void testCloneFunction() {
		Line3D expected = new Line3D();
		double dirCoords[] = { 2.0/3, 1.0/3, 2.0/3 };
		double posCoords[] = { 1, 2, 3 };
		expected.setDir(dirCoords);
		expected.setPos(posCoords);
		Line3D test = expected.clone();
		
		assertEquals(expected, test);
		assertFalse(expected == test);
		assertFalse(expected.getDir() == test.getDir());
		assertFalse(expected.getPos() == test.getPos());
	}
	
	@Test
	void testSetAndGetDir() {
		Line3D test = new Line3D();
		double dirCoords[] = { 1, 2, 3 };
		Vector3D expected = new Vector3D(1, 2, 3);
		test.setDir(dirCoords);
		assertEquals(expected.normalize(), test.getDir());
	}

	@Test
	void testSetAndGetPos() {
		Line3D test = new Line3D();
		double posCoords[] = { 1, 2, 3 };
		Position3D expected = new Position3D(1, 2, 3);
		test.setPos(posCoords);
		assertEquals(expected, test.getPos());
	}

	@Test
	void testGetLinePoint() {
		Line3D test = new Line3D();
		double dirCoords[] = { 2.0/3, 1.0/3, 2.0/3 };
		double posCoords[] = { 1, 2, 3 };
		test.setDir(dirCoords);
		test.setPos(posCoords);
		Position3D expected = new Position3D(-1.0/3,4.0/3,5.0/3);
		assertEquals(expected, test.getLinePoint(-2.0));
	}

	@Test
	void testRotateDir() {
		Line3D test = new Line3D();
		double dirCoords[] = {0, 2, 0};
		test.setDir(dirCoords);
		Vector3D axis = new Vector3D(1, 1, 0);
		Vector3D expected = new Vector3D(2, 0, 0);
		assertEquals(expected.normalize(), test.rotateDir(Math.PI, axis).getDir());
	}

	@Test
	void testRotatePos() {
		Line3D test = new Line3D();
		double dirCoords[] = {0, 2, 0};
		double posCoords[] = {0,3,0};
		test.setDir(dirCoords);
		test.setPos(posCoords);
		Vector3D axis = new Vector3D(1, 1, 0);
		Vector3D expectedVec = new Vector3D(2, 0, 0);
		Position3D expectedPos = new Position3D(3,0,0);
		assertEquals(expectedVec.normalize(), test.rotateDir(Math.PI, axis).getDir());
		assertEquals(expectedPos, test.rotatePos(Math.PI, axis).getPos());
	}

	@Test
	void testIsParallel() {
		Line3D test1 = new Line3D();
		Line3D test2 = new Line3D();
		double dirCoords1[] = {1, 2, 4};
		double dirCoords2[] = {-2,-4,-8};
		test1.setDir(dirCoords1);
		test2.setDir(dirCoords2);
		assertTrue(test1.isParallel(test2));
		assertTrue(test2.isParallel(test1));
	}

	@Test
	void testIntersectsParallel() {
		Line3D test1 = new Line3D();
		Line3D test2 = new Line3D();
		double dirCoords1[] = {1, 2, 4};
		double dirCoords2[] = {-2,-4,-8};
		
		test1.setDir(dirCoords1);
		test2.setDir(dirCoords2);
		assertEquals(null,test1.intersects(test2));
		assertEquals(null,test2.intersects(test1));
	}
	
	@Test
	void testIntersectsTypical() {
		Line3D test1 = new Line3D();
		Line3D test2 = new Line3D();
		double dirCoords1[] = {1, 2, 3};
		double dirCoords2[] = {-1,5,-7};
		
		double posCoords1[] = {1,1,0};
		double posCoords2[] = {2,3,3};
		
		test1.setDir(dirCoords1);
		test2.setDir(dirCoords2);
		test1.setPos(posCoords1);
		test2.setPos(posCoords2);
		
		Position3D expected = new Position3D(2,3,3);
		assertEquals(expected, test1.intersects(test2));
		assertEquals(expected, test2.intersects(test1));
	}

	@Test
	void testIntersectsNotIntersectsNotParallel() {
		Line3D test1 = new Line3D();
		Line3D test2 = new Line3D();
		double dirCoords1[] = {1, 2, 3};
		double dirCoords2[] = {-1,5,-7};
		
		double posCoords1[] = {1,1,0};
		double posCoords2[] = {2,1,0};
		
		test1.setDir(dirCoords1);
		test2.setDir(dirCoords2);
		test1.setPos(posCoords1);
		test2.setPos(posCoords2);

		assertEquals(null, test1.intersects(test2));
		assertEquals(null, test2.intersects(test1));
	}
	
	@Test
	void testIsParallelTruePlane() {
		Vector3D x = new Vector3D(1, 0, 0);
		Vector3D y = new Vector3D(0, 3.0 / 5, 4.0 / 5);
		Position3D pos = new Position3D(-1, 1, -1);
		Plane testPlane = new Plane(x, y, pos);
		
		Line3D testLine1 = new Line3D(new Vector3D(1,3.0/5,4.0/5), new Position3D(0,1,-1));
		Line3D testLine2 = new Line3D(new Vector3D(1,3.0/5,4.0/5), new Position3D(-1,1,-1));
		assertTrue(testLine1.isParallel(testPlane));
		assertTrue(testLine2.isParallel(testPlane));
	}
	@Test
	void testIsParallelFalsePlane() {
		Vector3D x = new Vector3D(1, 0, 0);
		Vector3D y = new Vector3D(0, 3.0 / 5, 4.0 / 5);
		Position3D pos = new Position3D(-1, 1, -1);
		Plane testPlane = new Plane(x, y, pos);
		
		Line3D testLine1 = new Line3D(new Vector3D(1,0,4.0/5), new Position3D(0,1,-1));
		Line3D testLine2 = new Line3D(new Vector3D(1,0,4.0/5), new Position3D(-1,1,-1));
		assertFalse(testLine1.isParallel(testPlane));
		assertFalse(testLine2.isParallel(testPlane));
	}
	
	@Test
	void testOnLineTrue() {
		Line3D test1 = new Line3D();
		double dirCoords1[] = {1, 2, 3};
		
		double posCoords1[] = {1,1,0};
		
		Position3D point = new Position3D(2, 3, 3);
		
		test1.setDir(dirCoords1);
		test1.setPos(posCoords1);

		assertTrue(test1.onLine(point));
	}
	
	@Test
	void testOnLineFalse() {
		Line3D test1 = new Line3D();
		double dirCoords1[] = {1, 2, 3};
		
		double posCoords1[] = {1,1,0};
		
		Position3D point = new Position3D(2, 7, 3);
		
		test1.setDir(dirCoords1);
		test1.setPos(posCoords1);

		assertFalse(test1.onLine(point));
	}
	
	@Test
	void testSimilarTrue() {
		Line3D test1 = new Line3D();
		Line3D test2 = new Line3D();
		double dirCoords1[] = {1, 2, 3};
		double dirCoords2[] = {-2,-4,-6};
		
		double posCoords1[] = {1,1,0};
		double posCoords2[] = {2,3,3};
		
		test1.setDir(dirCoords1);
		test2.setDir(dirCoords2);
		test1.setPos(posCoords1);
		test2.setPos(posCoords2);

		assertTrue(test1.similar(test2));
		assertTrue(test2.similar(test1));
	}
	
	@Test
	void testSimilarFalseLine() {
		Line3D test1 = new Line3D();
		Line3D test2 = new Line3D();
		double dirCoords1[] = {1, 1, 3};
		double dirCoords2[] = {-2,-4,-6};
		
		double posCoords1[] = {1,1,0};
		double posCoords2[] = {2,3,3};
		
		test1.setDir(dirCoords1);
		test2.setDir(dirCoords2);
		test1.setPos(posCoords1);
		test2.setPos(posCoords2);

		assertFalse(test1.similar(test2));
		assertFalse(test2.similar(test1));
	}
	
	@Test
	void testSimilarFalsePoint() {
		Line3D test1 = new Line3D();
		Line3D test2 = new Line3D();
		double dirCoords1[] = {1, 2, 3};
		double dirCoords2[] = {-2,-4,-6};
		
		double posCoords1[] = {1,1,8};
		double posCoords2[] = {2,3,3};
		
		test1.setDir(dirCoords1);
		test2.setDir(dirCoords2);
		test1.setPos(posCoords1);
		test2.setPos(posCoords2);

		assertFalse(test1.similar(test2));
		assertFalse(test2.similar(test1));
	}
	
	@Test
	void testGetIntersect() {
		Vector3D x = new Vector3D(1, 0, 0);
		Vector3D y = new Vector3D(0, 3.0 / 5, 4.0 / 5);
		Position3D pos = new Position3D(-1, 1, -1);
		Plane plane1 = new Plane(x, y, pos);

		Line3D line3D = new Line3D(new Vector3D(1,8, 6), new Position3D(-1, -7, -7));
		Position3D expected = new Position3D(0, 1, -1);
		assertEquals(expected, line3D.getIntersect(plane1)); 
	}
	
	@Test
	void testGetIntersectNone() {
		Vector3D x = new Vector3D(1, 0, 0);
		Vector3D y = new Vector3D(0, 3.0 / 5, 4.0 / 5);
		Position3D pos = new Position3D(-1, 1, -1);
		Plane plane1 = new Plane(x, y, pos);

		Line3D line3D = new Line3D(new Vector3D(2,3, 4), new Position3D(-1, 1, -1));
		assertEquals(null, line3D.getIntersect(plane1)); 
	}
}
