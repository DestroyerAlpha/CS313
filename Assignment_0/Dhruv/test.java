import java.util.*;
import java.io.*;
public class test {
    static private int infiltrator(double prob, int width){
        int total_decisions = 0;
        for (int i = 0; i < 100; i++){
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
        return Math.round(total_decisions/100);
    }
    public static void main(String[] args) throws IOException {
        /* 
        PrintWriter p = new PrintWriter(new BufferedWriter(new FileWriter(new File("data1.csv"),false)));
        for(int width = 1; width <= 1000 ; width++) {
            p.println(width+","+"0.5,"+infiltrator(0.5, width));
            System.out.println("Data1 Progress : "+width/10.0);
        }
        p.close();
        p = new PrintWriter(new BufferedWriter(new FileWriter(new File("data2.csv"),false)));
        for(int prob = 1; prob <= 1000 ; prob++) {
            p.println("100,"+prob/1000.0+","+infiltrator(prob/1000.0, 100));
            System.out.println("Data2 Progress : "+prob/10.0);
        }
        p.close();
        */
        PrintWriter p = new PrintWriter(new BufferedWriter(new FileWriter(new File("data.csv"),false))); 
        for(int width = 10; width <= 1000 ; width+=10)
            for(double prob = 0.2; prob <= 0.9 ; prob+=0.05) {
                p.println(width+","+prob+","+infiltrator(prob, width));
                System.out.println(width+"\t"+prob);
            }
        p.close();
    }
}