package bearmaps;

import java.util.List;

/**
 * Find the closest point to a given coordinate.
 */
public class NaivePointSet implements PointSet {
    /**
     * A list include all points.
     */
    private List<Point> points;

    /**
     * Initialize a NaivePointSet using a list of points.
     */
    public NaivePointSet(List<Point> points) {
        this.points = points;
    }

    /**
     * Given a coordinate, return the nearest point.
     */
    @Override
    public Point nearest(double x, double y) {
        Point comp = new Point(x, y);
        Point nearestPoint = points.get(0);
        double d = Point.distance(nearestPoint, comp);
        for (Point p : points) {
            double newDistance = Point.distance(p, comp);
            if (newDistance < d) {
                d = newDistance;
                nearestPoint = p;
            }
        }
        return nearestPoint;
    }
}
