package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {

    private int times;
    private int boundary;
    private int[] totalRuns;
    private Percolation[] p;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        boundary = N;
        times = T;
        p = new Percolation[T];
        Percolation pTemp = pf.make(N);
        for (int i = 0; i < T; i += 1) {
            p[i] = pTemp;
        }
        totalRuns = new int[times];
    }

    private void runPercolation() {
        PercolationFactory pf = new PercolationFactory();
        Percolation pTemp = pf.make(times);
        for (int i = 0; i < times; i += 1) {
            do {
                p[i].open(StdRandom.uniform(0, boundary), StdRandom.uniform(0, boundary));
            } while (p[i].percolates());
            totalRuns[i] = p[i].numberOfOpenSites();
            p[i] = pTemp;
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
