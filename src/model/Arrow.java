package model;

import constants.CONSTANTS;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 *
 * @author Aloren
 */
public class Arrow implements Serializable{

    PetriNode pn1;
    PetriNode pn2;
    private boolean selected = false;
    Point2D.Double uPoint;
    public static final float stroke = 0.2f;
    // private Line2D.Double line;
    // Width and height of rectangular region around mouse
    // pointer to use for hit detection on lines
    private static final int HIT_BOX_SIZE = 6;

    public Arrow(PetriNode pn1, PetriNode pn2) {
        this.uPoint = null;
        this.pn1 = pn1;
        this.pn2 = pn2;
    }

    /**
     * Draws the whole link.
     * @param g2
     */
    public void paintComponent(Graphics2D g2) {
        final int srcX = (int) (pn1.getX() + pn1.getWidth() / 2);
        final int srcY = (int) (pn1.getY() + pn1.getHeight() / 2);

        final int destX = (int) (pn2.getX() + pn2.getWidth() / 2);
        final int destY = (int) (pn2.getY() + pn2.getHeight() / 2);

        if (selected) {
            g2.setColor(CONSTANTS.BORDER_SELECTED_COLOR);
            g2.setStroke(new BasicStroke(3));
        } else {
            g2.setColor(Color.BLACK);
        }
        try {
            if (uPoint == null) {
                double aDir = Math.atan2(srcX - destX, srcY - destY);
                drawRelationship(srcX, srcY, destX, destY, g2);
                drawArrowHead(g2, aDir, srcX, srcY, destX, destY);
            } else {
                drawRelationshipWithUPoint(srcX, srcY, destX, destY, g2);
                double aDir = Math.atan2(uPoint.getX() - destX, uPoint.getY() - destY);
                drawArrowHead(g2, aDir, (int) uPoint.getX(), (int) uPoint.getY(), destX, destY);
            }
        } catch (NullPointerException e) {
            System.out.println("No second node selected");
        }
        g2.setStroke(new BasicStroke(1));
    }

    /**
     * Draws a line from the edge of the src node to the edge of the destination node.
     */
    private void drawRelationship(double srcX, double srcY, double destX, double destY, Graphics g) {
        double dx = getLength(srcX, destX);
        double dy = getLength(srcY, destY);
        double theta = Math.atan2(dy, dx);
        Point2D.Double srcIntersectionP;
        Point2D.Double destIntersectionP;
        if (pn1.getClass().equals(Place.class)) {
            srcIntersectionP = getEllipseIntersectionPoint(theta, pn1.getWidth(), pn1.getHeight());
            destIntersectionP = getRectIntersectionPoint(pn2, new Point2D.Double(srcX, srcY), new Point2D.Double(destX, destY));
            g.drawLine((int) (srcX + srcIntersectionP.getX()), (int) (srcY + srcIntersectionP.getY()),
                    (int) (destIntersectionP.getX()), (int) (destIntersectionP.getY()));
        } else {
            srcIntersectionP = getRectIntersectionPoint(pn1, new Point2D.Double(srcX, srcY), new Point2D.Double(destX, destY));
            destIntersectionP = getEllipseIntersectionPoint(theta, pn2.getWidth(), pn2.getHeight());
            g.drawLine((int) (srcIntersectionP.getX()), (int) (srcIntersectionP.getY()),
                    (int) (destX - destIntersectionP.getX()), (int) (destY - destIntersectionP.getY()));
        }
    }

    /**
     * Draws line with uPoin(point of bend).
     */
    private void drawRelationshipWithUPoint(double srcX, double srcY, double destX, double destY, Graphics g) {
        double dx = getLength(srcX, uPoint.getX());
        double dy = getLength(srcY, uPoint.getY());
        double theta = Math.atan2(dy, dx);
        double reverseTheta = theta > 0.0d ? theta - Math.PI : theta + Math.PI;
        if (pn1.getClass().equals(Place.class)) {
            Point2D.Double srcIntersectionP = getEllipseIntersectionPoint(theta, pn1.getWidth(), pn1.getHeight());
            g.drawLine((int) (srcX + srcIntersectionP.getX()), (int) (srcY + srcIntersectionP.getY()),
                    (int) uPoint.getX(), (int) uPoint.getY());
            Point2D.Double destIntersectionP = getRectIntersectionPoint(pn2, new Point2D.Double(uPoint.getX(), uPoint.getY()), new Point2D.Double(destX, destY));
            g.drawLine((int) uPoint.getX(), (int) uPoint.getY(),
                    (int) (destIntersectionP.getX()), (int) (destIntersectionP.getY()));
        } else {
            dx = getLength(uPoint.getX(), destX);
            dy = getLength(uPoint.getY(), destY);
            theta = Math.atan2(dy, dx);
            reverseTheta = theta > 0.0d ? theta - Math.PI : theta + Math.PI;
            try {
                Point2D.Double srcIntersectionP = getRectIntersectionPoint(pn1, new Point2D.Double(srcX, srcY), new Point2D.Double(uPoint.getX(), uPoint.getY()));
                g.drawLine((int) (srcIntersectionP.getX()), (int) (srcIntersectionP.getY()),
                        (int) uPoint.getX(), (int) uPoint.getY());
                Point2D.Double destIntersectionP = getEllipseIntersectionPoint(reverseTheta, pn2.getWidth(), pn2.getHeight());
                g.drawLine((int) uPoint.getX(), (int) uPoint.getY(),
                        (int) (destX + destIntersectionP.getX()), (int) (destY + destIntersectionP.getY()));
                //TODO To fix this bug when the bend point is on the object
            } catch (NullPointerException e) {
                System.out.println("To fix this bug when the bend point is on the object");
            }
        }
    }

    /**
     * Draws arrow-head of the link.
     */
    private void drawArrowHead(Graphics2D g2d, double aDir, int srcX, int srcY, int destX, int destY) {
        double dx = getLength(srcX, destX);
        double dy = getLength(srcY, destY);
        double theta = Math.atan2(dy, dx);
        double reverseTheta = theta > 0.0d ? theta - Math.PI : theta + Math.PI;
        Point2D.Double destIntersectionP;
        int x;
        int y;
        if (pn2.getClass().equals(Place.class)) {
            destIntersectionP = getEllipseIntersectionPoint(reverseTheta, pn2.getWidth(), pn2.getHeight());
            x = (int) (destX + destIntersectionP.getX());
            y = (int) (destY + destIntersectionP.getY());
        } else {
            destIntersectionP = getRectIntersectionPoint(pn2, new Point2D.Double(srcX, srcY), new Point2D.Double(destX, destY));
            x = (int) (destIntersectionP.getX());
            y = (int) (destIntersectionP.getY());
        }

        g2d.setStroke(new BasicStroke(1f));					// make the arrow head solid even if dash pattern has been specified
        Polygon tmpPoly = new Polygon();
        int i1 = 12 + (int) (stroke * 2);
        int i2 = 6 + (int) stroke;							// make the arrow head the same size regardless of the length length

        tmpPoly.addPoint(x, y);							// arrow tip
        tmpPoly.addPoint(x + xCor(i1, aDir + .5), y + yCor(i1, aDir + .5));
        tmpPoly.addPoint(x + xCor(i2, aDir), y + yCor(i2, aDir));
        tmpPoly.addPoint(x + xCor(i1, aDir - .5), y + yCor(i1, aDir - .5));
        tmpPoly.addPoint(x, y);							// arrow tip
        g2d.drawPolygon(tmpPoly);
        g2d.fillPolygon(tmpPoly);						// remove this line to leave arrow head unpainted

    }

    /**
     * Finds the intersection point between link-line and the input Petri node(Place) edge.
     * @param theta
     * @param ellipseWidth
     * @param ellipseHeight
     * @return
     */
    private static Point2D.Double getEllipseIntersectionPoint(double theta, double ellipseWidth, double ellipseHeight) {
        double nhalfw = ellipseWidth / 2.0; // half elllipse width
        double nhalfh = ellipseHeight / 2.0; // half ellipse height
        double tanTheta = Math.tan(theta);

        double a = nhalfw;
        double b = nhalfh;
        double x = (a * b) / Math.sqrt(Math.pow(b, 2) + Math.pow(a, 2) * Math.pow(tanTheta, 2));
        if ((theta > Math.PI / 2.0 && theta < 1.5 * Math.PI) || (theta < -Math.PI / 2.0 && theta > -1.5 * Math.PI)) {
            x = -x;
        }
        double y = tanTheta * x;
        Point2D.Double p = new Point2D.Double(x, y);
        return p;
    }

    /**
     * Finds the intersection point between link-line and the input Petri node(Transition) edge.
     * @param pn2
     * @param p1 point1 of the line.
     * @param p2 point2 of the line.
     * @return
     */
    private Point2D.Double getRectIntersectionPoint(PetriNode pn2, Point2D.Double p1, Point2D.Double p2) {
        Point2D.Double p = null;
        Line2D.Double line = new Line2D.Double(p1, p2);
        Point2D.Double pp1 = new Point2D.Double(pn2.getX(), pn2.getY());
        Point2D.Double pp2 = new Point2D.Double(pn2.getX() + pn2.getWidth(), pn2.getY());
        Point2D.Double pp3 = new Point2D.Double(pn2.getX(), pn2.getY() + pn2.getHeight());
        Point2D.Double pp4 = new Point2D.Double(pn2.getX() + pn2.getWidth(), pn2.getY() + pn2.getHeight());

        if (line.intersectsLine(new Line2D.Double(pp1, pp3))) {
            p = getIntersectionPoint(line, new Line2D.Double(pp1, pp3));
        } else if (line.intersectsLine(new Line2D.Double(pp1, pp2))) {
            p = getIntersectionPoint(line, new Line2D.Double(pp1, pp2));
        } else if (line.intersectsLine(new Line2D.Double(pp2, pp4))) {
            p = getIntersectionPoint(line, new Line2D.Double(pp2, pp4));
        } else if (line.intersectsLine(new Line2D.Double(pp3, pp4))) {
            p = getIntersectionPoint(line, new Line2D.Double(pp3, pp4));
        }
        return p;
    }

    /**
     * Finds the intersection point between two lines.
     * @param line1
     * @param line2
     * @return
     */
    private Point2D.Double getIntersectionPoint(Line2D.Double line1, Line2D.Double line2) {
        if (!line1.intersectsLine(line2)) {
            return null;
        }
        double px = line1.getX1(),
                py = line1.getY1(),
                rx = line1.getX2() - px,
                ry = line1.getY2() - py;
        double qx = line2.getX1(),
                qy = line2.getY1(),
                sx = line2.getX2() - qx,
                sy = line2.getY2() - qy;

        double det = sx * ry - sy * rx;
        if (det == 0) {
            return null;
        } else {
            double z = (sx * (qy - py) + sy * (px - qx)) / det;
            if (z == 0 || z == 1) {
                return null;  // intersection at end point!
            }
            return new Point2D.Double(
                    (double) (px + z * rx), (double) (py + z * ry));
        }
    }

    /**
     * Returns all lines of this link.
     * @return
     */
    public Line2D.Double[] getLines() {
        int srcX = (int) (pn1.getX() + pn1.getWidth() / 2);
        int srcY = (int) (pn1.getY() + pn1.getHeight() / 2);
        int destX = (int) (pn2.getX() + pn2.getWidth() / 2);
        int destY = (int) (pn2.getY() + pn2.getHeight() / 2);
        if (uPoint != null) {
            return new Line2D.Double[]{new Line2D.Double(destX, destY, (int) uPoint.getX(), (int) uPoint.getY()),
                        new Line2D.Double((int) uPoint.getX(), (int) uPoint.getY(), srcX, srcY)};
        } else {
            return new Line2D.Double[]{new Line2D.Double(destX, destY, srcX, srcY)};
        }
    }

    /**
     * Returns the length of a line ensuring it is not too small to render.
     * @param start
     * @param end
     * @return
     */
    private double getLength(double start, double end) {
        double length = end - start;
        // make sure dx is not zero or too small
        if (length < 0.01 && length > -0.01) {
            if (length > 0) {
                length = 0.01;
            } else if (length < 0) {
                length = -0.01;
            }
        }
        return length;
    }

    private static int yCor(int len, double dir) {
        return (int) (len * Math.cos(dir));
    }

    private static int xCor(int len, double dir) {
        return (int) (len * Math.sin(dir));
    }

    public boolean contains(int x, int y) {
        int boxX = x - HIT_BOX_SIZE / 2;
        int boxY = y - HIT_BOX_SIZE / 2;
        int width = HIT_BOX_SIZE;
        int height = HIT_BOX_SIZE;
        boolean contains = false;
        for (int j = 0; (j < getLines().length) && !contains; j++) {
            if (getLines()[j].intersects(boxX, boxY, width, height)) {
                contains = true;
            }
        }
        return contains;
    }

    void setSelected(boolean selected) {
        this.selected = selected;
    }
}
