/*************************************************************************
 *  Compilation:  javac Deque.java
 *  Execution:    java Deque 
 *
 *   A double-ended queue or deque is a generalization of a stack 
 *   and a queue that supports inserting and removing items from either
 *   the front or the back of the data structure
 *  
 *************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;



public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private int N;

    // helper linked list class
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    public Deque() {
        first = null;
        last = null;
        N = 0;
    }

    public int size() {
        return N;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void addLast(Item item) {
        if (item == null)
            throw new NullPointerException("Cannot add an empty "
                + "element to the deque.");
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldLast;
        oldLast.next = last;
        if (N == 0)
            first = last;
        N++;
    }
    
    public void addFirst(Item item) {
        
        if (item == null)
            throw new NullPointerException("Cannot add an empty "
                + "element to the deque.");

        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        
        // if this was the first item, update the last pointer as well.
        if (N == 0)
            last = first;
        N++;
    }
    
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException("The Deque is empty. "
                + "Cannot remove the first element.");
        Item item = first.item;
        Node oldFirst = first;
        first = first.next;
        oldFirst.next = null; // avoid loitering
        N--;
        return item; 
    }
    
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException("The Deque is empty. "
                + "Cannot remove the last element");
        Item item = last.item;
        Node oldLast = last;
        last = last.prev;
        last.next = null; // avoid loitering.
        oldLast.next = null;
        N--;
        return item;
    }
    
    public Iterator<Item> iterator() {
        return new DequeItemIterator();
    }
    
    private class DequeItemIterator implements Iterator<Item> {

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
    private void printDeque() {
        Iterator dIterator = this.iterator();
        while (dIterator.hasNext()) {
            StdOut.println(dIterator.next());
        }
    }

    public static void main(String[] args) {
        Deque<String> d = new Deque<String>();
        StdOut.println("IsEmpty() returns " + d.isEmpty());
        d.addFirst("a");
        d.addFirst("b");
        d.addLast("c");
        d.addLast("d");
        d.addFirst("m");
        d.printDeque();
        StdOut.println("Removing the first and the last elements ");
        d.removeFirst();
        d.removeLast();
        d.printDeque();
        StdOut.println("The size of the deque is " + d.size());
        StdOut.println("IsEmpty() returns " + d.isEmpty());
        d.removeFirst();
        d.removeFirst();
        d.removeFirst();
        //d.addFirst(null);
    }
}
