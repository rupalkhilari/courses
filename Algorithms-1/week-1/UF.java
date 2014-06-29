import java.io;

public class UF {
    
    int[][] numbers;
    public void UF(int N) {
        System.out.println("Called the constructor");
        numbers = new int[N][2];
    }
    
    public boolean connected(int p, int q) {
        return false;
    }
    
    public void union(int p, int q) {
        System.out.println("Connecting " +  p + " and " + q );
        
    }
    public static void main(String[] args) throws java.io.IOException {
        System.out.println("Enter the number of connected pairs: ");
        BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(buf.readLine());
        UF uf = new UF(N);
        while (!StdIn.isEmpty()) {
            int p = Integer.parseInt(buf.read());
            int q = Integer.parseInt(buf.read());
            if (!uf.connected(p, q)) {
                uf.union(p, q);
                System.out.println(p + " " + q);
            }
            else {
                System.out.println(p + " " + q + " are already connected.");
            }
        }
    }
}