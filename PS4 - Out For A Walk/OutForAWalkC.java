// Copy paste this Java Template and save it as "OutForAWalk.java"
import java.util.*;
import java.io.*;

class OutForAWalk {
    private int V; // number of vertices in the graph (number of rooms in the building)
    private Vector < Vector < IntegerPair > > AdjList; // the weighted graph (the building), effort rating of each corridor is stored here too
    private Vector<Boolean> taken;
    private PriorityQueue <IntegerPair> pq;

    public OutForAWalk() {
        // Write necessary codes during construction;
        //
        // write your answer here
    }

    void PreProcess() {
        // write your answer here
        // you can leave this method blank if you do not need it


    }

    int Query(int source, int destination) {
        int maxAns = 0;

        // You have to report the weight of a corridor (an edge)
        // which has the highest effort rating in the easiest path from source to destination for Grace
        //
        // write your answer here
        taken = new Vector<Boolean>(V);
        taken.addAll(Collections.nCopies(V, false));
        pq = new PriorityQueue<IntegerPair>();

        // Enqueues neighbours of source first, not vertex 0
        enqueueNeighbours(source);

        // Loop till pq is empty
        while (!pq.isEmpty()){
            IntegerPair firstElement = pq.poll();

            if (!taken.get(firstElement.second())){
                maxAns = Math.max(maxAns, firstElement.first()); // set the max
                // Break once found a path to destination
                if (firstElement.second() == destination){
                    break;
                }
                enqueueNeighbours(firstElement.second());
            }
        }

        return maxAns;
    }

    private void enqueueNeighbours(int vertex){
        taken.set(vertex, true);
        for (int j = 0; j < AdjList.get(vertex).size(); j++){
            IntegerPair neighbour = AdjList.get(vertex).get(j);
            if(!taken.get(neighbour.first())){
                // Add IntegerPairs of weight and Vertex index below!
                pq.add(new IntegerPair(neighbour.second(), neighbour.first()));
            }
        }
    }

    // You can add extra function if needed
    // --------------------------------------------



    // --------------------------------------------

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