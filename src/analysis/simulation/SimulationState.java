package analysis.simulation;

/**
 *
 * @author Aloren
 */
public class SimulationState implements Comparable{

    private String marking;
    private Double prevTime;
    private Double timeReturn;
    private Double timeStay;
    private Double probability;
    private int freq;

    public SimulationState(String marking, Double prevTime, Double timeStay, Double probability, int freq){
        this.marking = marking;
        this.prevTime = prevTime;
        this.timeReturn = 0.0;
        this.probability = probability;
        this.timeStay = timeStay;
        this.freq = freq;
    }

    @Override
    public String toString(){
        return marking+" stay="+timeStay+" return="+timeReturn+" P="+probability+" Freq="+freq;
    }

    public String getName(){
        return marking;
    }

    /**
     * @return the marking
     */
    public String getMarking() {
        return marking;
    }

    /**
     * @param marking the marking to set
     */
    public void setMarking(String marking) {
        this.marking = marking;
    }

    /**
     * @return the prevTime
     */
    public Double getPrevTime() {
        return prevTime;
    }

    /**
     * @param prevTime the prevTime to set
     */
    public void setPrevTime(Double prevTime) {
        this.prevTime = prevTime;
    }

    /**
     * @return the time
     */
    public Double getTimeStay() {
        return timeStay;
    }
    
        public Double getTimeStayForChart() {
        return timeStay/freq;
    }

    /**
     * @param time the time to set
     */
    public void setTimeStay(Double time) {
        this.timeStay = time;
    }

    /**
     * @return the probability
     */
    public Double getProbability() {
        return probability;
    }

    /**
     * @param probability the probability to set
     */
    public void setProbability(Double probability) {
        this.probability = probability;
    }

    public int compareTo(Object o) {
        return ((SimulationState)o).getMarking().compareTo(this.getMarking());
    }

    /**
     * @return the timeReturn
     */
    public Double getTimeReturn() {
        return timeReturn;
    }
    
        public Double getTimeReturnForChart() {
        return timeReturn/freq;
    }

    /**
     * @param timeReturn the timeReturn to set
     */
    public void setTimeReturn(Double timeReturn) {
        this.timeReturn = timeReturn;
    }

    /**
     * @return the freq
     */
    public int getFreq() {
        return freq;
    }

    /**
     * @param freq the freq to set
     */
    public void setFreq(int freq) {
        this.freq = freq;
    }
}
