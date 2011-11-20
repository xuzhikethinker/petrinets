package main.visual;

import edu.uci.ics.jung.visualization.layout.PersistentLayout.Point;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 *
 * @author Aloren
 */
public class Grid {

    private int spacing = 5;
    private boolean snapToGrid = true;
    private boolean showGrid = true;

    public void paint(Graphics2D g, int width, int height) {
        if (showGrid) {
            g.setColor(new Color(240, 240, 240));
            //vertical lines
            for (int i = spacing; i < width; i += spacing) {
                g.drawLine(i, 0, i, height);
            }
            //horizontal lines
            for (int j = spacing; j < height; j += spacing) {
                g.drawLine(0, j, width, j);
            }
        }
    }

    public Point2D.Double getSnapToGridPoint(int x, int y) {
        int xGrid = x / spacing;
        int yGrid = y / spacing;
        if (snapToGrid) {
            return new Point2D.Double((int) xGrid * spacing, (int) yGrid * spacing);
        } else {
            return new Point2D.Double(x, y);
        }
    }
    
        /**
     * @return the snapToGrid
     */
    public boolean isSnapToGrid() {
        return snapToGrid;
    }

    /**
     * @param snapToGrid the snapToGrid to set
     */
    public void setSnapToGrid(boolean snapToGrid) {
        this.snapToGrid = snapToGrid;
    }

    /**
     * @return the showGrid
     */
    public boolean isShowGrid() {
        return showGrid;
    }

    /**
     * @param showGrid the showGrid to set
     */
    public void setShowGrid(boolean showGrid) {
        this.showGrid = showGrid;
    }

    /**
     * @param spacing the SPACING to set
     */
    public void setSpacing(int spacing) {
        this.spacing = spacing;
    }
}
