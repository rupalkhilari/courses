/*----------------------------------------------------------------
 *  Author:        Rupal Khilari
 *  Written:       29/6/2014
 *  Last updated:  29/6/2014
 *
 *  Compilation:   javac PercolationStats.java
 *  Execution:     java PercolationStats 20 50
 *  
 *  Runs the specified number of computational experiments for
 *  Percolation for a grid of a specified size.
 *  
 *  % java PercolationStats 20 50
 *  mean                    = 0.59655
 *  stddev                  = 0.045607828051682495
 *  95% confidence interval = 0.5839081550397104, 0.6091918449602897
 *
 *----------------------------------------------------------------*/


public class PercolationStats {
    
    private double[] thresholds = null;
    private int gridLength = 0;
    private int numExperiments = 0;

    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException("Both N and T "
                + "should be positive integers ");
        }
        // We will be performing T computational experiments.
        thresholds = new double[T];
        gridLength = N;
        numExperiments = T;
        performExperiments();
    }
    
    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }
     // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }
    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        // 95% confidence corresponds to a critical value of 1.96.
        double z = 1.96;
        double errorMargin = stddev()/Math.sqrt(numExperiments);
        return mean() - (z * errorMargin);
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        // 95% confidence corresponds to a critical value of 1.96.
        double z = 1.96;
        double errorMargin = stddev()/Math.sqrt(numExperiments);
        return mean() + (z * errorMargin);
    }
    
    // performs the required number of percolation experiments.
    private void performExperiments() {
        for (int i = 0; i < numExperiments; i++) {
            Percolation p = new Percolation(gridLength);
            int numOpenSites = 0;
            while (!p.percolates()) {
                // Find a random (i, j) from (1..gridLength) such that
                // grid(i, j) is not open.
                int iRand = StdRandom.uniform(gridLength);
                int jRand = StdRandom.uniform(gridLength);
                while (p.isOpen(iRand + 1, jRand + 1)) {
                    iRand = StdRandom.uniform(gridLength);
                    jRand = StdRandom.uniform(gridLength);
                }
                p.open(iRand + 1, jRand + 1);
                numOpenSites++;
            }

            double threshold = (double) ((numOpenSites*1.0)/(gridLength*gridLength));
            thresholds[i] = threshold;

        }
    }

    // test client, described below
    public static void main(String[] args) {
        if (args.length < 2) {
            throw new java.lang.IllegalArgumentException(
                "Please provide N (length of the percolation "
                + " grid) and T (number of computational experiments) "
                + " as command line arguments. "
            );
        }
        
        // Collect statistics
        PercolationStats pStats = new PercolationStats(
             Integer.parseInt(args[0]),
             Integer.parseInt(args[1])
        );
        StdOut.println("mean\t\t\t= " + pStats.mean());
        StdOut.println("stddev\t\t\t= " + pStats.stddev());
        StdOut.println("95% confidence interval\t= " + pStats.confidenceLo()
            + ", " + pStats.confidenceHi());
        StdOut.println();
    }
}