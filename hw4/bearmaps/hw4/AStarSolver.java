package bearmaps.hw4;

import bearmaps.proj2ab.DoubleMapPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.*;

/**
 * Implementation of A* algorithm to find shortest path.
 * This is prepared for HW4 of CS61b Spring 2019.
 * https://sp19.datastructur.es/materials/hw/hw4/hw4
 * Solution is not tested by Autograder.
 * Solution gives different result on wordladderpuzzle test
 * comparing to the solution given in the lecture..
 */
public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private double timeSpent;
    private SolverOutcome outcome;
    private HashMap<Vertex, Double> solutionMap;
    private HashMap<Vertex, Vertex> solutionVertex;
    private Vertex start;
    private Vertex end;
    private int numStatesExplored;

    /**
     * Constructor to find shortest path.
     * @param input Input that contains all possible vertices
     *              and estimated distance from one vertex to goal vertex.
     * @param start Starting vertex.
     * @param end Goal vertex.
     * @param timeout Maximum time in second that algorithm can run.
     */
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        if (timeout < 0 || input == null || start == null || end == null) {
            throw new IllegalArgumentException();
        }

        this.start = start;
        this.end = end;
        solutionMap = new HashMap<>();
        solutionVertex = new HashMap<>();
        HashSet<Vertex> visited = new HashSet<>();
        numStatesExplored = 0;
        Stopwatch sw = new Stopwatch();

        DoubleMapPQ<Vertex> vertexPQ = new DoubleMapPQ<>();
        vertexPQ.add(start, input.estimatedDistanceToGoal(start, end));

        solutionMap.put(start, 0.00);

        while (vertexPQ.getSmallest() != null) {
            Vertex p = vertexPQ.removeSmallest();
            double distanceToP = solutionMap.get(p);
            numStatesExplored += 1;

            if (!visited.contains(p)) {
                visited.add(p);

                if (p.equals(end)) {
                    outcome = SolverOutcome.SOLVED;
                    break;
                }

                if (timeSpent > timeout) {
                    outcome = SolverOutcome.TIMEOUT;
                    break;
                }

                List<WeightedEdge<Vertex>> neighbourEdges = input.neighbors(p);
                for (WeightedEdge<Vertex> e : neighbourEdges) {
                    Vertex q = e.to();
                    double d = e.weight();
                    double newDToQ = distanceToP + d;
                    double estQToEnd = input.estimatedDistanceToGoal(q, end);

                    if (!visited.contains(q)) {
                        if (!solutionMap.containsKey(q) || newDToQ < solutionMap.get(q)) {
                            solutionMap.put(q, newDToQ);
                            solutionVertex.put(q, p);
                            if (!vertexPQ.contains(q)) {
                                vertexPQ.add(q, newDToQ + estQToEnd);
                            } else {
                                vertexPQ.changePriority(q, newDToQ + estQToEnd);
                            }
                        }
                    }
                }
            }
            timeSpent = sw.elapsedTime();
        }
    }

    /**
     * Return SOLVED if the shortest path is found.
     * Return UNSOLVED if the goal is not in Input.
     * Return TIMEOUT if the total run time is greater than timeout.
     */
    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    /**
     * Return vertices in the shortest path in a list.
     */
    @Override
    public List<Vertex> solution() {
        if (outcome == SolverOutcome.TIMEOUT || outcome == SolverOutcome.UNSOLVABLE) {
            return null;
        }
        LinkedList<Vertex> solution = new LinkedList<>();
        Vertex p = solutionVertex.get(end);
        solution.addFirst(end);
        solution.addFirst(p);
        while (!p.equals(start)) {
            p = solutionVertex.get(p);
            solution.addFirst(p);
        }
        return solution;
    }

    /**
     * Return the total length of the shortest path.
     */
    @Override
    public double solutionWeight() {
        if (outcome == SolverOutcome.TIMEOUT || outcome == SolverOutcome.UNSOLVABLE) {
            return 0.00;
        }
        return solutionMap.get(end);
    }

    /**
     * Return total numbers of vertices explored.
     */
    @Override
    public int numStatesExplored() {
        return numStatesExplored;
    }

    /**
     * Return total run time.
     */
    @Override
    public double explorationTime() {
        return timeSpent;
    }
}
