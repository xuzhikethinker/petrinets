package model;

import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class that incapsulates all links inside the graph.
 * @author Aloren
 */
public class Links extends ArrayList implements Serializable{

   // private Link[] elementData;
    private static final long serialVersionUID = 2;

    public Links() {
        super(10);
    }

    public Links(ArrayList<Link> arrows) {
        super(arrows);
    }

    public void add(Link l) {
        super.add(l);
    }

    /**
     * Removes link between two nodes.
     * @param p1
     * @param p2
     */
    public void remove(PetriNode p1, PetriNode p2) {
        ArrayList<Link> arrowsToDel = new ArrayList<Link>();
        for (int i = 0; i < super.size(); i++) {
            if (((Link)super.get(i)).getPN1().equals(p1) && ((Link)super.get(i)).getPN1().equals(p2)) {
                arrowsToDel.add(((Link)super.get(i)));
            }
        }
        super.removeAll(arrowsToDel);
    }

    @Override
    public Link get(int i) {
        return (Link)super.get(i);
    }

    public boolean remove(Link l){
        return super.remove(l);
    }


    /**
     * Removes from- and in-links for the input node.
     * @param p
     */
    public void remove(PetriNode p) {
        ArrayList<Link> arrowsToDel = new ArrayList<Link>();
        for (int i = 0; i < super.size(); i++) {
            if (((Link)super.get(i)).getPN1().equals(p)) {
                arrowsToDel.add(((Link)super.get(i)));
            }
            if (((Link)super.get(i)).getPN2().equals(p)) {
                arrowsToDel.add(((Link)super.get(i)));
            }
        }
        super.removeAll(arrowsToDel);
    }

    public void paintArrow(Graphics2D gd, int i) {
        ((Link)super.get(i)).paintComponent(gd);
    }

}
