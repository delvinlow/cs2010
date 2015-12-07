// Copy paste this Java Template and save it as "Supermarket.java"
import java.util.*;
import java.io.*;

class Supermarket {
    private int N; // number of items in the supermarket. V = N+1
    private int K; // the number of items that Steven has to buy
    private int[] shoppingList; // indices of items that Steven has to buy
    private int[][] T; // the complete weighted graph that measures the direct walking time to go from one point to another point in seconds

    // if needed, declare a private data structure here that
    // is accessible to all methods in this class
    // --------------------------------------------
    private int [][] memo;
    private static final int INF = 999999999;

    private int[][] shortestDist;


    public Supermarket() {



    }

    int Query() {
        int ans = 0;

        // You have to report the quickest shopping time that is measured
        // since Steven enters the supermarket (vertex 0),
        // completes the task of buying K items in that supermarket as ordered by Grace,
        // then, reaches the cashier of the supermarket (back to vertex 0).
        //
        // write your answer here
        int numStates = (int) Math.pow(2, K + 1);
        // Num vertex * Num States
        int numVertices = N + 1;
        memo = new int[numVertices][numStates];
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numStates; j++) {
                memo[i][j] = -1;
            }
        }
        // for APSP
        floydWarshall();

        // TSP
        ans = go_to(0, 1); //Start at vertex 0, with rightmost bit turned on
        return ans;
    }

    void floydWarshall(){
        // Copy the 2D array first
        shortestDist = new int[N+1][N+1];
        for (int v = 0; v < T.length ; v++) {
            for (int x = 0; x < T[v].length; x++) {
                shortestDist[v][x] = T[v][x];
            }
        }

        // Floyd Warshall
        for (int k = 0; k < N+1; k++) {
            for (int i = 0; i < N+1; i++) {
                for (int j = 0; j < N+1; j++) {
                    shortestDist[i][j] = Math.min(shortestDist[i][j], shortestDist[i][k] + shortestDist[k][j]);
                }
            }
        }

        T = shortestDist;
    }

    // You can add extra function if needed
    // --------------------------------------------
    int go_to(int u, int mask){
        // All k items taken, so go back to entrance (vertex 0)
        if (mask == (1 << K + 1) - 1){
            return T[u][0];
        }
        // If memorized results before, just fetch it
        if (memo[u][mask] != -1) {
            return memo[u][mask];
        }

        // Initialize to INF
        memo[u][mask] = INF;
        for (int ver = 0; ver < K ; ver++){
            int item = shoppingList[ver];
            if (item != u && ((1 << ver +1) & mask) == 0 ){
                // Go to ver with new state as mask
                memo[u][mask] = Math.min(memo[u][mask], T[u][item] + go_to(item, mask | (1<< ver +1)));
            }
        }
        return memo[u][mask];
    }


    void run() throws Exception {
        // do not alter this method to standardize the I/O speed (this is already very fast)
        IntegerScanner sc = new IntegerScanner(System.in);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        int TC = sc.nextInt(); // there will be several test cases
        while (TC-- > 0) {
            // read the information of the complete graph with N+1 vertices
            N = sc.nextInt(); K = sc.nextInt(); // K is the number of items to be bought

            shoppingList = new int[K];
            for (int i = 0; i < K; i++)
                shoppingList[i] = sc.nextInt();

            T = new int[N+1][N+1];
            for (int i = 0; i <= N; i++)
                for (int j = 0; j <= N; j++)
                    T[i][j] = sc.nextInt();

            pw.println(Query());
        }

        pw.close();
    }

    public static void main(String[] args) throws Exception {
        // do not alter this method
        Supermarket ps6 = new Supermarket();
        ps6.run();
    }
}



class IntegerScanner { // coded by Ian Leow, using any other I/O method is not recommended
    BufferedInputStream bis;
    IntegerScanner(InputStream is) {
        bis = new BufferedInputStream(is, 1000000);
    }

    public int nextInt() {
        int result = 0;
        try {
            int cur = bis.read();
            if (cur == -1)
                return -1;

            while ((cur < 48 || cur > 57) && cur != 45) {
                cur = bis.read();
            }

            boolean negate = false;
            if (cur == 45) {
                negate = true;
                cur = bis.read();
            }

            while (cur >= 48 && cur <= 57) {
                result = result * 10 + (cur - 48);
                cur = bis.read();
            }

            if (negate) {
                return -result;
            }
            return result;
        }
        catch (IOException ioe) {
            return -1;
        }
    }
}