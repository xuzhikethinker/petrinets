package analysis.simulation;

import java.util.Comparator;
import model.Transition;

/**
 *
 * @author Aloren
 */
public class ProbabilityComparator implements Comparator{

    public int compare(Object o1, Object o2) {
        double t1 = ((Transition)o1).getProbability();
        double t2 = ((Transition)o2).getProbability();
        if(t1>t2){
            return 1;
        } else if(t1<t2){
            return -1;
        } else{
            return 0;
        }
    }

}
