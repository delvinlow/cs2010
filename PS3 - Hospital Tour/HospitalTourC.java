import java.util.*;
import java.io.*;

class HospitalTour {
    private int V; // number of vertices in the graph (number of rooms in the hospital)
    private int[] RatingScore; // the weight of each vertex (rating score of each room)
    private ArrayList<MyIntegerPair> EdgeList;

    // Class for edge List
    class MyIntegerPair {
        int startVertex;
        int endVertex;

        public MyIntegerPair(int start, int end) {
            startVertex = start;
            endVertex = end;
        }

        public int getStartVertex() {
            return startVertex;
        }

        public int getEndVertex() {
            return endVertex;
        }
    }

    // UFDS code from lecture 5 notes
    class UnionFind {
        private ArrayList<Integer> parent, rank;
        private int countDisjoint = 0;

        public UnionFind(int N) {
            parent = new ArrayList<Integer>(N);
            rank = new ArrayList<Integer>(N);
            countDisjoint = N;
            for (int j = 0; j < N; j++) {
                parent.add(j); // Initialize p[j] = j
                rank.add(0); // Initialize rank to 0
            }
        }

        // Union two sets if disjoint
        public void unionSet(int i, int j) {
            if (!isSameSet(i, j)) {
                int x = findSet(i);
                int y = findSet(j);
                if (rank.get(x) > rank.get(y)) {
                    parent.set(y, x);
                } else { //if rank is same
                    parent.set(x, y);
                    if (rank.get(x) == rank.get(y)) {
                        rank.set(y, rank.get(y) + 1);
                    }
                }
                countDisjoint--; //Decrement counters of disjoint sets
            }
        }

        public Boolean isSameSet(int i, int j) {
            return findSet(i) == findSet(j);
        }

        public int findSet(int i) {
            if (parent.get(i) == i) {
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
    }

    // You have to report the rating score of the important room (vertex)
    // with the lowest rating score in this hospital
    // or report -1 if that hospital has no important room
    int Query() {
        int ansIndex = -1;
        int ansRating = -1;
        // Ignore edges of every vertex (in each iteration) to simulate ignoring it
        for (int vertex = 0; vertex < V; vertex++) {
            UnionFind testConnectivity = new UnionFind(V);
            // Loop through the edge list
            for (int edgeNum = 0; edgeNum < EdgeList.size(); edgeNum++) {
                MyIntegerPair currEdge = EdgeList.get(edgeNum);
                // If edge contains the vertex to be ignored this round, continue with next edge
                if (currEdge.getStartVertex() == vertex || currEdge.getEndVertex() == vertex) {
                    continue;
                }
                // Otherwise, recognize the edge and union the 2 vertexes
                testConnectivity.unionSet(currEdge.getStartVertex(), currEdge.getEndVertex());
            }
            int countDisjointSets = testConnectivity.getCountDisjoint();
            if (countDisjointSets > 2){ //Found important room because connectivity broken
                if (ansIndex == -1){
                    ansIndex = vertex;
                    ansRating = RatingScore[ansIndex];
                }
                //Found better one
                if (RatingScore[vertex] < RatingScore[ansIndex]){
                    ansIndex = vertex;
                    ansRating = RatingScore[ansIndex];
                }
                if (RatingScore[vertex] == RatingScore[ansIndex]){
                    ansIndex = Math.min(vertex, ansIndex);
                    ansRating = RatingScore[ansIndex];
                }
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
            EdgeList = new ArrayList<MyIntegerPair>(V);
            for (int i = 0; i < V; i++) {
                st = new StringTokenizer(br.readLine());
                int k = Integer.parseInt(st.nextToken());
                while (k-- > 0) {
                    int j = Integer.parseInt(st.nextToken());
                    MyIntegerPair edge = new MyIntegerPair(i, j);
                    EdgeList.add(edge);
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