package actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import main.visual.DrawPanel.MODE;
import main.Main;
import main.visual.DrawPanel;

/**
 *
 * @author Aloren
 */
public class ArcAction extends AbstractAction{

    public void actionPerformed(ActionEvent e) {
        if (Main.VTransitionButton.isSelected()) {
            Main.VTransitionButton.setSelected(false);
        } else if (Main.PlaceButton.isSelected()) {
            Main.PlaceButton.setSelected(false);
        } else if (Main.MTransitionButton.isSelected()) {
            Main.MTransitionButton.setSelected(false);
        }
        if (Main.ArcButton.isSelected()) {
           ((DrawPanel) Main.drawPanel).setMode(MODE.ARC);
        } else {
            ((DrawPanel) Main.drawPanel).setMode(MODE.SELECT);
        }
    }
    
}
