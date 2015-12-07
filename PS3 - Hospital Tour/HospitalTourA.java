
import java.util.*;
import java.io.*;

class HospitalTour {
    private int V; // number of vertices in the graph (number of rooms in the hospital)
    private int[][] AdjMatrix; // the graph (the hospital)
    private int[] RatingScore; // the weight of each vertex (rating score of each room)
    Boolean[] visited; // for DFS

    // if needed, declare a private data structure here that
    // is accessible to all methods in this class
    public HospitalTour() {
    }

    int Query() {
        int ans = -1;
        // You have to report the rating score of the important room (vertex)
        // with the lowest rating score in this hospital
        // or report -1 if that hospital has no important room
        // write your answer here
        Boolean isStillConnected = true;
        int currBest = 0;
        int removed[] = new int[V];
        visited = new Boolean[V];

        for (int j = 0; j < V; j++) {
            // Remove vertex entries
            for (int k = 0; k < V; k++) {
                if (AdjMatrix[j][k] == 1) {
                    AdjMatrix[j][k] = 0;
                    AdjMatrix[k][j] = 0;
                    removed[k] = 1;
                }
            }
            // initialization
            for (int currentVertex = 0; currentVertex < V; currentVertex++) {
                visited[currentVertex] = false;
            }
            // Test connectivity after a vertex's edges is removed
            isStillConnected = testConnectivity();
            // If false, implies removing that vertex breaks the tree connectivity
            if (!isStillConnected) {
                if (RatingScore[j] < RatingScore[currBest]) {
                    ans = RatingScore[j];
                    currBest = j;
                }
                if (RatingScore[j] == RatingScore[currBest]){
                    if (j <= currBest){
                        ans = RatingScore[j];
                        currBest = j;
                    }
                }
            }
            for (int v = 0; v < V; v++) {
                AdjMatrix[j][v] = removed[v]; //restore the 1s for next round
                AdjMatrix[v][j] = removed[v]; //restore the 1s for next round
            }
        }
        return ans;
    }

    // You can add extra function if needed
    // --------------------------------------------

    //Tests whether the tree is still connected
    public Boolean testConnectivity() {
        int count = dfs(0);
        return count >= V - 1;
    }

    private int dfs(int index) {
        visited[index] = true; // Avoid cycle back
        // Traverse down the adjacency matrix row to find neighbors
        for (int neighbour = 0; neighbour < V; neighbour++) {
            if (neighbour == index) {
                continue;
            }
            Boolean hasEdge = AdjMatrix[index][neighbour] == 1;
            if (hasEdge && !visited[neighbour]) {
                return 1 + dfs(neighbour);
            }
        }
        return 0;
    }


    // --------------------------------------------

    void run() throws Exception {
        // for this PS3, you can alter this method as you see fit

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        int TC = Integer.parseInt(br.readLine()); // there will be several test cases
        while (TC-- > 0) {
            br.readLine(); // ignore dummy blank line
            V = Integer.parseInt(br.readLine());

            StringTokenizer st = new StringTokenizer(br.readLine());
            // read rating scores, A (index 0), B (index 1), C (index 2), ..., until the V-th index
            RatingScore = new int[V];
            for (int i = 0; i < V; i++)
                RatingScore[i] = Integer.parseInt(st.nextToken());

            // clear the graph and read in a new graph as Adjacency Matrix
            AdjMatrix = new int[V][V];
            for (int i = 0; i < V; i++) {
                st = new StringTokenizer(br.readLine());
                int k = Integer.parseInt(st.nextToken());
                while (k-- > 0) {
                    int j = Integer.parseInt(st.nextToken());
                    AdjMatrix[i][j] = 1; // edge weight is always 1 (the weight is on vertices now)
                }
            }

            pr.println(Query());
        }
        pr.close();
    }

    public static void main(String[] args) throws Exception {
        // do not alter this method
        HospitalTour ps3 = new HospitalTour();
        ps3.run();
    }
}

abstract class IntegerPair implements Comparable {
    Integer _first, _second;

    public IntegerPair(Integer f, Integer s) {
        _first = f;
        _second = s;
    }

    public int compareTo(IntegerPair o) {
        if (!this.first().equals(o.first()))
            return this.first() - o.first();
        else
            return this.second() - o.second();
    }

    Integer first() {
        return _first;
    }

    Integer second() {
        return _second;
    }
}