package analysis.simulation;

/**
 *
 * @author Aloren
 */
public class AllInstantTransitionException extends Exception {

    public AllInstantTransitionException(String s) {
        super(s);
    }
    
        public AllInstantTransitionException() {
	super("All transitions are instant. Simulation mode is not possible.");
    }
}
