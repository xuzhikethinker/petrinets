package actions;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import main.Main;
import main.visual.DrawPanel;
import model.Graph;
import utils.PNFileFilter;

/**
 *
 * @author Aloren
 */
public class OpenAction extends AbstractAction {

    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.removeChoosableFileFilter(fileChooser.getChoosableFileFilters()[0]);
        fileChooser.setLocation(((DrawPanel) Main.drawPanel).getParent().getLocationOnScreen());
        fileChooser.addChoosableFileFilter(new PNFileFilter());
        int returnVal = fileChooser.showOpenDialog(((DrawPanel) Main.drawPanel));
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                File serialFile = fileChooser.getSelectedFile();
                Graph gr = (Graph) Graph.deserialize(serialFile);
                ((DrawPanel) Main.drawPanel).setGraph(gr);
                ((DrawPanel) Main.drawPanel).prepareForOpenedGraph(gr);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error occured while opening the file."
                        + "Contact program developer to fix the bug.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
