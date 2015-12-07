// Copy paste this Java Template and save it as "BabyNames.java"

import java.util.*;
import java.io.*;

class BabyNames {
    // if needed, declare a private data structure here that
    // is accessible to all methods in this class
    // --------------------------------------------
    private ArrayList<String> boysNames, girlsNames;
    public static final int NUM_BABIES = 26;

    // --------------------------------------------

    public BabyNames() {
        // Write necessary code during construction;
        boysNames = new ArrayList<String>(NUM_BABIES);
        girlsNames = new ArrayList<String>(NUM_BABIES);
    }

    void AddSuggestion(String babyName, int genderSuitability) {
        // You have to insert the information (babyName, genderSuitability)
        // into your chosen data structure
        //
        // write your answer here
        // Add new name to respective arraylist based on gender.
        if (genderSuitability == 1) {
            boysNames.add(babyName);
        }
        if (genderSuitability == 2) {
            girlsNames.add(babyName);
        }
    }

    void RemoveSuggestion(String babyName) {
        // You have to remove the babyName from your chosen data structure
        //
        //Use Arraylist remove method to remove first occurrence of babyName in either list.
        if (!boysNames.remove(babyName)) {
            girlsNames.remove(babyName);
        }
        // --------------------------------------------
    }

    int Query(String START, String END, int genderPreference) {
        int ans = 0;

        // You have to answer how many baby name starts
        // with prefix that is inside query interval [START..END)
        if (genderPreference == 0) {
            ans = countBoys(START, END, ans);
            ans = countGirls(START, END, ans);
        } else if (genderPreference == 1) {
            ans = countBoys(START, END, ans);
        } else {
            ans = countGirls(START, END, ans);
        }
        return ans;
    }

    //Counts the number of girls names within the range
    private int countGirls(String START, String END, int ans) {
        for (String name : girlsNames) {
            if (name.compareTo(START) >= 0 && name.compareTo(END) < 0) { //right open
                ans++;
            }
        }
        return ans;
    }

    //Counts the number of boys names within the range
    private int countBoys(String START, String END, int ans) {
        for (String name : boysNames) {
            if (name.compareTo(START) >= 0 && name.compareTo(END) < 0) { //right open
                ans++;
            }
        }
        return ans;
    }

    void run() throws Exception {
        // do not alter this method to avoid unnecessary errors with the automated judging
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int command = Integer.parseInt(st.nextToken());
            if (command == 0) // end of input
                break;
            else if (command == 1) // AddSuggestion
                AddSuggestion(st.nextToken(), Integer.parseInt(st.nextToken()));
            else if (command == 2) // RemoveSuggestion
                RemoveSuggestion(st.nextToken());
            else // if (command == 3) // Query
                pr.println(Query(st.nextToken(), // START
                        st.nextToken(), // END
                        Integer.parseInt(st.nextToken()))); // GENDER
        }
        pr.close();
    }

    public static void main(String[] args) throws Exception {
        // do not alter this method to avoid unnecessary errors with the automated judging
        BabyNames ps2 = new BabyNames();
        ps2.run();
    }
}