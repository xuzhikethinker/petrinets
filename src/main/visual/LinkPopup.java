package main.visual;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import model.Link;

/**
 *
 * @author Aloren
 */
public class LinkPopup extends PetriNodePopup implements ActionListener {

    private final Link link;

    public LinkPopup(Link link) {
        this.link = link;
        JMenuItem menuItem1 = new JMenuItem("Properties");
        menuItem1.addActionListener(this);
        add(menuItem1);
    }

    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Not implemented yet.");
        System.out.println("Not implemented yet.");
    }
}
