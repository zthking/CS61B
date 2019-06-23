package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {

    private int times;
    private double[] frictions;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }

        times = T;
        Percolation[] p = new Percolation[T];
        frictions = new double[T];

        int row;
        int col;
        for (int i = 0; i < T; i += 1) {
            p[i] = pf.make(N);
            while (!p[i].percolates()) {
                row = StdRandom.uniform(0, N);
                col = StdRandom.uniform(0, N);
                if (!p[i].isOpen(row, col)) {
                    p[i].open(row, col);
                }
            }
            frictions[i] = (double) p[i].numberOfOpenSites() / (N * N);
        }
    }

    public double mean() {
        return StdStats.mean(frictions);
    }

    public double stddev() {
        return StdStats.stddev(frictions);
    }

    public double confidenceLow() {
        return this.mean() - 1.96 * this.stddev() / Math.sqrt(times);
    }

    public double confidenceHigh() {
        return this.mean() + 1.96 * this.stddev() / Math.sqrt(times);
    }
}
