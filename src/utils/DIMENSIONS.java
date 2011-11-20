package utils;

import java.awt.GraphicsConfiguration;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 *
 * @author Aloren
 */
public class DIMENSIONS {

    public static void fullScreen(JFrame frame) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        GraphicsConfiguration gc = frame.getGraphicsConfiguration();
        Rectangle bounds = gc.getBounds();
        Insets insets = toolkit.getScreenInsets(gc);
        bounds.x += insets.left;
        bounds.y += insets.top;
        bounds.width -= insets.left + insets.right;
        bounds.height -= insets.top + insets.bottom;
        frame.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
    }
}
