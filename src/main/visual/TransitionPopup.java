package main.visual;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import model.Transition;

/**
 *
 * @author Aloren
 */
public class TransitionPopup extends PetriNodePopup implements ActionListener {

    private Transition t;

    public TransitionPopup(Transition t) {
        this.t = t;
        JMenuItem menuItem1 = new JRadioButtonMenuItem("Real");
        menuItem1.addActionListener(this);
        add(menuItem1);
        JMenuItem menuItem2 = new JRadioButtonMenuItem("Instant");
        menuItem2.addActionListener(this);
        add(menuItem2);
//        JMenuItem menuItem4 = new JRadioButtonMenuItem("Show attributes");
//        add(menuItem4);
        JMenuItem menuItem3 = new JMenuItem("Properties");
        menuItem3.addActionListener(this);
        add(menuItem3);
        if (t.isReal()) {
            menuItem1.setSelected(true);
        } else {
            menuItem2.setSelected(true);
        }
    }

    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem) (e.getSource());
        if (source.getText().equals("Real")) {
            if (!t.isReal()) {
                t.setReal(true);
                t.paintComponent((Graphics2D) this.getInvoker().getGraphics());
            }
        } else if (source.getText().equals("Instant")) {
            if (t.isReal()) {
                t.setReal(false);
                t.paintComponent((Graphics2D) this.getInvoker().getGraphics());
            }
        } else if (source.getText().equals("Properties")) {
            TransitionProperties tp = new TransitionProperties(t,(DrawPanel)this.getInvoker());
            tp.setLocationRelativeTo(null);
            tp.setVisible(true);
        } 
// else if (source.getText().equals("Show attributes")) {
//            System.out.println("Not ready yet");
//        }
    }
}
