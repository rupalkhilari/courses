/*************************************************************************
 *  Compilation:  javac KdTree.java
 *  Execution:    java KdTree
 *  Dependencies: StdDraw.java Point2D.java java.util.Stack
 *
 *  KdTree (2D Tree) BST implementation.
 *  Derived some of the concepts from the really good implementation
 *  on http://code.google.com/ 
 *************************************************************************/


public class KdTree {
    
    private Node myRoot;
    private int size;
    
    // representation of a node in the 2d tree.
    private class Node {
        private int depth;
        private Point2D p;
        private RectHV rect;
        private Node left;
        private Node right;
    }

    // construct an empty set of points
    public KdTree() {
        myRoot = null;
        size = 0;
    }
    
    // is the set empty ?
    public boolean isEmpty() {
        return size == 0;
    }
    
    // number of points in the set
    public int size() {
        return size;
    }
    
    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (!contains(p))
            myRoot = insertNode(myRoot, p, 0, new RectHV(0.0, 0.0, 1.0, 1.0));
    }
    
    private Node insertNode(Node node, Point2D p, int depth, RectHV rect) {
        
        if (node == null) { // we have found a null link. Insert.
            node = new Node();
            node.p = p;
            node.depth = depth;
            node.rect = rect;
            node.left = null;
            node.right = null;
            size++;  // counts the number of nodes in the tree.
        }
        else {
            if (node.depth % 2 == 0) {
                // if the x coordinate of the given point is smaller
                // x coordinate of the current node, insert into the left subtree
                if (Point2D.X_ORDER.compare(p, node.p) < 0)
                    node.left = insertNode(node.left, p, ++depth, 
                        new RectHV(rect.xmin(), rect.ymin(), node.p.x(),
                                   rect.ymax()));
                else // insert into the right subtree.
                    node.right = insertNode(node.right, p, ++depth, 
                        new RectHV(node.p.x(), rect.ymin(), rect.xmax(),
                                   rect.ymax()));
            }
            else { 
                // if the y coordinate of the given point is smaller than
                // y coordinate of the current node, insert into the left subtree.
                if (Point2D.Y_ORDER.compare(p, node.p) < 0)
                    node.left = insertNode(node.left, p, ++depth,
                        new RectHV(rect.xmin(), rect.ymin(), rect.xmax(),
                                   node.p.y()));
                else 
                    node.right = insertNode(node.right, p, ++depth,
                        new RectHV(rect.xmin(), node.p.y(), rect.xmax(),
                                   rect.ymax()));
            }
        }
        return  node;
      
    }
    // does the set contain the point p ?
    public boolean contains(Point2D p) {
        return containsNode(myRoot, p);
    }
    
    // recursive function check point p exists in the tree
    // with root 'node'
    private boolean containsNode(Node node, Point2D p) {
        
        if (node == null) 
            return false;
        if (node.p.equals(p))
            return true;
        else        
            if (node.depth % 2 == 0) { // Horizontal line
            if (Point2D.X_ORDER.compare(p, node.p) < 0)
                return containsNode(node.left, p);
            else
                return containsNode(node.right, p);
        }
        else {  // Vertical line.
            if (Point2D.Y_ORDER.compare(p, node.p) < 0)
                return containsNode(node.left, p);
            else
                return containsNode(node.right, p);
        }
    }
    

    // draw all of the points to standard draw
    public void draw() {                             
        drawNode(myRoot);
    }
    
    private void drawNode(Node node) {
        if (node == null)
            return;
        
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        node.p.draw();
        
        StdDraw.setPenRadius(.003);
        
        if (node.depth % 2 == 0) {
            StdDraw.setPenColor(StdDraw.RED);
            node.p.drawTo(new Point2D(node.p.x(), node.rect.ymax()));
            node.p.drawTo(new Point2D(node.p.x(), node.rect.ymin()));
            
            drawNode(node.left);
            drawNode(node.right);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            node.p.drawTo(new Point2D(node.rect.xmax(), node.p.y()));
            node.p.drawTo(new Point2D(node.rect.xmin(), node.p.y()));
            
            drawNode(node.left);
            drawNode(node.right);
        }
        
    }
    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        
        Stack<Point2D> stack = new Stack<Point2D>();
        rangeNode(myRoot, stack, rect);
        return stack;
    }
    
    private void rangeNode(Node node, Stack<Point2D> stack, RectHV rect) { 
        
        if (node == null)
            return;
        
        if (!node.rect.intersects(rect))
            return;
        
        if (rect.contains(node.p))
            stack.push(node.p);
        
        rangeNode(node.left, stack, rect);
        rangeNode(node.right, stack, rect);
     
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        // find the distance from the query point p to the 
        Stack<Point2D> stack = new Stack<Point2D>();
        nearestNode(myRoot, p, stack, 1.5);
        return stack.pop();
    }
    
    private void nearestNode(Node node, Point2D p, Stack<Point2D> stack,
                                                     double distance) {
        if (node == null)
            return;
        
        if (distance < node.rect.distanceSquaredTo(p))
            return;
        
        double distanceNew;
        if (p.distanceSquaredTo(node.p) < distance) {
            stack.push(node.p);
            distanceNew = p.distanceSquaredTo(node.p);
        }
        
        if (node.depth % 2 == 0) { // Vertical line
            if (p.x() < node.p.x()) { // left of the vertical line
                distanceNew = p.distanceSquaredTo(stack.peek());
                nearestNode(node.left, p, stack, distanceNew);
                distanceNew = p.distanceSquaredTo(stack.peek());
                nearestNode(node.right, p, stack, distanceNew);
            }
            else {
                distanceNew = p.distanceSquaredTo(stack.peek());
                nearestNode(node.right, p, stack, distanceNew);
                distanceNew = p.distanceSquaredTo(stack.peek());
                nearestNode(node.left, p, stack, distanceNew);                
            }
        }
        else {
            if (p.y() < node.p.y()) { // right of the vertical line
                distanceNew = p.distanceSquaredTo(stack.peek());
                nearestNode(node.left, p, stack, distanceNew);
                distanceNew = p.distanceSquaredTo(stack.peek());
                nearestNode(node.right, p, stack, distanceNew);
            }
            else {
                distanceNew = p.distanceSquaredTo(stack.peek());
                nearestNode(node.right, p, stack, distanceNew);
                distanceNew = p.distanceSquaredTo(stack.peek());
                nearestNode(node.left, p, stack, distanceNew);                
            }
        }
    }

    // test client
    public static void main(String[] args) {
       
        KdTree kdTree = new KdTree();
        Point2D p1 = new Point2D(0.3, 0.4);
        StdOut.println("The size of the pointset is " + kdTree.size());
        kdTree.insert(p1);
        kdTree.insert(new Point2D(0.2, 0.3));
        kdTree.insert(new Point2D(0.1, 0.5));
        kdTree.insert(new Point2D(0.5, 0.6));
        kdTree.insert(new Point2D(0.2, 0.9));
        kdTree.insert(new Point2D(0.05, 0.05));
        RectHV rect = new RectHV(0.05, 0.05, 0.45, 0.45);
        rect.draw();
        StdOut.println("The size of the pointset is not " + kdTree.size());
        // print the number of points in the stack. 
        StdOut.println("The points inside the rectange are :");
        for (Point2D p:kdTree.range(rect))
            StdOut.println(p.toString());
        
        StdOut.println("Finding the nearest point to the given point:");
        StdOut.println(kdTree.nearest(new Point2D(0.21, 0.33)).toString());
        kdTree.draw();
        
    }
    
    
}

