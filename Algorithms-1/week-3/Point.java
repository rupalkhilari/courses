/*************************************************************************
 * Name: Rupal Khilari
 * Email: rupal.khilari@gmail.com
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER =  new Comparator<Point>() {
        @Override
        public int compare(Point p1, Point p2) {
            double m1 = Point.this.slopeTo(p1);
            double m2 = Point.this.slopeTo(p2);
            if (m1 < m2) 
                return -1;
            else if (m1 > m2)
                return 1;
            else
                return 0;
        }
    };

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        double m;
        if (that.x == this.x)
            if (that.y > this.y) m = Double.POSITIVE_INFINITY;
            else m = Double.NEGATIVE_INFINITY;
        else
            m = (double)(that.y - this.y)/(double)(that.x - this.x);
        return m;
        
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.y < that.y)
            return -1;
        else if (this.y > that.y)
            return 1;
        else if (this.y == that.y) {
            if (this.x < that.x)
                return -1;
            else if (this.x > that.x)
                return 1;
            else
                return 0;
        }
        return 0;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        
        Point p, q, r;
        p = new Point(87, 479);
        q = new Point(87, 479);
        assert p.slopeTo(q) == Double.NEGATIVE_INFINITY;
        p = new Point(25681, 22210);
        q = new Point(25681, 22212);
        assert p.slopeTo(q) == Double.POSITIVE_INFINITY;
        p = new Point(3, 4);
        q = new Point(3, 4);
        assert p.slopeTo(q) == Double.NEGATIVE_INFINITY;
        p = new Point(8, 5);
        q = new Point(9, 4);
        r = new Point(8, 5);
        assert p.SLOPE_ORDER.compare(q, r) == 1;
        assert p.slopeTo(q)    == -1.0;
        assert p.slopeTo(r)    == Double.NEGATIVE_INFINITY;
        
        StdOut.println("A new test");
        p = new Point(1234, 5678);
        q = new Point(19000, 10000);
        StdOut.println(p.slopeTo(q));
    }
}