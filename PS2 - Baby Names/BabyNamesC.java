import java.util.*;
import java.util.NoSuchElementException;
import java.io.*;

class BabyNames {
    // if needed, declare a private data structure here that
    // is accessible to all methods in this class

    // --------------------------------------------
    private AVLTree boysNames;
    private AVLTree girlsNames;
    // --------------------------------------------

    public BabyNames() {
        boysNames = new AVLTree();
        girlsNames = new AVLTree();
    }

    public static void main(String[] args) throws Exception {
        // do not alter this method to avoid unnecessary errors with the automated judging
        BabyNames ps2 = new BabyNames();
        ps2.run();
    }

    void AddSuggestion(String babyName, int genderSuitability) {
        if (genderSuitability == 1) {
            boysNames.insert(babyName);
        } else {
            girlsNames.insert(babyName);
        }
    }

    void RemoveSuggestion(String babyName) {
        boysNames.delete(babyName);
        girlsNames.delete(babyName);
    }

    int Query(String START, String END, int genderPreference) {
        //boysNames.printTree();
        //girlsNames.printTree();
        //System.out.println("--------------------------------------------");
        if (genderPreference == 1) {
            return Math.abs(countBoysName(START, END));
        } else if (genderPreference == 2) {
            return Math.abs(countGirlsName(START, END));
        } else {
            return Math.abs(countBoysName(START, END) + countGirlsName(START, END));
        }
    }

    private int countGirlsName(String START, String END) {
        int ans = girlsNames.rank(END) - girlsNames.rank(START);
        return ans;
    }

    private int countBoysName(String START, String END) {
        int end = boysNames.rank(END);
        int start = boysNames.rank(START);
        int ans = end - start;
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
}

class AVLTree extends BST {

    public AVLTree() {
        root = null;
    }

    @Override
    protected BSTVertex insert(BSTVertex v, String babyName) {
        if (v == null) {
            return new BSTVertex(babyName);
        } else if (v.key.compareTo(babyName) < 0) {
            v.right = insert(v.right, babyName);
            v.right.parent = v;
            v.size += 1; //Increment size when recursing up.
        } else {
            v.left = insert(v.left, babyName);
            v.left.parent = v;
            v.size += 1;
        }
        updateHeight(v);

        // Balance here.
        int bf = getBalanceFactor(v);
        if (bf == 2) {
            //case 1
            if (getBalanceFactor(v.left) == 1) {
                v = rotateRight(v);
                return v; //Return after one balancing.
            }
            //case 2
            if (getBalanceFactor(v.left) == -1) {
                v.left = rotateLeft(v.left);
                v = rotateRight(v);
                return v;
            }
        }
        if (bf == -2) {
            //case 3
            if (getBalanceFactor(v.right) == -1) {
                v = rotateLeft(v);
                return v;
            }
            //case 4
            if (getBalanceFactor(v.right) == 1) {
                v.right = rotateRight(v.right);
                v = rotateLeft(v);
                return v;
            }
        }
        return v;
    }

    private void updateHeight(BSTVertex v) {
        int leftHeight = -1;
        int rightHeight = -1;
        if (v.left != null) {
            leftHeight = v.left.getHeight();
        }
        if (v.right != null) {
            rightHeight = v.right.getHeight();
        }
        v.setHeight(Math.max(leftHeight, rightHeight) + 1);
    }

    private void updateSize(BSTVertex v) {
        if (v == null) {
            v.setSize(0);
        }
        int leftSize = 0;
        int rightSize = 0;
        if (v.left != null) {
            leftSize = v.left.size;
        }
        if (v.right != null) {
            rightSize = v.right.size;
        }
        v.setSize(leftSize + rightSize + 1);
    }

    BSTVertex rotateLeft(BSTVertex v) {
        BSTVertex pushUp = v.right;
        pushUp.parent = v.parent;
        v.parent = pushUp;
        v.right = pushUp.left;
        if (pushUp.left != null) {
            pushUp.left.parent = v;
        }
        pushUp.left = v;
        //Update the heights after rotating
        updateHeight(v);
        updateHeight(pushUp);
        updateSize(v);
        updateSize(pushUp);

        return pushUp;
    }

    BSTVertex rotateRight(BSTVertex v) {
        BSTVertex pushUp = v.left;
        pushUp.parent = v.parent;
        v.parent = pushUp;
        v.left = pushUp.right; //To transfer right child
        if (pushUp.right != null) {
            pushUp.right.parent = v;
        }
        pushUp.right = v;
        //Update the heights after rotating
        updateHeight(v);
        updateHeight(pushUp);
        updateSize(v);
        updateSize(pushUp);

        return pushUp;
    }

    protected int getBalanceFactor(BSTVertex T) {
        int leftHeight = -1;
        int rightHeight = -1;
        if (T.left != null) {
            leftHeight = T.left.getHeight();
        }
        if (T.right != null) {
            rightHeight = T.right.getHeight();
        }
        return leftHeight - rightHeight;
    }

    // Returns size of the subtree rooted at T
    protected int getSize(BSTVertex T) {
        int leftSize = 0;
        int rightSize = 0;
        if (T.left != null) {
            leftSize = T.left.size;
        }
        if (T.right != null) {
            rightSize = T.right.size;
        }
        return leftSize + rightSize + 1;
    }

    //Returns number of elements to the left
    protected int rank(BSTVertex T, String start) {
        if (T == null) {
            return 0;
        }
        if (start.compareTo(T.key) <= 0) {
            return rank(T.left, start);
        } else /*if (start.compareTo(T.key) > 0)*/ {
            if (T.left == null) {
                return rank(T.right, start) + 1; //Plus one for parent
            } else {
                return T.left.size + rank(T.right, start) + 1;
            }
        }
    }

    protected int rank(String start) {
        return rank(root, start);
    }

    protected BSTVertex delete(BSTVertex v, String babyName) {
        if (v == null) return v;
        if (v.key.equals(babyName)) {
            if (v.left == null && v.right == null)
                v = null;
            else if (v.left == null && v.right != null) {
                BSTVertex temp = v;
                v.right.parent = v.parent;
                v = v.right;
                temp = null;
                v = updateAndRotate(v);

            } else if (v.left != null && v.right == null) {
                BSTVertex temp = v;
                v.left.parent = v.parent;
                v = v.left;
                temp = null;
                v = updateAndRotate(v);
            } else {
                String successorV = successor(babyName);
                v.key = successorV;
                v.right = delete(v.right, successorV);
                v = updateAndRotate(v);
            }
        } else if (v.key.compareTo(babyName) < 0) {
            v.right = delete(v.right, babyName);
            v = updateAndRotate(v);
        } else {
            v.left = delete(v.left, babyName);
            v = updateAndRotate(v);
        }

        return v;
    }

    private BSTVertex updateAndRotate(BSTVertex v) {
        // Update the height
        updateHeight(v);
        updateSize(v);

        // Balance here.
        int bf = getBalanceFactor(v);
        if (bf == 2) {
            //case 1
            if (getBalanceFactor(v.left) == 1) {
                v = rotateRight(v);
            }
            //case 2
            if (getBalanceFactor(v.left) == -1) {
                v.left = rotateLeft(v.left);
                v = rotateRight(v);
            }
        }
        if (bf == -2) {
            //case 3
            if (getBalanceFactor(v.right) == -1) {
                v = rotateLeft(v);
            }
            //case 4
            if (getBalanceFactor(v.right) == 1) {
                v.right = rotateRight(v.right);
                v = rotateLeft(v);
            }
        }
        return v;
    }

    public void delete(String babyName) {
        root = this.delete(root, babyName);
    }

    public void printTree(BSTVertex v){
        if (v == null){
            return;
        } else {
            System.out.printf("%s ", v.key);
            printTree(v.left);
            printTree(v.right);
        }
    }
    public void printTree(){
        printTree(root);
        System.out.println();
    }
}

// Every vertex in this BST is a Java Class
class BSTVertex {
    // all these attributes remain public to slightly simplify the code
    public BSTVertex parent, left, right;
    public String key;
    public int height, size; // will be used in AVL lecture

    BSTVertex(String v) {
        key = v;
        parent = left = right = null;
        height = 0;
        size = 1;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getHeight() {
        if (this == null) {
            return -1;
        }
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

// Code from BSTDemo.java given in lecture
class BST {
    protected BSTVertex root;

    public BST() {
        root = null;
    }

    protected BSTVertex search(BSTVertex T, String babyName) {
        if (T == null) {
            return T;                                  // not found
        } else if (T.key.equals(babyName)) {
            return T;                                      // found
        } else if (babyName.compareTo(T.key) > 0) {
            return search(T.right, babyName);       // search to the right
        } else {
            return search(T.left, babyName);         // search to the left
        }
    }

    protected BSTVertex insert(BSTVertex T, String babyName) {
        if (T == null) {
            return new BSTVertex(babyName);
        }
        if (T.key.compareTo(babyName) < 0) {
            T.right = insert(T.right, babyName);
            T.right.parent = T;
        } else {
            T.left = insert(T.left, babyName);
            T.left.parent = T;
        }
        return T;
    }

    protected String findMin(BSTVertex T) {
        if (T == null) {
            throw new NoSuchElementException("BST is empty, no minimum");
        } else if (T.left == null) {
            return T.key;
        } else {
            return findMin(T.left);
        }
    }

    protected String findMax(BSTVertex T) {
        if (T == null) {
            throw new NoSuchElementException("BST is empty, no maximum");
        } else if (T.right == null) {
            return T.key;
        } else {
            return findMax(T.right);
        }
    }

    protected String successor(BSTVertex T) {
        if (T.right != null)
            return findMin(T.right);
        else {
            BSTVertex par = T.parent;
            BSTVertex cur = T;
            // if par(ent) is not root and cur(rent) is its right children
            while ((par != null) && (cur == par.right)) {
                cur = par;
                par = cur.parent;
            }
            if (par == null) {
                return "No successor";
            } else {
                return par.key;
            }
        }
    }

    protected String predecessor(BSTVertex T) {
        if (T.left != null)
            return findMax(T.left);
        else {
            BSTVertex par = T.parent;
            BSTVertex cur = T;
            // if par(ent) is not root and cur(rent) is its left children
            while ((par != null) && (cur == par.left)) {
                cur = par;
                par = cur.parent;
            }
            return par == null ? "no predecessor" : par.key;
        }
    }

    public String search(String v) {
        BSTVertex res = search(root, v);
        return res == null ? "No such element" : res.key;
    }

    public void insert(String v) {
        root = insert(root, v);
    }
    public String findMin() {
        return findMin(root);
    }

    public String findMax() {
        return findMax(root);
    }

    public String successor(String v) {
        BSTVertex vPos = search(root, v);
        return vPos == null ? "No successor" : successor(vPos);
    }

    public String predecessor(String v) {
        BSTVertex vPos = search(root, v);
        return vPos == null ? "No predecessor" : predecessor(vPos);
    }

    // will be used in AVL lecture
    protected int getHeight(BSTVertex T) {
        if (T == null) return -1;
        else return T.height;
    }

    // will be used in AVL lecture
    protected int getSize(BSTVertex T) {
        if (T == null) return 0;
        else return T.size;
    }

    public int getSize() {
        return getSize(root);
    }

    public int getHeight() {
        return getHeight(root);
    }
}


