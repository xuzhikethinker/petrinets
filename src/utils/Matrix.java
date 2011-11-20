package utils;

/**
 * Class Data-matrix vor storaging matrices.
 * @author Aloren
 */
public class Matrix {

    private Integer[][] data;

    public Matrix(Integer[][] data) {
        this.data = data;
    }

    /**
     * Returns the number of rows in this matrix.
     * @return the number of rows in this matrix.
     */
    public int getRowDimension() {
        return data.length;
    }

    /**
     * Returns the number of columns in this matrix.
     * @return the number of columns in this matrix.
     */
    public int getColumnDimension() {
        if (data.length > 0) {
            return data[0].length;
        } else {
            return 0;
        }
    }

    /**
     * Returns the Integer[][] array contained in this matrix.
     * @return the Integer[][] array contained in this matrix
     */
    public Integer[][] getMatrix() {
        return data;
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < getRowDimension(); i++) {
            for (int j = 0; j < getColumnDimension(); j++) {
                str = str + data[i][j] + "  ";
            }
            str = str + "\n";
        }
        return str;
    }
}
