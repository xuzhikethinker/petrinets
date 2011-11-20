package actionlisteners;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import main.Main;
import main.visual.DrawPanel;

/**
 *
 * @author Aloren
 */
public class GridSpacingListener implements ChangeListener {

    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        ((DrawPanel) (Main.drawPanel)).grid.setSpacing(slider.getValue());
        ((DrawPanel) (Main.drawPanel)).repaint();
    }
}
