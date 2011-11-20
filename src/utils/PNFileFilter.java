package utils;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * PN-File filter.
 *
 * @author Aloren
 */
public class PNFileFilter extends FileFilter {

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = Utils.getExtension(f);
        if (extension != null) {
            if (extension.equals(Utils.pn)) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    @Override
    public String getDescription() {
        return "PetriNets(*.pn)";
    }
}

