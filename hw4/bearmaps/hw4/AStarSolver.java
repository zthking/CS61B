package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private double timeSpent;
    private SolverOutcome outcome;
    private List<Vertex> solution;
    private HashMap<Vertex, Double> solutionMap;
    private Vertex end;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        if (timeout < 0 || input == null || start == null || end == null) {
            throw new IllegalArgumentException();
        }

        this.end = end;
        solution = new ArrayList<>();
        solutionMap = new HashMap<>();
        //Stopwatch sw = new Stopwatch();

        ArrayHeapMinPQ<Vertex> vertexPQ = new ArrayHeapMinPQ<>();
        vertexPQ.add(start, input.estimatedDistanceToGoal(start, end));
        solution.add(start);
        solutionMap.put(start, 0.00);

        while (!vertexPQ.getSmallest().equals(end)) {
            Vertex p = vertexPQ.getSmallest();
            double distanceToP = solutionMap.get(p);
            List<WeightedEdge<Vertex>> neighbourEdges = input.neighbors(p);
            vertexPQ.removeSmallest();

            if (neighbourEdges == null) {
                outcome = SolverOutcome.UNSOLVABLE;
                return;
            }

            for (WeightedEdge<Vertex> e : neighbourEdges) {
                Vertex q = e.to();
                double d = e.weight();
                double newDToQ = distanceToP + d;

                if (!solutionMap.containsKey(q)) {
                    solutionMap.put(q, newDToQ);
                    vertexPQ.add(q, newDToQ +
                            input.estimatedDistanceToGoal(q, end));
                } else if (newDToQ < solutionMap.get(q)) {
                    solutionMap.put(q, newDToQ);
                    vertexPQ.changePriority(q, newDToQ +
                            input.estimatedDistanceToGoal(e.to(), end));
                }

                if (q.equals(end)) {
                    outcome = SolverOutcome.SOLVED;
                }
            }
            solution.add(vertexPQ.getSmallest());
        }
    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {
        return solutionMap.get(end);
    }

    @Override
    public int numStatesExplored() {
        return solution.size() - 1;
    }

    @Override
    public double explorationTime() {
        return timeSpent;
    }
}
