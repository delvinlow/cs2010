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
    }

    private static final int MAX_WOMAN = 200000;
    private Woman[] womenHeap;
    private int heapsize = 0;
    private int counter = 0;

    public SchedulingDeliveries() {
        womenHeap = new Woman[MAX_WOMAN];
    }

    void ArriveAtHospital(String womanName, int dilation) {
        // You have to insert the information (womanName, dilation)
        // into your chosen data structure
        counter++;
        Woman incoming = new Woman(womanName, dilation, counter);
        heapsize++;
        womenHeap[heapsize] = incoming;
        shiftUp(heapsize);
    }

    private void shiftUp(int i){
        while ( i > 1){
            if (womenHeap[i].getDilation() > womenHeap[parent(i)].getDilation()){
                //Swaps to maintain max Heap property
                Woman temp = new Woman(womenHeap[i]); //Copies the woman to temp
                womenHeap[i] = womenHeap[parent(i)];
                womenHeap[parent(i)] = temp;
                i = parent(i);
            } else{
                break;
            }
        }
    }

    // Navigation
    private static int parent(int i){
        return i/2;
    }

    /* Wont be called for part B*/
    void UpdateDilation(String womanName, int increaseDilation) {
        // You have to update the dilation of womanName to
        // dilation += increaseDilation
        // and modify your chosen data structure (if needed)
        //
        // write your answer here
    }

    //Same as extractMax for part B
    void GiveBirth(String womanName) {
        // This womanName gives birth 'instantly, remove her from your chosen data structure
        womenHeap[1] = womenHeap[heapsize];
        heapsize--;
        shiftDown(1);
    }

    private void shiftDown(int i) {
        while (i <= heapsize){
            int maxDilation = womenHeap[i].getDilation();
            int max_index = i;
            int maxTimestamp = womenHeap[i].getTimestamp();
            if (left(i) <= heapsize && maxDilation < womenHeap[left(i)].getDilation()){
                maxDilation = womenHeap[left(i)].getDilation();
                max_index = left(i);
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
                i = max_index;
            } else {
                break;
            }
        }
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
                case 0: ArriveAtHospital(st.nextToken(), Integer.parseInt(st.nextToken())); break;
                case 1: UpdateDilation(st.nextToken(), Integer.parseInt(st.nextToken())); break;
                case 2: GiveBirth(st.nextToken()); break;
                case 3: pr.println(Query()); break;
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