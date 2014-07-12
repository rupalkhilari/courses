class Selection {
    
    private static boolean less(Comparable a, Comparable b) {
       return a.compareTo(b) < 0;
    }
    
    private static void exch(Comparable[] a, int i, int j) {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi+1;
        while (true) {
            
            while(less(a[++i], a[lo]))
                if ( i == hi ) break;
            
            while (less(a[lo], a[--j]))
                if ( j == lo ) break;
            
            if (i >= j) break;
            exch(a, i, j);
        }
        
        // exchange the position of the elements.
        exch(a, lo, j);

        return j;
    }
    public static Comparable select(Comparable[] a, int k) {
        
        StdRandom.shuffle(a);
        int lo = 0, hi = a.length - 1;
        while ( hi > lo ) {
            int j = partition(a, lo, hi);
            if ( j < k ) 
                lo = j + 1;
            else if ( j > k )
                hi = j - 1;
            else
                return a[k];
                
        }
        return a[k];
    }
    
    public static void main(String[] args) {
        int num;
        StdOut.println("Enter the number of elements in the array:");
        num = StdIn.readInt();
        StdOut.println("Enter a set of " + num + " elements " );
        Comparable[] a = new Comparable[num];
        for ( int i=0; i < num; i++ ) {
            a[i] = StdIn.readInt();
        }
        
        StdOut.println("Enter the index of the sorted element:");
        int index = StdIn.readInt();
        StdOut.println("The selected index is " + select(a, index));
    }
}