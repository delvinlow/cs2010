import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Addition {
    public static void main(String[] args) {
        IntegerScanner sc = new IntegerScanner(System.in);
        int a, b;
        while (true) {
            a = sc.nextInt();
            b = sc.nextInt();
            if ( a== -1 && b == -1){
                return;
            } else {
                System.out.println(a+b);
            }
        }
    }
}

class IntegerScanner { // coded by Ian Leow, we will use this quite often in CS2010 PSes
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
        } catch (IOException ioe) {
            return -1;
        }
    }
}

