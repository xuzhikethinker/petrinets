package utils;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import analysis.simulation.visual.SaveImageAction;
import java.awt.Font;
import javax.swing.JTextArea;

/**
 *
 * @author Aloren
 */
public class Utils {

    public final static String dat = "dat";
    public final static String pn = "pn";
    public final static String png = "png";

    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    public static void writeJPEGImage(VisualizationViewer vv, File f) throws IOException {
        BufferedImage bi = new BufferedImage(vv.getWidth(), vv.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        vv.paint(g);
        g.dispose();
        ImageIO.write(bi, png, f);
    }

    public static JButton getSaveImageButton(VisualizationViewer vv) {
        SaveImageAction saveImageAction = new SaveImageAction(vv);
        JButton saveImage = new JButton(saveImageAction);
        saveImage.setText("Save as image");
        saveImage.setToolTipText("Ctrl+S");
        saveImage.setAction(saveImageAction);

        saveImage.getInputMap(JOptionPane.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK), "saveImage");
        saveImage.getActionMap().put("saveImage", saveImageAction);
        return saveImage;
    }

    public static double round(double value, int decimalPlace) {
        double power_of_ten = 1;
        // floating point arithmetic can be very tricky.
        // that's why I introduce a "fudge factor"
        double fudge_factor = 0.05;
        while (decimalPlace-- > 0) {
            power_of_ten *= 10.0d;
            fudge_factor /= 10.0d;
        }
        return Math.round((value + fudge_factor) * power_of_ten) / power_of_ten;
    }

    public static void setFontSize(JTextArea output, int dif) {
        Font f = output.getFont();
        Font f2 = new Font(f.getFontName(), f.getStyle(), f.getSize() + dif);
        output.setFont(f2);
    }
}
