package main.visual;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import model.Place;

/**
 *
 * @author Aloren
 */
public class PlacePopup extends PetriNodePopup implements ActionListener {

    Place place;

    public PlacePopup(Place p) {
        place = p;
        JMenuItem menuItem1 = new JMenuItem("Properties");
        menuItem1.addActionListener(this);
        add(menuItem1);
    }

    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem) (e.getSource());
        if (source.getText().equals("Properties")) {
            PlaceProperties pp = new PlaceProperties(place, (DrawPanel) this.getInvoker());
            pp.setLocationRelativeTo(null);
            pp.setVisible(true);
        }
    }
}
