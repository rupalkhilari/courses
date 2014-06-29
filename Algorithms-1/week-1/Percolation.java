/*----------------------------------------------------------------
 *  Author:        Rupal Khilari
 *  Written:       29/6/2014
 *  Last updated:  29/6/2014
 *
 *  Compilation:   javac Percolation.java
 *  
 *  Uses the WeightedQuickUnionUF algorithm to solve the
 *  Percolation problem.
 *  
 *----------------------------------------------------------------*/

public class Percolation {

    private boolean[][] grid = null;
    private int gridSideLen = 0;
    private int openSiteCount = 0;
    private WeightedQuickUnionUF wQuickUnion = null;

    // create an N-by-N grid, with all sites blocked.
    public Percolation(int N) {

        wQuickUnion = new WeightedQuickUnionUF(N*N);
        gridSideLen = N;
        grid = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = false; // block this site.
            }
        }
    }
    
    // translates 2-dimensional indices to a 1-dimensional array index.
    private int getIndex(int i, int j) {
        return (gridSideLen * (i - 1)) + (j - 1);
    }
    
    // validates the row and column number.
    private void validateIndices(int i, int j) {
        if (i < 1 || i > gridSideLen || j < 1 || j > gridSideLen) {
            throw new java.lang.IndexOutOfBoundsException(
                "Could not access grid element (" + i + ", " + j + ")");
        }
    }

    /**
     *   Opens site (row i, column j) if it is not already open.
     * 
     *   Throws a IndexOutOfBoundsException if indexes are invalid.
     */
    public void open(int i, int j) {
        validateIndices(i, j);

        grid[i-1][j-1] = true; // open the site.
        openSiteCount++; // increment the number of open sites.
        
        // Perform the union operation to connect the open site to its
        // neighbours.
        // top
        if (i > 1 && isOpen(i-1, j)) { // top
            wQuickUnion.union(getIndex(i, j), getIndex(i - 1, j));
        }
        
        // bottom
        if (i < gridSideLen && isOpen(i + 1, j)) {
            wQuickUnion.union(getIndex(i, j), getIndex(i + 1, j));
        }
         
        // left
        if (j > 1 && isOpen(i, j - 1)) {
            wQuickUnion.union(getIndex(i, j), getIndex(i, j - 1));
        }
        
        // right
        if (j < gridSideLen && isOpen(i, j + 1)) {
            wQuickUnion.union(getIndex(i, j), getIndex(i, j + 1));
        }

    }
    
    /**
     *   Returns true if site (row i, column j) open, else false.
     * 
     *   Throws a IndexOutOfBoundsException if indexes are invalid.
     */
    public boolean isOpen(int i, int j) {
        validateIndices(i, j);
        return grid[i - 1][j - 1];
    }

    /**
     *   Returns true if site (row i, column j) full, else false.
     *   A full site is an open site that can be connected to an open site in
     *   the top row via a chain of neighboring (left, right, up, down) open
     *   sites.
     * 
     *   Throws a IndexOutOfBoundsException if indexes are invalid.
     */
    public boolean isFull(int i, int j) {
        validateIndices(i, j);
        
        // iterating through the elements in the top row to check if (i, j)
        // are connected with it.
        for (int m = 0; m < gridSideLen; m++) { 
            if (wQuickUnion.connected(m, (gridSideLen * (i - 1)) + (j - 1))) {
                return true;
            }
        }
        return false;
    }
    
    /**
     *   Returns true if the system percolates, else false.
     *   a system percolates if we fill all open sites connected to the top row
     *   and that process fills some open site on the bottom row.
     * 
     */
    public boolean percolates() {
        // iterate through all the sites on the botton row and check
        // if it is a full site.
        for (int m = 0; m < gridSideLen; m++) {
            if (isOpen(gridSideLen, m + 1) 
                && isFull(gridSideLen, m + 1)) {
                return true;
            }
        }
        return false;
    }
}

    