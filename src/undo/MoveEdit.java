package undo;

import java.awt.geom.Point2D;
import javax.swing.undo.AbstractUndoableEdit;
import model.PetriNode;

/**
 *
 * @author Aloren
 */
public class MoveEdit extends AbstractUndoableEdit {

    protected PetriNode p;
    protected Point2D.Double oldP;
    protected Point2D.Double newP;


    public MoveEdit(PetriNode p, Point2D.Double oldP, Point2D.Double newP) {
        this.p = p;
        this.oldP = oldP;
        this.newP = newP;
    }

    @Override
    public String getPresentationName() {
        return "MoveEdit";
    }

    @Override
    public void undo() {
        super.undo();
        p.setPoint(oldP);
    }

    public void redo() {
        super.redo();
        p.setPoint(newP);

    }
}
