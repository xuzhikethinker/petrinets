/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import main.visual.DrawPanel;
import main.Main;
import main.TableUpdator;
import main.visual.DrawPanel.MODE;

/**
 *
 * @author Aloren
 */
public class DeleteAction extends AbstractAction {

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
        } else if (Main.MTransitionButton.isSelected()) {
            Main.MTransitionButton.setSelected(false);
        }
        ((DrawPanel) Main.drawPanel).deleteMode();
        ((DrawPanel) Main.drawPanel).setMode(MODE.SELECT);
        TableUpdator.updateTables();
    }
}
