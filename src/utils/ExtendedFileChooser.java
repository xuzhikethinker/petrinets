package utils;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Aloren
 */
public class ExtendedFileChooser extends JFileChooser {

    private Saveable toSave;
    private String format;

    public ExtendedFileChooser(Saveable toSave, String format) {
        this.toSave = toSave;
        this.format = format;
    }

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
                    toSave.writeToFile(file);
                } catch (Exception ee) {
                    JOptionPane.showMessageDialog(null, "Something is broken.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    ee.printStackTrace();
                }
            }

        } else {
            super.approveSelection();
            try {
                boolean contains = file.getPath().endsWith(format);
                if (contains) {
                    file = new File(getSelectedFile().getCanonicalPath());
                } else {
                    file = new File(getSelectedFile().getCanonicalPath().concat(format));
                }
                toSave.writeToFile(file);
            } catch (Exception ee) {
                JOptionPane.showMessageDialog(null, "Something is broken.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                ee.printStackTrace();
            }
        }
    }
}
