package actions;

import analysis.tree.visual.MarkovGraphPanel;
import constants.CONSTANTS;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import main.Main;
import main.visual.DrawPanel;
import utils.DIMENSIONS;

/**
 *
 * @author Aloren
 */
public class BuildMarkovChain extends AbstractAction {

    public void actionPerformed(ActionEvent e) {
        try {
            JFrame MFrame = new JFrame(CONSTANTS.MARKOV_GRAPH_TITLE);
            MFrame.add(new MarkovGraphPanel("Real",
                    ((DrawPanel) Main.drawPanel).getGraph().getChips(),
                    ((DrawPanel) Main.drawPanel).getGraph(), null, null, true));
            DIMENSIONS.fullScreen(MFrame);
            MFrame.setVisible(true);
        } catch (ArrayIndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(null, "Petri nets is empty. Input more data.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}
