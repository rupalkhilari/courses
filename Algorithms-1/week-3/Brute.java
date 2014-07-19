/*************************************************************************
 * Name: Rupal Khilari
 * Email: rupal.khilari@gmail.com
 *
 * Compilation:  javac Brute.java
 * Execution: java Brute collinear/input6.txt
 * Dependencies: StdDraw.java
 *
 * Description: A program to find the sets of 4 sorted points that are
 * collinear. The order of growth of the running time should be N^4
 * in the worst case and it should use space proportional to N.
 *
 *************************************************************************/

class Brute {
    
    // reads the given set of points and plots them on the canvas.
    private static Point[] readAndPlotInput(String filename) {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.02);  // make the points a bit larger
        

        // read in the input
        In in = new In(filename);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            points[i] = p;
            p.draw();
        }
        return points;
    }
    
    // returns true if the given set of points is sorted.
    private static boolean isSorted(Point[] points) {
        int num = points.length;
        if (num < 2) 
            return true;
        else {
            double firstSlope = points[0].compareTo(points[1]);
            for (int i = 1; i < num-1; i++) {
                // make sure that points[i] is less than points[i+1]
                if (points[i].compareTo(points[i+1]) != firstSlope)
                    return false;
            }
            return true;
        }
    }

    // draws a line connecting the given points.
    private static void draw(Point[] points) {
        assert isSorted(points);
        StdDraw.setPenRadius(0.005);  // make the points a bit larger
        points[0].drawTo(points[points.length - 1]);
    }
    
    // displays the screen.
    public static void show() {
        
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        // reset the pen radius
        StdDraw.setPenRadius();
    }

    // returns true if the given points are collinear.
    private static boolean isCollinear(Point[] points) {
        int num = points.length;
        if (num < 2)
            return true;
        else {
            double baseSlope = points[0].slopeTo(points[1]);
            for (int i = 2; i < num; i++) {
                if (baseSlope != points[0].slopeTo(points[i]))
                    return false;
            }
        }
        return true;
    }
    
    // prints out the given set of points on stdout, in order.
    private static void printPoints(Point[] points) {
        StdOut.println(points[0] + " -> " + points[1] + " -> " + points[2] 
            + " -> " + points[3]);
    }

    // main function.
    public static void main(String[] args) {
        Point[] points = Brute.readAndPlotInput(args[0]);
        Point[] result = new Point[4];
        int N = points.length;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < N; k++) {
                    for (int m = 0; m < N; m++) {
                        if (i == j || j == k || k == m || m == j || m == i || k == i)
                            continue;
                        // make sure we navigate all combinations.
                        result[0] = points[i];
                        result[1] = points[j];
                        result[2] = points[k];
                        result[3] = points[m];
                        if (isCollinear(result) && isSorted(result) 
                            && result[0].compareTo(result[3]) < 0) {
                            Brute.printPoints(result);
                            Brute.draw(result);
                        }
                    }
                }
            }
        }
        Brute.show();
    }
}