/*************************************************************************
 *  Compilation:  javac RandomizedQueue.java
 *  Execution:    java RandomizedQueue
 * 
 *  A generic randomized queue, implemented using a linked list. Each 
 *  queue element is of type Item. 
 *
 *************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private Node first, last;
    private int N;

    // helper linked list class
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    public RandomizedQueue() {
        first = null;
        last = null;
        N = 0;
    }

    public int size() {
        return N+1;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void enqueue(Item item) {
        
        if (item == null) 
            throw new NullPointerException();
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = null;
        if (isEmpty()) {
            first = last;
            N = 0;
        }
        else {
            oldLast.next = last;
            last.prev = oldLast;
            N++;
        }
        
    }
    

    public Item dequeue() {
        
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        // select a random item to dequeue.
        int index;
        if (size() == 1)
            index = 0;
        else 
            index = StdRandom.uniform(N);
        // keep traversing till we reach the required node.
        // StdOut.println("selected index: " + index + " to delete");
        Iterator i = iterator();
        int counter = 0;
        while (i.hasNext()) {
            Item item = (Item) i.next();
            if (counter++ == index) { 
                // iterate from the first to the current,
                // remove from the queue.
                Node current = first;
                while (current.item != item) {
                    current = current.next;
                }
                // remove the current node from the queue.
                //StdOut.println("current item is " + current.item);
                if (current.prev != null) {
                    //StdOut.println("current prev is " + current.prev.item);
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                }
                else {
                    first = current.next;
                    if (current.next != null)
                        current.next.prev = current.prev;
                }
                current = null; // prevent loitering.
                if (isEmpty())
                    N = 0;
                else
                    N--;
                //StdOut.println("Deleted item = " + item);
                return item;
            }
        }
        return null;
    }
    
    public Item sample() {
        
        if (isEmpty())
            throw new java.util.NoSuchElementException();
 
        int index = StdRandom.uniform(N);
        Iterator i = iterator();
        int counter = 0;
        while (i.hasNext()) {
            Item item = (Item) i.next();
            if (counter++ == index) {
                return item;
            }
        }
        return null;
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
    
    private class RandomizedQueueIterator implements Iterator<Item> {

        private Node current = first;
        
        public boolean hasNext() { 
            return current != null;
        }
        public void remove() { 
            throw new UnsupportedOperationException();
        }
        
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }
    
    // Remove after testing.
    private void printQueue() {
        Iterator dIterator = this.iterator();
        while (dIterator.hasNext()) {
            StdOut.println(dIterator.next());
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        rq.enqueue("first");
        rq.enqueue("second");
        rq.enqueue("third");
        rq.enqueue("fourth");
        rq.enqueue("fifth");
        rq.enqueue("six");
        rq.enqueue("seventh");
        rq.printQueue();
        rq.dequeue();
        rq.printQueue();
        rq.dequeue();
        rq.printQueue();
        rq.dequeue();
        rq.printQueue();
        rq.dequeue();
        rq.printQueue();
        rq.dequeue();
        rq.printQueue();
        rq.dequeue();
        rq.printQueue();
        rq.dequeue();
    }
}
