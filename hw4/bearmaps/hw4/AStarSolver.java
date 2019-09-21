package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import bearmaps.proj2ab.DoubleMapPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private double timeSpent;
    private SolverOutcome outcome;
    private ArrayList<Vertex> solution;
    private HashMap<Vertex, Double> solutionMap;
    AStarGraph<Vertex> input;
    private Vertex end;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        if (timeout < 0 || input == null || start == null || end == null) {
            throw new IllegalArgumentException();
        }

        this.input = input;
        this.end = end;
        solution = new ArrayList<>();
        solutionMap = new HashMap<>();
        HashSet<Vertex> visited = new HashSet<>();
        //Stopwatch sw = new Stopwatch();

        DoubleMapPQ<Vertex> vertexPQ = new DoubleMapPQ<>();
        vertexPQ.add(start, input.estimatedDistanceToGoal(start, end));

        solution.add(start);
        solutionMap.put(start, 0.00);

        //solution = relax(start, solution);

        while (vertexPQ.getSmallest() != null) {
            Vertex p = vertexPQ.removeSmallest();
            double distanceToP = solutionMap.get(p);


            if (!visited.contains(p)) {
                visited.add(p);

                if (p.equals(end)) {
                    outcome = SolverOutcome.SOLVED;
                    break;
                }

                List<WeightedEdge<Vertex>> neighbourEdges = input.neighbors(p);
                for (WeightedEdge<Vertex> e : neighbourEdges) {
                    Vertex q = e.to();
                    double d = e.weight();
                    double newDToQ = distanceToP + d;
                    double estQToEnd = input.estimatedDistanceToGoal(q, end);
                    System.out.println(p.toString() + " " +q.toString() + " " + d + " " + newDToQ);

                    if (!visited.contains(q)) {
                        if (!solutionMap.containsKey(q)) {
                            solutionMap.put(q, newDToQ);
                            vertexPQ.add(q, newDToQ + estQToEnd);
                        } else if (newDToQ < solutionMap.get(q)) {
                            solutionMap.put(q, newDToQ);
                            if (!vertexPQ.contains(q)) {
                                vertexPQ.add(q, newDToQ + estQToEnd);
                            } else {
                                vertexPQ.changePriority(q, newDToQ + estQToEnd);
                            }
                        }
                    }
                }
                solution.add(vertexPQ.getSmallest());
            }
        }
    }

    private ArrayList<Vertex> relax(Vertex p,
                                    ArrayList<Vertex> solutionHelper) {
        List<WeightedEdge<Vertex>> neighbourEdges = input.neighbors(p);
        ArrayHeapMinPQ<Vertex> vertexPQ = new ArrayHeapMinPQ<>();

        if (neighbourEdges == null) {
            outcome = SolverOutcome.UNSOLVABLE;
            return null;
        }
        for (WeightedEdge<Vertex> e : neighbourEdges) {
            Vertex q = e.to();
            double d = e.weight();
            double distanceToP = solutionMap.get(p);
            double newDToQ = distanceToP + d;

            if (q.equals(end)) {
                outcome = SolverOutcome.SOLVED;
                solutionMap.put(end, newDToQ);
                solutionHelper.add(end);
                return solutionHelper;
            } else {
                if (!solutionMap.containsKey(q)) {
                    solutionMap.put(q, newDToQ);
                    vertexPQ.add(q, newDToQ +
                            input.estimatedDistanceToGoal(q, end));
                } else if (newDToQ <= solutionMap.get(q)) {
                    solutionMap.put(q, newDToQ);
                    vertexPQ.add(q, newDToQ +
                            input.estimatedDistanceToGoal(q, end));
                } else {
                    vertexPQ.add(q, solutionMap.get(q) +
                            input.estimatedDistanceToGoal(q, end));
                }
            }

            solutionHelper.add(vertexPQ.getSmallest());
        }

        return relax(vertexPQ.getSmallest(), solutionHelper);
    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        if (outcome == SolverOutcome.TIMEOUT || outcome == SolverOutcome.UNSOLVABLE) {
            return null;
        }
        return solution;
    }

    @Override
    public double solutionWeight() {
        if (outcome == SolverOutcome.TIMEOUT || outcome == SolverOutcome.UNSOLVABLE) {
            return 0.00;
        }
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
