/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis.simulation.visual;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import utils.Utils;

/**
 *
 * @author User
 */
public class SaveImageAction extends AbstractAction {

    VisualizationViewer vv;

    public SaveImageAction(VisualizationViewer vv) {
        this.vv = vv;
    }

    public void actionPerformed(ActionEvent e) {
        JFileChooser fc = new JFileChooser() {

            @Override
            public void approveSelection() {
                File file = getSelectedFile();
                if (file != null && file.exists()) {
                    int choice = JOptionPane.showConfirmDialog(this,
                            "Overwrite existing file?", "Confirm Overwrite",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if (choice == JOptionPane.CANCEL_OPTION) {
                        // User doesn't want to overwrite the file
                        return;
                    } else {
                        super.approveSelection();
                        try {
                            Utils.writeJPEGImage(vv, file);
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, "This file can not be opened.",
                                    "IOError", JOptionPane.ERROR_MESSAGE);
                        } catch (Exception ee) {
                            JOptionPane.showMessageDialog(null, "Something is broken.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                } else {
                    super.approveSelection();
                    try {
                        boolean contains = file.getPath().endsWith(".png");
                        if (contains) {
                            file = new File(getSelectedFile().getCanonicalPath());
                        } else {
                            file = new File(getSelectedFile().getCanonicalPath().concat(".png"));
                        }
                        Utils.writeJPEGImage(vv, file);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "This file can not be opened.",
                                "IOError", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ee) {
                        JOptionPane.showMessageDialog(null, "Something is broken.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        };
        fc.setDialogTitle("Save as");
        if (fc.showSaveDialog(fc) == JFileChooser.APPROVE_OPTION) {
            System.out.println("Writing to file");
        }
    }
}
