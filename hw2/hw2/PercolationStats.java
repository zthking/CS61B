package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {

    private int times;
    private int boundary;

    private Percolation[] p;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        boundary = N;
        times = T;
        p = new Percolation[T];
        //Percolation pMaker = pf.make(N);
        for (int i = 0; i < T; i += 1) {
            p[i] = pf.make(N);
        }
    }

    private double[] runPercolation() {
        Percolation[] pTemp = p;
        double[] frictions = new double[times];
        int row;
        int col;
        for (int i = 0; i < times; i += 1) {
            do {
                row = StdRandom.uniform(0, boundary);
                col = StdRandom.uniform(0, boundary);
                if (!pTemp[i].isOpen(row, col)) {
                    pTemp[i].open(row, col);
                }
            } while (pTemp[i].percolates());
            frictions[i] = (double) p[i].numberOfOpenSites() / boundary / boundary;
        }
        return frictions;
    }

    public double mean() {
        return StdStats.mean(runPercolation());
    }

    public double stddev() {
        return StdStats.stddev(runPercolation());
    }

    public double confidenceLow() {
        return this.mean() - 1.96 * this.stddev() / Math.sqrt(times);
    }

    public double confidenceHigh() {
        return this.mean() + 1.96 * this.stddev() / Math.sqrt(times);
    }
}
