import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private int trials;
    private double[] array;
    private Stopwatch stopwatch;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) {
            throw new IllegalArgumentException();
        }

        // initialize variables
        this.trials = trials;
        this.array = new double[trials];
        stopwatch = new Stopwatch();

        // run the simulation function as many times as number of input trials
        for (int i = 0; i < trials; i++) {
            array[i] = calculatePThreshold(n);
        }
    }

    // randomly opens a grid site one after another until the grid percolates
    private double calculatePThreshold(int n) {
        Percolation P = new Percolation(n);

        // while it doesn't percolate, keep opening sites
        while (!P.percolates()) {
            int randRow = StdRandom.uniformInt(0, n);
            int randCol = StdRandom.uniformInt(0, n);
            P.open(randRow, randCol);
        }
        // return number of opened sites / the grid size (percolation threshold)
        return (double) P.numberOfOpenSites() / (n * n);
    }

    private double getTime() {
        return stopwatch.elapsedTime();
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(array);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(array);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - (1.96 * stddev()) / (Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + (1.96 * stddev()) / (Math.sqrt(trials));
    }

    // main
    public static void main(String[] args) {
    }
}