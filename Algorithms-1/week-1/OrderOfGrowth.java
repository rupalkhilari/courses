class OrderOfGrowth {
 
    // Holds values corresponding to [N][t]
    // where N is the input size.
    // and t is the running time of the program.
    private double[][] table;
    
    public OrderOfGrowth(double[][] table) {
        this.table = table;
    }
    
    private void printTable() {
        StdOut.println("Input(N)\tTime(t)");
        for (int i=0; i < table.length; i++) {
            StdOut.println(table[i][0] + "\t" + table[i][1]);
        }
    }

    private double log2(double num) {
        return Math.log(num)/Math.log(2);
    }

    // Returns the value of b on solving for the 
    // p and q th indexes in table.
    private double solveForb(int p, int q) {
        // following the equation 
        // l2(T(n)) = b l2(N) + c
        double b = (log2(table[p][1]) - log2(table[q][1])) / 
            (log2(table[p][0]) - log2(table[q][0]));
        return b;
    }

    // solves the equations based on consecutive entries in the 
    public void solve() {
        for (int i = 0; i < table.length-1; i++) {
            StdOut.println("Solving for indexes " + i + " and " + (i+1));
            StdOut.println("\tb = " + solveForb(i, i+1));
        }
    }

    public static void main(String[] args) {
        
        StdOut.println("Enter the number of tests:");
        int num = StdIn.readInt();

        double[][] input = new double[num][2];
        
        for (int i = 0; i < num; i++) {
            StdOut.println("Enter N:");
            input[i][0] = StdIn.readDouble();
            StdOut.println("Enter t:");
            input[i][1] = StdIn.readDouble();
        }
        
        OrderOfGrowth oog = new OrderOfGrowth(input);
        oog.printTable();
        oog.solve();

    }
}