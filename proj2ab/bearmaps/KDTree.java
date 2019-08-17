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
     * @param points Input list of points.
     */
    public KDTree(List<Point> points) {
        if (points == null) {
            throw new IllegalArgumentException("Input cannot be null!");
        }
        for (Point p : points) {
            Point newPoint = new Point(p.getX(), p.getY());
            root = buildKDTree(root, newPoint, true);
        }
    }

    /**
     * Helper method to convert input list to KD Tree.
     */
    private Node buildKDTree(Node n, Point newPoint, Boolean level) {
        if (n == null) {
            return new Node(newPoint, level);
        }
        if (level) {
            if (n.p.getX() > newPoint.getX()) {
                n.left = buildKDTree(n.left, newPoint, false);
            } else {
                n.right = buildKDTree(n.right, newPoint, false);
            }
        } else {
            if (n.p.getY() > newPoint.getY()) {
                n.left = buildKDTree(n.left, newPoint, true);
            } else {
                n.right = buildKDTree(n.right, newPoint, true);
            }
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
        Point cmp = new Point(x, y);
        Node bestNode;
        if (root.left == null && root.right == null) {
            return root.p;
        } else if (root.left == null) {
            bestNode = findBest(root.right, cmp, new Node(root.p, root.evenLevel));
            return bestNode.p;
        } else if (root.right == null) {
            bestNode = findBest(root.left, cmp, new Node(root.p, root.evenLevel));
            return bestNode.p;
        }
        bestNode = findBest(root, cmp, new Node(root.p, root.evenLevel));
        return bestNode.p;
    }

    /**
     * Helper method to find the nearest point.
     */
    private Node findBest(Node n, Point point, Node best) {
        if (n == null) {
            return best;
        }
        if (Point.distance(n.p, point) < Point.distance(best.p, point)) {
            best = n;
        }
        Node goodSide;
        Node badSide;
        if (n.evenLevel) {
            if (n.p.getX() > point.getX()) {
                goodSide = n.left;
                badSide = n.right;
            } else {
                goodSide = n.right;
                badSide = n.left;
            }
        } else {
            if (n.p.getY() > point.getY()) {
                goodSide = n.left;
                badSide = n.right;
            } else {
                goodSide = n.right;
                badSide = n.left;
            }
        }
        best = findBest(goodSide, point, best);

        if (n.evenLevel) {
            if (Point.distance(point, best.p) > Math.pow((point.getX() - n.p.getX()), 2)) {
              best = findBest(badSide, point, best);
            }
        } else {
            if (Point.distance(point, best.p) > Math.pow((point.getY() - n.p.getY()), 2)) {
                best = findBest(badSide, point, best);
            }
        }

        return best;
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
