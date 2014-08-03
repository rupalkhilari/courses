/*************************************************************************
 *  Compilation:  javac Subset.java
 *  Execution:    java Subset 8
 * 
 *  A generic randomized queue, implemented using a linked list. Each 
 *  queue element is of type Item. 
 *
 *************************************************************************/

public class Subset {
    
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        
        int N = 0;
        
        /*queue.enqueue("AA");
        queue.enqueue("BB");
        queue.enqueue("BB");
        queue.enqueue("BB");
        queue.enqueue("BB");
        queue.enqueue("BB");
        queue.enqueue("CC");
        queue.enqueue("CC");
        N = 8;*/

        while (StdIn.hasNextLine() && !StdIn.isEmpty()) {
            queue.enqueue(StdIn.readString());
            N++;
        }
        
        
        if (k <= N) { 
            for (int i = 0; i < k; i++) {
                StdOut.println(queue.dequeue());
            }
        }
        else {
            StdOut.println("k = " + k + "cannot be greater than input N = " + N);
        }
    }
}
