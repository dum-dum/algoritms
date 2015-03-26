/**
 * Created by Dim on 16.02.2015.
 */
public class Board {

    private final int N;
    private final int[][] blocks;
    private final int index0row;     //index x(row) blank square
    private final int index0column;     //index y(column) blank square
    private final boolean isGoal;


    public Board(int[][] blocks)           // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    {
        N = blocks.length;
        this.blocks = copyArray(blocks);
        //find 0(blank square) and remember
        breakCikle:
        {
            for (int row = 0; row < N; row++) {
                for (int column = 0; column < N; column++) {
                    if (blocks[row][column] == 0) {
                        index0row = row;
                        index0column = column;
                        break breakCikle;
                    }
                }
            }
            throw new IllegalArgumentException("no blank square");
        }

        // is this board the goal board?
        if (hamming() == 0) isGoal = true;
        else isGoal = false;
    }

    public int dimension() {                // board dimension N
        return N;
    }

    public int hamming()                   // number of blocks out of place
    {
        int index = 0;
        int hamming = 0;

        for (int row = 0; row < N; row++) {
            for (int column = 0; column < N; column++) {
                index++;
                if (blocks[row][column] == 0) continue;
                if (blocks[row][column] != index) hamming++;
            }
        }
        return hamming;
    }

    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        int indexRow;
        int indexColumn;
        int manhatten = 0;

        for (int row = 0; row < N; row++) {
            for (int column = 0; column < N; column++) {
                if (blocks[row][column] == 0) continue;
                indexRow = (blocks[row][column] - 1) / N;
                indexColumn = (blocks[row][column] - 1) - indexRow * N;
                manhatten += Math.abs(indexRow - row) + Math.abs(indexColumn - column);
                //System.out.print(Math.abs(indexRow - row) + " " + Math.abs(indexColumn - column) + " "
                // + (Math.abs(indexRow - row) + Math.abs(indexColumn - column)) +"   ");
            }
            //System.out.println();
        }
        return manhatten;
    }

    public boolean isGoal()                // is this board the goal board?
    {
        return isGoal;
    }

    public Board twin()                    // a boadr that is obtained by exchanging two adjacent blocks in the same row
    {
        //copy array
        int[][] newBlocks = copyArray(blocks);

        if (newBlocks[0][0] != 0 && newBlocks[0][1] != 0) exchArr(newBlocks, 0, 0, 0, 1);

        else exchArr(newBlocks, 1, 0, 1, 1);

        return new Board(newBlocks);
    }

    public boolean equals(Object y)        // does this board equal y?
    {
        if (this == y) return true;
        if (y == null || y.getClass() != this.getClass()) return false;

        Board x = (Board) y;
        if (x.dimension() == this.dimension()) {
            for (int row = 0; row < N; row++) {
                for (int column = 0; column < N; column++) {
                    if (x.blocks[row][column] != this.blocks[row][column]) return false;
                }
            }
            return true;
        }
        return false;
    }

    public Iterable<Board> neighbors()     // all neighboring boards
    {
        Stack<Board> stack = new Stack<Board>();
        //top
        if (index0row != 0) {
            int[][] newBlocks = copyArray(blocks);
            exchArr(newBlocks, index0row, index0column, index0row - 1, index0column);
            stack.push(new Board(newBlocks));
        }
        //bottom
        if (index0row != (N - 1)) {
            int[][] newBlocks = copyArray(blocks);
            exchArr(newBlocks, index0row, index0column, index0row + 1, index0column);
            stack.push(new Board(newBlocks));
        }
        //left
        if (index0column != 0) {
            int[][] newBlocks = copyArray(blocks);
            exchArr(newBlocks, index0row, index0column, index0row, index0column - 1);
            stack.push(new Board(newBlocks));
        }
        //right
        if (index0column != (N - 1)) {
            int[][] newBlocks = copyArray(blocks);
            exchArr(newBlocks, index0row, index0column, index0row, index0column + 1);
            stack.push(new Board(newBlocks));
        }
        return stack;
    }

    public String toString()               // string representation of this board (in the output format specified below)
    {
        StringBuilder str = new StringBuilder();
        str.append(N + "\n");
        for (int row = 0; row < N; row++) {
            for (int column = 0; column < N; column++) {
                str.append(blocks[row][column] + "\t");
            }
            str.append("\n");
        }
        return str.toString();
    }

    private static int[][] copyArray(int[][] array) {
        int n = array.length;
        int[][] newBlocks = new int[n][n];
        for (int row = 0; row < n; row++) {
            for (int column = 0; column < n; column++) {
                newBlocks[row][column] = array[row][column];
            }
        }
        return newBlocks;
    }

    private static void exchArr(int[][] array, int aRow, int aColumn, int bRow, int bColumn) {
        array[aRow][aColumn] = array[aRow][aColumn] ^ array[bRow][bColumn];
        array[bRow][bColumn] = array[aRow][aColumn] ^ array[bRow][bColumn];
        array[aRow][aColumn] = array[aRow][aColumn] ^ array[bRow][bColumn];
    }

    public static void main(String[] args) // unit tests (not graded)
    {
        String filename = "D:\\JAVA\\algoritms\\Puzzle\\8puzzle\\puzzle07.txt";
        In in = new In(filename);
        int N = in.readInt();
        int[][] tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board initial = new Board(tiles);
        System.out.println(initial);
        System.out.println(initial.isGoal());
        System.out.println("manhatten = " + initial.manhattan());
        System.out.println("hamming = " + initial.hamming());
        System.out.println(initial.twin());

        for (Board x : initial.neighbors()) {
            System.out.println(x);
            System.out.println("manhatten = " + x.manhattan());
            System.out.println("hamming = " + x.hamming());
        }
    }
}