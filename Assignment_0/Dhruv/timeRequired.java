import java.util.*;
public class timeRequired {
    public static void main(String[] args) {
       Scanner sc = new Scanner(System.in);
       System.out.print("w = ");
       int w = sc.nextInt();
       System.out.print("p = ");
       double p = sc.nextDouble();
       System.out.print("No. of test cases = ");
       int f = sc.nextInt();
       border.bwidth = w;
       time.t = 0;
       for (int i = 0; i < f; i++)
           time.t += (10 * (new infiltrator(p, border.bwidth).total_decisions));
       System.out.println("Time taken for Inflitrator to cross border is " + time.t/f + " secs");
    }
}