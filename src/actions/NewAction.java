package actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import main.Main;
import main.visual.DrawPanel;

/**
 *
 * @author Aloren
 */
public class NewAction extends AbstractAction {

    public void actionPerformed(ActionEvent e) {
        int choice = JOptionPane.showConfirmDialog(((DrawPanel) Main.drawPanel),
                "Do you want to save changes to the current graph?", "Petri nets",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null);
        if (choice == 0) {
            SaveAsAction.saveAs();
        } else if (choice == 1) {
            ((DrawPanel) Main.drawPanel).prepareForNewGraph();
        }
    }
}
