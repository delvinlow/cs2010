import java.util.*;
import java.io.*;

class SchedulingDeliveries {
    // if needed, declare a private data structure here that
    // is accessible to all methods in this class
    class Woman {
        String womanName;
        int dilation;

        public Woman(String womanName, int dilation) {
            this.womanName = womanName;
            this.dilation = dilation;
        }

        public Woman(Woman another) {
            this.womanName = another.getWomanName();
            this.dilation = another.getDilation();
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
    }

    private ArrayList<Woman> womenArrayList;

    public SchedulingDeliveries() {
        // Write necessary code during construction
        //
        // write your answer here
        womenArrayList = new ArrayList<Woman>();
    }

    void ArriveAtHospital(String womanName, int dilation) {
        // You have to insert the information (womanName, dilation)
        // into your chosen data structure
        //
        // write your answer here
        womenArrayList.add(new Woman(womanName, dilation));
    }

    void UpdateDilation(String womanName, int increaseDilation) {
        // You have to update the dilation of womanName to
        // dilation += increaseDilation
        // and modify your chosen data structure (if needed)
        //
        // write your answer here
        for (Woman woman : womenArrayList) {
            if (woman.getWomanName().equals(womanName)) {
                int newDilation = woman.getDilation() + increaseDilation;
                woman.setDilation(newDilation);
            }
        }
    }

    void GiveBirth(String womanName) {
        // This womanName gives birth 'instantly'
        // remove her from your chosen data structure
        //
        // write your answer here
        for (int j = 0; j < womenArrayList.size(); j++) {
            if (womenArrayList.get(j).getWomanName().equals(womanName)) {
                womenArrayList.remove(j);
            }
        }
    }

    String Query() {
        String ans = "The delivery suite is empty";

        // You have to report the name of the woman that the doctor
        // has to give the most attention to. If there is no more woman to
        // be taken care of, return a String "The delivery suite is empty"
        //
        // write your answer here
        if (!womenArrayList.isEmpty()) {
            int indexMax = 0;
            for (int j = 1; j < womenArrayList.size(); j++) {
                if (womenArrayList.get(j).getDilation() > womenArrayList.get(indexMax).getDilation()) {
                    indexMax = j;
                }
            }
            ans = womenArrayList.get(indexMax).getWomanName();
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