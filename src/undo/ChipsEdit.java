package undo;

import javax.swing.undo.AbstractUndoableEdit;
import model.Place;

/**
 *
 * @author Aloren
 */
public class ChipsEdit extends AbstractUndoableEdit {

    protected Place p;
    protected int oldChips;
    protected int newChips;

    public ChipsEdit(Place p, int oldChips, int newChips) {
        this.p = p;
        this.oldChips = oldChips;
        this.newChips = newChips;
    }

    @Override
    public String getPresentationName() {
        return "ChipsEdit";
    }

    public void undo(){
        super.undo();
        p.setChips(oldChips);
    }

    public void redo(){
        super.redo();
        p.setChips(newChips);
    }
}
