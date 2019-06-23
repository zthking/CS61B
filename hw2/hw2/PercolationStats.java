package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {

    private int times;
    private int boundary;
    private double[] frictions;
    private Percolation[] p;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        boundary = N;
        times = T;
        p = new Percolation[T];
        frictions = new double[times];
        //Percolation pMaker = pf.make(N);
        for (int i = 0; i < T; i += 1) {
            p[i] = pf.make(N);
        }
    }

    private void runPercolation() {
        int row;
        int col;
        for (int i = 0; i < times; i += 1) {
            while (!p[i].percolates()) {
                row = StdRandom.uniform(0, boundary);
                col = StdRandom.uniform(0, boundary);
                if (!p[i].isOpen(row, col)) {
                    p[i].open(row, col);
                }
            }
            frictions[i] = (double) p[i].numberOfOpenSites() / boundary / boundary;
        }
    }

    public double mean() {
        if (frictions[0] != 0.0d) {
            return StdStats.mean(frictions);
        }
        runPercolation();
        return StdStats.mean(frictions);
    }

    public double stddev() {
        if (frictions[0] != 0.0d) {
            return StdStats.stddev(frictions);
        }
        runPercolation();
        return StdStats.stddev(frictions);
    }

    public double confidenceLow() {
        return this.mean() - 1.96 * this.stddev() / Math.sqrt(times);
    }

    public double confidenceHigh() {
        return this.mean() + 1.96 * this.stddev() / Math.sqrt(times);
    }
}
