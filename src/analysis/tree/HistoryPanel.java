package analysis.tree;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import javax.swing.JPanel;
import constants.GraphMarkovColors;

/**
 *
 * @author Aloren
 */
public class HistoryPanel extends JPanel {

    public static final int HISTORY_SHAPE_SIZE = 20;
    public static final int MEAN_Y = 15;
    public static final int BEGIN_Y = 10;
    public static final String REAL_STATE_NAME = "Real";
    public static final String TERMINAL_STATE_NAME = "Terminal";
    public static final String INST_STATE_NAME = "Instantaneous";
    public static final String REAL_DUP_STATE_NAME = "Real duplicate";
    public static final String INST_DUP_STATE_NAME = "Instantaneous duplicate";
    public static final String ROOT_STATE_NAME = "Root";
    public static final String COV_STATE_NAME = "Covering";

    public void paint(Graphics g) {
        super.paintComponents(g);
        Dimension d = getSize();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        FontMetrics fontM = g2.getFontMetrics();

        double next = BEGIN_Y;
        Ellipse2D.Double circle1 = new Ellipse2D.Double(next, BEGIN_Y, HISTORY_SHAPE_SIZE, HISTORY_SHAPE_SIZE);
        next += circle1.getMaxY() + 10 + fontM.stringWidth(REAL_STATE_NAME);
        Ellipse2D.Double circle2 = new Ellipse2D.Double(next, BEGIN_Y, HISTORY_SHAPE_SIZE, HISTORY_SHAPE_SIZE);
        next += circle2.getMaxY() + 10 + fontM.stringWidth(INST_STATE_NAME);
        Ellipse2D.Double circle3 = new Ellipse2D.Double(next, BEGIN_Y, HISTORY_SHAPE_SIZE, HISTORY_SHAPE_SIZE);
        next += circle3.getMaxY() + 10 + fontM.stringWidth(REAL_DUP_STATE_NAME);
        Ellipse2D.Double circle4 = new Ellipse2D.Double(next, BEGIN_Y, HISTORY_SHAPE_SIZE, HISTORY_SHAPE_SIZE);
        next += circle4.getMaxY() + 10 + fontM.stringWidth(INST_DUP_STATE_NAME);
        Ellipse2D.Double circle5 = new Ellipse2D.Double(next, BEGIN_Y, HISTORY_SHAPE_SIZE, HISTORY_SHAPE_SIZE);
        next += circle5.getMaxY() + 10 + fontM.stringWidth(ROOT_STATE_NAME);
        Ellipse2D.Double circle6 = new Ellipse2D.Double(next, BEGIN_Y, HISTORY_SHAPE_SIZE, HISTORY_SHAPE_SIZE);
        next += circle6.getMaxY() + 10 + fontM.stringWidth(TERMINAL_STATE_NAME);
        Ellipse2D.Double circle7 = new Ellipse2D.Double(next, BEGIN_Y, HISTORY_SHAPE_SIZE, HISTORY_SHAPE_SIZE);

        g2.setColor(GraphMarkovColors.REAL_STATE_COLOR);
        g2.fill(circle1);
        g2.setColor(GraphMarkovColors.INST_STATE_COLOR);
        g2.fill(circle2);
        g2.setColor(GraphMarkovColors.REAL_STATE_DUP_COLOR);
        g2.fill(circle3);
        g2.setColor(GraphMarkovColors.INST_STATE_DUP_COLOR);
        g2.fill(circle4);
        g2.setColor(GraphMarkovColors.ROOT_COLOR);
        g2.fill(circle5);
        g2.setColor(GraphMarkovColors.TERMINAL_STATE_COLOR);
        g2.fill(circle6);
        g2.setColor(GraphMarkovColors.COV_STATE_COLOR);
        g2.fill(circle7);

        g2.setColor(Color.BLACK);
        g2.drawString(REAL_STATE_NAME, (float) (circle1.getMaxX()) + 5, (float) (MEAN_Y + circle1.getHeight() / 2));
        g2.drawString(INST_STATE_NAME, (float) (circle2.getMaxX()) + 5, (float) (MEAN_Y + circle1.getHeight() / 2));
        g2.drawString(REAL_DUP_STATE_NAME, (float) (circle3.getMaxX()) + 5, (float) (MEAN_Y + circle1.getHeight() / 2));
        g2.drawString(INST_DUP_STATE_NAME, (float) (circle4.getMaxX()) + 5, (float) (MEAN_Y + circle1.getHeight() / 2));
        g2.drawString(ROOT_STATE_NAME, (float) (circle5.getMaxX()) + 5, (float) (MEAN_Y + circle1.getHeight() / 2));
        g2.drawString(TERMINAL_STATE_NAME, (float) (circle6.getMaxX()) + 5, (float) (MEAN_Y + circle1.getHeight() / 2));
        g2.drawString(COV_STATE_NAME, (float) (circle7.getMaxX()) + 5, (float) (MEAN_Y + circle1.getHeight() / 2));

    }
}
