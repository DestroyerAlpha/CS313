import java.util.*;
import java.io.*;
public class Batch {
    static private int time(double prob, int width){
        int total_decisions = 0;
        for (int i = 0; i < 1000; i++){
            int moved_ctr = 0 , grid[][] = new int[2][3];
            while (moved_ctr < width) {
                total_decisions++;
                for (int j = 0; j < 2; j++)
                    for(int h=0;h<2;h++)
                        grid[j][h] = (Math.random() > prob)?1:0;
                if ((grid[0][0] == 0 || grid[0][1] == 0 || grid[0][2] == 0) && (grid[1][1] == 0 || moved_ctr==0))
                    moved_ctr++;
            }
        }
        return total_decisions/100;
    }
    public static void main(String[] args) throws IOException {
        PrintWriter p = new PrintWriter(new BufferedWriter(new FileWriter(new File("data.csv"),false)));
        /*for(int width = 0; width <= 1000 ; width++)
            p.println(width+","+"0.5,"+time(0.5, width));
        p = new PrintWriter(new BufferedWriter(new FileWriter(new File("data2.csv"),false)));
        for(int prob = 1; prob <= 1000 ; prob++)
            p.println("100,"+prob/1000+","+time(prob/1000.0, 100));
        p = new PrintWriter(new BufferedWriter(new FileWriter(new File("data3.csv"),false))); */      
        for(int width = 1; width <= 1000 ; width++)
            for(int prob = 1; prob <= 1000 ; prob++)
                p.println(width+","+prob/1000+","+time(prob/1000.0, width));
    }
}