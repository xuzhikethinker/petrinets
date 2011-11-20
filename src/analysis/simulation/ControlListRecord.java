package analysis.simulation;

/**
 *
 * @author Aloren
 */
public class ControlListRecord implements Comparable {

    /**
     * @return the transition
     */
    public String getTransition() {
        return transition;
    }

    /**
     * @param transition the transition to set
     */
    public void setTransition(String transition) {
        this.transition = transition;
    }

    /**
     * @return the startMarking
     */
    public String getStartMarking() {
        return startMarking;
    }

    public static enum TYPE {

        FIRE, LAUNCH
    }
    private String transition;
    private double time;
    private TYPE type;
    private String relation;
    private String startMarking;
    private String endMarking;

    public ControlListRecord(String transition, double startTime, TYPE type, String relation, String startMarking, String endMarking){
        this.transition = transition;
        this.time = startTime;
        this.type = type;
        this.relation = relation;
        this.startMarking = startMarking;
        this.endMarking = endMarking;
    }

        public int compareTo(Object o) {
        ControlListRecord clr = (ControlListRecord)o;
                if (time > clr.getTime()) {
            return 1;
        } else if (time < clr.getTime()) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * @return the time
     */
    public double getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(double time) {
        this.time = time;
    }

    /**
     * @return the type
     */
    public TYPE getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(TYPE type) {
        this.type = type;
    }

    /**
     * @return the relation
     */
    public String getRelation() {
        return relation;
    }

    /**
     * @param relation the relation to set
     */
    public void setRelation(String relation) {
        this.relation = relation;
    }

    /**
     * @return the endMarking
     */
    public String getEndMarking() {
        return endMarking;
    }

    /**
     * @param endMarking the endMarking to set
     */
    public void setEndMarking(String endMarking) {
        this.endMarking = endMarking;
    }
}
