package generators;

import java.util.Random;

/**
 *
 * @author Aloren
 */
public class UniformGenerator implements Generator {

    /**
     * Линейно-конгруэнтный генератор.
     */
    LKG lkg = new LKG(51839, 1627, 51769, 0.5);
    double lambda;
    protected static final long serialVersionUID = 64453545L;

    public UniformGenerator(double lambda) {
        this.lambda = lambda;
    }

    public double next() {
        return (new Random().nextDouble()) * 2 / lambda;
    }

    public void setVariation(double var) {
    }

    public void setLambda(double lambda) {
        this.lambda = lambda;
    }

    public double getLambda() {
        return this.lambda;
    }

    public double getVariation() {
        return 0.33;
    }
    
    public String toString(){
        return "Uniform";
    }
}
