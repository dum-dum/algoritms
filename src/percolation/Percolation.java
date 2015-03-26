/**
 * Created by Dim on 31.01.2015.
 */
public class Percolation {

    private boolean[][] grid;
    private WeightedQuickUnionUF uf;
    private int N;

    public Percolation(int N)               // create N-by-N grid, with all sites blocked
    {
        if (N <= 0) throw new IllegalArgumentException();
        this.N = N;
        uf = new WeightedQuickUnionUF(N * N + 2);
        grid = new boolean[N][N];
    }

    public void open(int i, int j)          // open site (row i, column j) if it is not open already
    {
        if (i <= 0 || i > N || j <= 0 || j > N) throw new IndexOutOfBoundsException();

        if (!grid[i-1][j-1]) {      //check  isOpen

            int indexSite = (i - 1) * N + (j - 1);

            grid[i - 1][j - 1] = true;

            //left
            if (j > 1 && grid[i - 1][j - 2]) {
                uf.union(indexSite, indexSite - 1);
            }
            //right
            if (j < N && grid[i - 1][j]) {
                uf.union(indexSite, indexSite + 1);
            }
            //top
            if (i > 1 && grid[i - 2][j - 1]) {
                uf.union(indexSite, indexSite - N);
            }
            //bottom
            if (i < N && grid[i][j - 1]) {
                uf.union(indexSite, indexSite + N);
            }
            // is top line connected
            if (i == 1) uf.union(indexSite, N*N);

            //is bottom line connected
            if (i == N) uf.union(indexSite, N*N + 1);
        }
    }



    public boolean isOpen(int i, int j)     // is site (row i, column j) open?
    {
        if (i <= 0 || i > N || j <= 0 || j > N) throw new IndexOutOfBoundsException();

        return grid[i-1][j-1];
    }

    public boolean isFull(int i, int j)     // is site (row i, column j) full?
    {
        if (i <= 0 || i > N || j <= 0 || j > N) throw new IndexOutOfBoundsException();

        if (!grid[i-1][j-1]) return false;   // check isOpen

        int indexSite = (i-1)*N + (j-1);

        if (uf.connected(indexSite, N*N)) return true;

        return false;
    }

    public boolean percolates()             // does the system percolate?
    {
        return (uf.connected(N * N, N * N + 1));
    }

    public static void main(String[] args)   // test client (optional)
    {

    }

}