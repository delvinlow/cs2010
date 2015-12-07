import java.util.*;
import java.io.*;

class SchedulingDeliveries {
    // if needed, declare a private data structure here that
    // is accessible to all methods in this class

    class Woman {
        private String womanName;
        private int dilation;
        private int timestamp;

        public Woman(String womanName, int dilation, int counter){
            this.womanName = womanName;
            this.dilation = dilation;
            this.timestamp = counter;
        }

        public Woman(Woman another){
            this.womanName = another.getWomanName();
            this.dilation = another.getDilation();
            this.timestamp = another.timestamp;
        }

        //Getters & setters
        public String getWomanName() {
            return womanName;
        }

        public void setWomanName(String womanName) {
            this.womanName = womanName;
        }

        public int getDilation() {
            return dilation;
        }

        public void setDilation(int dilation) {
            this.dilation = dilation;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        @Override
        public String toString() {
            return "[" + getWomanName() + ", " + getDilation() + ", " + getTimestamp() + "]";
        }
    }

    private static final int MAX_WOMAN = 200020;
    private Woman[] womenHeap;
    private int heapsize;
    private int counter;

    private TreeMap <String, Integer> treeMap;
    
    public SchedulingDeliveries() {
        womenHeap = new Woman[MAX_WOMAN];
        treeMap = new TreeMap<String, Integer>();
        counter = 0;
        heapsize = 0;
    }

    void ArriveAtHospital(String womanName, int dilation) {
        // You have to insert the information (womanName, dilation)
        // into your chosen data structure
        counter++;
        Woman incoming = new Woman(womanName, dilation, counter);
        heapsize++;
        womenHeap[heapsize] = incoming;
        treeMap.put(womanName, heapsize);
        shiftUp(heapsize);
    }

    private void shiftUp(int i){
        while ( i > 1){
            if (womenHeap[i].getDilation() > womenHeap[parent(i)].getDilation()){
                //Swaps to maintain max Heap property
                Woman temp = new Woman(womenHeap[i]); //Copies the woman to temp
                womenHeap[i] = womenHeap[parent(i)];
                womenHeap[parent(i)] = temp;

                //To updateKey in O(lg n), maintain their names and corresponding indexes in a tree map
                treeMap.put(womenHeap[parent(i)].getWomanName(), parent(i));
                treeMap.put(womenHeap[i].getWomanName(), i);
            } else if (womenHeap[i].getDilation()== womenHeap[parent(i)].getDilation()){
                if (womenHeap[i].getTimestamp() < womenHeap[parent(i)].getTimestamp()){
                    //Swaps to maintain max Heap property
                    Woman temp = new Woman(womenHeap[i]); //Copies the woman to temp
                    womenHeap[i] = womenHeap[parent(i)];
                    womenHeap[parent(i)] = temp;

                    //To updateKey in O(lg n), maintain their names and corresponding indexes in a tree map
                    treeMap.put(womenHeap[parent(i)].getWomanName(), parent(i));
                    treeMap.put(womenHeap[i].getWomanName(), i);

                }
            }else {
                break;
            }
            i = parent(i);
        }
    }

    /* Update the keys in heap by searching in binary search tree for correct index first. */
    void UpdateDilation(String womanName, int increaseDilation) {
        int index = treeMap.get(womanName);
        //update the dilation
        int currDilation = womenHeap[index].getDilation();
        womenHeap[index].setDilation(currDilation + increaseDilation);

        //To maintain the max heap property, call shiftUp on index
        shiftUp(index);
    }

    //Different from extractMax for part B. Now can extract anywhere.
    void GiveBirth(String womanName) {
        int indexToRemove = treeMap.get(womanName);
        //Swops with last element
        womenHeap[indexToRemove] = womenHeap[heapsize];
        //decrement counter to 'remove' woman
        heapsize--;

        //Update indexes
        treeMap.remove(womanName);
        treeMap.put(womenHeap[indexToRemove].getWomanName(), indexToRemove);

        //Can shiftUp or shiftDown depending on where is the element to remove.
        if (indexToRemove > 1 && womenHeap[indexToRemove].getDilation() > womenHeap[parent(indexToRemove)].getDilation()){
            shiftUp(indexToRemove);
        } else {
            shiftDown(indexToRemove);
        }
    }

    private void shiftDown(int i) {
        while (i <= heapsize){
            int maxDilation = womenHeap[i].getDilation();
            int max_index = i;
            int maxTimestamp = womenHeap[i].getTimestamp();
            if (left(i) <= heapsize && maxDilation < womenHeap[left(i)].getDilation()){
                maxDilation = womenHeap[left(i)].getDilation();
                max_index = left(i);
                maxTimestamp = womenHeap[left(i)].getTimestamp();
            }
            //Compares timestamp if same dilation
            if (left(i) <= heapsize && maxDilation == womenHeap[left(i)].getDilation()){
                if (womenHeap[left(i)].getTimestamp() < maxTimestamp) {
                    maxDilation = womenHeap[left(i)].getDilation();
                    max_index = left(i);
                    maxTimestamp = womenHeap[left(i)].getTimestamp();
                }
            }
            if (right(i) <= heapsize && maxDilation < womenHeap[right(i)].getDilation()) {
                maxDilation = womenHeap[right(i)].getDilation();
                max_index = right(i);
                maxTimestamp = womenHeap[right(i)].getTimestamp();
            }
            if (right(i) <= heapsize && maxDilation == womenHeap[right(i)].getDilation()){
                if (womenHeap[right(i)].getTimestamp() < maxTimestamp) {
                    maxDilation = womenHeap[right(i)].getDilation();
                    max_index = right(i);
                    maxTimestamp = womenHeap[right(i)].getTimestamp();
                }
            }
            if (max_index != i){
                Woman temp = new Woman(womenHeap[max_index]);
                womenHeap[max_index] = womenHeap[i];
                womenHeap[i] = temp;

                // If there are movement in the heap, update the indexes in TreeMap.
                treeMap.put(womenHeap[max_index].getWomanName(), max_index);
                treeMap.put(womenHeap[i].getWomanName(), i);

                i = max_index ;
            } else {
                break;
            }
        }
    }

    // Navigation
    private static int parent(int i){
        return i/2;
    }

    private static int left(int i){
        return i << 1;
    }

    private static int right(int i){
        return (i << 1) + 1;
    }

    String Query() {
        String ans = "The delivery suite is empty";

        // You have to report the name of the woman that the doctor
        // has to give the most attention to. If there is no more woman to
        // be taken care of, return a String "The delivery suite is empty"
        //
        // write your answer here
        if (heapsize > 0){
            ans = womenHeap[1].getWomanName();
        }
        return ans;
    }

    void run() throws Exception {
        // do not alter this method
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        int numCMD = Integer.parseInt(br.readLine()); // note that numCMD is >= N
        while (numCMD-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int command = Integer.parseInt(st.nextToken());
            switch (command) {
                case 0:
                    ArriveAtHospital(st.nextToken(), Integer.parseInt(st.nextToken()));
                    break;
                case 1:
                    UpdateDilation(st.nextToken(), Integer.parseInt(st.nextToken()));
                    break;
                case 2:
                    GiveBirth(st.nextToken());
                    break;
                case 3:
                    pr.println(Query());
                    break;
            }
        }
        pr.close();
    }

    public static void main(String[] args) throws Exception {
        // do not alter this method
        SchedulingDeliveries ps1 = new SchedulingDeliveries();
        ps1.run();
    }
}