/*************************************************************************
 * Name: Rupal Khilari
 * Email: rupal.khilari@gmail.com
 *
 * Compilation:  javac Fast.java
 * Execution:
 * Dependencies: StdDraw.java, java.utils.Arrays
 *
 * Description: Given a point p, determines if it participates in a
 * set of points of 4 or more collinear points. The order of growth of 
 * the running time of the program should be N2 log N in the worst case
 * and it should use space proportional to N.
 *
 *************************************************************************/

import java.util.Arrays;

class Fast {
    
    // Read the number and the set of points from the specified file.
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
    
    // Returns true, if the given set of points are sorted.
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

    // Draws a line connecting the specified points.
    private static void draw(Point[] points) {
        assert isSorted(points);
        StdDraw.setPenRadius(0.005);  // make the points a bit larger
        points[0].drawTo(points[points.length - 1]);
    }
    
    // Displays the screen.
    private static void show() {
        
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        // display to screen all at once
        StdDraw.show(0);
        // reset the pen radius
        StdDraw.setPenRadius();
    }

    // Returns true if the given set of points is collinear.
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
    
    // Prints the given set of points in order to stdout.
    private static void printPoints(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            if (i == 0)
                StdOut.print(points[i]);
            else
                StdOut.print(" -> " + points[i]);
        }
        StdOut.println();
    }

    // main function.
    public static void main(String[] args) {
        Point[] points = Fast.readAndPlotInput(args[0]);
        int N = points.length;
        Arrays.sort(points);
        int firstIndex, lastIndex; // Pointers to the beginning and end of runs.
        double lastSlope, nextSlope; // To hold subsequent slopes.
        for (int i = 0; i < N - 4; i++) {
            if (i > 0 && points[i].compareTo(points[i - 1]) == 0)
                continue;
            Point[] temp = new Point[N - i];
            for (int j = i; j < N; j++)
                temp[j - i] = points[j];
            Arrays.sort(temp, points[i].SLOPE_ORDER);
            lastIndex = 0;
            do {

                nextSlope = points[i].slopeTo(temp[lastIndex]);
                // move the lastIndex forward until we reach a point
                // such that 2 consecutive slopes are the same.
                // we might be in for a collinear point run.
                do {
                    lastIndex += 1;
                    lastSlope = nextSlope;
                    nextSlope = points[i].slopeTo(temp[lastIndex]);
                } while (lastIndex < N - i - 1 && lastSlope != nextSlope);
                firstIndex = lastIndex - 1;
                while (lastIndex < N - i 
                    && lastSlope == points[i].slopeTo(temp[lastIndex])) {
                    lastIndex++;
                }
                if (lastIndex - firstIndex + 1 >= 4) // Add one for points[i].
                {
                    Point[] line = new Point[lastIndex - firstIndex + 1];
                    line[0] = points[i];
                    for (int m = 1; m < line.length; m++) {
                        line[m] = temp[firstIndex + m - 1];
                    }
                    if (Fast.isSorted(line)) {
                        Fast.draw(line);
                        Fast.printPoints(line);
                    }
                }
            } while (lastIndex < N - i - 1);
        }
        Fast.show();
    }

}