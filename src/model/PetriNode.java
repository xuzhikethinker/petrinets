package model;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.io.Serializable;
import main.visual.PetriNodePopup;

/**
 *
 * @author Aloren
 */
public abstract class PetriNode implements Comparable, Serializable {

    protected boolean selected = false;
    protected Integer number;

    public void setNumber(int i) {
        this.number = i;
    }

    public Integer getNumber() {
        return this.number;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public abstract String getName();

    public abstract int getX();

    public abstract int getY();

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract void setPoint(int x, int y);

    public abstract void paintComponent(Graphics2D g);

    public abstract void setPoint(Point2D.Double p);

    public abstract Point2D.Double getPoint();

    public abstract boolean contains(int x, int y);

    public abstract PetriNodePopup getPopup();
}
