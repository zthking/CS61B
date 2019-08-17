package bearmaps;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class KDTreeTest {
    /**
     * Initialize a random variable.
     */
    private Random r = new Random(500);

    /**
     * Test one sample point.
     * Test will fail if the KD-tree does not test bad side.
     */
    @Test
    public void samplePointTest() {
        Point p1 = new Point(11, 10);
        Point p2 = new Point(4, 7);
        Point p3 = new Point(16, 10);
        Point p4 = new Point(9, 4);
        Point p5 = new Point(7, 13);
        Point p6 = new Point(15, 3);
        Point p7 = new Point(14, 11);

        List<Point> points = List.of(p1, p2, p3, p4, p5, p6, p7);
        NaivePointSet np = new NaivePointSet(points);
        KDTree kd = new KDTree(points);
        Point npActual = np.nearest(14, 9);
        Point kdActual = kd.nearest(14, 9);
        Point expected = new Point(14, 11);

        assertEquals(expected, npActual);
        assertEquals(expected, kdActual);
    }

    /**
     * Test 1000 random points with 1000 runs.
     */
    @Test
    public void randomPointTest() {
        List<Point> points = randomPoints(1000);
        NaivePointSet np = new NaivePointSet(points);
        KDTree kd = new KDTree(points);
        for (int i = 0; i < 5; i++) {
            double x = r.nextDouble();
            double y = r.nextDouble();
            Point npNearest = np.nearest(x, y);
            Point kdNearest = kd.nearest(x, y);
            assertEquals(npNearest, kdNearest);
        }
    }

    /**
     * Generate one random point with x-axis and y-axis values between 0 and 1.
     * @return One random point.
     */
    private Point randomPoint() {
        double x = r.nextDouble();
        double y = r.nextDouble();
        return new Point(x, y);
    }

    /**
     * Generate a list with N random points.
     * @param N Total points.
     * @return A list with N random points.
     */
    private List<Point> randomPoints(int N) {
        List<Point> points = new ArrayList<>(N);
        for (int i = 0; i < N; i += 1) {
            points.add(randomPoint());
        }
        return points;
    }

    public static void main(String[] args) {
        jh61b.junit.TestRunner.runTests(KDTreeTest.class);
    }
}
