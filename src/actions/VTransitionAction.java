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
public class VTransitionAction extends AbstractAction {

    public void actionPerformed(ActionEvent e) {
        if (Main.PlaceButton.isSelected()) {
            Main.PlaceButton.setSelected(false);
        } else if (Main.ArcButton.isSelected()) {
            Main.ArcButton.setSelected(false);
            ((DrawPanel) Main.drawPanel).getGraph().setSelectedNode(null);
            ((DrawPanel) Main.drawPanel).removeActiveLink();
            ((DrawPanel) Main.drawPanel).repaint();
        } else if (Main.MTransitionButton.isSelected()) {
            Main.MTransitionButton.setSelected(false);
        }
        if (Main.VTransitionButton.isSelected()) {
            ((DrawPanel) Main.drawPanel).setMode(MODE.VTRANSITION);
        } else {
            ((DrawPanel) Main.drawPanel).setMode(MODE.SELECT);
        }
    }
}
