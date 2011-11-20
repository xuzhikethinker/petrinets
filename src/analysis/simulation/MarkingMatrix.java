package analysis.simulation;

/**
 * Matrix for storing probabilities between markings.
 * @author Aloren
 */
public class MarkingMatrix {

    private Integer[][] data;

    public MarkingMatrix() {
        data = new Integer[2][2];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                data[i][j] = 0;
            }
        }
    }
    
    public void inc(int i, int j) {
        if (!RangeCheck(i, j)) {
            int increment = 1;
            if(i>j){
                increment = i-data.length+1;
            } else{
                increment = j-data.length+1;
            }
            ensureCapacity(data.length + increment);
        }
        data[i][j]++;
    }

    public Integer get(int i, int j) {
        if (RangeCheck(i, j)) {
            return data[i][j];
        } else {
            throw new IndexOutOfBoundsException(
                    "Index: " + i + ";" + j + ", Size: " + data.length);
        }
    }

    public void ensureCapacity(int minCapacity) {
        int oldCapacity = data.length;
        if (minCapacity > oldCapacity) {
            Integer oldData[][] = data;

            data = new Integer[minCapacity][minCapacity];
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[0].length; j++) {
                    if (i >= oldCapacity || j >= oldCapacity) {
                        data[i][j] = 0;
                    } else {
                        data[i][j] = oldData[i][j];
                    }
                }
            }
        }
    }

    private boolean RangeCheck(int i, int j) {
        if (i >= data.length || j >= data.length) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("\n");
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                out.append(data[i][j]).append(" ");
            }
            out.append("\n");
        }
        return out.toString();
    }
    
    public int size(){
        return data.length;
    }
    
    public Integer[][] toArray(){
        return data;
    }
}
