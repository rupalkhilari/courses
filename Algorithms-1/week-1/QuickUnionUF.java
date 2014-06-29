public class QuickUnionUF {
    
    private int[] id;
    public QuickUnionUF(int N) {
        id = new int[N];
        for (int i = 0; i< N; i++) id[i] = i;
    }
    
    private int root(int i) {
        // chase parent pointers till they are the same as the element.
        // i.e. the root has been reached.
        while ( i != id[i]) i = id[i];
        return i;
    }
    
    public boolean connected(int p, int q) {
        // check if p and q have the same root
        return root(p) == root(q);
    }
    
    public void union(int p, int q) {
        // change root of p to point to the root of q
        int i = root(p);
        int j = root(q);
        id[i] = j;
    }
}
