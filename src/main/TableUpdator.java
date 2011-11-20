package main;

import javax.swing.table.DefaultTableModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import main.visual.DrawPanel;
import utils.RowHeaderRenderer;

public class TableUpdator {

    public static void createNewTables() {
        //System.out.println("Creating new tables");
        Main.jtI.removeAll();
        Main.jtO.removeAll();
        Main.jtO.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{}, new String[]{}));
        Main.jtI.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{}, new String[]{}));

        JScrollPane scrollI = (JScrollPane) Main.jtI.getParent().getParent();
        JScrollPane scrollO = (JScrollPane) Main.jtO.getParent().getParent();
        scrollI.setRowHeaderView(null);
        scrollO.setRowHeaderView(null);

        Main.jtI.repaint();
        Main.jtO.repaint();
    }

    public static void updateTables() {
        //System.out.println("Updating tables");
        if (!((DrawPanel) Main.drawPanel).getGraph().getTransitions().isEmpty()
                && !((DrawPanel) Main.drawPanel).getGraph().getPlaces().isEmpty()) {
            Integer[][] inMatrix = ((DrawPanel) Main.drawPanel).getGraph().buildIMatrix();
            Integer[][] outMatrix = ((DrawPanel) Main.drawPanel).getGraph().buildQMatrix();

            Object[] columnHeaders = createColumnHeaders(inMatrix);
            Object[] rowHeaders = createRowHeaders(outMatrix);
            Main.jtI.setModel(new DefaultTableModel(inMatrix, columnHeaders));
            Main.jtO.setModel(new DefaultTableModel(outMatrix, columnHeaders));

            JList rowHeadersI = new JList(rowHeaders);
            JList rowHeadersO = new JList(rowHeaders);

            //rowHeadersI.setFixedCellWidth(40);
            rowHeadersI.setFixedCellHeight(16);
            rowHeadersI.setCellRenderer(new RowHeaderRenderer(Main.jtI));
            rowHeadersI.setBackground(Main.jtI.getParent().getBackground());

            //rowHeadersO.setFixedCellWidth(40);
            rowHeadersO.setFixedCellHeight(16);
            rowHeadersO.setCellRenderer(new RowHeaderRenderer(Main.jtO));
            rowHeadersO.setBackground(Main.jtO.getParent().getBackground());

            JScrollPane scrollI = (JScrollPane) Main.jtI.getParent().getParent();
            JScrollPane scrollO = (JScrollPane) Main.jtO.getParent().getParent();
            scrollI.setRowHeaderView(rowHeadersI);
            scrollO.setRowHeaderView(rowHeadersO);

            Main.jtI.repaint();
            Main.jtO.repaint();
        }
    }

    private static Object[] createColumnHeaders(Object[][] data) {
        Object[] columnHeaders = new Object[data[0].length];
        for (int i = 0; i < columnHeaders.length; i++) {
            columnHeaders[i] = "P" + i;
        }
        return columnHeaders;
    }

    private static Object[] createRowHeaders(Object[][] data) {
        Object[] columnHeaders = new Object[data.length];
        for (int i = 0; i < columnHeaders.length; i++) {
            columnHeaders[i] = "T" + i;
        }
        return columnHeaders;
    }
}
