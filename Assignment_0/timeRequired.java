import java.util.Scanner;
import java.util.*;

class Time {
    int t = 0;
}

class Border {
    int length = 1000;
    int width;
}

class Sensor {

    public static boolean isOn(Double x) {
        Random random = new Random();
        double rn = Math.random();
        // double rn = rand.nextDouble(1);
        rn /= 100;
        if (rn <= x) {
            return true;
        } else {
            return false;
        }
    }

}

class Infiltrator {
    public static int path(double y, int d) {
        int grid[] = new int[3];
        int i = 0, k = 0;
        while (i < d) {
            k++;
            for (int j = 0; j < 3; j++) {
                if (Sensor.isOn(y)) {
                    grid[j] = 1;
                } else {
                    grid[j] = 0;
                }
            }
            if (grid[0] == 1 && grid[1] == 1 && grid[2] == 1) {
                i++;
            }

        }
        return k;
    }
}

public class timeRequired {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("w = ");
        int w = sc.nextInt();
        System.out.print("p = ");
        double p = sc.nextDouble();
        System.out.print("No. of test cases = ");
        int f = sc.nextInt();

        Border b1 = new Border();
        b1.width = w;

        Time tt = new Time();
        int avg = 0;
        for (int i = 0; i < f; i++) {
            tt.t = tt.t + (10 * (Infiltrator.path(p, b1.width)));
            avg += tt.t;
        }

        avg /= f;
        System.out.println("Time taken for Inflitrator to cross bborder is " + avg + " secs");

    }
}
