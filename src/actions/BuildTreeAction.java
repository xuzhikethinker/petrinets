package actions;

import analysis.tree.Tree;
import analysis.tree.visual.TreeFrame;
import constants.CONSTANTS;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import main.Main;
import main.visual.DrawPanel;
import model.Graph;
import utils.DIMENSIONS;

/**
 *
 * @author Aloren
 */
public class BuildTreeAction extends AbstractAction {

    public void actionPerformed(ActionEvent e) {
        try {
            Graph mainGraph = ((DrawPanel) Main.drawPanel).getGraph();
            TreeFrame mTree = new TreeFrame(Tree.buildTree("I", 
                    mainGraph.getChips(), mainGraph), mainGraph);
            mTree.setTitle(CONSTANTS.TREE_TITLE);
            mTree.setVisible(true);
            DIMENSIONS.fullScreen(mTree);
        } catch (ArrayIndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(null, "Petri nets is empty. Input more data.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}
