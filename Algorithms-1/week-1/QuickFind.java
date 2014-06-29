import java.io.*;

public class QuickFind {
    
    private int[] id;
    public QuickFind(int N) {
        id = new int[N];
        for (int i = 0; i< N; i++) id[i] = i;
    }
    
    
    public boolean connected(int p, int q) {
        // p and q are connected if they have the same id.
        return id[p] == id[q];
    }
    
    public void union(int p, int q) {
        // change the id[p] to that of q
        int pid = id[p];
        int qid = id[q];
        for (int i=0; i < id.length; i++)
            if (id[i] == pid) id[i] = qid;
    }
    
    public static void main(String[] args) throws IOException {
        
        System.out.println("Enter the total number of items (N): ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        
        System.out.println("Enter the number of union operations (M):");
        int M = Integer.parseInt(br.readLine());
        
        // Initializing the ids
        QuickFind qf = new QuickFind(N);
        for (int i=0; i<N; i++)
            System.out.print(qf.id[i] + " ");

        // Read in pairs of numbers on which we perform the union operation.
        for (int i=0; i<M; i++) {
            System.out.println("\n" + (i+1) + ":");
            int p = Integer.parseInt(br.readLine());
            int q = Integer.parseInt(br.readLine());
            System.out.println(p + ", "+ q );
            qf.union(p, q);
        }
        
        // Print the id array at the end
        for (int i=0; i<N; i++)
            System.out.print(qf.id[i] + " ");
    }
}
