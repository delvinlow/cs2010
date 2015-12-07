import java.util.*;
import java.io.*;

class OutForAWalk {
    private int V; // number of vertices in the graph (number of rooms in the building)
    private Vector < Vector < IntegerPair > > AdjList; // the weighted graph (the building), effort rating of each corridor is stored here too
    // For MST
    private Vector<Boolean> taken;
    private PriorityQueue <IntegerTriple> pq;
    private int [][] ansQueries;
    // For DFS
    private ArrayList<ArrayList<IntegerPair>> mstAdjList; // cannot use EdgeList because DFS needs neighbours
    private Vector <Boolean> visited;
    int currDFSsource;

    public OutForAWalk() {
    }

    void PreProcess() {
        // Initialization
        taken = new Vector<Boolean>(V);
        taken.addAll(Collections.nCopies(V, false));
        pq = new PriorityQueue<IntegerTriple>();
        mstAdjList = new ArrayList<ArrayList<IntegerPair>>();
        for (int i = 0; i < V; i++) {
            mstAdjList.add(new ArrayList<IntegerPair>());
        }

        // Build mst ONCE using Prim's algorithm
        enqueueNeighbours(0);// Enqueues neighbours of vertex 0
        while (!pq.isEmpty()) { // Loop till pq is empty
            IntegerTriple firstElement = pq.poll();
            if (!taken.get(firstElement.second())) {
                enqueueNeighbours(firstElement.second());
                // Add to mstAdjList in form [u -> (w, v), ...] to include into MST for DFS
                int vertex = firstElement.third();
                mstAdjList.get(vertex).add(new IntegerPair(firstElement.first(), firstElement.second()));
                mstAdjList.get(firstElement.second()).add(new IntegerPair(firstElement.first(), vertex)); // Opp direction
            }
        }

        // Call DFS from vertex 0 to possibly 10 to fill the 2D array
        int numSrc = (V >= 10)? 10: V; // Must be > not >=
        ansQueries = new int[numSrc][V];
        for (int k = 0; k < numSrc; k++) { // Loop through vertexes 0 to 10 (max) and start DFS there
            visited = new Vector<Boolean>();
            visited.addAll(Collections.nCopies(V, false));
            currDFSsource = k;
            DFS(k, 0); //vertex k, curMaxWeight = 0
        }
    }

    private void enqueueNeighbours(int vertex){
        taken.set(vertex, true);
        for (int j = 0; j < AdjList.get(vertex).size(); j++) {
            IntegerPair neighbour = AdjList.get(vertex).get(j);
            if(!taken.get(neighbour.first())){
                // Add IntegerTriples of weight, neighbour vertex index (reversed), and the vertex
                pq.add(new IntegerTriple(neighbour.second(), neighbour.first(), vertex));

            }
        }
    }

    void DFS(int u, int curMaxWeight){
        visited.set(u, true);
        for (int v = 0; v < mstAdjList.get(u).size(); v++) {
            IntegerPair currentPair = mstAdjList.get(u).get(v);
            if (!visited.get(currentPair.second())){
                // Dont use currMaxWeight because it will overwrite in next iteration. Use new variable.
                int weight = Math.max(curMaxWeight, currentPair.first());
                ansQueries[currDFSsource][currentPair.second()] = weight;
                DFS(currentPair.second(), weight);
            }
        }
    }

    // You have to report the weight of a corridor (an edge)
    // which has the highest effort rating in the easiest path from source to destination for Grace
    int Query(int source, int destination) {
        return ansQueries[source][destination];
    }

    void run() throws Exception {
        // do not alter this method
        IntegerScanner sc = new IntegerScanner(System.in);
        PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        int TC = sc.nextInt(); // there will be several test cases
        while (TC-- > 0) {
            V = sc.nextInt();

            // clear the graph and read in a new graph as Adjacency List
            AdjList = new Vector < Vector < IntegerPair > >();
            for (int i = 0; i < V; i++) {
                AdjList.add(new Vector < IntegerPair >());

                int k = sc.nextInt();
                while (k-- > 0) {
                    int j = sc.nextInt(), w = sc.nextInt();
                    AdjList.get(i).add(new IntegerPair(j, w)); // edge (corridor) weight (effort rating) is stored here
                }
            }

            PreProcess(); // you may want to use this function or leave it empty if you do not need it

            int Q = sc.nextInt();
            while (Q-- > 0)
                pr.println(Query(sc.nextInt(), sc.nextInt()));
            pr.println(); // separate the answer between two different graphs
        }

        pr.close();
    }

    public static void main(String[] args) throws Exception {
        // do not alter this method
        OutForAWalk ps4 = new OutForAWalk();
        ps4.run();
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



class IntegerPair implements Comparable < IntegerPair > {
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



class IntegerTriple implements Comparable < IntegerTriple > {
    Integer _first, _second, _third;

    public IntegerTriple(Integer f, Integer s, Integer t) {
        _first = f;
        _second = s;
        _third = t;
    }

    public int compareTo(IntegerTriple o) {
        if (!this.first().equals(o.first()))
            return this.first() - o.first();
        else if (!this.second().equals(o.second()))
            return this.second() - o.second();
        else
            return this.third() - o.third();
    }

    Integer first() { return _first; }
    Integer second() { return _second; }
    Integer third() { return _third; }
}