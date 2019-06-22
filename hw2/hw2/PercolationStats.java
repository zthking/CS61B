package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {

    private Percolation p;
    private int times;
    private int boundary;
    private int[] totalRuns;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        boundary = N;
        times = T;
        p = pf.make(N);
        totalRuns = new int[times];
    }

    private void runPercolation() {
        for (int i = 0; i < times; i += 1) {
            do {
                p.open(StdRandom.uniform(0,boundary), StdRandom.uniform(0,boundary));
            } while (p.percolates());
            totalRuns[i] = p.numberOfOpenSites();
        }
    }

    public double mean() {
       runPercolation();
       return StdStats.mean(totalRuns);
    }

    public double stddev() {
        runPercolation();
        return StdStats.stddev(totalRuns);
    }

    public double confidenceLow() {

        return this.mean() - 1.96 * this.stddev() / Math.sqrt(times);
    }

    public double confidenceHigh() {
        return this.mean() + 1.96 * this.stddev() / Math.sqrt(times);
    }
}
