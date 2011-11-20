package analysis.simulation;

import java.util.Comparator;

/**
 *
 * @author Aloren
 */
public class TimedTransitionComparator implements Comparator {

    public int compare(Object o1, Object o2) {
        double t1 = (Double) o1;
        double t2 = (Double) o2;
        if (t1 > t2) {
            return 1;
        } else if (t1 < t2) {
            return -1;
        } else {
            return 0;
        }
    }
}
