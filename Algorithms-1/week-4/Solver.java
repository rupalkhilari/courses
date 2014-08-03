/*************************************************************************
 *  Compilation:  javac Solver.java
 *  Execution:    java Solver puzzle07.txt
 *  Dependencies: Board.java
 *  Description:  Solves the 8-puzzle problem using a custom search node, 
 *                using a minimum priority queue. (using manhattan
 *                priority)
 *
 *************************************************************************/

public class Solver {
 
    private SearchNode result; 

    // search node of the game.
    private class SearchNode implements Comparable<SearchNode> {
     
        private final Board board;
        private final int numMoves;
        private final SearchNode previous;
        private final int priority;
        
        private SearchNode(Board b, SearchNode s) {
            // initialize 
            board = b;
            previous = s;
            
            if (previous == null)
                numMoves = 0;
            else 
                numMoves = previous.numMoves + 1;
            
            // use the manhattan priority function.
            priority = board.manhattan() + numMoves;
            
        }
        
        public int compareTo(SearchNode sn) {
            return this.priority - sn.priority;
        }
    }
    // find a solution to the initial board 
    // using the A* algorithm
    public Solver(Board initial) {
        if (initial.isGoal())
            result = new SearchNode(initial, null);
        else
            result = solve(initial, initial.twin());
    }
    
    private SearchNode step(MinPQ<SearchNode> pq) {
        SearchNode least = pq.delMin();
        for (Board neighbour: least.board.neighbors()) {
            if (least.previous == null || !neighbour.equals(least.previous.board))
                pq.insert(new SearchNode(neighbour, least));
        }
        return least;
    }

    private SearchNode solve(Board initial, Board twin) {
        
        SearchNode last;
        MinPQ<SearchNode> mainpq = new MinPQ<SearchNode>();
        MinPQ<SearchNode> twinpq = new MinPQ<SearchNode>();
        mainpq.insert(new SearchNode(initial, null));
        twinpq.insert(new SearchNode(twin, null));
        
        while (true) {
            last = step(mainpq);
            if (last.board.isGoal())
                return last;
            if (step(twinpq).board.isGoal())
                return null;
        }
    }
    // is the initial board solvable
    public boolean isSolvable() {
        return result != null;
    }
    
    // min number of moves to solve initial board
    // -1 if no solution.
    public int moves() {
        if (result != null)
            return result.numMoves;
        return -1;
    }
    
    // sequence of boards in a shortest solution,
    // null of no solution.
    public Iterable<Board> solution() {
        if (result == null)
            return null;
        Stack<Board> s = new Stack<Board>();
        for (SearchNode n = result; n != null; n = n.previous)
            s.push(n.board);
        return s;
    }
    
    // main function
    public static void main(String[] args) {
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
}