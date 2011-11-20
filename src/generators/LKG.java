package generators;

import java.io.Serializable;

/**
 *
 * @author Aloren
 */
public class LKG implements Serializable{

    /**
     * Начальное значение генератора.
     */
    private double W0 = 0.5;
    /**
     * Параметр генератора.
     */
    private int A = 157;
    /**
     * Параметр генератора.
     */
    private int C = 53;
    /**
     * Параметр генератора.
     */
    private int D = 113;
    private double oldseed = W0;

    public LKG(int A, int C, int D, double W0) {
        this.A = A;
        this.C = C;
        this.D = D;
        this.W0 = W0;
    }

    public double next() {
        double nextseed = ((A * oldseed + C) / D) % 1;
        this.oldseed = nextseed;
        return nextseed;
    }
}
