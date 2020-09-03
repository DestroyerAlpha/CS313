import java.util.Scanner;
import java.util.*;

public class timeRequired {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // System.out.print("w = ");
        // int w = sc.nextInt();
        // System.out.print("p = ");
        // double p = sc.nextDouble();
        // System.out.print("No. of test cases = ");
        int f = 100;
        time tt = new time();
        //int avg = 0;
        for(int w=0;w<1000;w+=10)
        {
            border b1 = new border();
            b1.width = w;
            for(float p=0;p<0.99;p+=0.01)
            {
                for (int i = 0; i < f; i++)
                {
                    infiltrator i1 = new infiltrator();
                    tt.t = tt.t + (10 * (i1.path(p, b1.width)));
                    //avg = tt.t;
                }

        //avg /= f;
                tt.t /= f;
                String output = String.format("%d %f %d", w, p, tt.t);
                System.out.println(output);
        // System.out.println("Time taken for Inflitrator to cross border is " + tt.t + " secs");
            }
        }
    }
}