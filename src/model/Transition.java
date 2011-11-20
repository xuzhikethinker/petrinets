package model;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import constants.CONSTANTS;
import generators.Generator;
import generators.PuassonGenerator;
import java.awt.BasicStroke;
import main.visual.PetriNodePopup;
import main.visual.TransitionPopup;

/**
 *
 * @author Aloren
 */
public class Transition extends PetriNode implements java.io.Serializable {

    protected Rectangle2D.Double rectangle;
    protected ArrayList<LinkWeight> OutputLinks = new ArrayList<LinkWeight>();
    protected ArrayList<LinkWeight> InputLinks = new ArrayList<LinkWeight>();
    protected boolean isReal;
    protected double probability;
    private Generator generator = new PuassonGenerator(100);
    protected static final long serialVersionUID = 5;

    public Transition(int name, Point2D.Double point, boolean isReal) {
        this(name, (int) point.x, (int) point.y, isReal);
    }

    public Transition(int name, int x, int y, boolean isReal) {
        this.number = name;
        this.rectangle = new Rectangle2D.Double(x, y, CONSTANTS.TRANSITION_WIDTH,
                CONSTANTS.TRANSITION_HEIGHT);
        this.isReal = isReal;
        if (isReal) {
            probability = 0.0;
        } else {
            probability = 1.0;
        }
    }

    public boolean isAllowed() {
        boolean allowed = true;
        for (int i = 0; i < InputLinks.size(); i++) {
            if (InputLinks.get(i).getPlace().getChips() < InputLinks.get(i).getNumberOfLinks()) {
                allowed = false;
            }
        }
        return allowed;
    }

    /**
     * Returns true if it's real transition, else false - instantaneous.
     * @return
     */
    public boolean isReal() {
        return this.isReal;
    }

    public void setReal(boolean real) {
        isReal = real;
    }

    /**
     * Adds an outer link to this transition.
     * @param p place to add.
     */
    public void addOutLink(Place p) {
        boolean has = false;
        if (!OutputLinks.isEmpty()) {
            for (int i = 0; i < OutputLinks.size(); i++) {
                if (OutputLinks.get(i).getPlace().equals(p)) {
                    OutputLinks.get(i).incNumberOfLinks();
                    has = true;
                }
            }
        }
        if (!has) {
            OutputLinks.add(new LinkWeight(p, 1));
        }

    }

    /**
     * Adds an inner link to this transition.
     * @param p place to add.
     */
    public void addInLink(Place p) {
        boolean has = false;
        if (!InputLinks.isEmpty()) {
            for (int i = 0; i < InputLinks.size(); i++) {
                if (InputLinks.get(i).getPlace().equals(p)) {
                    InputLinks.get(i).incNumberOfLinks();
                    has = true;
                }
            }
        }
        if (!has) {
            InputLinks.add(new LinkWeight(p, 1));
        }

    }

    public String getName() {
        return "T" + Integer.toString(number);
    }

    public String getNameWithType() {
        if (isReal) {
            return "T" + Integer.toString(number) + " Real";
        } else {
            return "T" + Integer.toString(number) + " Instant";
        }
    }

    public String getType() {
        if (isReal) {
            return "Real";
        } else {
            return "Instant";
        }
    }

    /**
     * Removes place from InputLinks and OutputLinks.
     * @param p place to remove.
     */
    public void removePlace(Place p) {
        for (int i = 0; i < OutputLinks.size(); i++) {
            if (OutputLinks.get(i).getPlace().equals(p)) {
                OutputLinks.remove(i);
            }
        }
        for (int i = 0; i < InputLinks.size(); i++) {
            if (InputLinks.get(i).getPlace().equals(p)) {
                InputLinks.remove(i);
            }
        }
    }

    /**
     * Paints this transition.
     * @param g2
     */
    public void paintComponent(Graphics2D g2) {
        if (isSelected()) {
            g2.setColor(CONSTANTS.SELECTED_COLOR);
        } else {
            g2.setColor(CONSTANTS.DEFAULT_REAL_TRANSITION_COLOR);
        }
        if (!isReal() && !isSelected()) {
            g2.setColor(CONSTANTS.DEFAULT_INST_TRANSITION_COLOR);
        }
        g2.fill(rectangle);
        if (isSelected()) {
            g2.setStroke(new BasicStroke(3));
            g2.setColor(CONSTANTS.BORDER_SELECTED_COLOR);
        } else {
            g2.setColor(CONSTANTS.DEFAULT_COLOR);
        }
        g2.draw(rectangle);
        g2.drawString("T".concat(Integer.toString(number)), (int) rectangle.x - 10,
                (int) rectangle.y - 5);
        if (isReal) {
            g2.drawString("R=".concat(Double.toString(getProbability())),
                    (int) rectangle.x + CONSTANTS.TRANSITION_HEIGHT, (int) rectangle.y + CONSTANTS.TRANSITION_HEIGHT);
        }
        g2.setStroke(new BasicStroke(1));
    }

    public int compareTo(Object o) {
        Transition emp = (Transition) o;
        return number.compareTo(emp.getNumber());
    }

    /**
     * Sets the point on panel.
     * @param x
     * @param y
     */
    public void setPoint(int x, int y) {
        this.rectangle.x = x;
        this.rectangle.y = y;
    }

    /**
     * @return the OutputLinks
     */
    public ArrayList<LinkWeight> getOutputLinks() {
        return OutputLinks;
    }

    /**
     * @return the InputLinks
     */
    public ArrayList<LinkWeight> getInputLinks() {
        return InputLinks;
    }

    public void setPoint(Point2D.Double p) {
        this.rectangle.x = p.x;
        this.rectangle.y = p.y;
    }

    public Point2D.Double getPoint() {
        return new Point2D.Double(this.rectangle.x, this.rectangle.y);
    }

    /**
     * @return the probability
     */
    public double getProbability() {
        return probability;
    }

    /**
     * @param probability the probability to set
     */
    public void setProbability(double probability) {
        this.probability = probability;
    }

    @Override
    public int getX() {
        return (int) rectangle.x;
    }

    @Override
    public int getY() {
        return (int) rectangle.y;
    }

    @Override
    public int getWidth() {
        return (int) rectangle.width;
    }

    @Override
    public int getHeight() {
        return (int) rectangle.height;
    }

    @Override
    public boolean contains(int x, int y) {
        return rectangle.contains(x, y);
    }

    /**
     * @return the generator
     */
    public Generator getGenerator() {
        if (isReal) {
            return generator;
        } else {
            throw new UnsupportedOperationException("Instant transitions don't have generators");
        }
    }

    /**
     * @param generator the generator to set
     */
    public void setGenerator(Generator generator) {
        if (isReal) {
            this.generator = generator;
        } else {
            throw new UnsupportedOperationException("Instant transitions don't have generators");
        }
    }

    @Override
    public PetriNodePopup getPopup() {
        TransitionPopup pp = new TransitionPopup(this);
        return pp;
    }
}
