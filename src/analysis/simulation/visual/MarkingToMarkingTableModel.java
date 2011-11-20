package analysis.simulation.visual;

import analysis.simulation.MarkingMatrix;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Aloren
 */
public class MarkingToMarkingTableModel extends AbstractTableModel {

    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
    private final List<String> markingLabels;
    //вероятности переходов между маркировками
    public static double[][] probabilities;
    //частоты испытаний
    private int[] V;

    public MarkingToMarkingTableModel(List<String> markingLabels, MarkingMatrix markingMatrix, double[] times) {
        //for initial deltaT=1
        this.V = new int[times.length];
        for (int i = 0; i < times.length; i++) {
            V[i] = (int) times[i];
        }
        this.markingLabels = markingLabels;
        //for initial deltaT=1
        probabilities = new double[markingMatrix.size()][markingMatrix.size()];
        for (int i = 0; i < probabilities.length; i++) {
            for (int j = 0; j < probabilities[0].length; j++) {
                this.probabilities[i][j] = markingMatrix.get(i,j);
            }
        }
    }

    public int getRowCount() {
        return probabilities.length;
    }

    public int getColumnCount() {
        //one more column for test frequencies
        return probabilities.length + 1;
    }

    public String getColumnName(int columnIndex) {
        if (columnIndex >= markingLabels.size()) {
            return "Частота испытаний";
        } else {
            return markingLabels.get(columnIndex);
        }
    }

    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex >= markingLabels.size()) {
            return V[rowIndex];
        } else {
            return probabilities[rowIndex][columnIndex];
        }
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex >= markingLabels.size()) {
            V[rowIndex] = (Integer) aValue;
            fireTableCellUpdated(rowIndex, columnIndex);
        } else {
            probabilities[rowIndex][columnIndex] = (Double) aValue;
        }
    }

    public void addTableModelListener(TableModelListener listener) {
        listeners.add(listener);
    }

    public void removeTableModelListener(TableModelListener l) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
