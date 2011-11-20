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
public class PlaceAction extends AbstractAction {

    public void actionPerformed(ActionEvent e) {
        if (Main.VTransitionButton.isSelected()) {
            Main.VTransitionButton.setSelected(false);
        } else if (Main.ArcButton.isSelected()) {
            Main.ArcButton.setSelected(false);
            ((DrawPanel) Main.drawPanel).getGraph().setSelectedNode(null);
            ((DrawPanel) Main.drawPanel).removeActiveLink();
            ((DrawPanel) Main.drawPanel).repaint();
        } else if (Main.MTransitionButton.isSelected()) {
            Main.MTransitionButton.setSelected(false);
        }
        if (Main.PlaceButton.isSelected()) {
            ((DrawPanel) Main.drawPanel).setMode(MODE.POSITION);
            // drawPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        } else {
            ((DrawPanel) Main.drawPanel).setMode(MODE.SELECT);
            //  drawPanel.setCursor(Cursor.getDefaultCursor());
        }
    }
}
