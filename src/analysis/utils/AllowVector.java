package analysis.utils;

import utils.Matrix;

/**
 * Class Data-vector vor storaging vector of allow.
 * @author Aloren
 */
public class AllowVector {

    private Integer[] vector;

    public AllowVector(Integer[] mas) {
        this.vector = mas;
    }

    /**
     * Create vector which consists of 0 except pos position, which equals 1.
     * @param pos
     */
    public AllowVector(int pos, int length) {
        vector = new Integer[length];
        vector[pos] = 1;
    }

    public int getDimension() {
        return vector.length;
    }

    public Integer[] getVector() {
        return vector;
    }

    /**
     * Multiplies this vector by input Matrix.
     * @param matrixToMult the matrix by which to multiply
     * @return the result vector
     */
    public AllowVector multiply(Matrix matrixToMult) {
        Integer[] vect = new Integer[matrixToMult.getColumnDimension()];
        for (int j = 0; j < vect.length; j++) {
            vect[j] = 0;
        }
        for (int i = 0; i < matrixToMult.getColumnDimension(); i++) {
            for (int j = 0; j < vector.length; j++) {
                vect[i] = vect[i] + vector[j] * matrixToMult.getMatrix()[j][i];
            }
        }
        return new AllowVector(vect);
    }

    /**
     * Adds to this vector the input vector. Returns result vector.
     * @param vectorToAdd the vector adding to current.
     * @return the result vector.
     */
    public AllowVector add(AllowVector vectorToAdd) {
        Integer[] rez = new Integer[vector.length];
        for (int i = 0; i < vector.length; i++) {
            if(vector[i]!=Integer.MAX_VALUE && vectorToAdd.getVector()[i]!=Integer.MAX_VALUE){
            rez[i] = vector[i] + vectorToAdd.getVector()[i];
            } else{
                rez[i] = Integer.MAX_VALUE;
            }
        }
        return new AllowVector(rez);
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < getDimension(); i++) {
            str = str + vector[i] + "  ";
        }
        return str;
    }
}
