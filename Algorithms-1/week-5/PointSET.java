/*************************************************************************
 *  Compilation:  javac PointSET.java
 *  Execution:    java PointSET
 *  Dependencies: StdDraw.java Point2D.java SET.java
 *
 *  Brute force BST implementation for range searching using set.
 *************************************************************************/


public class PointSET {
    
    private SET<Point2D> pset;
    
    // construct an empty set of points
    public PointSET() {
        pset = new SET<Point2D>();
    }
    
    // is the set empty ?
    public boolean isEmpty() {
        return size() == 0;
    }
    
    // number of points in the set
    public int size() {
        return pset.size();
    }
    
    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p != null)
            pset.add(p);
    }
    
    // does the set contain the point p ?
    public boolean contains(Point2D p) {
        return pset.contains(p);
    }
    
    // draw all of the points to standard draw.
    public void draw() {
        
        // initialize graphics.     
        // the plot is 1 by 1
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger
        for (Point2D p: pset) {
            p.draw();
        }
        StdDraw.setPenRadius();
        
    }
    
    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        SET<Point2D> rangeSet = new SET<Point2D>();
        
        for (Point2D p : pset) {
            if (rect.contains(p)) {
                rangeSet.add(p);
            }
        }
        return rangeSet;
    }
    
    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        
        if (size() == 0) 
            return null;

        Point2D n = null;
        
        for (Point2D point : pset) {
            if (n == null) {
                n = point;
            }

            if (point.distanceSquaredTo(p) < n.distanceSquaredTo(p)) {
                n = point;
            }
        }
        
        return n;
    }
    
    // test client.
    public static void main(String[] args) {
        
        PointSET pointSet = new PointSET();
        Point2D p1 = new Point2D(0.3, 0.4);
        StdOut.println("The size of the pointset is " + pointSet.size());
        pointSet.insert(p1);
        pointSet.insert(new Point2D(0.2, 0.3));
        pointSet.insert(new Point2D(0.1, 0.5));
        pointSet.insert(new Point2D(0.05, 0.05));
        RectHV rect = new RectHV(0.05, 0.05, 0.45, 0.45);
        rect.draw();
        StdOut.println("The size of the pointset is not " + pointSet.size());
        // print the number of points in the stack. 
        StdOut.println("The points inside the rectange are :");
        for (Point2D p:pointSet.range(rect))
            StdOut.println(p.toString());
        
        StdOut.println("Finding the nearest point to the given point:");
        StdOut.println(pointSet.nearest(new Point2D(0.05, 0.06)).toString());
        pointSet.draw();
    }
}

