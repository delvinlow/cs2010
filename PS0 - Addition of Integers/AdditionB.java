import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Scanner;

public class AdditionB {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        BigInteger sum = BigInteger.valueOf(0);

        while (true) {
            BigInteger a = sc.nextBigInteger();
            BigInteger b = sc.nextBigInteger();

            if (a.compareTo(BigInteger.valueOf(-1)) == 0 && b.compareTo(BigInteger.valueOf(-1)) == 0) {
                return;
            } else {
                sum = a.add(b);
                System.out.println(sum);
            }
        }
    }
}

