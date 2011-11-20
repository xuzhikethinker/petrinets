package undo;

import javax.swing.undo.AbstractUndoableEdit;
import model.Graph;
import model.Link;
import model.PetriNode;
import model.Place;
import model.Transition;
import main.visual.DrawPanel;

/**
 *
 * @author Aloren
 */
public class AddDeleteEdit extends AbstractUndoableEdit {

        protected Graph graph;
        protected PetriNode p;
        protected DrawPanel panel;

        public AddDeleteEdit(DrawPanel panel, Graph graph, PetriNode p) {
            this.graph = graph;
            this.p = p;
            this.panel = panel;
        }

        @Override
        public String getPresentationName() {
            return "AddDeleteEdit";
        }

        @Override
        public void undo() {
            super.undo();

            if (p.isSelected()) {
                panel.deleteMode();
            } else {
                if (p.getClass().equals(Place.class)) {
                    panel.setLastPlaceName(panel.getLastPlaceName()-1);
                } else if (p.getClass().equals(Transition.class)) {
                    panel.setLastTransitionName(panel.getLastTransitionName()-1);
                } else if (p.getClass().equals(Link.class)) {
                    if (((Link) p).getPN1().getClass().equals(Place.class)) {
                        Link l = (Link) p;
                        Place p = (Place) l.getPN1();
                        Transition t = (Transition) l.getPN2();
                        System.out.println(l);
                        System.out.println("P" + p.getNumber() + " T" + t.getNumber());
                    }
                }

                graph.removeNode(p);
            }
        }

        public void redo() {
            super.redo();
            if (p.getClass().equals(Place.class)) {
                panel.setLastPlaceName(panel.getLastPlaceName()+1);
                graph.add((Place) p);
            } else if (p.getClass().equals(Transition.class)) {
                panel.setLastTransitionName(panel.getLastTransitionName()+1);
                graph.add((Transition) p);
            } else if (p.getClass().equals(Link.class)) {
                graph.add(p);
            }

        }
    }
