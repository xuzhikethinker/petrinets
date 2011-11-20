package model;

import utils.Matrix;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import utils.Saveable;
import main.visual.DrawPanel;

/**
 *
 * @author Aloren
 */
public class Graph implements java.io.Serializable, Cloneable, Saveable {

    private transient Matrix Dr = null;
    private transient Matrix Di = null;
    private transient Matrix Dq = null;
    private ArrayList<Place> places;
    private ArrayList<Transition> transitions;
    private Links links;
    private transient PetriNode selectedNode = null;
    private static final long serialVersionUID = 1;

    public Graph() {
        this.places = new ArrayList<Place>();
        this.transitions = new ArrayList<Transition>();
        this.links = new Links();
    }
    
    public Graph clone(){
        Graph g = new Graph();
        g.Di = this.Di;
        g.Dr = this.Dr;
        g.Dq = this.Dq;
        g.places = this.places;
        g.transitions = this.transitions;
        g.links = this.links;
        g.selectedNode = this.selectedNode;
        return g;
    }

    /**
     * Returns the array of all allowed transitions.
     * @return
     */
    public ArrayList<Transition> getAllowedTransitions() {
        ArrayList<Transition> allowedTransitions = new ArrayList<Transition>();
        for (int i = 0; i < transitions.size(); i++) {
            if (transitions.get(i).isAllowed()) {
                allowedTransitions.add(transitions.get(i));
            }
        }
        return allowedTransitions;
    }

    /**
     * Returns true if this graph has allowed instant transitions.
     * @return
     */
    public boolean hasAllowedInstantTransitions() {
        boolean has = false;
        for (int i = 0; (i < transitions.size()) && !has; i++) {
            if (!transitions.get(i).isReal() && transitions.get(i).isAllowed()) {
                has = true;
            }
        }
        return has;
    }

    /**
     * Returns true if all input transitions are instant.
     * @param trans
     * @return 
     */
    public boolean allInstant() {
        boolean all = true;
        for (int i = 0; i < transitions.size() && all; i++) {
            if (transitions.get(i).isReal) {
                all = false;
            }
        }
        return all;
    }

    /**
     * Returns marking(chips) of the current graph.
     * @return 
     */
    public Integer[] getChips() {
        Integer[] chips = new Integer[places.size()];
        for (int i = 0; i < places.size(); i++) {
            chips[i] = places.get(i).getChips();

        }
        return chips;
    }

    /**
     * Sets marking to rhis graph.
     * @param chips
     */
    public void setMarking(Integer[] chips) {
        for (int i = 0; i < chips.length; i++) {
            places.get(i).setChips(chips[i]);
        }
    }

    /**
     * Writes(serializes) graph to file.
     * @param fileSerial
     *            Name of the file to which serialized data will be saved.
     * @throws IOException
     */
    public void writeToFile(final File fileSerial) throws IOException {
        System.out.println("Writing graph");
        System.out.println(fileSerial);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
                fileSerial));
        oos.writeObject(this);
        oos.close();
    }

    /**
     * @param fileFromSerial
     *            Name of the file, from which serialized data will be loaded.
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Graph deserialize(final File fileFromSerial) throws IOException,
            ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
                fileFromSerial));
        return (Graph) ois.readObject();
    }

    /**
     * Add Petri-node to this graph.
     * @param p
     */
    public void add(PetriNode p) {
        if (p instanceof Place) {
            places.add((Place) p);
        } else if (p instanceof Transition) {
            transitions.add((Transition) p);
        } else if (p instanceof Link) {
            addLink((Link) p);
        }
    }

    private void addLink(Link link) {
        if (link.getPN1() instanceof Place) {
            int transitionIndex = transitions.indexOf(link.getPN2());
            transitions.get(transitionIndex).addInLink((Place) link.getPN1());
            links.add(link);
        } else if (link.getPN1() instanceof Transition) {
            int transitionIndex = transitions.indexOf(link.getPN1());
            transitions.get(transitionIndex).addOutLink((Place) link.getPN2());
            links.add(link);
        }
    }

    /**
     * Returns conflicting transitions for the place.
     * @param place
     * @param transitions
     * @return 
     */
    public static ArrayList<Transition> getConflicting(Place place, ArrayList<Transition> transitions) {
        ArrayList<Transition> trans = new ArrayList<Transition>();
        for (int i = 0; i < transitions.size(); i++) {
            for (int j = 0; j < transitions.get(i).getInputLinks().size(); j++) {
                if (transitions.get(i).getInputLinks().get(j).getPlace().equals(place)) {
                    trans.add(transitions.get(i));
                }
            }
        }
        return trans;
    }

    /**
     * Returns true if some node is selected in graph.
     * @return
     */
    public boolean isAnySelected() {
        if (selectedNode != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the seleted node.
     * @return
     */
    public PetriNode getSelectedNode() {
        return this.selectedNode;
    }

    /**
     * Sets the selected node.
     * If null than deselects the selected node.
     * @param pn node to set selected.
     */
    public void setSelectedNode(PetriNode pn) {
        if (pn != null) {
            selectedNode = pn;
            pn.setSelected(true);
        } else {
            if (selectedNode != null) {
                selectedNode.setSelected(false);
                selectedNode = null;
            }
        }
    }

    /**
     * Returns selected node, that containt the input point.
     * Actually this method must be named cointainsSelectedNode.
     * @param xx
     * @param yy
     * @return
     */
    public PetriNode getSelectedNodeContain(int xx, int yy) {
        if (selectedNode.contains(xx, yy)) {
            return selectedNode;
        } else {
            return null;
        }
    }

    /**
     * Returns the node that contains the input point.
     * @param xx
     * @param yy
     * @return
     */
    public PetriNode getNodeAtPoint(int xx, int yy) throws NullPointerException{
        for (int i = 0; i < transitions.size(); i++) {
            if (transitions.get(i).contains(xx, yy)) {
                return transitions.get(i);
            }
        }
        for (int i = 0; i < places.size(); i++) {
            if (places.get(i).contains(xx, yy)) {
                return places.get(i);
            }
        }
        for (int i = 0; i < links.size(); i++) {
            for (int j = 0; j < links.get(i).getLines().length; j++) {
                if (links.get(i).contains(xx, yy)) {
                    return links.get(i);
                }
            }
        }
        return null;

    }

    /**
     * Removes node from the graph and the links that are connected with it.
     * @param pn node to remove from the graph.
     */
    public void removeNode(PetriNode pn) {
        if (pn instanceof Place) {
            removePlace((Place) pn);
        } else if (pn instanceof Transition) {
            removeTransition((Transition) pn);
        } else if (pn instanceof Link) {
            removeLink((Link) pn);
        }
    }

    private void removePlace(Place p) {
        Collections.sort(places);
        int indexOfDeleted = places.indexOf(p);
        if (places.size() - 1 != indexOfDeleted) {
            for (int i = indexOfDeleted; i < places.size(); i++) {
                places.get(i).setNumber(places.get(i).getNumber() - 1);
            }
        }
        for (int i = 0; i < transitions.size(); i++) {
            transitions.get(i).removePlace(p);
        }
        links.remove(p);
        places.remove(p);
    }

    private void removeTransition(Transition t) {
        Collections.sort(transitions);
        int indexOfDeleted = transitions.indexOf(t);
        if (transitions.size() - 1 != indexOfDeleted) {
            for (int i = indexOfDeleted; i < transitions.size(); i++) {
                transitions.get(i).setNumber(transitions.get(i).getNumber() - 1);
            }
        }
        links.remove(t);
        transitions.remove(t);
    }

    private void removeLink(Link l) {
        if (l.getPN1() instanceof Transition) {
            Transition t = (Transition) l.getPN1();
            for (int i = 0; i < t.getOutputLinks().size(); i++) {
                if (t.getOutputLinks().get(i).getPlace().equals(l.getPN2())) {
                    t.getOutputLinks().get(i).decNumberOfLinks();
                }
            }
        } else if (l.getPN1() instanceof Place) {
            Transition t = (Transition) l.getPN2();
            for (int i = 0; i < t.getInputLinks().size(); i++) {
                if (t.getInputLinks().get(i).getPlace().equals(l.getPN1())) {
                    t.getInputLinks().get(i).decNumberOfLinks();
                }
            }
        }
        this.links.remove(l);
    }

    /**
     * Builds input matrix.
     * @return
     */
    public Integer[][] buildIMatrix() {
        Integer[][] inMatrix = new Integer[transitions.size()][places.size()];
        for (int i = 0; i < inMatrix.length; i++) {
            for (int j = 0; j < inMatrix[i].length; j++) {
                inMatrix[i][j] = 0;
            }
        }
        for (int i = 0; i < transitions.size(); i++) {
            for (int j = 0; j < transitions.get(i).getInputLinks().size(); j++) {
                int first = transitions.get(i).getNumber();
                Place p = (Place) transitions.get(i).getInputLinks().get(j).getPlace();
                int second = p.getNumber();
                Integer number = transitions.get(i).getInputLinks().get(j).getNumberOfLinks();
                inMatrix[first][second] = number;
            }
        }
        Di = new Matrix(inMatrix);
        return inMatrix;
    }

    /**
     * Builds output matrix.
     * @return
     */
    public Integer[][] buildQMatrix() {
        Integer[][] outMatrix = new Integer[transitions.size()][places.size()];
        for (int i = 0; i < outMatrix.length; i++) {
            for (int j = 0; j < outMatrix[i].length; j++) {
                outMatrix[i][j] = 0;
            }
        }
        for (int i = 0; i < transitions.size(); i++) {
            for (int j = 0; j < transitions.get(i).getOutputLinks().size(); j++) {
                int first = transitions.get(i).getNumber();
                Place p = (Place) transitions.get(i).getOutputLinks().get(j).getPlace();
                int second = p.getNumber();
                Integer number = transitions.get(i).getOutputLinks().get(j).getNumberOfLinks();
                outMatrix[first][second] = number;
            }
        }
        Dq = new Matrix(outMatrix);
        return outMatrix;
    }

    /**
     * Builds result matrix.
     * @return
     */
    public Integer[][] buildRMatrix() throws ArrayIndexOutOfBoundsException {
        Integer[][] dq = buildQMatrix();
        Integer[][] di = buildIMatrix();
        Integer[][] dr = new Integer[dq.length][dq[0].length];
        for (int i = 0; i < dr.length; i++) {
            for (int j = 0; j < dr[0].length; j++) {
                dr[i][j] = dq[i][j] - di[i][j];
            }
        }
        this.Dr = new Matrix(dr);
        return dr;
    }

    /**
     * Returns true if input array of transitions contains instant transitions.
     * @param trans
     * @return true if input array contains instantaneous transitions.
     */
    public static boolean containsInstantTransitions(ArrayList<Transition> trans) {
        boolean contains = false;
        for (int i = 0; (i < trans.size()) && !contains; i++) {
            if (!trans.get(i).isReal()) {
                contains = true;
            }
        }
        return contains;
    }

    /**
     * Returns true if all input transitions are instant.
     * @param trans
     * @return 
     */
    public static boolean allInstant(ArrayList<Transition> trans) {
        boolean all = true;
        for (int i = 0; i < trans.size() && all; i++) {
            if (trans.get(i).isReal) {
                all = false;
            }
        }
        return all;
    }

    /**
     * @return the Dr
     */
    public Matrix getDr() {
        if (Dr == null) {
            Dr = new Matrix(buildRMatrix());
        }
        return Dr;
    }

    /**
     * @return the Di
     */
    public Matrix getDi() {
        if (Di == null) {
            Di = new Matrix(buildIMatrix());
        }
        return Di;
    }

    /**
     * @return the Dq
     */
    public Matrix getDq() {
        if (Dq == null) {
            Dq = new Matrix(buildQMatrix());
        }
        return Dq;
    }

    /**
     * @return the places
     */
    public ArrayList<Place> getPlaces() {
        return places;
    }

    /**
     * @return the transitions
     */
    public ArrayList<Transition> getTransitions() {
        return transitions;
    }

    /**
     * @return the arrows
     */
    public Links getArrows() {
        return links;
    }
}
