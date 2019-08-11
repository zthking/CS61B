package bearmaps;

import java.util.List;

/**
 * Implementation of 2 dimension KD Tree.
 * This is for CS61B Spring 2019 Project 2b.
 * https://sp19.datastructur.es/materials/proj/proj2ab/proj2ab
 * Solution is not tested by Autograder.
 */
public class KDTree {
    /**
     * Root to save point and the depth of point.
     * First node in the root has a depth of 0.
     */
    private Node root;

    /**
     * Initialize a KDTree using given list of points.
     *
     * @param points Input list of points.
     */
    public KDTree(List<Point> points) {
        if (points == null) {
            throw new IllegalArgumentException("Input cannot be null!");
        }
        for (Point p : points) {
            root = buildKDTree(root, p, true);
        }
    }

    /**
     * Helper method to convert input list to KD Tree.
     */
    private Node buildKDTree(Node n, Point newPoint, Boolean level) {
        if (n == null) {
            return new Node(newPoint, level);
        }
        if (n.evenLevel && n.p.getX() > newPoint.getX()) {
            n.left = buildKDTree(n.left, newPoint, !level);
        } else if (n.evenLevel && n.p.getX() <= newPoint.getX()) {
            n.right = buildKDTree(n.right, newPoint, !level);
        } else if (n.evenLevel && n.p.getY() > newPoint.getY()) {
            n.left = buildKDTree(n.left, newPoint, !level);
        } else {
            n.right = buildKDTree(n.right, newPoint, !level);
        }
        return n;
    }

    /**
     * Return the nearest point in the KD Tree at given coordinate.
     *
     * @param x x axis
     * @param y y axis
     * @return Nearest point at given coordinate.
     */
    public Point nearest(double x, double y) {
        Point comp = new Point(x, y);
        if (root.left == null && root.right == null) {
            return root.p;
        } else if (root.left == null) {
            return findBest(root.right, comp);
        } else if (root.right == null) {
            return findBest(root.left, comp);
        }

        Point leftSidePoint = findBest(root.left, comp);
        double leftSideDistance = Point.distance(leftSidePoint, comp);

        Point rightSidePoint = findBest(root.right, comp);
        double rightSideDistance = Point.distance(rightSidePoint, comp);

        if (leftSideDistance < rightSideDistance) {
            return leftSidePoint;
        } else {
            return rightSidePoint;
        }
    }

    /**
     * Helper method to find the nearest point.
     */
    private Point findBest(Node n, Point p) {
        if (n.left == null && n.right == null) {
            return n.p;
        } else if (n.left == null) {
            n = n.right;
        } else if (n.right == null) {
            n = n.left;
        } else if (n.evenLevel && n.p.getX() > p.getX()) {
            n = n.left;
        } else if (n.evenLevel && n.p.getX() <= p.getX()) {
            n = n.right;
        } else if (n.evenLevel && n.p.getY() > p.getY()) {
            n = n.left;
        } else {
            n = n.right;
        }
        return findBest(n, p);
    }

    /**
     * Node to save point and depth.
     * If the depth of a node is even, evenLevel will be true. Otherwise, false.
     */
    private class Node {
        private Point p;
        private Boolean evenLevel;
        private Node left, right;

        private Node(Point p, Boolean evenLevel) {
            this.p = p;
            this.evenLevel = evenLevel;
        }
    }
}
