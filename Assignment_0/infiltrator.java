public class infiltrator {
    public int path(double y, int d) {
        int grid[][] = new int[2][3];
        int i = 0, k = 0;
        while (i < d) {
            k++;
            for (int j = 0; j < 2; j++) {
                for(int h=0;h<2;h++){
                    sensor s1 = new sensor();
                    if (s1.isOn(y)) {
                        grid[j][h] = 1;
                    } else {
                        grid[j][h] = 0;
                    }
                }
            }

            if(grid[0][0] == 0 || grid[0][1] == 0 || grid[0][2] == 0){
                if (grid[1][1] == 0 || i==0) {
                    i++;
                }
            }
        }
        return k;
    }
    
}
