package model;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import main.visual.LinkPopup;
import main.visual.PetriNodePopup;

/**
 *
 * @author Aloren
 */
public class Link extends PetriNode implements java.io.Serializable {

    private static final long serialVersionUID = 3;
    private PetriNode pn1;
    private PetriNode pn2;
    Arrow arrow;

    public Link(PetriNode p1, PetriNode p2) {
        this.pn1 = p1;
        this.pn2 = p2;
        arrow = new Arrow(pn1, pn2);
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        arrow.setSelected(selected);
    }

    @Override
    public String toString() {
        return pn1.getNumber().toString().concat(pn2.getNumber().toString());
    }

    /**
     * Draws the whole link.
     * @param g2
     */
    public void paintComponent(Graphics2D g2) {
        arrow.paintComponent(g2);
    }

    public PetriNode getPN1() {
        return pn1;
    }

    public void setPN1(PetriNode p1) {
        this.pn1 = p1;
        arrow.pn1 = pn1;
    }

    public PetriNode getPN2() {
        return pn2;
    }

    public void setPN2(PetriNode p2) {
        this.pn2 = p2;
        arrow.pn2 = pn2;
    }

    /**
     * Sets x-coordinate for the bend-point.
     * @param x
     */
    public void setX(int x) {
        arrow.uPoint.x = x;
    }

    /**
     * Sets y-coordinate for the bend point.
     * @param y
     */
    public void setY(int y) {
        arrow.uPoint.y = y;
    }

    /**
     * Returns x-coord of the bend-point.
     * @return
     */
    public int getX() {
        return (int) arrow.uPoint.x;
    }

    /**
     * Returns y coorfinate for the bend point.
     * @return
     */
    public int getY() {
        return (int) arrow.uPoint.y;
    }

    public int getWidth() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getHeight() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double getX1() {
        return pn1.getX() + pn1.getWidth() / 2;
    }

    public double getY1() {
        return pn1.getY() + pn1.getHeight() / 2;
    }

    public Point2D getP1() {
        return new Point2D.Double(pn1.getX() + pn1.getWidth() / 2, pn1.getY() + pn1.getHeight() / 2);
    }

    public double getX2() {
        return pn2.getX() + pn2.getWidth() / 2;
    }

    public double getY2() {
        return pn2.getY() + pn2.getHeight() / 2;
    }

    public Point2D getP2() {
        return new Point2D.Double(pn2.getX() + pn2.getWidth() / 2, pn2.getY() + pn2.getHeight() / 2);
    }

    public int compareTo(Object o) {
        return number.compareTo(((Link) o).getNumber());
    }

    public Line2D.Double[] getLines() {
        return arrow.getLines();
    }

    /**
     * Sets the point of bend.
     * @param x
     * @param y
     */
    public void setPoint(int x, int y) {
        arrow.uPoint = new Point2D.Double(x, y);
    }

    public void setPoint(Point2D.Double p) {
        arrow.uPoint = p;
    }

    public Point2D.Double getPoint() {
        return arrow.uPoint;
    }

    public boolean contains(int x, int y) {
        return arrow.contains(x, y);
    }

    @Override
    public String getName() {
        return "Link";
    }

    @Override
    public PetriNodePopup getPopup() {
        return new LinkPopup(this);
    }
}
