/**
 * Created by Dim on 01.02.2015.
 */
public class PercolationStats {

    private double[] result;

    public PercolationStats(int N, int T)     // perform T independent experiments on an N-by-N grid
    {
        if (N <= 0 || T <= 0) throw new IllegalArgumentException();

        result = new double[T];

        for (int i = 0; i < T; i++) {
            result[i] = perc(N);
        }
    }

    public double mean()                      // sample mean of percolation threshold
    {
        return StdStats.mean(result);
    }

    public double stddev()                    // sample standard deviation of percolation threshold
    {
        return StdStats.stddev(result);
    }

    public double confidenceLo()              // low  endpoint of 95% confidence interval
    {
        return mean() - (1.96 * stddev() / Math.sqrt(result.length));
    }

    public double confidenceHi()              // high endpoint of 95% confidence interval
    {
        return mean() + (1.96 * stddev() / Math.sqrt(result.length));
    }

    public static void main(String[] args)    // test client (described below)
    {
        if (args.length != 2)  return;

        PercolationStats percStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        StdOut.println("mean                    = " + percStats.mean());
        StdOut.println("stddev                  = " + percStats.stddev());
        StdOut.println("95% confidence interval = " + percStats.confidenceLo() +" " + percStats.confidenceHi());
    }

    private static double perc(int N) {

        Percolation perc = new Percolation(N);

        int step = 0;

        while (!perc.percolates()) {

            int i = StdRandom.uniform(N) + 1;
            int j = StdRandom.uniform(N) + 1;

            if (!perc.isOpen(i, j)) {
                perc.open(i, j);
                step++;
            }
        }
        return (double) step / (N * N);
    }
}
