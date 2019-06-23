package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {

    //private Percolation p;
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
        for (int i = 0; i < T; i += 1) {
            p[i] = pf.make(N);
        }
        //p = pf.make(N);
        totalRuns = new int[times];
    }

    /*
    private void runPercolation() {
        for (int i = 0; i < times; i += 1) {
            do {
                p.open(StdRandom.uniform(0,boundary), StdRandom.uniform(0,boundary));
            } while (p.percolates());
            totalRuns[i] = p.numberOfOpenSites();
        }
    }
*/
    private void runPercolation() {
        for (int i = 0; i < times; i += 1) {
            do {
                p[i].open(StdRandom.uniform(0, boundary), StdRandom.uniform(0, boundary));
            } while (p[i].percolates());
            totalRuns[i] = p[i].numberOfOpenSites();
        }
    }

    public double mean() {
        if (totalRuns[0] != 0) {
            return StdStats.mean(totalRuns);
        }
       runPercolation();
       return StdStats.mean(totalRuns);
    }

    public double stddev() {
        if (totalRuns[0] != 0) {
            return StdStats.stddev(totalRuns);
        }
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
