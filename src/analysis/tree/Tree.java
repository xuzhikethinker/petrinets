package analysis.tree;

import java.util.ArrayList;
import java.util.Arrays;
import model.Graph;
import utils.Matrix;

/**
 *
 * @author Aloren
 */
public class Tree {

    public static Marking buildTree(String type, Integer[] startChips, Graph graph) {
        Matrix DI = graph.getDi();
        Matrix DR = graph.getDr();
        if (!Arrays.equals(graph.getChips(), startChips)) {
            System.out.println("Set chips " + Arrays.toString(graph.getChips()) + " " + Arrays.toString(startChips));
            graph.setMarking(startChips);
        }
        Marking root = new Marking(startChips, DR, DI, graph.getTransitions());
        ArrayList<Marking> previous = new ArrayList<Marking>();
        root.getTree(type, previous);
        return root;
    }
    
}
