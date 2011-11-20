package generators;

import java.io.Serializable;

/**
 *
 * @author Aloren
 */
public interface Generator extends Serializable{

    public double next();

    public void setVariation(double var);
    public void setLambda(double lambda);

    public double getLambda();

    public double getVariation();

}
