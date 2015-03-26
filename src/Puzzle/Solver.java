import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dim on 16.02.2015.
 */
public class Solver {

    private boolean isSolvable;
    private Map<Board, Board> allSearchBoards = new HashMap<Board, Board>();
    private Stack<Board> pathSolve = new Stack<Board>();

    private Comparator<BoardNode> comparator = new Comparator<BoardNode>() {
        @Override
        public int compare(BoardNode o1, BoardNode o2) {
            if (o1 == null && o2 == null) return 0;
            if (o1 == null) return 1;
            if (o2 == null) return -1;

            return (o1.board.manhattan()+o1.step) - (o2.board.manhattan() + o2.step);
        }
    };

    private MinPQ<BoardNode> boardsMinPQ = new MinPQ<BoardNode>(comparator);
    private MinPQ<BoardNode> boardsMinPQTwin = new MinPQ<BoardNode>(comparator);

    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        if (initial == null) throw new NullPointerException();

        boolean isSolve = false;
        BoardNode goalBoardNode = null;

        boardsMinPQ.insert(new BoardNode(initial, initial, 0));
        boardsMinPQTwin.insert(new BoardNode(initial.twin(), initial.twin(), 0));
        allSearchBoards.put(initial, initial);

        while(!isSolve)
        {
        ////////////////////////////////////////////////////////////////////////////////////
        //find initial
        ////////////////////////////////////////////////////////////////////////////////////
            BoardNode searchBoard = boardsMinPQ.delMin();

            allSearchBoards.put(searchBoard.board, searchBoard.lastBoard);

            //System.out.println(searchBoard.board);

            for (Board board : searchBoard.board.neighbors())
            {
                if (!board.equals(searchBoard.lastBoard)) {

                    boardsMinPQ.insert(new BoardNode(board, searchBoard.board, searchBoard.step + 1));

                    if (board.isGoal()){
                        isSolve = true;
                        isSolvable = true;
                        goalBoardNode = new BoardNode(board, searchBoard.board, searchBoard.step + 1);
                        pathSolve.push(goalBoardNode.board);
                        allSearchBoards.put(board, searchBoard.board);
                    }
                }
            }
            ///////////////////////////////////////////////////////////////////////////////////////
            //find initial.twin
            ///////////////////////////////////////////////////////////////////////////////////////
            BoardNode searchBoardTwin = boardsMinPQTwin.delMin();

            for (Board boardTwin : searchBoardTwin.board.neighbors())
            {
                if (!boardTwin.equals(searchBoardTwin.lastBoard)) {

                    boardsMinPQTwin.insert(new BoardNode(boardTwin, searchBoardTwin.board, searchBoardTwin.step + 1));

                    if (boardTwin.isGoal()){
                        isSolve = true;
                        isSolvable = false;
                    }
                }
            }
        }
        ////////////////////////////////////////////
        //find path solving and steps count
        ////////////////////////////////////////////
        if (isSolvable) {

            Board tempBoard = goalBoardNode.board;

            while (!tempBoard.equals(initial)) {
                //System.out.println("t");

                tempBoard = allSearchBoards.get(tempBoard);
                pathSolve.push(tempBoard);
            }
        }
    }

    public boolean isSolvable()            // is the initial board solvable?
    {
        return isSolvable;
    }
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        if (!isSolvable) return -1;
        return pathSolve.size() - 1 ;
    }
    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        if (!isSolvable) return null;
        return pathSolve;
    }
    public static void main(String[] args) // solve a slider puzzle (given below)
    {
        // create initial board from file
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private class BoardNode
    {
        protected final Board board;
        protected final Board lastBoard;
        protected final int step;

        public BoardNode(Board board, Board lastBoard, int step) {
            this.board = board;
            this.lastBoard = lastBoard;
            this.step = step;
        }
    }
}