package analysis.tree;

import utils.Matrix;
import analysis.utils.AllowVector;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import model.Graph;
import model.Transition;
import utils.Saveable;

/**
 *
 * @author Aloren
 */
public class Marking implements Saveable {

    public static final String REAL = "Real";
    /**
     * Parent of the marking.
     */
    private Marking parent;
    /**
     * Array of chips in this marking.
     */
    private Integer[] chips;
    /**
     * Type of this marking.
     */
    private TYPE type;
    /**
     * Children of this marking.
     */
    private ArrayList<Marking> children = new ArrayList<Marking>();
    /**
     * Transitions for Markov chain.
     */
    private ArrayList<MarkovLink> markovTransitions = new ArrayList<MarkovLink>();
    /**
     * Transitions for reachability tree.
     */
    private ArrayList<MarkovLink> treeTransitions = new ArrayList<MarkovLink>();
    /**
     * Result matrix.
     */
    Matrix Dr;
    Matrix Di;
    private StringBuilder firedWithTrans = new StringBuilder();
    private int yarus;
    private final ArrayList<Transition> transitions;

    /**
     *
     * @param chips array of start chips.
     * @param type type of the marking. It must be Root for you to find the reachability tree.
     * @param parent parent of the marking. It must be null for you to find the reachability tree.
     * @param graph graph for which you want to find the reachability tree.
     * @param dr result matrix=dq-di=matrix of outputs-matrix of inputs for transitions
     */
    public Marking(final Integer[] chips, final Matrix dr, final Matrix Di, ArrayList<Transition> transitions) {
        this(chips, TYPE.Root, null, dr, Di, transitions, 0);
    }

    private Marking(final Integer[] chips, final TYPE type, final Marking parent, final Matrix dr, final Matrix Di, ArrayList<Transition> transitions, int yarus) {
        this.chips = Arrays.copyOf(chips, chips.length);
        this.type = type;
        this.parent = parent;
        this.Dr = dr;
        this.Di = Di;
        this.yarus = yarus;
        this.transitions = transitions;
    }

    /**
     * Builds next marking according to matrix equations.
     * @param startChips
     * @param vecOfAllow
     * @return next marking.
     */
    public Marking buildNextMarking(Integer[] startChips, Integer[] vecOfAllow, int yarus, String allowedTrans) {
        AllowVector allowedM = new AllowVector(vecOfAllow);
        AllowVector temp = allowedM.multiply(Dr);
        AllowVector temp1 = new AllowVector(startChips);
        AllowVector nextChips = temp.add(temp1);
        Marking m = new Marking(nextChips.getVector(), TYPE.Instant, this, Dr, Di, transitions, yarus);
        m.firedWithTrans.append(allowedTrans);
        if (!m.isInstant()) {
            m.setType(TYPE.Real);
        }
        return m;
    }

    public static Marking buildNextMarking(Integer[] startChips, Integer[] vecOfAllow, Matrix DR, Matrix DI) {
        AllowVector allowedM = new AllowVector(vecOfAllow);
        AllowVector temp = allowedM.multiply(DR);
        AllowVector temp1 = new AllowVector(startChips);
        AllowVector nextChips = temp.add(temp1);
        Marking m = new Marking(nextChips.getVector(), TYPE.Instant, null, DR, DI, null, 0);
        return m;
    }

    public ArrayList<Transition> getAllowedTransitions(Integer[] chips) {
        ArrayList<Transition> notAllowed = new ArrayList<Transition>();
        ArrayList<Transition> allowed = new ArrayList<Transition>();
        for (int i = 0; i < transitions.size(); i++) {
            if (!transitions.get(i).getInputLinks().isEmpty()) {
                allowed.add(transitions.get(i));
            }
        }
        for (int i = 0; i < Di.getRowDimension(); i++) {
            for (int j = 0; j < Di.getColumnDimension(); j++) {
                int cur = Di.getMatrix()[i][j];
                if (cur > 0) {
                    if (cur > chips[j]) {
                        notAllowed.add(transitions.get(i));
                        break;
                    }
                }
            }
        }
        allowed.removeAll(notAllowed);
        return allowed;
    }

    /**
     * Finds out whether contains this marking in previous markings or not.
     * This method compares markings by chips.
     * Do not use method previousMarkings.contains(this).
     * @param previousMarkings the ArrayList of previous markings.
     * @return <tt>true</tt> if this marking contains in previous markings.
     */
    private boolean containsInPrevious(ArrayList<Marking> previousMarkings) {
        boolean contains = false;
        for (int i = 0; (i < previousMarkings.size()) && !contains; i++) {
            String previous = previousMarkings.get(i).toString();
            if (previous.equals(this.toString())) {
                contains = true;
            }
        }
        return contains;
    }

    private int getSumOfChips() {
        int sum = 0;
        for (int i = 0; i < chips.length; i++) {
            if (chips[i] != Integer.MAX_VALUE) {
                sum += chips[i];
            }
        }
        return sum;
    }

    private boolean hasLessChips(Marking m) {
        boolean hasLess = false;
        for (int i = 0; (i < chips.length) && !hasLess; i++) {
            if (this.chips[i] < m.chips[i]) {
                hasLess = true;
            }
        }
        return hasLess;
    }

    private void getAllParents(ArrayList<Marking> previous) {
        if (this.parent != null) {
            previous.add(this.parent);
            this.parent.getAllParents(previous);
        }
    }

    private void checkCovering() {
        boolean covering = false;
        ArrayList<Marking> parents = new ArrayList<Marking>();
        this.getAllParents(parents);
        int pos = -1;
        for (int i = 0; (i < parents.size()) && !covering; i++) {
            if (parents.get(i).getSumOfChips() < this.getSumOfChips() && !this.hasLessChips(parents.get(i))) {
                covering = true;
                pos = i;
            }
        }
        if (covering) {
            Marking p = parents.get(pos);
            for (int i = 0; i < chips.length; i++) {
                if (this.chips[i] > p.chips[i]) {
                    this.chips[i] = Integer.MAX_VALUE;
                }

            }
        }
    }

    /**
     * Counts number of chips in the parent Tree and this,
     * compares them and finds out whether it's covering marking or not.
     * @return <tt>true</tt> if this marking is covering.
     */
    private boolean isCovering() {
        if (parent != null) {
            boolean notAllMax = false;
            for (int i = 0; (i < this.chips.length) && !notAllMax; i++) {
                if (this.chips[i] != Integer.MAX_VALUE) {
                    notAllMax = true;
                }
            }
            if (notAllMax) {
                return false;
            } else {
                this.setType(TYPE.Covering);
                return true;
            }
        } else {
            return false;
        }

    }

    /**
     * Finds the original marking for this duplicate.
     * It's used for building Markov chain.
     * @param previous all previous markings.
     * @return original of the duplicate.
     */
    private Marking findOriginal(ArrayList<Marking> previous) {
        boolean found = false;
        Marking m = null;
        for (int i = 0; (i < previous.size()) & !found; i++) {
            if (Arrays.equals(getChips(), previous.get(i).getChips())
                    && (!previous.get(i).getType().equals(TYPE.InstantDup)
                    && !previous.get(i).getType().equals(TYPE.RealDup))) {
                found = true;
                m = previous.get(i);
            }
        }
        if (m == null) {
            return this;
        } else {
            return m;
        }
    }

    private ArrayList<Marking> getTempChildren(ArrayList<Transition> allowedTransitions) {
        ArrayList<Marking> tempChildren = new ArrayList<Marking>();
        for (int i = 0; i < allowedTransitions.size(); i++) {
            tempChildren.add(buildNextMarking(chips, buildVecOfAllow(allowedTransitions.get(i), transitions.size()),
                    this.yarus + 1, allowedTransitions.get(i).getName()));
        }
        return tempChildren;
    }

    /**
     * According to the type tree can be built with instant markings or without.
     * Type must be "Real" if you want to see only real markings or any other 
     * string if you want to see instant markings as well.
     * @param TreeType type of tree.
     * @param previousMarkings empty arraylist.
     */
    void getTree(String TreeType, ArrayList<Marking> previousMarkings) {
        if (this.type.equals(TYPE.Root)) {
            if (this.isInstant()) {
                this.type = TYPE.InstantRoot;
            }
        }
        if (!containsInPrevious(previousMarkings) && !isCovering()) {
            previousMarkings.add(this);
            ArrayList<Transition> allowedTransitions = getAllowedTransitions(chips);
            removeTimedTransitions(allowedTransitions);
            ArrayList<Marking> tempChildren = getTempChildren(allowedTransitions);
            if (tempChildren.isEmpty()) {
                this.setType(TYPE.Terminal);
            } else {
                if (TreeType.equals(REAL)) {
                    makeRealTree(tempChildren);
                } else {
                    makeInstantTree(tempChildren);
                }
                makeGrandChildren(TreeType, previousMarkings, allowedTransitions);
            }
        } else {
            if (getType().equals(TYPE.Instant)) {
                setType(TYPE.InstantDup);
            } else if (getType().equals(TYPE.Real)) {
                setType(TYPE.RealDup);
            }
        }
    }

    private void makeRealTree(ArrayList<Marking> tempChildren) {
        for (int i = 0; i < tempChildren.size(); i++) {
            Marking child = tempChildren.get(i);
            if (!child.isInstant()) {
                this.addChild(child);
                child.setParent(this);
                child.checkCovering();
            } else {
                ArrayList<Marking> realMarkings = new ArrayList<Marking>();
                if (!Graph.allInstant(this.transitions)) {
                    child.getAllRealMarkings(realMarkings, this.yarus + 1, new ArrayList<Marking>());
                }
                for (int j = 0; j < realMarkings.size(); j++) {
                    this.addChild(realMarkings.get(j));
                    realMarkings.get(j).setParent(this);
                    realMarkings.get(j).checkCovering();
                }
            }
        }
    }

    private void makeInstantTree(ArrayList<Marking> tempChildren) {
        for (int i = 0; i < tempChildren.size(); i++) {
            this.addChild(tempChildren.get(i));
            tempChildren.get(i).setParent(this);
            tempChildren.get(i).checkCovering();
        }
    }

    private void duplicateChildMethod(String TreeType, ArrayList<Marking> previousMarkings, ArrayList<Transition> allowedTransitions, int index) {
        if (children.get(index).getType().equals(TYPE.Real)) {
            children.get(index).setType(TYPE.RealDup);
        }
        if (TreeType.equals(REAL)) {
            treeTransitions.add(new MarkovLink(children.get(index).firedWithTrans.toString(),
                    this, children.get(index)));
            markovTransitions.add(new MarkovLink(
                    children.get(index).firedWithTrans.toString(),
                    this, children.get(index).findOriginal(previousMarkings)));

        } else {
            if (children.get(index).getType().equals(TYPE.Instant)) {
                children.get(index).setType(TYPE.InstantDup);
            }
            treeTransitions.add(new MarkovLink(allowedTransitions.get(index).getName(),
                    this, children.get(index)));
            markovTransitions.add(new MarkovLink(allowedTransitions.get(index).getName(),
                    this, children.get(index).findOriginal(previousMarkings)));
        }
    }

    private void uniqueChildMethod(String TreeType, ArrayList<Marking> previousMarkings, ArrayList<Transition> allowedTransitions, int index) {
        if (TreeType.equals(REAL)) {
            treeTransitions.add(new MarkovLink(children.get(index).firedWithTrans.toString(),
                    this, children.get(index)));
            markovTransitions.add(new MarkovLink(children.get(index).firedWithTrans.toString(), this, children.get(index)));
        } else {
            MarkovLink markovLink = new MarkovLink(allowedTransitions.get(index).getName(),
                    this, children.get(index));
            treeTransitions.add(markovLink);
            markovTransitions.add(markovLink);
        }
        if (!children.get(index).isCovering()) {
            children.get(index).getTree(TreeType, previousMarkings);
        } else {
            previousMarkings.add(children.get(index));
        }
    }

    private void makeGrandChildren(String TreeType, ArrayList<Marking> previousMarkings, ArrayList<Transition> allowedTransitions) {
        for (int j = 0; j < children.size(); j++) {
            if (children.get(j).containsInPrevious(previousMarkings)) {
                duplicateChildMethod(TreeType, previousMarkings, allowedTransitions, j);
            } else {
                uniqueChildMethod(TreeType, previousMarkings, allowedTransitions, j);

            }
        }
    }

    public static void removeTimedTransitions(ArrayList<Transition> allowedTrans) {
        // Есть ли в разрешенных переходах мгновенные?
        if (Graph.containsInstantTransitions(allowedTrans)) {
            // Есть.удаляем все временные.На этмо этапе могут произойти только мгновеннные.
            ArrayList<Transition> listToRemove = new ArrayList<Transition>();
            for (int i = 0; i < allowedTrans.size(); i++) {
                if (allowedTrans.get(i).isReal()) {
                    listToRemove.add(allowedTrans.get(i));
                }
            }
            if (!listToRemove.isEmpty()) {
                allowedTrans.removeAll(listToRemove);
            }
            // В массиве разрешенных переходов остались только мгновенные переходы.
        }
    }

    private void getAllRealMarkings(ArrayList<Marking> realMarkings, int yarus, ArrayList<Marking> instPrev) {
        if (!containsInPrevious(instPrev)) {
            ArrayList<Marking> tempMarkings = new ArrayList<Marking>();
            instPrev.add(this);
            ArrayList<Transition> allowedTransitions = getAllowedTransitions(chips);
            removeTimedTransitions(allowedTransitions);
            for (int i = 0; i < allowedTransitions.size(); i++) {
                tempMarkings.add(buildNextMarking(chips, buildVecOfAllow(allowedTransitions.get(i), transitions.size()), yarus, this.firedWithTrans.toString()));
                tempMarkings.get(i).firedWithTrans.append(allowedTransitions.get(i).getName());
            }
            for (int i = 0; i < tempMarkings.size(); i++) {
                if (!tempMarkings.get(i).isInstant()) {
                    realMarkings.add(tempMarkings.get(i));
                    // realMarkings.get(i).firedWithTrans = tempMarkings.get(i).firedWithTrans;
                } else {
                    tempMarkings.get(i).getAllRealMarkings(realMarkings, yarus, instPrev);
                }
            }
        }
    }

    /**
     * Checks whether this is instantaneous marking or not.
     * @return true if this is instantaneous marking.
     */
    public boolean isInstant() {
        boolean instant = false;
        ArrayList<Transition> allowedTransitions = getAllowedTransitions(chips);
        for (int i = 0; (i < allowedTransitions.size()); i++) {
            if (!allowedTransitions.get(i).isReal()) {
                instant = true;
                break;
            }
        }
        return instant;
    }

    /**
     * Builds vector of allow for input transition.
     * @param allowedT allowed transition.
     * @return vector of allow for input transition.
     */
    public static Integer[] buildVecOfAllow(Transition allowedT, int size) {
        Integer[] vecOfAllow = new Integer[size];
        for (int i = 0; i < vecOfAllow.length; i++) {
            vecOfAllow[i] = 0;
        }
        int indexOfAllowed = allowedT.getNumber();
        vecOfAllow[indexOfAllowed] = 1;
        return vecOfAllow;
    }

    public void writeToFile(File file) throws FileNotFoundException, IOException {
        OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file), Charset.defaultCharset());
        try {
            out.write(this.getCSV());
        } finally {
            out.close();
        }
    }

    public String getTransString() {
        return this.firedWithTrans.toString();
    }

    /**
     * Returns transitions for Markov chain.
     * @return the transNames
     */
    public ArrayList<MarkovLink> getTransForMarkov() {
        return markovTransitions;
    }

    /**
     * Returns transitions for reachability tree.
     * @return the transForTree
     */
    public ArrayList<MarkovLink> getTransForTree() {
        return treeTransitions;
    }

    public String getCSV() {
        StringBuilder begin = new StringBuilder("Reachability tree for Petri nets\r\n" + this.toStringWithType() + "\r\n");
        begin.append("Stage|Begin marking|Allowed transitions|Finish marking\r\n");
        java.util.Vector queue = new java.util.Vector();
        queue.addElement(this);
        while (!queue.isEmpty()) {
            Marking m = (Marking) queue.firstElement();
            m.appendToString(begin, m.yarus);
            queue.removeElement(m);
            for (int i = 0; i < m.children.size(); i++) {
                queue.addElement(m.children.get(i));
            }
        }
        return begin.toString();
    }

    private void appendToString(StringBuilder all, int N) {
        for (int i = 0; i < children.size(); i++) {
            all.append(N).append("|").append(this.toStringWithType()).append("|");
            all.append(children.get(i).firedWithTrans).append("|");
            all.append(children.get(i).toStringWithType()).append("\r\n");
        }
    }

    public String toString() {
        StringBuilder s = new StringBuilder("[");
        for (int i = 0; i < chips.length; i++) {
            if (chips[i] == Integer.MAX_VALUE) {
                s.append("w");
            } else {
                s.append(Integer.toString(chips[i]));
            }
            if (i != chips.length - 1) {
                s.append(", ");
            }
        }
        s.append("]");
        return s.toString();
    }

    /**
     * Returns String representation of tree or Markov graph.
     * @param type "T" for tree, "M" for Markov
     * @return 
     */
    public String toStringForTreeOrMarkov(String type) {
        StringBuilder s = new StringBuilder(this.toString());
        if (!children.isEmpty()) {
            s.append("<p>Children:<p>");
            for (int i = 0; i < children.size(); i++) {
                if (type.equals("M")) {
                    s.append(markovTransitions.get(i).getName()).append(" [");
                } else if (type.equals("T")) {
                    s.append(treeTransitions.get(i).getName()).append(" [");
                }
                s.append(children.get(i).toString());
                s.append("]");
                s.append("<p>");
            }
        }
        return s.toString();
    }

    public String toStringWithType() {
        StringBuilder s = new StringBuilder(this.toString());
        s.append(getType());
        return s.toString();
    }

    /**
     * Add child to this marking.
     * @param child child to add.
     */
    private void addChild(Marking child) {
        children.add(child);
    }

    /**
     * @return the chips
     */
    public Integer[] getChips() {
        return chips;
    }

    public void setChips(Integer[] chips) {
        this.chips = chips;
    }

    /**
     * @return the children
     */
    public ArrayList<Marking> getChildren() {
        return children;
    }

    /**
     * @return the type
     */
    public TYPE getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    private void setType(TYPE type) {
        this.type = type;
    }

    /**
     * @param parent the parent to set
     */
    private void setParent(Marking parent) {
        this.parent = parent;
    }
}
