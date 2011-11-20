package actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import main.Main;
import main.visual.DrawPanel;
import main.visual.DrawPanel.MODE;

/**
 *
 * @author Aloren
 */
public class MTransitionAction extends AbstractAction {

    public void actionPerformed(ActionEvent e) {
        if (Main.VTransitionButton.isSelected()) {
            Main.VTransitionButton.setSelected(false);
        } else if (Main.PlaceButton.isSelected()) {
            Main.PlaceButton.setSelected(false);
        } else if (Main.ArcButton.isSelected()) {
            Main.ArcButton.setSelected(false);
            ((DrawPanel) Main.drawPanel).getGraph().setSelectedNode(null);
            ((DrawPanel) Main.drawPanel).removeActiveLink();
            ((DrawPanel) Main.drawPanel).repaint();
        }
        if (Main.MTransitionButton.isSelected()) {
            ((DrawPanel) Main.drawPanel).setMode(MODE.MTRANSITION);
        } else {
            ((DrawPanel) Main.drawPanel).setMode(MODE.SELECT);
        }
    }
}
