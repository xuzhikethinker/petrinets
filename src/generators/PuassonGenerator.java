package generators;

import java.util.Random;

/**
 *
 * @author Aloren
 */
public class PuassonGenerator implements Generator {

    /**
     * Линейно-конгруэнтный генератор, генерирующий числа [0;1].
     */
    LKG lkg = new LKG(939, 47, 709, 0.9);
    double lambda;
    protected static final long serialVersionUID = 98743284778L;

    public PuassonGenerator(double lambda) {
        this.lambda = lambda;
    }

    /**
     * Интенсивность потока.
     */
    public double next() {
        return -Math.log(new Random().nextDouble()) / lambda;
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
        return 1;
    }
    
    public String toString(){
        return "Puasson";
    }
}
