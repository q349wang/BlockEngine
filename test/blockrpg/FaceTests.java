package blockrpg;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FaceTests {

	@Test
	void testGetCenter3D() {
		Position2D[] points = { new Position2D(0, 0), new Position2D(0, 10), new Position2D(10, 10),
				new Position2D(10, 0) };
		Plane plane = new Plane(new Vector3D(0, 1, 0), new Vector3D(0, 0, 1), new Position3D());
		Face test = new Face(points, points.length, plane,
				new Perspective(new double[] { 0, 0, 0 }, new double[] { 1, 0, 0 }, new double[] { 0, 1, 0 }));

		Position3D expected = new Position3D(0, 5, 5);

		assertEquals(expected, test.getCenter3D());
	}

	@Test
	void testGetCenter2D() {
		Position2D[] points = { new Position2D(-4, -4), new Position2D(-4, 4), new Position2D(4, 4),
				new Position2D(4, -4) };
		Plane plane = new Plane(new Vector3D(0, 1, 0), new Vector3D(0, 0, 1), new Position3D(3, 1, 0));
		Face test = new Face(points, points.length, plane,
				new Perspective(new double[] { 0, 1, 0 }, new double[] { 1, 0, 0 }, new double[] { 0, 1, 0 }));

		Position2D expected = new Position2D(0, 0);

		assertEquals(expected, test.getCenter2D());
	}
	
	@Test
	void testGetBound2D() {
		Position2D[] points = { new Position2D(0, 4), new Position2D(0, -4)};
		Plane plane = new Plane(new Vector3D(0, 1, 0), new Vector3D(0, 0, 1), new Position3D(3, 0, 0));
		Face test = new Face(points, points.length, plane,
				new Perspective(new double[] { 0, 0, 0 }, new double[] { 1, 0, 0 }, new double[] { 0, 1, 0 }));
		test.getPOV().setZoom(5);
		test.setPoints();

		assertTrue(Math.abs(4-test.getBound2D()) < Coord3D.ERROR);
	}
	
	@Test
	void testGetBound3D() {
		Position2D[] points = { new Position2D(-3, -4), new Position2D(-3, 4), new Position2D(3, 4),
				new Position2D(3, -4) };
		Plane plane = new Plane(new Vector3D(0, 1, 0), new Vector3D(0, 0, 1), new Position3D(3, 0, 0));
		Face test = new Face(points, points.length, plane,
				new Perspective(new double[] { 0, 0, 0 }, new double[] { 1, 0, 0 }, new double[] { 0, 1, 0 }));


		assertTrue(Math.abs(5-test.getBound3D()) < Coord3D.ERROR);
	}

	@Test
	void testMayIntersect2DTrue() {
		Perspective pov = new Perspective(new double[] { -10, 0, 0 }, new double[] { 1, 0, 0 }, new double[] { 0, 1, 0 });
		Position2D[] points1 = { new Position2D(0, 0), new Position2D(0, 10), new Position2D(10, 10),
				new Position2D(10, 0) };
		Plane plane1 = new Plane(new Vector3D(0, 1, 0), new Vector3D(0, 0, 1), new Position3D());
		Face test1 = new Face(points1, points1.length, plane1, pov);

		Position2D[] points2 = { new Position2D(0, 0), new Position2D(0, 10), new Position2D(10, 10),
				new Position2D(10, 0) };
		Plane plane2 = new Plane(new Vector3D(0, 1, 0), new Vector3D(0, 0, 1), new Position3D(0, 0, 1));
		Face test2 = new Face(points2, points2.length, plane2, pov);

		assertTrue(test1.mayIntersect2D(test2));
		assertTrue(test2.mayIntersect2D(test1));
	}
	
	@Test
	void testMayIntersect2DFalse() {
		Perspective pov = new Perspective(new double[] { -10, 0, 0 }, new double[] { 1, 0, 0 }, new double[] { 0, 1, 0 });
		Position2D[] points1 = { new Position2D(0, 0), new Position2D(0, 10), new Position2D(10, 10),
				new Position2D(10, 0) };
		Plane plane1 = new Plane(new Vector3D(0, 1, 0), new Vector3D(0, 0, 1), new Position3D());
		Face test1 = new Face(points1, points1.length, plane1, pov);

		Position2D[] points2 = { new Position2D(0, 0), new Position2D(0, 10), new Position2D(10, 10),
				new Position2D(10, 0) };
		Plane plane2 = new Plane(new Vector3D(0, 1, 0), new Vector3D(0, 0, 1), new Position3D(0, 300, 300));
		Face test2 = new Face(points2, points2.length, plane2, pov);

		assertFalse(test1.mayIntersect2D(test2));
		assertFalse(test2.mayIntersect2D(test1));
	}

	@Test
	void testMayIntersect2DItself() {
		Perspective pov = new Perspective(new double[] { 0, 0, 0 }, new double[] { 1, 0, 0 }, new double[] { 0, 1, 0 });
		Position2D[] points1 = { new Position2D(0, 0), new Position2D(0, 10), new Position2D(10, 10),
				new Position2D(10, 0) };
		Plane plane1 = new Plane(new Vector3D(0, 1, 0), new Vector3D(0, 0, 1), new Position3D());
		Face test1 = new Face(points1, points1.length, plane1, pov);
		
		assertTrue(test1.mayIntersect2D(test1));
	}
	
	@Test
	void testMayIntersect3DTrue() {
		Perspective pov = new Perspective(new double[] { 0, 0, 0 }, new double[] { 1, 0, 0 }, new double[] { 0, 1, 0 });
		Position2D[] points1 = { new Position2D(0, 0), new Position2D(0, 10), new Position2D(10, 10),
				new Position2D(10, 0) };
		Plane plane1 = new Plane(new Vector3D(0, 1, 0), new Vector3D(0, 0, 1), new Position3D());
		Face test1 = new Face(points1, points1.length, plane1, pov);

		Position2D[] points2 = { new Position2D(0, 0), new Position2D(0, 10), new Position2D(10, 10),
				new Position2D(10, 0) };
		Plane plane2 = new Plane(new Vector3D(1, 1, 0), new Vector3D(0, 0, 1), new Position3D(1, 0,0));
		Face test2 = new Face(points2, points2.length, plane2, pov);

		assertTrue(test1.mayIntersect3D(test2));
		assertTrue(test2.mayIntersect3D(test1));
	}
	
	@Test
	void testMayIntersect3DFalse() {
		Perspective pov = new Perspective(new double[] { -1, 0, 0 }, new double[] { 1, 0, 0 }, new double[] { 0, 1, 0 });
		Position2D[] points1 = { new Position2D(0, 0), new Position2D(0, 10), new Position2D(10, 10),
				new Position2D(10, 0) };
		Plane plane1 = new Plane(new Vector3D(0, 1, 0), new Vector3D(0, 0, 1), new Position3D());
		Face test1 = new Face(points1, points1.length, plane1, pov);

		Position2D[] points2 = { new Position2D(0, 0), new Position2D(0, 10), new Position2D(10, 10),
				new Position2D(10, 0) };
		Plane plane2 = new Plane(new Vector3D(0, 1, 0), new Vector3D(0, 0, 1), new Position3D(20, 0, 1));
		Face test2 = new Face(points2, points2.length, plane2, pov);

		assertFalse(test1.mayIntersect3D(test2));
		assertFalse(test2.mayIntersect3D(test1));
	}
	
	@Test
	void testMayIntersect3DItself() {
		Perspective pov = new Perspective(new double[] { 0, 0, 0 }, new double[] { 1, 0, 0 }, new double[] { 0, 1, 0 });
		Position2D[] points1 = { new Position2D(0, 0), new Position2D(0, 10), new Position2D(10, 10),
				new Position2D(10, 0) };
		Plane plane1 = new Plane(new Vector3D(0, 1, 0), new Vector3D(0, 0, 1), new Position3D());
		Face test1 = new Face(points1, points1.length, plane1, pov);
		
		assertTrue(test1.mayIntersect3D(test1));
	}


}
