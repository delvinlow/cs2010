// Copy paste this Java Template and save it as "BabyNames.java"

import java.util.*;
import java.io.*;

class BabyNames {
    // if needed, declare a private data structure here that
    // is accessible to all methods in this class

    // --------------------------------------------
    private TreeSet<String> boysNames, girlsNames;

    // --------------------------------------------

    public BabyNames() {
        // Write necessary code during construction;
        //
        // write your answer here

        // --------------------------------------------
        boysNames = new TreeSet<String>();
        girlsNames = new TreeSet<String>();

        // --------------------------------------------
    }

    void AddSuggestion(String babyName, int genderSuitability) {
        // You have to insert the information (babyName, genderSuitability)
        // into your chosen data structure
        //
        // write your answer here

        // --------------------------------------------
        if (genderSuitability == 1) {
            boysNames.add(babyName);
        }
        if (genderSuitability == 2) {
            girlsNames.add(babyName);
        }
        // --------------------------------------------
    }

    void RemoveSuggestion(String babyName) {
        // You have to remove the babyName from your chosen data structure
        //
        // write your answer here

        // --------------------------------------------
        if (!boysNames.remove(babyName)) {
            girlsNames.remove(babyName);
        }


        // --------------------------------------------
    }

    int Query(String START, String END, int genderPreference) {
        int ans = 0;

        // You have to answer how many baby name starts
        // with prefix that is inside query interval [START..END)
        //
        // write your answer here
        SortedSet<String> preceedingBoysNames, exceedingBoysNames, preceedingGirlsNames, exceedingGirlsNames;

        // --------------------------------------------
        if (genderPreference == 0) {
            int totalNames = boysNames.size() + girlsNames.size();
            preceedingBoysNames = boysNames.headSet(START);
            exceedingBoysNames = boysNames.tailSet(END, true);

            preceedingGirlsNames = girlsNames.headSet(START);
            exceedingGirlsNames = girlsNames.tailSet(END, true);

            ans = totalNames - preceedingBoysNames.size() - exceedingBoysNames.size() -
                    preceedingGirlsNames.size() - exceedingGirlsNames.size();
        } else if (genderPreference == 1) {
            preceedingBoysNames = boysNames.headSet(START);
            exceedingBoysNames = boysNames.tailSet(END, true);
            ans = boysNames.size() - preceedingBoysNames.size() - exceedingBoysNames.size();
        } else {
            preceedingGirlsNames = girlsNames.headSet(START);
            exceedingGirlsNames = girlsNames.tailSet(END, true);
            ans = girlsNames.size() - preceedingGirlsNames.size() - exceedingGirlsNames.size();
        }
        // --------------------------------------------

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