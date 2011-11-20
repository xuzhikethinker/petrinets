package generators;

import java.util.Random;

/**
 *
 * @author Aloren
 */
public class HyperGenerator implements Generator{

    /**
     * Линейно-конгруэнтный генератор.
     */
    LKG lkg = new LKG(337047452,267752951,99667745,0.3);
    private double lambda;
    private double gg;
    protected static final long serialVersionUID = 323458909L;

    public HyperGenerator(double lambda, double gg){
        this.lambda = lambda;
        this.gg = gg;
    }

    public double next() {
        double fi = 0.5 + Math.sqrt(0.25 - (double)(1.0 / (2.0 * gg + 2.0)));
        double alpha1 = 2 * fi * lambda;
        double alpha2 = 2 * (1 - fi) * lambda;
        double alpha = 0;
            if (new Random().nextDouble() < fi) {
                alpha = alpha1;
            } else {
                alpha = alpha2;
            }
            return -Math.log(new Random().nextDouble())/alpha;
    }

    public void setVariation(double var) {
        this.gg = var;
    }

    public void setLambda(double lambda) {
        this.lambda = lambda;
    }

    public double getLambda() {
        return this.lambda;
    }

    public double getVariation() {
        return this.gg;
    }
    
    public String toString(){
        return "Hyper";
    }
}
