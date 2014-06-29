import java.io.*;
// 6-8 2-5 6-0 9-8 8-1 3-7 7-2 9-4 0-3 
public class WeightedQuickUnion {
    
    private int[] id;
    private int[] sz;
    public WeightedQuickUnion(int N) {
        id = new int[N];
        sz = new int[N];
        for (int i = 0; i< N; i++) id[i] = i;
        for (int i = 0; i< N; i++) sz[i] = 1;
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
        if ( i == j ) return;
        if ( sz[i] < sz[j] ) {
            id[i] = j;
            sz[j] += sz[i];
        }
        else {
            id[j] = i;
            sz[i] += sz[j];
        }
    }
    
    public static void main(String[] args) throws IOException {
        
        System.out.println("Enter the total number of items (N): ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        
        System.out.println("Enter the number of union operations (M):");
        int M = Integer.parseInt(br.readLine());
        
        // Initializing the ids
        WeightedQuickUnion wqu = new WeightedQuickUnion(N);
        for (int i=0; i<N; i++)
            System.out.print(wqu.id[i] + " ");

        // Read in pairs of numbers on which we perform the union operation.
        for (int i=0; i<M; i++) {
            System.out.println("\n" + (i+1) + ":");
            int p = Integer.parseInt(br.readLine());
            int q = Integer.parseInt(br.readLine());
            System.out.println(p + ", "+ q );
            wqu.union(p, q);

            for (int j=0; j<N; j++)
                System.out.print(wqu.id[j] + " ");
        }
        
        // Print the id array at the end
        System.out.println("The elements of the id[] are: ");
        for (int i=0; i<N; i++)
            System.out.print(wqu.id[i] + " ");
        
        System.out.println("\nThe elements of the sz[] are: ");
        for (int i=0; i<N; i++)
            System.out.print(wqu.sz[i] + " ");
        
        
    }
}