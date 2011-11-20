package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import constants.CONSTANTS;
import java.awt.BasicStroke;
import main.visual.PetriNodePopup;
import main.visual.PlacePopup;

/**
 *
 * @author Aloren
 */
public class Place extends PetriNode implements java.io.Serializable {

    private int chips = 0;
    private Ellipse2D.Double ellipse;
    private static final long serialVersionUID = 4;

    public Place(int number, Point2D.Double point) {
        this(number, (int) point.x, (int) point.y);
    }

    public Place(int number, int x, int y) {
        this.number = number;
        this.ellipse = new Ellipse2D.Double(x, y, CONSTANTS.PLACE_WIDTH, CONSTANTS.PLACE_WIDTH);
    }

    /**
     * Paints this place.
     * @param g2
     */
    public void paintComponent(Graphics2D g2) {
        if (isSelected()) {
            g2.setColor(CONSTANTS.SELECTED_COLOR);
        } else {
            g2.setColor(Color.WHITE);
        }
        g2.fill(getEllipse());
        if (isSelected()) {
            g2.setStroke(new BasicStroke(3));
            g2.setColor(CONSTANTS.BORDER_SELECTED_COLOR);
        } else {

            g2.setColor(Color.BLACK);
        }
        g2.draw(getEllipse());
        int x = (int) getEllipse().x;
        int y = (int) getEllipse().y;
        g2.drawString(getName(), x - 10, y - 5);
        int chipsN = getChips();
        switch (chipsN) {
            case 5:
                g2.drawString("●", x + 6, y + 24);
            case 4:
                g2.drawString("●", x + 18, y + 13);
            case 3:
                g2.drawString("●", x + 6, y + 13);
            case 2:
                g2.drawString("●", x + 18, y + 24);
            case 1:
                g2.drawString("●", x + 12, y + 18);
                break;
            case 0:
                break;
            default:
                g2.drawString(String.valueOf(chipsN), x + 13, y + 18);
        }
        g2.setStroke(new BasicStroke(1));
    }

    public int compareTo(Object o) {
        Place otherPlace = (Place) o;
        return number.compareTo(otherPlace.getNumber());
    }

    /**
     * Sets the point of this place on panel.
     * @param x
     * @param y
     */
    public void setPoint(int x, int y) {
        this.ellipse.x = x;
        this.ellipse.y = y;
    }

    /**
     * Returns number of chips in thois place.
     * @return the chips
     */
    public int getChips() {
        return chips;
    }

    /**
     * Sets number of chips to this place.
     * @param chips the chips to set
     */
    public void setChips(int chips) {
        this.chips = chips;
    }

    public void setPoint(Point2D.Double p) {
        this.ellipse.x = p.x;
        this.ellipse.y = p.y;
    }

    public Point2D.Double getPoint() {
        return new Point2D.Double(this.getEllipse().x, this.getEllipse().y);
    }

    @Override
    public int getX() {
        return (int) this.getEllipse().x;
    }

    @Override
    public int getY() {
        return (int) this.getEllipse().y;
    }

    @Override
    public int getWidth() {
        return (int) this.getEllipse().width;
    }

    public int getHeight() {
        return (int) this.getEllipse().height;
    }

    @Override
    public boolean contains(int x, int y) {
        return getEllipse().contains(x, y);
    }

    /**
     * @return the ellipse
     */
    public Rectangle getBounds() {
        return getEllipse().getBounds();
    }

    /**
     * @return the ellipse
     */
    public Ellipse2D.Double getEllipse() {
        return ellipse;
    }

    @Override
    public String getName() {
        return "P".concat(this.number.toString());
    }

    @Override
    public PetriNodePopup getPopup() {
        PlacePopup popup = new PlacePopup(this);
        return popup;
    }
}
