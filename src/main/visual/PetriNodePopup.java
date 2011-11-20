package main.visual;

import javax.swing.JPopupMenu;
import model.PetriNode;

/**
 *
 * @author Aloren
 */
public abstract class PetriNodePopup extends JPopupMenu{

    public static PetriNodePopup getPopup(PetriNode pn, DrawPanel aThis) {
        return pn.getPopup();
    }    
}
