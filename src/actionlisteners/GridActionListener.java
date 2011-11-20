package actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import main.Main;
import main.visual.DrawPanel;

/**
 *
 * @author Aloren
 */
public class GridActionListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        JCheckBox box = (JCheckBox) e.getSource();
            if (box.isSelected()) {
                ((DrawPanel) (Main.drawPanel)).grid.setShowGrid(true);
            } else {
                ((DrawPanel) (Main.drawPanel)).grid.setShowGrid(false);
            }
        Main.drawPanel.repaint();
    }
}
