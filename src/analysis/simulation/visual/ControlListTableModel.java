package analysis.simulation.visual;

import analysis.simulation.ControlListRecord;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Aloren
 */
public class ControlListTableModel implements TableModel {

    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
    private List<ControlListRecord> controlList;

    public ControlListTableModel(List<ControlListRecord> controlList) {
        this.controlList = controlList;
    }

    public void addTableModelListener(TableModelListener listener) {
        listeners.add(listener);
    }

    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    public int getColumnCount() {
        return 6;
    }

    public String getColumnName(final int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "№";
            case 1:
                return "Время события";
            case 2:
                return "Описание";
            case 3:
                return "Переход";
            case 4:
                return "Начальная маркировка";
            case 5:
                return "Завершающая маркировка";
        }
        return "";
    }

    public int getRowCount() {
        return controlList.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        ControlListRecord record = (ControlListRecord) controlList.toArray()[rowIndex];
        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return record.getTime();
            case 2:
                return record.getRelation();
            case 3:
                return record.getTransition();
            case 4:
                return record.getStartMarking();
            case 5:
                return record.getEndMarking();
        }
        return "";
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void removeTableModelListener(TableModelListener listener) {
        listeners.remove(listener);
    }

    public void setValueAt(Object value, int rowIndex, int columnIndex) {
    }
}
