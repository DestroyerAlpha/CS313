import java.util.*;

public class sensor {
    public boolean isOn(Double x) {
        //Random random = new Random();
        double rn = Math.random();
        // double rn = rand.nextDouble(1);
        //rn /= 100;
        if (rn <= x) {
            //System.out.println(rn);
            return true;
        } else {
            return false;
        }
    }
}
