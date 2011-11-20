package analysis.simulation.visual;

import analysis.simulation.MarkingMatrix;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import utils.JTextFieldFilter;

/**
 *
 * @author Aloren
 */
public class MarkingToMarkingPanel extends JPanel {

    JTextField deltaField = new JTextField(20);
    JLabel status = new JLabel("");
    JLabel freqCount = new JLabel("Vj=T/Δt");
    JTable table;
    private final double[] stayTimes;
    private final MarkingMatrix markingMatrix;;

    public MarkingToMarkingPanel(JScrollPane scrollPane, JTable table, double[] times, MarkingMatrix markingMatrix) {
        super(new BorderLayout());
        this.stayTimes = times;
        this.markingMatrix = markingMatrix;
        this.table = table;
        setVisible(true);
        JPanel controls = new JPanel();
        JPanel statusPanel = new JPanel();
        statusPanel.add(status);
        JLabel deltaLabel = new JLabel("Δt");
        JLabel freq = new JLabel("Частота испытаний");
        deltaField.setDocument(new JTextFieldFilter("0123456789."));
        deltaField.setText("1.0"); //Insert here initial deltaT
        deltaField.addActionListener(new DeltaFieldActionListener());
        //  freqCount.setText(Double.toString(Tmod / Double.valueOf(deltaField.getText())));
        controls.add(deltaLabel);
        controls.add(deltaField);
        controls.add(freq);
        controls.add(freqCount);
        add(controls, BorderLayout.NORTH);
        add(statusPanel, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private class DeltaFieldActionListener implements ActionListener {

        public DeltaFieldActionListener() {
        }

        public void actionPerformed(ActionEvent e) {
            JTextComponent tc = (JTextComponent) e.getSource();
            if (tc.getText().equals("")) {
                status.setText("Введите коэффициент");
            } else {
                //enter button has been hit
                if (e.ACTION_PERFORMED == 1001) {
                    try {
                        status.setText("");
                        int VColumnIndex = table.getColumnCount() - 1;
                        for (int i = 0; i < table.getRowCount(); i++) {
                            int V = (int) (stayTimes[i] / Double.valueOf(deltaField.getText()));
                            table.setValueAt(V, i, VColumnIndex);
                            //update probabilities as they are P=Freq/V
                            for (int j = 0; j < table.getColumnCount()-1; j++) {
                                Integer freq = markingMatrix.get(i,j);
                                table.setValueAt((Double)(freq/(double)V), i, j);
                            }
                            table.setValueAt(probInside(i), i, i);
                        }
                        table.repaint();
                        //  freqCount.setText(Double.toString(Tmod / Double.valueOf(deltaField.getText())));
                    } catch (NumberFormatException ex1) {
                        status.setText("Ошибка в формате значений");
                        System.out.println(ex1);
                    } catch (java.lang.OutOfMemoryError ee) {
                        status.setText("Слишком большое введённое значение");
                    }
                }
            }
        }

        private Double probInside(int rowIndex) {
            double sum = 0;
            for (int i = 0; i < table.getColumnCount()-1; i++) {
                if(i!=rowIndex){
                    sum+=(Double)table.getValueAt(rowIndex, i);
                }                
            }
            return 1-sum;
        }
    }
}
