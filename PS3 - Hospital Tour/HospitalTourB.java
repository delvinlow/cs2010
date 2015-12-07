import java.util.*;
import java.io.*;

class HospitalTour {
    private int V; // number of vertices in the graph (number of rooms in the hospital)
    private int[][] AdjMatrix; // the graph (the hospital)
    private int[] RatingScore; // the weight of each vertex (rating score of each room)
    private Boolean[] visited;

    // UFDS code from lecture 5 notes
    class UnionFind {
        private ArrayList<Integer> parent, rank;
        private int countDisjoint = 0;

        public UnionFind(int N){
            parent = new ArrayList<Integer>(N);
            rank = new ArrayList<Integer>(N);
            countDisjoint = N;
            for (int j = 0; j < N; j++){
                parent.add(j); // Initialize p[j] = j
                rank.add(0); // Initialize rank to 0
            }
            // Union the disjoint sets if there's an edge
            for (int row = 0; row < V; row++){
                for ( int col = 0; col < V; col++){
                    if (AdjMatrix[row][col] == 1){
                        unionSet(row, col);
                    }
                }
            }
        }

        // Union two sets if disjoint
        public void unionSet(int i, int j){
            if ( !isSameSet(i, j)){
                int x = findSet(i);
                int y = findSet(j);
                if (rank.get(x) > rank.get(y)){
                    parent.set(y, x);
                } else { //if rank is same
                    parent.set(x, y);
                    if (rank.get(x) == rank.get(y)){
                        rank.set(y, rank.get(y) + 1);
                    }
                }
                countDisjoint--; //Decrement counters of disjoint sets
            }
        }

        public Boolean isSameSet(int i, int j){
            return findSet(i) == findSet(j);
        }

        public int findSet(int i){
            if (parent.get(i) == i){
                return i;
            } else {
                int root = findSet(parent.get(i));
                parent.set(i, root); //Path compression
                return root;
            }
        }

        public int getCountDisjoint() {
            return countDisjoint;
        }
    }
    public HospitalTour() {
        // Write necessary code during construction
        // write your answer here
    }

    int Query() {
        int ansIndex = -1;
        int ansRating = -1;
        // You have to report the rating score of the important room (vertex)
        // with the lowest rating score in this hospital
        // or report -1 if that hospital has no important room

        for (int row = 0; row < V; row++) {
            int removed[] = new int[V];
            // Remove vertex entries
            for (int col = 0; col < V; col++) {
                if (AdjMatrix[row][col] == 1) {
                    AdjMatrix[row][col] = 0;
                    AdjMatrix[col][row] = 0;
                    removed[col] = 1;
                }
            }

            UnionFind testConnectivity = new UnionFind(V);
            int countDisjointSets = testConnectivity.getCountDisjoint();
            if (countDisjointSets > 2){ //Found important room because connectivity broken
                if (ansIndex == -1){
                    ansIndex = row;
                    ansRating = RatingScore[ansIndex];
                }
                //Found better one
                if (RatingScore[row] < RatingScore[ansIndex]){
                    ansIndex = row;
                    ansRating = RatingScore[ansIndex];
                }
                if (RatingScore[row] == RatingScore[ansIndex]){
                    ansIndex = Math.min(row, ansIndex);
                    ansRating = RatingScore[ansIndex];
                }

            }
            //Restore vertexes entries 1s for next round
            for (int col = 0; col < V; col++) {
                AdjMatrix[row][col] = removed[col];
                AdjMatrix[col][row] = removed[col];
            }
        }
        return ansRating;
    }

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

    Integer first() { return _first; }
    Integer second() { return _second; }
}