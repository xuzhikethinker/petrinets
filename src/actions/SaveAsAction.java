package actions;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import main.Main;
import main.visual.DrawPanel;
import utils.ExtendedFileChooser;
import utils.PNFileFilter;

/**
 *
 * @author Aloren
 */
public class SaveAsAction extends AbstractAction {

    public void actionPerformed(ActionEvent e) {
        saveAs();
    }

    public static void saveAs() throws HeadlessException {
        JFileChooser fc = new ExtendedFileChooser(((DrawPanel) Main.drawPanel).getGraph(), ".pn");
        fc.setDialogTitle("Save as");
        fc.setFileFilter(new PNFileFilter());
        if (fc.showSaveDialog(fc) == JFileChooser.APPROVE_OPTION) {
            System.out.println("Writing to file");
        }
    }
}
