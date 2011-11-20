package main.visual;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.Collections;
import javax.swing.JPanel;
import model.Graph;
import model.Link;
import model.PetriNode;
import model.Place;
import model.Transition;
import main.TableUpdator;

/**
 *
 * @author Aloren
 */
public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener {

    /**
     * State of the panel to process the clicks.
     */
    private MODE mode = MODE.SELECT;

    public enum MODE {

        POSITION, VTRANSITION, MTRANSITION, ARC, SELECT, DELETE,
    }
    private int lastPlaceName = 0;
    private int lastTransitionName = 0;
    private Graph graph = new Graph();
    public Grid grid = new Grid();
    Link activeLink;

    /**
     * Constructs new white panel.
     */
    public DrawPanel() {
        setBackground(Color.WHITE);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    /**
     * Paints panel and all nodes.
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        grid.paint(g2, getWidth(), getHeight());
        for (int i = 0; i < graph.getPlaces().size(); i++) {
            graph.getPlaces().get(i).paintComponent(g2);
        }
        for (int i = 0; i < graph.getTransitions().size(); i++) {
            graph.getTransitions().get(i).paintComponent(g2);
        }
        for (int i = 0; i < graph.getArrows().size(); i++) {
            graph.getArrows().paintArrow(g2, i);
        }
    }

    public void prepareForOpenedGraph(Graph graph) {
        Collections.sort(graph.getPlaces());
        if (!graph.getPlaces().isEmpty()) {
            setLastPlaceName(graph.getPlaces().get(graph.getPlaces().size() - 1).getNumber() + 1);
        } else {
            setLastPlaceName(0);
        }
        Collections.sort(graph.getTransitions());
        if (!graph.getTransitions().isEmpty()) {
            setLastTransitionName(graph.getTransitions().get(graph.getTransitions().size() - 1).getNumber() + 1);
        } else {
            setLastTransitionName(0);
        }
        removeActiveLink();
        TableUpdator.updateTables();

        repaint();
    }

    public void prepareForNewGraph() {
        setLastPlaceName(0);
        setLastTransitionName(0);
        removeActiveLink();
        graph = new Graph();
        TableUpdator.createNewTables();
        repaint();
    }

    public void removeActiveLink() {
        activeLink = null;
    }

    public void mouseClicked(MouseEvent evt) {
        if (evt.getModifiers() == MouseEvent.BUTTON1_MASK) {
            try {
                Point2D.Double point = grid.getSnapToGridPoint(evt.getX(), evt.getY());
                if (mode.equals(MODE.POSITION)) {
                    positionMode(point);
                } else if (mode.equals(MODE.VTRANSITION)) {
                    vTransitionMode(point);
                } else if (mode.equals(MODE.MTRANSITION)) {
                    mTransitionMode(point);
                } else if (mode.equals(MODE.ARC)) {
                    arcMode(evt.getX(), evt.getY());
                } else if (mode.equals(MODE.SELECT)) {
                    selectMode(evt.getX(), evt.getY());
                }
                TableUpdator.updateTables();
            } catch (Exception e) {
                System.out.println(e);
            }
        } else if (evt.getModifiers() == MouseEvent.BUTTON3_MASK) {
            PetriNode pn = graph.getNodeAtPoint(evt.getX(), evt.getY());
            if (pn != null) {
                PetriNodePopup pp = PetriNodePopup.getPopup(pn, this);
                pp.show(this, evt.getX(), evt.getY());
            }
        }
        repaint();
    }

    public void mousePressed(MouseEvent e) {
        if (!mode.equals(MODE.ARC)) {
            selectMode(e.getX(), e.getY());
        }
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    /**
     * Moves selected node over the panel.
     * @param evt
     */
    public void mouseDragged(MouseEvent evt) {
        PetriNode selectedNode = graph.getSelectedNode();
        if (selectedNode != null && !mode.equals(MODE.ARC)) {
            selectedNode.setPoint(grid.getSnapToGridPoint(evt.getX(), evt.getY()));
        }
        repaint();
    }

    /**
     * Changes the state of the panel.
     * @param mode
     */
    public void setMode(MODE mode) {
        this.mode = mode;
        graph.setSelectedNode(null);
        repaint();
    }

    public void deleteMode() {
        //delete selected node
        PetriNode pn = graph.getSelectedNode();
        if (pn != null) {
            graph.removeNode(pn);
            graph.setSelectedNode(null);
            if (pn.getClass().equals(Place.class)) {
                setLastPlaceName(getLastPlaceName() - 1);
            } else if (pn.getClass().equals(Transition.class)) {
                setLastTransitionName(getLastTransitionName() - 1);
            }
            repaint();
        }
    }

    private void mTransitionMode(Double point) {
        //add new Transition mode
        Transition inst = new Transition(getLastTransitionName(), point, false);
        graph.add(inst);
        setLastTransitionName(getLastTransitionName() + 1);
    }

    private void positionMode(Double point) {
        //Add new Place mode
        Place p = new Place(getLastPlaceName(), point);
        graph.add(p);
        setLastPlaceName(getLastPlaceName() + 1);
    }

    private void vTransitionMode(Double point) {
        //add new Transition mode
        Transition t = new Transition(getLastTransitionName(), point, true);
        graph.add(t);
        setLastTransitionName(getLastTransitionName() + 1);
    }

    /**
     * Mode for adding new link between two selected nodes.
     * @param xx
     * @param yy
     */
    private void arcMode(int xx, int yy) {
        //Add new Arc mode
        PetriNode pn = graph.getNodeAtPoint(xx, yy);
        if (!pn.getClass().equals(Link.class)) {
            if (graph.getSelectedNode() == null) { //first click
                graph.setSelectedNode(pn);
                activeLink = new Link(pn, null);
            } else { //second click
                if (!pn.getClass().equals(graph.getSelectedNode().getClass())) {
                    activeLink.setPN2(pn);
                    graph.add(activeLink);
                    graph.setSelectedNode(null);
                    removeActiveLink();
                }
            }
        }
    }

    /**
     * Mode for selecting nodes.
     * @param pickedX
     * @param pickedY
     */
    private void selectMode(int pickedX, int pickedY) {
        //Select mode
        if (graph.getSelectedNode() == null) {
            PetriNode pn = graph.getNodeAtPoint(pickedX, pickedY);
            if (pn != null) {
                graph.setSelectedNode(pn);
            }
        } else {
            PetriNode p = graph.getSelectedNodeContain(pickedX, pickedY);
            if (p == null) {
                graph.setSelectedNode(null);
                selectMode(pickedX, pickedY);
            }
        }
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    /**
     * @return the lastPlaceName
     */
    public int getLastPlaceName() {
        return lastPlaceName;
    }

    /**
     * @param lastPlaceName the lastPlaceName to set
     */
    public void setLastPlaceName(int lastPlaceName) {
        this.lastPlaceName = lastPlaceName;
    }

    /**
     * @return the lastTransitionName
     */
    public int getLastTransitionName() {
        return lastTransitionName;
    }

    /**
     * @param lastTransitionName the lastTransitionName to set
     */
    public void setLastTransitionName(int lastTransitionName) {
        this.lastTransitionName = lastTransitionName;
    }
}
