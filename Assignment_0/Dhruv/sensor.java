import java.util.*;
public class sensor {
    public boolean isOn(Double x) {
        return (Math.random() > x) ? true : false;
    }
}