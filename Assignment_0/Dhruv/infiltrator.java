public class infiltrator {
    int width = 0, moved_ctr = 0, total_decisions = 0;
    double prob = 0.0;
    public infiltrator(double p, int w) {
        width = w;
        prob = p;
        int grid[][] = new int[2][3];
        while (moved_ctr < width) {
            total_decisions++;
            for (int j = 0; j < 2; j++) {
                for(int h=0;h<2;h++){
                    sensor s1 = new sensor();
                    if (s1.isOn(prob)) {
                        grid[j][h] = 1;
                    } else {
                        grid[j][h] = 0;
                    }
                }
            }
            if(grid[0][0] == 0 || grid[0][1] == 0 || grid[0][2] == 0){
                if (grid[1][1] == 0 || moved_ctr==0) {
                    moved_ctr++;
                }
            }
        }
    }
}
