package blockrpg;

import java.util.ArrayList;
import java.util.Comparator;

public class FirstFaceCompare implements Comparator<Face> {

	@Override
	public int compare(Face o1, Face o2) {

		ArrayList<Position2D> intersects = new ArrayList<Position2D>();
		intersects.clear();

		for (int i = 0; i < o1.getEdges2D().length - 1; i++) {

			for (int j = 0; j < o2.getEdges2D().length - 1; j++) {

				Position2D po1 = o1.getEdges2D()[i].intersects(o2.getEdges2D()[j]);
				if (po1 != null) { // Test for parallel
					// Test for bounds
					if (o1.inBounds(po1, o1.getViewPoints()[i], o1.getViewPoints()[i + 1])) {
						if (o2.inBounds(po1, o2.getViewPoints()[j], o2.getViewPoints()[j + 1])) {
							intersects.add(po1);
						}
					}
				} else if (o1.getEdges2D()[i].similar(o2.getEdges2D()[j])) {
					if (o1.inBounds(o1.getViewPoints()[i], o1.getViewPoints()[i], o1.getViewPoints()[i + 1])) {
						if (o2.inBounds(o1.getViewPoints()[i], o2.getViewPoints()[j], o2.getViewPoints()[j + 1])) {
							intersects.add(o1.getViewPoints()[i]);
						}
					}

					if (o1.inBounds(o1.getViewPoints()[i + 1], o1.getViewPoints()[i], o1.getViewPoints()[i + 1])) {
						if (o2.inBounds(o1.getViewPoints()[i + 1], o2.getViewPoints()[j], o2.getViewPoints()[j + 1])) {
							intersects.add(o1.getViewPoints()[i + 1]);
						}
					}

					if (o1.inBounds(o2.getViewPoints()[j], o1.getViewPoints()[i], o1.getViewPoints()[i + 1])) {
						if (o2.inBounds(o2.getViewPoints()[j], o2.getViewPoints()[j], o2.getViewPoints()[j + 1])) {
							intersects.add(o2.getViewPoints()[j]);
						}
					}

					if (o1.inBounds(o2.getViewPoints()[j + 1], o1.getViewPoints()[i], o1.getViewPoints()[i + 1])) {
						if (o2.inBounds(o2.getViewPoints()[j + 1], o2.getViewPoints()[j], o2.getViewPoints()[j + 1])) {
							intersects.add(o2.getViewPoints()[j + 1]);
						}
					}
				}
			}

			Position2D po1 = o1.getEdges2D()[i].intersects(o2.getEdges2D()[o2.getNumPoints() - 1]);
			if (po1 != null) { // Test for parallel
				// Test for bounds
				if (o1.inBounds(po1, o1.getViewPoints()[i], o1.getViewPoints()[i + 1])) {
					if (o2.inBounds(po1, o2.getViewPoints()[o2.getNumPoints() - 1], o2.getViewPoints()[0])) {
						intersects.add(po1);
					}
				}
			} else if (o1.getEdges2D()[i].similar(o2.getEdges2D()[o2.getNumPoints() - 1])) {
				if (o1.inBounds(o1.getViewPoints()[i], o1.getViewPoints()[i], o1.getViewPoints()[i + 1])) {
					if (o2.inBounds(o1.getViewPoints()[i], o2.getViewPoints()[o2.getNumPoints() - 1],
							o2.getViewPoints()[0])) {
						intersects.add(o1.getViewPoints()[i]);
					}
				}

				if (o1.inBounds(o1.getViewPoints()[i + 1], o1.getViewPoints()[i], o1.getViewPoints()[i + 1])) {
					if (o2.inBounds(o1.getViewPoints()[i + 1], o2.getViewPoints()[o2.getNumPoints() - 1],
							o2.getViewPoints()[0])) {
						intersects.add(o1.getViewPoints()[i + 1]);
					}
				}

				if (o1.inBounds(o2.getViewPoints()[o2.getNumPoints() - 1], o1.getViewPoints()[i], o1.getViewPoints()[i + 1])) {
					if (o2.inBounds(o2.getViewPoints()[o2.getNumPoints() - 1], o2.getViewPoints()[o2.getNumPoints() - 1],
							o2.getViewPoints()[0])) {
						intersects.add(o2.getViewPoints()[o2.getNumPoints() - 1]);
					}
				}

				if (o1.inBounds(o2.getViewPoints()[0], o1.getViewPoints()[i], o1.getViewPoints()[i + 1])) {
					if (o2.inBounds(o2.getViewPoints()[0], o2.getViewPoints()[o2.getNumPoints() - 1],
							o2.getViewPoints()[0])) {
						intersects.add(o2.getViewPoints()[0]);
					}
				}
			}
		}

		for (int j = 0; j < o2.getEdges2D().length - 1; j++) {

			Position2D po1 = o1.getEdges2D()[o1.getNumPoints() - 1].intersects(o2.getEdges2D()[j]);
			if (po1 != null) { // Test for parallel
				// Test for bounds
				if (o1.inBounds(po1, o1.getViewPoints()[o1.getNumPoints() - 1], o1.getViewPoints()[0])) {
					if (o2.inBounds(po1, o2.getViewPoints()[j], o2.getViewPoints()[j + 1])) {

						intersects.add(po1);
					}
				}
			} else if (o1.getEdges2D()[o1.getNumPoints() - 1].similar(o2.getEdges2D()[j])) {
				if (o1.inBounds(o1.getViewPoints()[o1.getNumPoints() - 1], o1.getViewPoints()[o1.getNumPoints() - 1],
						o1.getViewPoints()[0])) {
					if (o2.inBounds(o1.getViewPoints()[o1.getNumPoints() - 1], o2.getViewPoints()[j],
							o2.getViewPoints()[j + 1])) {
						intersects.add(o1.getViewPoints()[o1.getNumPoints() - 1]);
					}
				}

				if (o1.inBounds(o1.getViewPoints()[0], o1.getViewPoints()[o1.getNumPoints() - 1], o1.getViewPoints()[0])) {
					if (o2.inBounds(o1.getViewPoints()[0], o2.getViewPoints()[j], o2.getViewPoints()[j + 1])) {
						intersects.add(o1.getViewPoints()[0]);
					}
				}

				if (o1.inBounds(o2.getViewPoints()[j], o1.getViewPoints()[o1.getNumPoints() - 1], o1.getViewPoints()[0])) {
					if (o2.inBounds(o2.getViewPoints()[j], o2.getViewPoints()[j + 1], o2.getViewPoints()[j + 1])) {
						intersects.add(o2.getViewPoints()[j]);
					}
				}

				if (o1.inBounds(o2.getViewPoints()[j + 1], o1.getViewPoints()[o1.getNumPoints() - 1], o1.getViewPoints()[0])) {
					if (o2.inBounds(o2.getViewPoints()[j + 1], o2.getViewPoints()[j], o2.getViewPoints()[j + 1])) {
						intersects.add(o2.getViewPoints()[j + 1]);
					}
				}
			}
		}

		Position2D po1 = o1.getEdges2D()[o1.getNumPoints() - 1].intersects(o2.getEdges2D()[o2.getNumPoints() - 1]);
		if (po1 != null) { // Test for parallel
			// Test for bounds
			if (o1.inBounds(po1, o1.getViewPoints()[o1.getNumPoints() - 1], o1.getViewPoints()[0])) {
				if (o2.inBounds(po1, o2.getViewPoints()[o2.getNumPoints() - 1], o2.getViewPoints()[0])) {

					intersects.add(po1);
				}
			}
		} else if (o1.getEdges2D()[o1.getNumPoints() - 1].similar(o2.getEdges2D()[o2.getNumPoints() - 1])) {
			if (o1.inBounds(o1.getViewPoints()[o1.getNumPoints() - 1], o1.getViewPoints()[o1.getNumPoints() - 1],
					o1.getViewPoints()[0])) {
				if (o2.inBounds(o1.getViewPoints()[o1.getNumPoints() - 1], o2.getViewPoints()[o2.getNumPoints() - 1],
						o2.getViewPoints()[0])) {
					intersects.add(o1.getViewPoints()[o1.getNumPoints() - 1]);
				}
			}

			if (o1.inBounds(o1.getViewPoints()[0], o1.getViewPoints()[o1.getNumPoints() - 1], o1.getViewPoints()[0])) {
				if (o2.inBounds(o1.getViewPoints()[0], o2.getViewPoints()[o2.getNumPoints() - 1], o2.getViewPoints()[0])) {
					intersects.add(o1.getViewPoints()[0]);
				}
			}

			if (o1.inBounds(o2.getViewPoints()[o2.getNumPoints() - 1], o1.getViewPoints()[o1.getNumPoints() - 1],
					o1.getViewPoints()[0])) {
				if (o2.inBounds(o2.getViewPoints()[o2.getNumPoints() - 1], o2.getViewPoints()[o2.getNumPoints() - 1],
						o2.getViewPoints()[0])) {
					intersects.add(o2.getViewPoints()[o2.getNumPoints() - 1]);
				}
			}

			if (o1.inBounds(o2.getViewPoints()[0], o1.getViewPoints()[o1.getNumPoints() - 1], o1.getViewPoints()[0])) {
				if (o2.inBounds(o2.getViewPoints()[0], o2.getViewPoints()[o2.getNumPoints() - 1], o2.getViewPoints()[0])) {
					intersects.add(o2.getViewPoints()[0]);
				}
			}

		}
		
		for (Position2D vertex : o1.getViewPoints()) {
			if (o2.inShape(vertex)) {
				intersects.add(vertex);
			}
		}
		
		for (Position2D vertex : o2.getViewPoints()) {
			if (o1.inShape(vertex)) {
				intersects.add(vertex);
			}
		}

		if (intersects.size() == 0) {
			double o1Dis = o1.getPOV().getPos().totDistanceFrom(o1.getCenter3D());
			double o2Dis = o2.getPOV().getPos().totDistanceFrom(o2.getCenter3D());

			if (Math.abs(o1Dis - o2Dis) < Coord3D.ERROR) {

				o1.getComps().put(o2, 0);
				o2.getComps().put(o1, 0);
				return 0;
			} else if (o1Dis > o2Dis) {

				o1.getComps().put(o2, -1);
				o2.getComps().put(o1, 1);
				return -1;
			} else {

				o1.getComps().put(o2, 1);
				o2.getComps().put(o1, -1);
				return 1;
			}
		}

		Position2D center = new Position2D();

		for (int i = 0; i < intersects.size(); i++) {
			double[] coords = center.getCoord();
			for (int j = 0; j < 2; j++) {
				coords[j] += intersects.get(i).getCoord()[j];
			}
			center.setCoord(coords);
		}

		center.setCoord(center.toVec().multiply(1.0 / intersects.size()).toPos().getCoord());

		
		Position3D o1Real = o1.getPOV().getRealPoint(center, o1.getPlane());
		Position3D o2Real = o2.getPOV().getRealPoint(center, o2.getPlane());

		
		if (o1Real == null || o2Real == null) {
			double o1Dis = o1.getPOV().getPos().totDistanceFrom(o1.getCenter3D());
			double o2Dis = o2.getPOV().getPos().totDistanceFrom(o2.getCenter3D());

			if (Math.abs(o1Dis - o2Dis) < Coord3D.ERROR) {

				o1.getComps().put(o2, 0);
				o2.getComps().put(o1, 0);
				return 0;
			} else if (o1Dis > o2Dis) {

				o1.getComps().put(o2, -1);
				o2.getComps().put(o1, 1);
				return -1;
			} else {

				o1.getComps().put(o2, 1);
				o2.getComps().put(o1, -1);
				return 1;
			}
		}
		
		Line3D ray = new Line3D(o1.getPOV().getPos(), o1Real);
		double o1Dis = o1.getPOV().getPos().totDistanceFrom(o1.getPlane().getIntersect(ray));
		double o2Dis = o2.getPOV().getPos().totDistanceFrom(o2.getPlane().getIntersect(ray));

		if (Math.abs(o1Dis - o2Dis) < Coord3D.ERROR) {

			o1.getComps().put(o2, 0);
			o2.getComps().put(o1, 0);
			return 0;
		} else if (o1Dis > o2Dis) {

			o1.getComps().put(o2, -1);
			o2.getComps().put(o1, 1);
			return -1;
		} else {

			o1.getComps().put(o2, 1);
			o2.getComps().put(o1, -1);
			return 1;
		}
	}

}
