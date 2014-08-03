/*************************************************************************
 *  Compilation:  javac Board.java
 *  Execution:    java Board
 *
 *  Description:  Defines the Board API representing the board required
 *                to solve the 8puzzle problem.
 *
 *************************************************************************/

public class Board {
    
    private final int[][] blocks;
    private final int dimension;
    
    // construct a board from an N by N array of blocks
    public Board(int[][] blocks) {
        int len = blocks.length;
        this.blocks = new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        }
        dimension = len;
    }
    
    // board dimension N
    public int dimension() {
        return dimension;
    }
    
    // number of blocks out of place
    public int hamming() {
        int len = blocks.length;
        int priority = 0;
        int expectation = 1;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (this.blocks[i][j] != (expectation++ % (len*len))) {
                    if (this.blocks[i][j] != 0) {
                        priority++;
                    }
                }
            }
        }
        return priority;
    }
    
    // sum of Manhattan distances between blocks and goal.
    public int manhattan() {
        int len = blocks.length;
        int priority = 0;
        int expectation = 1;
        int distance = 0;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                int value = blocks[i][j];
                if (value == 0) continue;
                // find the expected position of the value
                int goalRow = (value-1)/len;
                int goalCol = (value-1) % len;
                if (goalRow != i || goalCol != j) {
                    distance += (Math.abs(goalRow-i) + Math.abs(goalCol-j));
                }   
            }
        }
        return distance;
    }
    
    // is this board the goal board.
    public boolean isGoal() {
        if (blocks[dimension-1][dimension-1] != 0)
            return false;
        int expectation = 1;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (this.blocks[i][j] != (expectation++ % (dimension*dimension))) {
                    return false;
                }
            }
        }
        return true;
    }
    
    // a board obtained by exchanging two adjacent blocks 
    // A twin is obtained by swapping two adjacent blocks 
    // (the blank does not count) in the same row.
    public Board twin() {
        
        int[][] copy = new int[dimension][dimension];
        if (dimension <= 1)
            return new Board(copy);
        
        // make a copy of the existing board.
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                copy[i][j] = blocks[i][j];
            }
        }

        // find the index of zero in the row.
        // if there exists one, skip that row.
        
        int row = 0;
        int col = 0;
        
        int lastValue = blocks[0][0];
        int value = blocks[0][0];
        int targetRow = 0;
        int targetColumn = 0;
        for (row = 0; row < dimension; row++) {
            for (col = 0; col < dimension; col++) {
                value = blocks[row][col];
                
                if (value != 0 && lastValue != 0 && col > 0)
                {
                    targetRow = row;
                    targetColumn = col;
                    break;
                }
                lastValue = value;
            }
        }
        copy[targetRow][targetColumn] = lastValue;
        copy[targetRow][targetColumn-1] = value;
        return new Board(copy);
    }
    
    // does this board equal y
    public boolean equals(Object y) {
        if (y == this) 
            return true;
        if (y == null) 
            return false;
        Board otherBoard = (Board) y;
        if (this.blocks.length != otherBoard.blocks.length)
            return false;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (this.blocks[i][j] != otherBoard.blocks[i][j])
                    return false;
            }
        }
        return true;
    }
    
    private int[][] swap(int[][] array, int fromRow, int fromCol,
                         int toRow, int toCol) {
        int[][] copy = new int[dimension][dimension];
        
        // make a copy of the existing board.
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                copy[i][j] = array[i][j];
            }
        }
        
        int temp = copy[toRow][toCol];
        copy[toRow][toCol] = copy[fromRow][fromCol];
        copy[fromRow][fromCol] = temp;
        return copy;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> q = new Queue<Board>();

        // Find zero
        int row = 0;
        int col = 0;
        zerosearch:
        for (row = 0; row < dimension; row++) {
            for (col = 0; col < dimension; col++) {
                if (blocks[row][col] == 0) {
                    break zerosearch;
                }
            }
        }
        if (row > 0)
            q.enqueue(new Board(swap(blocks, row, col, row - 1, col)));
        if (row < dimension - 1)
            q.enqueue(new Board(swap(blocks, row, col, row + 1, col)));
        if (col > 0)
            q.enqueue(new Board(swap(blocks, row, col, row, col - 1)));
        if (col < dimension - 1)
            q.enqueue(new Board(swap(blocks, row, col, row, col + 1)));
        return q;        
    }
    
    // string representation of the board (in the output format)
    public String toString() {
        int len = blocks.length;
        StringBuilder sb = new StringBuilder();
        sb.append(dimension + "\n");
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                int value = blocks[i][j];
                if (value == 0)
                    sb.append("   ");
                else
                    sb.append(String.format("%3d", blocks[i][j]));
            }
            sb.append("\n");
        }      
        
        return sb.toString();
    }
    
    // test client
    public static void main(String[] args) {
        
        int[][] test = new int[3][3];
        
        test[0][0] = 0;
        test[0][1] = 1;
        test[0][2] = 3;
        test[1][0] = 4;
        test[1][1] = 2;
        test[1][2] = 5;
        test[2][0] = 7;
        test[2][1] = 8;
        test[2][2] = 6;
         
        /*
        test[0][0] = 8;
        test[0][1] = 1;
        test[0][2] = 3;
        test[1][0] = 4;
        test[1][1] = 0;
        test[1][2] = 2;
        test[2][0] = 7;
        test[2][1] = 6;
        test[2][2] = 5;
        */
        /*
        test[0][0] = 1;
        test[0][1] = 2;
        test[0][2] = 3;
        test[1][0] = 4;
        test[1][1] = 5;
        test[1][2] = 6;
        test[2][0] = 7;
        test[2][1] = 8;
        test[2][2] = 0;
        */
        Board b = new Board(test);
        StdOut.println(b.toString());
        Board twin = b.twin();
        StdOut.println(twin.toString());
        StdOut.println("The hamming distance is " + b.hamming());
        StdOut.println("The manhattan distance is " + b.manhattan());
        StdOut.println("The given board is the goal board. " + b.isGoal());
    }
}