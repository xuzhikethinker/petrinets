package analysis.simulation;

/**
 *
 * @author Aloren
 */
public class NoCyclesException extends Exception {

    public NoCyclesException(String s) {
        super(s);
    }
    
        public NoCyclesException() {
	super("No cycles are set.");
    }
}
